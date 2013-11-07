package com.tentelemed.archipel.core.infrastructure.repo;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: msi
 * Date: 6 avr. 2007
 * Time: 12:23:06
 */
public class IdGenerator extends SequenceStyleGenerator {
    private static final Logger log = LoggerFactory.getLogger(IdGenerator.class);

    private static int nextTmpId = 0;
    private static final String tmpKey = "TMP";
    private static String key = null;
    private static final ThreadLocal<String> reservations = new ThreadLocal<String>();

    private static IdGenerator instance;
    private static boolean configured = false;

    public IdGenerator() {
        if (instance == null) {
            log.info("OidGenerator instancié");
            instance = this;
        }
    }

    public static synchronized void makeReservation(String idToUse) {
        reservations.set(idToUse);
    }

    public static synchronized void clearReservation() {
        reservations.remove();
    }

    public static void setKey(String key) {
        IdGenerator.key = key;
    }

    public void configure(Type type, Properties properties, Dialect dialect) {
        if (! configured) {
            configured = true;
            instance.__superConfigure(new IntegerType(), properties, dialect);
        }
        super.configure(new IntegerType(), properties, dialect);
    }

    public Serializable generate(SessionImplementor session, Object object) {
        String id;

        id = reservations.get();
        clearReservation();

        if (id == null) {
            Object res = instance.__superGenerate(session, object);
            id = getKey() + "/" + res;
        }
        return id;
    }

    protected void __superConfigure(Type type, Properties properties, Dialect dialect) {
        super.configure(new IntegerType(), properties, dialect);
    }

    protected Serializable __superGenerate(SessionImplementor session, Object object) {
        return super.generate(session, object);
    }

    private String getKey() {
        return key == null ? "S1" : key;
    }

    public synchronized static String createTmpId() {
        return tmpKey + "/" + nextTmpId ++;
    }
}
