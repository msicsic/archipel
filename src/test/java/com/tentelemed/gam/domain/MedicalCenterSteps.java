package com.tentelemed.gam.domain;

import com.tentelemed.archipel.core.application.EventRegistry;
import com.tentelemed.archipel.core.application.EventStore;
import com.tentelemed.archipel.core.application.event.DomainEvent;
import com.tentelemed.archipel.core.domain.model.*;
import com.tentelemed.archipel.core.infrastructure.config.SpringConfiguration;
import com.tentelemed.archipel.infrastructure.config.TestSpringConfiguration;
import com.tentelemed.archipel.medicalcenter.domain.event.MedicalCenterRegistered;
import com.tentelemed.archipel.medicalcenter.domain.model.*;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.lang.reflect.Method;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/11/13
 * Time: 11:51
 */
public class MedicalCenterSteps {
    AnnotationConfigApplicationContext context;
    MedicalCenter center;
    EventStore eventStore;

    public void init() {
        context = new AnnotationConfigApplicationContext();
        context.register(TestSpringConfiguration.class);
        context.refresh();
        EventRegistry registry = (EventRegistry) context.getBean("eventRegistry");
        registry.addEntry(MedicalCenterRegistered.class, MedicalCenter.class, null);
    }

    private EventStore getEventStore() {
        if (eventStore == null) {
            init();
            eventStore = (EventStore) context.getBean("eventStore");
        }
        return eventStore;
    }

    @When("the user register a new MedicalCenter with name center1, type CHU, ident CTR1")
    public void whenTheUserRegisterANewMedicalCenterWithNameCenter1TypeCHUIdentCTR1() {
        center = getEventStore().get(MedicalCenter.class);
        center.register(MedicalCenterType.CHU, "center1", "CTR1");
        System.err.println("hop");
    }

    @Then("the new MedicalCenter has an Id")
    public void thenTheNewMedicalCenterHasAnId() {
        assertThat(center.getEntityId(), notNullValue());
    }

    @Then("the new MedicalCenter has a default Division with one Sector of type MED")
    //@Pending
    public void thenTheNewMedicalCenterHasADefaultDivisionWithOneSectorOfTypeMED() {
        Division division = center.getDivision();
        assertThat(division, notNullValue());
        assertThat(division.getSectors().size(), equalTo(1));
        Sector sector = division.getSectors().iterator().next();
        assertThat(sector, notNullValue());
        assertThat(sector.getType(), equalTo(Sector.Type.MED));
    }

    @Then("the new MedicalCenter is available in the EventStore")
    public void thenTheNewMedicalCenterIsAvailableInTheEventStore() {
        MedicalCenter foundCenter = (MedicalCenter) getEventStore().get(center.getEntityId());
        assertThat(foundCenter, notNullValue());
        assertThat(foundCenter.getName(), equalTo(center.getName()));
    }

    @Given("a MedicalCenter")
    public void givenAMedicalCenter() {
        center = getEventStore().get(MedicalCenter.class);
        center.register(MedicalCenterType.CHU, "center1", "CTR1");
    }

    @When("the user update the additional infos")
    public void whenTheUserUpdateTheAdditionalInfos() {
        Address address = new Address("street", "code", "Paris", new Country("FR"));
        String phone = "0155200800";
        String fax = "0155200800";
        Bank bank = new Bank("BNP", "BNP");
        MedicalCenterInfo info = new MedicalCenterInfo("siret", address, phone, fax, "Dupont", bank, true, true, true);
        center.updateAdditionalInfo(info);
    }

    @Then("the MedicalCenter contains the modified data")
    public void thenTheMedicalCenterContainsTheModifiedData() {
        assertThat(center.getInfo(), notNullValue());
        assertThat(center.getInfo().getAddress().getTown(), equalTo("Paris"));
    }




}
