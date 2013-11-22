package com.tentelemed.archipel.core.infrastructure.web;

import com.vaadin.ui.*;

public class ConfirmDialog extends Window {
    Runnable run;

    public ConfirmDialog(String title, String message, Runnable r) {
        super(title); // Set window caption
        this.run = r;
        setModal(true);
        center();

        // Some basic content for the window
        VerticalLayout content = new VerticalLayout();
        content.addComponent(new Label(message));
        content.setMargin(true);
        setContent(content);

        // Disable the close button
        setClosable(false);
        setResizable(false);

        // Trivial logic for closing the sub-window
        Button ok = new Button("Yes");
        ok.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                try {
                    run.run();
                } finally {
                    close(); // Close the sub-window
                }
            }
        });
        Button cancel = new Button("No");
        cancel.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                close(); // Close the sub-window
            }
        });
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setSpacing(true);
        //Panel panel = new Panel(hlayout);
        hlayout.setMargin(true);
        content.addComponent(hlayout);
        content.setComponentAlignment(hlayout, Alignment.MIDDLE_RIGHT);
        hlayout.addComponent(ok);
        hlayout.addComponent(cancel);
        hlayout.setComponentAlignment(ok, Alignment.MIDDLE_RIGHT);
        hlayout.setComponentAlignment(cancel, Alignment.MIDDLE_RIGHT);
    }
}

