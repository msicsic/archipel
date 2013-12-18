package com.tentelemed.archipel.medicalcenter.infrastructure.web;

import com.tentelemed.archipel.core.application.service.Command;
import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.medicalcenter.application.service.BeanCreator;
import com.tentelemed.archipel.medicalcenter.application.service.MedicalCenterCommandService;
import com.tentelemed.archipel.medicalcenter.application.service.MedicalCenterQueryService;
import com.tentelemed.archipel.medicalcenter.domain.model.Location;
import com.tentelemed.archipel.medicalcenter.domain.model.Sector;
import com.tentelemed.archipel.medicalcenter.infrastructure.model.MedicalCenterQ;
import com.tentelemed.archipel.medicalcenter.infrastructure.model.RoomQ;
import com.vaadin.data.fieldgroup.FieldGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 11/12/13
 * Time: 13:55
 */
@Component
@Scope("prototype")
public class UiRoomCreateViewModel extends BaseViewModel {
    private MedicalCenterCommandService.CmdCreateRoom cmdRegister = BeanCreator.createBean(new MedicalCenterCommandService.CmdCreateRoom());
    private MedicalCenterCommandService.CmdUpdateRoom cmdUpdate = BeanCreator.createBean(new MedicalCenterCommandService.CmdUpdateRoom());
    private boolean edit;
    @Autowired MedicalCenterCommandService serviceWrite;
    @Autowired MedicalCenterQueryService serviceRead;
    private MedicalCenterQ currentCenter;

    public boolean isEdit() {
        return edit;
    }

    public void setRoom(RoomQ room) {
        edit = true;
        cmdUpdate.name = room.getName();
        cmdUpdate.id = room.getEntityId();
        //cmdUpdate.location = room.getLocationCode();
    }

    @Valid
    public Command getCmd() {
        return isEdit() ? cmdUpdate : cmdRegister;
    }

    public MedicalCenterCommandService.CmdCreateRoom getCmdRegister() {
        return cmdRegister;
    }

    public MedicalCenterCommandService.CmdUpdateRoom getCmdUpdate() {
        return cmdUpdate;
    }

    public void action_createRoom() {
        try {
            commit();
            boolean valid = getBinder().isValid();
            System.err.println("valid : " + valid);
            serviceWrite.execute(isEdit() ? cmdUpdate : cmdRegister);
            close();
        } catch (FieldGroup.CommitException e) {
            show("Invalid data");
        }
    }

    public void action_cancel() {
        close();
    }

    public List<Location> getLocations() {
        Sector sector = new Sector(Sector.Type.MED, "Consultation", "CNS");
        List res = new ArrayList<>();
        res.add(sector);
        return res;
    }
}
