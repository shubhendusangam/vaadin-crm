package com.vaadin.tutorials.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorials.backend.entity.Company;
import com.vaadin.tutorials.backend.entity.Contact;
import com.vaadin.tutorials.backend.services.ContactService;

@Route("")
@CssImport("./shared-style.css")
public class MainView extends VerticalLayout {

    private ContactService contactService;

    private Grid<Contact> contactGrid = new Grid<>(Contact.class);
    private TextField filterText = new TextField();
    private ContactForm contactForm;

    public MainView(ContactService contactService) {
        this.contactService = contactService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureFilter();

        contactForm = new ContactForm();
        Div content = new Div(contactGrid, contactForm);
        content.addClassName("content");
        content.setSizeFull();

        add(filterText, content);
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

    private void configureFilter() {
        filterText.setPlaceholder("Filter by name ..");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private void updateList() {
        contactGrid.setItems(contactService.findAll(filterText.getValue()));
    }
}
