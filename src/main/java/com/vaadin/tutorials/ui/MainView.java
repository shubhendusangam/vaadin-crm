package com.vaadin.tutorials.ui;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorials.backend.entity.Company;
import com.vaadin.tutorials.backend.entity.Contact;
import com.vaadin.tutorials.backend.services.ContactService;

@Route("")
public class MainView extends VerticalLayout {

    private ContactService contactService;

    Grid<Contact> contactGrid = new Grid<>(Contact.class);

    public MainView(ContactService contactService) {
        this.contactService = contactService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(contactGrid);
        updateList();
    }

    private void configureGrid() {
        contactGrid.addClassName("contact-grid");
        contactGrid.setSizeFull();
        contactGrid.removeColumnByKey("company");
        contactGrid.setColumns("firstName", "lastName", "email", "status");
        contactGrid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Company");
        contactGrid.getColumns().forEach(contactColumn -> contactColumn.setAutoWidth(true));
    }

    private void updateList() {
        contactGrid.setItems(contactService.findAll());
    }
}
