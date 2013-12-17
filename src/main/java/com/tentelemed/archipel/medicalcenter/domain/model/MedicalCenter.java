package com.tentelemed.archipel.medicalcenter.domain.model;

import com.tentelemed.archipel.core.application.service.CmdRes;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.medicalcenter.domain.event.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 11:40
 */
public class MedicalCenter extends BaseAggregateRoot<MedicalCenterId> implements MedicalCenterEventHandler {

    @NotNull MedicalCenterType type;
    @NotNull String name;
    @NotNull String ident;
    @NotNull @Valid Division division;
    @Valid Set<RoomId> rooms = new HashSet<>();
    @Valid MedicalCenterInfo info;


    // COMMANDS
    public CmdRes register(MedicalCenterType type, String name, String ident) {
        validate("type", type);
        validate("name", name);
        validate("ident", ident);
        return result(new MedicalCenterRegistered(getEntityId(), type, name, ident, createDefaultDivision()));
    }

    public CmdRes updateMainInfo(MedicalCenterType type, String name, String ident) {
        validate("type", type);
        validate("name", name);
        validate("ident", ident);
        return result(new MedicalCenterMainInfoUpdated(getEntityId(), type, name, ident));
    }

    public CmdRes updateAdditionalInfo(MedicalCenterInfo info) {
        validate("info", info);
        return result(new MedicalCenterAdditionalInfoUpdated(getEntityId(), info));
    }

    public CmdRes addRoom(RoomId room) {
        // TODO : tester l'existance de la Room
        return result(new MedicalCenterRoomAdded(getEntityId(), room));
    }

    public CmdRes removeRoom(RoomId room) {
        // TODO : tester l'existance de la Room
        return result(new MedicalCenterRoomRemoved(getEntityId(), room));
    }

    public CmdRes delete() {
        return result(new MedicalCenterDeleted(getEntityId()));
    }
    // methodes utilitaires

    /**
     * Au minimum une Disivion possede un Sector de type médical
     */
    private Division createDefaultDivision() {
        Set<Sector> sectors = new HashSet<>();
        sectors.add(new Sector(Sector.Type.MED, "Default", "UNDEF", null));
        return new Division(sectors);
    }

    // EVENTS
    @Override
    public void handle(MedicalCenterRegistered event) {
        apply(event);
    }

    @Override
    public void handle(MedicalCenterMainInfoUpdated event) {
        apply(event);
    }

    @Override
    public void handle(MedicalCenterAdditionalInfoUpdated event) {
        apply(event);
    }

    @Override
    public void handle(MedicalCenterRoomAdded event) {
        rooms.add(event.getRoomId());
    }

    @Override
    public void handle(MedicalCenterRoomRemoved event) {
        rooms.remove(event.getRoomId());
    }

    @Override
    public void handle(MedicalCenterDeleted event) {
        // ras
    }

// GETTERS

    @Override
    protected Class<MedicalCenterId> getIdClass() {
        return MedicalCenterId.class;
    }

    public MedicalCenterType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getIdent() {
        return ident;
    }

    public Division getDivision() {
        return division;
    }

    public Set<RoomId> getRooms() {
        return Collections.unmodifiableSet(rooms);
    }

    public MedicalCenterInfo getInfo() {
        return info;
    }

}
