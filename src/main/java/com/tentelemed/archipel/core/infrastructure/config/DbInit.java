package com.tentelemed.archipel.core.infrastructure.config;

import com.tentelemed.archipel.core.domain.model.Country;
import com.tentelemed.archipel.site.domain.model.Bank;
import com.tentelemed.archipel.security.application.command.CmdCreateRole;
import com.tentelemed.archipel.security.application.command.CmdCreateUser;
import com.tentelemed.archipel.security.application.service.UserCommandService;
import com.tentelemed.archipel.security.domain.model.Right;
import com.tentelemed.archipel.security.domain.model.RoleId;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.InputStream;
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
@Transactional
public class DbInit {
    private final static Logger log = LoggerFactory.getLogger(DbInit.class);

    @Autowired
    UserCommandService service;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PersistenceContext
    EntityManager em;

    public void initDb() {
        try {
            Connection conn = jdbcTemplate.getDataSource().getConnection();
            ResultSet tables = conn.getMetaData().getTables(conn.getCatalog(), null, "T_EVENTS", null);
            boolean created = tables.next();
            tables.close();
            if (!created) {

                createCountries();
                createBanks();

                jdbcTemplate.update(
                        "create table T_EVENTS (c_aggregate_id INTEGER NOT NULL, c_data TEXT NOT NULL, c_version INTEGER NOT NULL, INDEX idx_aggregate_id (c_aggregate_id), INDEX idx_version (c_version))"
                );
                jdbcTemplate.update(
                        "create table T_AGGREGATE (c_aggregate_id INTEGER NOT NULL, c_type VARCHAR(128) NOT NULL, c_version INTEGER NOT NULL, PRIMARY KEY(c_aggregate_id), INDEX idx_version (c_version))"
                );
                RoleId role1 = service.execute(new CmdCreateRole("administrateur", Right.RIGHT_A));
                RoleId role2 = service.execute(new CmdCreateRole("user", Right.RIGHT_B));
                for (int i = 0; i < 100; i++) {
                    service.execute(new CmdCreateUser(i%2==0?role1:role2, "Paul" + i, "Durand" + i, new Date(), "mail" + i + "@mail.com", "login" + i));
                }
            }

            System.err.println("hop");
        } catch (Exception e) {
            log.error(null, e);
        }

    }

    @Transactional
    public void createCountries() {
        try {
            InputStream inp = getClass().getClassLoader().getResourceAsStream("01_Country_Code_Table.xls");
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            for (Row row : sheet) {
                String iso = row.getCell(0).getStringCellValue().trim();
                String name = row.getCell(1).getStringCellValue().trim();
                Country country = new Country(iso, name);
                try {
                    em.persist(country);
                    em.flush();
                } catch (Exception e) {
                    log.error("Cant save Country : " + iso);
                }
            }

        } catch (Exception e) {
            log.error(null, e);
        }
    }

    @Transactional
    public void createBanks() {
        try {
            InputStream inp = getClass().getClassLoader().getResourceAsStream("02_Bank_Table.xls");
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            for (Row row : sheet) {
                String code = row.getCell(0).getStringCellValue().trim();
                String name = row.getCell(1).getStringCellValue().trim();
                Bank bank = new Bank(code, name);
                try {
                    em.persist(bank);
                    em.flush();
                } catch (Exception e) {
                    log.error("Cant save Bank : " + code);
                }
            }

        } catch (Exception e) {
            log.error(null, e);
        }
    }


}
