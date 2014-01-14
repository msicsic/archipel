package com.tentelemed.archipel.site.application.command;

import com.tentelemed.archipel.core.application.service.CmdHandlerTest;
import com.tentelemed.archipel.core.application.command.CmdRes;
import com.tentelemed.archipel.core.domain.model.DomainException;
import com.tentelemed.archipel.site.domain.pub.EvtRoomBedAdded;
import com.tentelemed.archipel.site.domain.pub.EvtRoomBedRemoved;
import com.tentelemed.archipel.site.domain.pub.EvtRoomRegistered;
import com.tentelemed.archipel.site.domain.model.*;
import com.tentelemed.archipel.site.domain.pub.Bed;
import com.tentelemed.archipel.site.domain.pub.LocationPath;
import com.tentelemed.archipel.site.domain.pub.SiteId;
import com.tentelemed.archipel.site.domain.pub.SiteType;
import com.tentelemed.archipel.site.infrastructure.model.RoomQ;
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
        handler.execute(new CmdSiteCreateSector(new SiteId(0), Sector.Type.TECH, "SectorTech", "ABC"));
        handler.execute(new CmdSiteCreateService(new SiteId(0), "ABC", "SV1", "ServiceName"));
        CmdRes res = handler.execute(new CmdSiteCreateFunctionalUnit(new SiteId(0), "SV1", "FU1", "FUName"));
        Site site = (Site) res.aggregate;

        // When
        res = roomHandler.execute(new CmdRoomCreate(site.getEntityId(), "RoomName", false, new LocationPath("SEC:ABC|SRV:SV1|FU:FU1")));

        // Then (evt levé)
        assertThat(res.getEvent(EvtRoomRegistered.class), notNullValue());

        // Then (Room créé)
        Room room = (Room) eventStore.get(((Room) res.aggregate).getEntityId());
        assertThat(room, notNullValue());
        assertThat(room.getName(), equalTo("RoomName"));
        assertThat(room.getLocationPath(), equalTo(new LocationPath("SEC:ABC|SRV:SV1|FU:FU1")));
        assertThat(room.getSiteId(), equalTo(site.getEntityId()));

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
        Site site = (Site) res.aggregate;

        // When
        roomHandler.execute(new CmdRoomCreate(site.getEntityId(), "RoomName", false, new LocationPath("SEC:ABC|SRV:SV1|FU:FU4")));

        // Then (exception)
    }

    @Test(expected = DomainException.class)
    public void executeCmdRoomCreateBadMedical() {
        // Given (Site créé)
        handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        CmdRes res = handler.execute(new CmdSiteCreateSector(new SiteId(0), Sector.Type.TECH, "SectorTech", "ABC"));
        Site site = (Site) res.aggregate;

        // When
        roomHandler.execute(new CmdRoomCreate(site.getEntityId(), "RoomName", true, new LocationPath("SEC:ABC")));

        // Then (exception)
    }

    @Test
    public void executeCmdRoomAddBed() {
        // Given (site créé avec une Room)
        CmdRes res1 = handler.execute(new CmdSiteCreate(SiteType.CHU, "SiteName", "AAAA"));
        Site site = (Site) res1.aggregate;
        CmdRes res2 = roomHandler.execute(new CmdRoomCreate(site.getEntityId(), "RoomName", true, new LocationPath("SEC:MED")));
        Room room = (Room) res2.aggregate;

        // When
        CmdRes res = roomHandler.execute(new CmdRoomAddBed(room.getEntityId(), new Bed("Lit1")));

        // Then (evt levé)
        assertThat(res.getEvent(EvtRoomBedAdded.class), notNullValue());

        // Then (Room modifié)
        room = (Room) eventStore.get(room.getEntityId());
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
        Site site = (Site) res1.aggregate;
        CmdRes res2 = roomHandler.execute(new CmdRoomCreate(site.getEntityId(), "RoomName", true, new LocationPath("SEC:MED")));
        Room room = (Room) res2.aggregate;
        CmdRes res3 = roomHandler.execute(new CmdRoomAddBed(room.getEntityId(), new Bed("Lit1")));

        // When
        CmdRes res = roomHandler.execute(new CmdRoomRemoveBed(room.getEntityId(), new Bed("Lit1")));

        // Then (evt levé)
        assertThat(res.getEvent(EvtRoomBedRemoved.class), notNullValue());

        // Then (Room modifié)
        room = (Room) eventStore.get(room.getEntityId());
        assertThat(room.getBeds().contains(new Bed("Lit1")), equalTo(false));

        // Then (RoomQ modifié)
        RoomQ roomQ = pHandler.find(RoomQ.class, room.getEntityId().getId());
        assertThat(roomQ, notNullValue());
        assertThat(roomQ.getBeds().contains(new Bed("Lit1")), equalTo(false));
    }


}
