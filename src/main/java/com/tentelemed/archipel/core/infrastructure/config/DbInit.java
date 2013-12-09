package com.tentelemed.archipel.core.infrastructure.config;

import com.tentelemed.archipel.security.application.service.UserCommandService;
import com.tentelemed.archipel.security.domain.model.Right;
import com.tentelemed.archipel.security.domain.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 31/10/13
 * Time: 15:30
 */
@Component
public class DbInit {
    private final static Logger log = LoggerFactory.getLogger(DbInit.class);

    @Autowired
    UserCommandService service;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void initDb() {
        try {
            Connection conn = jdbcTemplate.getDataSource().getConnection();
            ResultSet tables = conn.getMetaData().getTables(conn.getCatalog(), null, "T_EVENTS", null);
            boolean created = tables.next();
            tables.close();

            if (! created) {
                jdbcTemplate.update(
                        "create table T_EVENTS (c_aggregate_id INTEGER NOT NULL, c_data TEXT NOT NULL, c_version INTEGER NOT NULL, INDEX idx_aggregate_id (c_aggregate_id), INDEX idx_version (c_version))"
                );
                jdbcTemplate.update(
                        "create table T_AGGREGATE (c_aggregate_id INTEGER NOT NULL, c_type VARCHAR(128) NOT NULL, c_version INTEGER NOT NULL, PRIMARY KEY(c_aggregate_id), INDEX idx_version (c_version))"
                );
                Role role = service.registerRole("administrateur", Right.RIGHT_A);
                for (int i = 0; i < 100; i++) {
                    service.registerUser(role.getEntityId(), "Paul" + i, "Durand" + i, new Date(), "mail" + i + "@mail.com", "login" + i);
                }
            }

            System.err.println("hop");
        } catch (Exception e) {
            log.error(null, e);
        }

    }

}
