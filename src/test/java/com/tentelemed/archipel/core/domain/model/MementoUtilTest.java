package com.tentelemed.archipel.core.domain.model;


import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 05/12/13
 * Time: 14:38
 */
public class MementoUtilTest {

    private TestUser createUser() {
        TestRole role = new TestRole("Admin");

        TestUser kevin = new TestUser(new TestUserId(11), role, "Kevin", "Durand", "Login1");
        TestCountry country = new TestCountry("FR");
        TestAddress address = new TestAddress("102 av Edouard Vaillant", "Boulogne", country);
        kevin.setAddress(address);

        TestUser kevina = new TestUser(new TestUserId(13), role, "Kevina", "Durand", "Login2");
        kevina.setAddress(address);

        TestUser albert = new TestUser(new TestUserId(12), role, "Albert", "Durand", "Login3");
        albert.setAddress(address);
        albert.addChild(kevin);
        albert.addChild(kevina);
        return albert;
    }

    @Test
    public void testCreateMementoFromAggregate() {
        // given
        TestUser albert = createUser();

        // when
        Memento memento = MementoUtil.createMemento(albert);

        // then
        assertThat(memento, notNullValue());
        assertThat(memento.getType().getName(), equalTo(albert.getClass().getName()));
        assertThat((int)memento.get("id"), equalTo(12));
        assertThat((String)memento.get("lastName"), equalTo("Durand"));
        Memento adr = (Memento) memento.get("address");
        assertThat(adr, notNullValue());
        assertThat((String)adr.get("street"), equalTo("102 av Edouard Vaillant"));
        assertThat(Collection.class.isAssignableFrom(memento.get("children").getClass()), equalTo(true));
        assertThat(((List)memento.get("children")).size(), equalTo(1));
        Memento child = (Memento) ((List)memento.get("children")).get(0);
        assertThat((Memento)child.get("tutor"), equalTo(memento));
    }

    @Test
    public void testCreateAggregateFromMemento() {
        // given
        TestUser original = createUser();
        Memento memento = MementoUtil.createMemento(original);

        // when
        Object result = MementoUtil.instanciateFromMemento(memento);

        // then
        assertThat(result, notNullValue());
        assertThat(result.getClass().getName(), equalTo(TestUser.class.getName()));
        TestUser user = (TestUser) result;
        assertThat(user.getEntityId(), equalTo(original.getEntityId()));
        assertThat(user.getAddress(), equalTo(original.getAddress()));
        assertThat(user.getFirstName(), equalTo(original.getFirstName()));
        assertThat(user.getChildren().get(0).getClass().getName(), equalTo(TestUser.class.getName()));
        assertThat(user.getChildren().get(0), equalTo(original.getChildren().get(0)));
    }

    @Test
    public void testLoadAggregateFromMemento() {
        // given
        TestUser original = createUser();
        original.changeName("PAUL", "DUPONT");
        TestAddress oldAddress = new TestAddress("Adr1", "Town1", new TestCountry("US"));
        original.setAddress(oldAddress);

        Memento memento = MementoUtil.createMemento(original);

        original.changeName("Toto", "Titi");
        TestAddress newAddress = new TestAddress("...", "...", new TestCountry("GB"));
        original.setAddress(newAddress);
        original.getChildren().get(0).changeName("Titi", "Tata");
        original.getChildren().remove(1);

        // when
        MementoUtil.loadAggregate(original, memento);

        // then
        assertThat(original.getFirstName(), equalTo("PAUL"));
        assertThat(original.getLastName(), equalTo("DUPONT"));
        assertThat(original.getAddress(), equalTo(oldAddress));
        assertThat(original.getChildren().size(), equalTo(2));
        assertThat(original.getChildren().get(0).getFirstName(), equalTo("Kevin"));
    }

    @Test
    public void testCreateMementoFromEvent() {
        // Given
        TestUserNameChanged event = new TestUserNameChanged("Paul", "Durand");

        // When
        Memento memento = MementoUtil.createMemento(event);

        // Then
        assertThat(memento, notNullValue());
        assertThat(memento.getType().getName(), equalTo(TestUserNameChanged.class.getName()));
        assertThat((String)memento.get("firstName"), equalTo("Paul"));
    }

    @Test
    public void testCreateEventFromMemento() {
        // Given
        TestUserNameChanged event = new TestUserNameChanged("Paul", "Durand");
        Memento memento = MementoUtil.createMemento(event);

        // When
        Object res = MementoUtil.instanciateFromMemento(memento);

        // Then
        assertThat(res, notNullValue());
        assertThat(res.getClass().getName(), equalTo(TestUserNameChanged.class.getName()));
        TestUserNameChanged newEvent = (TestUserNameChanged)res;
        assertThat(newEvent.getFirstName(), equalTo("Paul"));
        assertThat(newEvent.getLastName(), equalTo("Durand"));
    }

    @Test
    public void testMementoToStringAndFromString() throws Exception {
        // Given
        TestUser original = createUser();
        Memento memento = MementoUtil.createMemento(original);

        // When
        String xml = MementoUtil.mementoToString(memento);
        Object object = MementoUtil.mementoFromString(xml);

        // Then
        assertThat(object, notNullValue());
        assertThat(object.getClass().getName(), equalTo(memento.getClass().getName()));
        Memento m2 = (Memento) object;
        assertThat(m2.getType(), equalTo(memento.getType()));
        assertThat(m2.get("id"), equalTo(memento.get("id")));
        assertThat(m2.get("firstName"), equalTo(memento.get("firstName")));
        assertThat(((List)m2.get("children")).size(), equalTo(2));
    }

}
