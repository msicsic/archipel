package com.tentelemed.archipel.core.infrastructure.config;

import com.tentelemed.archipel.core.application.command.CmdRes;
import com.tentelemed.archipel.core.domain.model.Country;
import com.tentelemed.archipel.security.application.command.CmdRoleCreate;
import com.tentelemed.archipel.security.application.command.CmdUserCreate;
import com.tentelemed.archipel.security.application.command.RoleCmdHandler;
import com.tentelemed.archipel.security.application.command.UserCmdHandler;
import com.tentelemed.archipel.security.domain.pub.Right;
import com.tentelemed.archipel.security.domain.pub.RoleId;
import com.tentelemed.archipel.security.infrastructure.shiro.MyRealm;
import com.tentelemed.archipel.site.application.command.*;
import com.tentelemed.archipel.site.domain.pub.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.realm.text.PropertiesRealm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DelegatingSubject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

    @Autowired RoleCmdHandler roleHandler;
    @Autowired UserCmdHandler userHandler;
    @Autowired SiteCmdHandler siteHandler;
    @Autowired RoomCmdHandler roomHandler;

    @Autowired ApplicationContext context;

    @SuppressWarnings("SpringJavaAutowiringInspection") @Autowired
    JdbcTemplate jdbcTemplate;

    @PersistenceContext
    EntityManager em;

    public void initDb() {
        try (
                Connection conn = jdbcTemplate.getDataSource().getConnection()
        ) {
            // ici il faut positionner manuellement le securityManager, car nous ne sommes pas
            // dans le contexte d'une requete http, le ShiroFilter n'a donc pas positionné
            // le securityManager.
            // On crée donc un secManager donnant tous les droits à un compte "root"
            Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
            org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
            SecurityUtils.setSecurityManager(securityManager);
            UsernamePasswordToken token = new UsernamePasswordToken("root", "secret");
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);

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
                RoleId role1 = (RoleId) roleHandler.execute(new CmdRoleCreate("administrateur", Right.GLOBAL_ADMIN)).entityId;
                RoleId role2 = (RoleId) roleHandler.execute(new CmdRoleCreate("user", Right.SITES_ADMIN)).entityId;
                for (int i = 0; i < 100; i++) {
                    userHandler.execute(new CmdUserCreate(i % 2 == 0 ? role1 : role2, "Paul" + i, "Durand" + i, new Date(), "mail" + i + "@mail.com", "login" + i));
                }

                createSite();
            }

            System.err.println("hop");
        } catch (Exception e) {
            log.error(null, e);
        }

    }

    @Transactional
    public void createCountries() {
        try (
                InputStream inp = Thread.currentThread().getContextClassLoader().getResourceAsStream("01_Country_Code_Table.xls");
        ) {
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
        try (
                InputStream inp = Thread.currentThread().getContextClassLoader().getResourceAsStream("02_Bank_Table.xls");
        ) {
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

    public void createSite() {
        CmdSiteCreate cmd = new CmdSiteCreate(SiteType.CHD, "Site 1", "CH1");
        CmdRes res = siteHandler.execute(cmd);
        SiteId siteId = (SiteId) res.entityId;

        CmdSiteUpdateAdditionalInfo cmd2 = new CmdSiteUpdateAdditionalInfo();
        cmd2.fax = "01 55 20 08 00";
        cmd2.phone = "01 55 20 08 01";
        cmd2.privateRoomAvailable = true;
        cmd2.emergenciesAvailable = true;
        cmd2.drugstoreAvailable = true;
        cmd2.bankCode = "BNP";
        cmd2.countryIso = "FRA";
        cmd2.postalCode = "92100";
        cmd2.directorName = "Durant";
        cmd2.siret = "FR65412";
        cmd2.street = "102 Edouard Vaillant";
        cmd2.town = "Boulogne-Billancourt";
        cmd2.id = siteId;
        siteHandler.execute(cmd2);

        CmdSiteCreateService cmd3 = new CmdSiteCreateService(siteId, "MED", "CNS", "Consultations");
        siteHandler.execute(cmd3);
        CmdSiteCreateFunctionalUnit cmd4 = new CmdSiteCreateFunctionalUnit(siteId, "CNS", "GEN", "Generaliste");
        siteHandler.execute(cmd4);

        createRooms(siteId);
    }

    public void createRooms(SiteId siteId) {
        // creation d'une Room
        CmdRoomCreate cmd = new CmdRoomCreate(siteId, "Accueil", false, new LocationPath("SEC:MED"));
        roomHandler.execute(cmd);

        // creation d'une Room
        cmd = new CmdRoomCreate(siteId, "Consultations", true, new LocationPath("SEC:MED|SRV:CNS|FU:GEN"));
        roomHandler.execute(cmd);

        // creation d'une Room
        cmd = new CmdRoomCreate(siteId, "Hospitalisations", true, new LocationPath("SEC:MED"));
        CmdRes res = roomHandler.execute(cmd);
        RoomId roomId = (RoomId) res.entityId;

        // ajout de 3 lits à cette Room
        roomHandler.execute(new CmdRoomAddBed(roomId, new Bed("Lit 1")));
        roomHandler.execute(new CmdRoomAddBed(roomId, new Bed("Lit 2")));
        roomHandler.execute(new CmdRoomAddBed(roomId, new Bed("Lit 3")));
        roomHandler.execute(new CmdRoomAddBed(roomId, new Bed("Lit 4")));
    }
}
