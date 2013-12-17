package com.tentelemed.archipel.medicalcenter.infrastructure.web;

import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.infrastructure.web.BaseViewModel;
import com.tentelemed.archipel.medicalcenter.application.service.MedicalCenterCommandService;
import com.tentelemed.archipel.medicalcenter.application.service.MedicalCenterQueryService;
import com.tentelemed.archipel.medicalcenter.domain.event.MedicalCenterDomainEvent;
import com.tentelemed.archipel.medicalcenter.domain.model.Division;
import com.tentelemed.archipel.medicalcenter.domain.model.MedicalCenterId;
import com.tentelemed.archipel.medicalcenter.infrastructure.model.MedicalCenterQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 11/12/13
 * Time: 13:56
 */
@Component
@Scope("prototype")
public class UiMedicalCentersViewModel extends BaseViewModel {

    @Autowired MedicalCenterQueryService serviceRead;
    @Autowired MedicalCenterCommandService serviceWrite;
    MedicalCenterQ currentCenter;

    public List<MedicalCenterQ> getMedicalCenters() {
        return sort(serviceRead.getAll(), new Comparator<MedicalCenterQ>() {
            @Override
            public int compare(MedicalCenterQ o1, MedicalCenterQ o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public void setCurrentCenter(MedicalCenterQ currentCenter) {
        this.currentCenter = currentCenter;
    }

    public MedicalCenterQ getCurrentCenter() {
        return currentCenter;
    }

    public void action_createCenter() {
        UiMedicalCenterCreateView view = getView(UiMedicalCenterCreateView.class);
        show(view);
    }

    public void action_deleteCenter() {
        confirm("Medical Center deletion", "Do you really want to delete this Medical Center ?", new Runnable() {
            @Override
            public void run() {
                serviceWrite.execute(new MedicalCenterCommandService.CmdDelete(getCurrentCenter().getEntityId()));
            }
        });
    }

    public boolean isDeleteCenterEnabled() {
        return getCurrentCenter() != null;
    }

    public void action_editMain() {
        UiMedicalCenterCreateView view = getView(UiMedicalCenterCreateView.class);
        view.setCenter(getCurrentCenter());
        show(view);
    }

    public boolean isEditMainEnabled() {
        return getCurrentCenter() != null;
    }

    public void action_editAddInfo() {
        UiMedicalCenterEditView view = getView(UiMedicalCenterEditView.class);
        view.setCenter(getCurrentCenter());
        show(view);
    }

    public boolean isEditAddInfoEnabled() {
        return getCurrentCenter() != null;
    }

    @Override
    protected void onDomainEventReceived(DomainEvent event) {
        if (event instanceof MedicalCenterDomainEvent) {
            // il faut recharcer la version à jour du modele
            currentCenter = serviceRead.getCenter((MedicalCenterId) event.getId());
        }
    }

    public Division createDefaultDivision() {
        return null;
    }
}
