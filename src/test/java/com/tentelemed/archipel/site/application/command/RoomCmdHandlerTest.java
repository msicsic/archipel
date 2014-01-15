package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.command.CmdRes;
import com.tentelemed.archipel.core.application.service.CmdHandlerTest;
import com.tentelemed.archipel.core.domain.model.DomainException;
import com.tentelemed.archipel.site.domain.model.Room;
import com.tentelemed.archipel.site.domain.pub.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/01/14
 * Time: 14:02
 */
public class RoomCmdHandlerTest extends CmdHandlerTest {
    SiteCmdHandler handler;
    RoomCmdHandler roomHandler;

    @Before
    public void setup() {
        super.setup();
        handler = context.getBean(SiteCmdHandler.class);
        roomHandler = context.getBean(RoomCmdHandler.class);
    }

    @Test
    public void executeCmdRoomCreate() {
        // Given (Site créé)
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        handler.execute(new CmdSiteCreateSector(new SiteId(0), SectorType.TECH, "SectorTech", "ABC"));
        handler.execute(new CmdSiteCreateService(new SiteId(0), "ABC", "SV1", "ServiceName"));
        CmdRes res = handler.execute(new CmdSiteCreateFunctionalUnit(new SiteId(0), "SV1", "FU1", "FUName"));
        SiteId siteId = (SiteId) res.entityId;

        // When
        res = roomHandler.execute(new CmdRoomCreate(siteId, "RoomName", false, new LocationPath("SEC:ABC|SRV:SV1|FU:FU1")));

        // Then (evt levé)
        assertThat(res.getEvent(EvtRoomRegistered.class), notNullValue());

        // Then (Room créé)
        Room room = (Room) eventStore.get((RoomId) res.entityId);
        assertThat(room, notNullValue());
        assertThat(room.getName(), equalTo("RoomName"));
        assertThat(room.getLocationPath(), equalTo(new LocationPath("SEC:ABC|SRV:SV1|FU:FU1")));
        assertThat(room.getSiteId(), equalTo(siteId));

        // Then (RoomQ créé)
        RoomQ roomQ = pHandler.find(RoomQ.class, room.getEntityId().getId());
        assertThat(roomQ, notNullValue());
        assertThat(roomQ.getName(), equalTo(room.getName()));
        assertThat(roomQ.getLocationPath(), equalTo(room.getLocationPath().toString()));
        assertThat(roomQ.getSiteId(), notNullValue());
    }

    @Test(expected = DomainException.class)
    public void executeCmdRoomCreateBadLocation() {
        // Given (Site créé)
        CmdRes res = handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        SiteId siteId = (SiteId) res.entityId;

        // When
        roomHandler.execute(new CmdRoomCreate(siteId, "RoomName", false, new LocationPath("SEC:ABC|SRV:SV1|FU:FU4")));

        // Then (exception)
    }

    @Test(expected = DomainException.class)
    public void executeCmdRoomCreateBadMedical() {
        // Given (Site créé)
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        CmdRes res = handler.execute(new CmdSiteCreateSector(new SiteId(0), SectorType.TECH, "SectorTech", "ABC"));
        SiteId siteId = (SiteId) res.entityId;

        // When
        roomHandler.execute(new CmdRoomCreate(siteId, "RoomName", true, new LocationPath("SEC:ABC")));

        // Then (exception)
    }

    @Test
    public void executeCmdRoomAddBed() {
        // Given (site créé avec une Room)
        CmdRes res1 = handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        SiteId siteId = (SiteId) res1.entityId;
        CmdRes res2 = roomHandler.execute(new CmdRoomCreate(siteId, "RoomName", true, new LocationPath("SEC:MED")));
        RoomId roomId = (RoomId) res2.entityId;

        // When
        CmdRes res = roomHandler.execute(new CmdRoomAddBed(roomId, new Bed("Lit1")));

        // Then (evt levé)
        assertThat(res.getEvent(EvtRoomBedAdded.class), notNullValue());

        // Then (Room modifié)
        Room room = (Room) eventStore.get(roomId);
        assertThat(room.getBeds().contains(new Bed("Lit1")), equalTo(true));

        // Then (RoomQ modifié)
        RoomQ roomQ = pHandler.find(RoomQ.class, room.getEntityId().getId());
        assertThat(roomQ, notNullValue());
        assertThat(roomQ.getBeds().contains(new Bed("Lit1")), equalTo(true));
    }

    @Test
    public void executeCmdRoomRemoveBed() {
        // Given (site créé avec une Room)
        CmdRes res1 = handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        SiteId siteId = (SiteId) res1.entityId;
        CmdRes res2 = roomHandler.execute(new CmdRoomCreate(siteId, "RoomName", true, new LocationPath("SEC:MED")));
        RoomId roomId = (RoomId) res2.entityId;
        CmdRes res3 = roomHandler.execute(new CmdRoomAddBed(roomId, new Bed("Lit1")));

        // When
        CmdRes res = roomHandler.execute(new CmdRoomRemoveBed(roomId, new Bed("Lit1")));

        // Then (evt levé)
        assertThat(res.getEvent(EvtRoomBedRemoved.class), notNullValue());

        // Then (Room modifié)
        Room room = (Room) eventStore.get(roomId);
        assertThat(room.getBeds().contains(new Bed("Lit1")), equalTo(false));

        // Then (RoomQ modifié)
        RoomQ roomQ = pHandler.find(RoomQ.class, room.getEntityId().getId());
        assertThat(roomQ, notNullValue());
        assertThat(roomQ.getBeds().contains(new Bed("Lit1")), equalTo(false));
    }


}
