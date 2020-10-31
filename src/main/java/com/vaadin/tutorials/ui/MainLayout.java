package com.vaadin.tutorials.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.tutorials.ui.dashboardview.DashboardView;
import com.vaadin.tutorials.ui.listview.ListView;

@CssImport("./shared-style.css")
public class MainLayout extends AppLayout {

   public MainLayout() {
      createHeader();
      createDrawer();
   }

   private void createHeader() {
      H1 logo = new H1("Vaadin CRM");
      logo.addClassName("logo");

      Anchor logout = new Anchor("logout", "Log out");

      HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
      header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
      header.expand(logo);
      header.setWidth("100%");
      header.addClassName("header");
      addToNavbar(header);
   }

   private void createDrawer() {
      RouterLink listLink = new RouterLink("List", ListView.class);
      listLink.setHighlightCondition(HighlightConditions.sameLocation());
      addToDrawer(new VerticalLayout(listLink, new RouterLink("Dashboard", DashboardView.class)));
   }

}
