package com.tentelemed.gam.domain;

import com.tentelemed.archipel.core.application.EventRegistry;
import com.tentelemed.archipel.core.application.EventStore;
import com.tentelemed.archipel.core.domain.model.*;
import com.tentelemed.archipel.infrastructure.config.TestSpringConfiguration;
import com.tentelemed.archipel.site.domain.event.SiteRegistered;
import com.tentelemed.archipel.site.domain.model.*;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 29/11/13
 * Time: 11:51
 */
public class SiteSteps {
    AnnotationConfigApplicationContext context;
    Site center;
    EventStore eventStore;

    public void init() {
        context = new AnnotationConfigApplicationContext();
        context.register(TestSpringConfiguration.class);
        context.refresh();
        EventRegistry registry = (EventRegistry) context.getBean("eventRegistry");
        registry.addEntry(SiteRegistered.class, Site.class, null);
    }

    private EventStore getEventStore() {
        if (eventStore == null) {
            init();
            eventStore = (EventStore) context.getBean("eventStore");
        }
        return eventStore;
    }

    @When("the user register a new Site with name center1, type CHU, ident CTR1")
    public void whenTheUserRegisterANewMedicalCenterWithNameCenter1TypeCHUIdentCTR1() {
        center = getEventStore().get(Site.class);
        center.register(SiteType.CHU, "center1", "CTR1");
        System.err.println("hop");
    }

    @Then("the new Site has an Id")
    public void thenTheNewMedicalCenterHasAnId() {
        assertThat(center.getEntityId(), notNullValue());
    }

    @Then("the new Site has a default Division with one Sector of type MED")
    //@Pending
    public void thenTheNewMedicalCenterHasADefaultDivisionWithOneSectorOfTypeMED() {
        assertThat(center.getSectors(), notNullValue());
        assertThat(center.getSectors().size(), equalTo(1));
        Sector sector = center.getSectors().iterator().next();
        assertThat(sector, notNullValue());
        assertThat(sector.getType(), equalTo(Sector.Type.MED));
    }

    @Then("the new Site is available in the EventStore")
    public void thenTheNewMedicalCenterIsAvailableInTheEventStore() {
        Site foundCenter = (Site) getEventStore().get(center.getEntityId());
        assertThat(foundCenter, notNullValue());
        assertThat(foundCenter.getName(), equalTo(center.getName()));
    }

    @Given("a Site")
    public void givenAMedicalCenter() {
        center = getEventStore().get(Site.class);
        center.register(SiteType.CHU, "center1", "CTR1");
    }

    @When("the user update the additional infos")
    public void whenTheUserUpdateTheAdditionalInfos() {
        Address address = new Address("street", "code", "Paris", "FRA");
        String phone = "0155200800";
        String fax = "0155200800";
        Bank bank = new Bank("BNP", "BNP");
        SiteInfo info = new SiteInfo("siret", address, phone, fax, "Dupont", "BNP", true, true, true);
        center.updateAdditionalInfo(info);
    }

    @Then("the Site contains the modified data")
    public void thenTheMedicalCenterContainsTheModifiedData() {
        assertThat(center.getInfo(), notNullValue());
        assertThat(center.getInfo().getAddress().getTown(), equalTo("Paris"));
    }




}
