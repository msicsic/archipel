package com.tentelemed.archipel.core.application;

import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.TimestampTypeDescriptor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class UtcTimestampTypeDescriptor extends TimestampTypeDescriptor {
    public static final UtcTimestampTypeDescriptor INSTANCE = new UtcTimestampTypeDescriptor();

    public static String srcTz = "UTC";
    private final static String dstTz = "UTC";

    public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicBinder<X>( javaTypeDescriptor, this ) {
            @Override
            protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options) throws SQLException {
                Date converted = convert((Date) value);
                st.setTimestamp( index, javaTypeDescriptor.unwrap((X) converted, Timestamp.class, options ));
            }
        };
    }

    public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicExtractor<X>( javaTypeDescriptor, this ) {
            @Override
            protected X doExtract(ResultSet rs, String name, WrapperOptions options) throws SQLException {
                return javaTypeDescriptor.wrap( unconvert(rs.getTimestamp( name )), options );
            }
        };
    }

    public static Date convert(Date sdate) {
        return _convert(sdate, srcTz, dstTz);
    }

    public static Date unconvert(Date sdate) {
        return _convert(sdate, dstTz, srcTz);
    }

    private static Date _convert(Date sdate, String srcTz, String dstTz) {
        LocalDateTime d1 = new LocalDateTime(sdate);
        DateTime srcDateTime = d1.toDateTime(DateTimeZone.forID(srcTz));
        DateTime dstDateTime = srcDateTime.withZone(DateTimeZone.forID(dstTz));
        Date res = dstDateTime.toLocalDateTime().toDateTime().toDate();
        return res;
    }
}