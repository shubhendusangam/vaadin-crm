package com.vaadin.tutorials.ui.listview;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorials.backend.entity.Company;
import com.vaadin.tutorials.backend.entity.Contact;
import com.vaadin.tutorials.backend.services.CompanyService;
import com.vaadin.tutorials.backend.services.ContactService;
import com.vaadin.tutorials.ui.MainLayout;

@Component
@Scope("prototype")
@Route(value="", layout = MainLayout.class)
@PageTitle("Contacts | Vaadin CRM")
@CssImport("./shared-style.css")
public class ListView extends VerticalLayout {

    ContactService contactService;

    Grid<Contact> contactGrid = new Grid<>(Contact.class);
    TextField filterText = new TextField();
    ContactForm contactForm;

    public ListView(ContactService contactService, CompanyService companyService) {
        this.contactService = contactService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        toolBar();

        contactForm = new ContactForm(companyService.findAll());
        contactForm.addListener(ContactForm.SaveEvent.class, this::saveContact);
        contactForm.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
        contactForm.addListener(ContactForm.CloseEvent.class, evt -> closeEditors());

        Div content = new Div(contactGrid, contactForm);
        content.addClassName("content");
        content.setSizeFull();

        add(toolBar(), content);
        updateList();
        closeEditors();
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
        contactGrid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));
    }

    private HorizontalLayout toolBar() {
        filterText.setPlaceholder("Filter by name ..");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add Contact");
        addContactButton.addClickListener(click -> addContact());

        HorizontalLayout toolBar = new HorizontalLayout(filterText, addContactButton);
        toolBar.addClassName("toolbar");
        return toolBar;
    }

    public void updateList() {
        contactGrid.setItems(contactService.findAll(filterText.getValue()));
    }

    public void addContact() {
        contactGrid.asSingleSelect().clear();
        editContact(new Contact());
    }

    public void editContact(Contact contact) {
        if (contact == null){
            closeEditors();
        } else {
            contactForm.setContact(contact);
            contactForm.setVisible(true);
            addClassName("editing");
        }
    }

    public void deleteContact(ContactForm.DeleteEvent deleteEvent) {
        contactService.delete(deleteEvent.getContact());
        updateList();
        closeEditors();
    }

    public void saveContact(ContactForm.SaveEvent saveEvent) {
        contactService.save(saveEvent.getContact());
        updateList();
        closeEditors();
    }

    private void closeEditors() {
        contactForm.setContact(null);
        contactForm.setVisible(false);
        removeClassName("editing");
    }
}
