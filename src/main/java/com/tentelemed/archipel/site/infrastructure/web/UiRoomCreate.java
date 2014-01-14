package com.tentelemed.archipel.site.infrastructure.web;

import com.tentelemed.archipel.core.infrastructure.web.BasePopup;
import com.tentelemed.archipel.site.domain.pub.Location;
import com.tentelemed.archipel.site.infrastructure.model.RoomQ;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 11/12/13
 * Time: 13:55
 */
@Component
@Scope("prototype")
public class UiRoomCreate extends BasePopup<UiRoomCreateModel> {

    @Autowired UiRoomCreateModel model;

    public UiRoomCreate() {
    }

    @Override
    public UiRoomCreateModel getModel() {
        return model;
    }

    @Override
    public void postConstruct() {

    }

    @Override
    public void onDisplay() {
        setCaption(model.isEdit() ? "Edit Room" : "Create Room");

        setModal(true);
        setResizable(false);

        setWidth("250px");
        setHeight("250px");
        center();

        VerticalLayout grid = new VerticalLayout();
        grid.setHeight("100%");
        FormLayout layout = new FormLayout();

        if (model.isEdit()) {
            layout.addComponent(bind(new TextField("Name"), "cmdUpdate.name"));
            layout.addComponent(bind(new CheckBox("Location"), "cmdUpdate.location"));
        } else {
            layout.addComponent(bind(new TextField("Name"), "cmdCreate.name"));
            ComboBox cb = new ComboBox("Location");
            for (Location location : model.getLocations()) {

            }
        }

        HorizontalLayout btLayout = new HorizontalLayout();
        btLayout.setSpacing(true);
        Button bt1 = bind(new Button(model.isEdit() ? "Confirm" : "Create Room"), "createRoom");
        Button bt2 = bind(new Button("Cancel"), "cancel");
        btLayout.addComponent(bt1);
        btLayout.addComponent(bt2);

        grid.addComponent(layout);
        //grid.setExpandRatio(layout, 1.0f);

        grid.addComponent(btLayout);
        grid.setComponentAlignment(btLayout, Alignment.BOTTOM_RIGHT);

        grid.setMargin(true);
        setContent(grid);
    }

    public void setRoom(RoomQ room) {
        model.setRoom(room);
    }
}
