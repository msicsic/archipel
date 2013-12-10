package com.tentelemed.archipel.medicalcenter.domain.model;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.domain.model.BaseAggregateRoot;
import com.tentelemed.archipel.medicalcenter.domain.event.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 11:40
 */
public class MedicalCenter extends BaseAggregateRoot<MedicalCenterId> implements MedicalCenterEventHandler {
    public static enum Type {
        CHU, CHD, Clinic, CS
    }

    @NotNull Type type;
    @NotNull String name;
    @NotNull String ident;
    @NotNull @Valid Division division;
    @Valid Set<RoomId> rooms = new HashSet<>();
    @NotNull @Valid MedicalCenterInfo info;

    // COMMANDS
    public List<DomainEvent> register(Type type, String name, String ident, Division division, MedicalCenterInfo info) {
        validate("type", type);
        validate("name", name);
        validate("ident", ident);
        validate("info", info);
        validate("division", division);
        return list(new MedicalCenterRegistered(type, name, ident, division, info));
    }

    public List<DomainEvent> update(Type type, String name, String ident, Division division, MedicalCenterInfo info) {
        return list(new MedicalCenterUpdated(type, name, ident, division, info));
    }

    public List<DomainEvent> addRoom(RoomId room) {
        return list(new MedicalCenterRoomAdded(room));
    }

    public List<DomainEvent> removeRoom(RoomId room) {
        return list(new MedicalCenterRoomRemoved(room));
    }

    // EVENTS
    @Override
    public void handle(MedicalCenterRegistered event) {
        apply(event);
    }

    @Override
    public void handle(MedicalCenterUpdated event) {
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

    // GETTERS

    @Override
    protected Class<MedicalCenterId> getIdClass() {
        return MedicalCenterId.class;
    }

    public Type getType() {
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
