package com.vaadin.tutorials.ui.dashboardview;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorials.backend.services.CompanyService;
import com.vaadin.tutorials.backend.services.ContactService;
import com.vaadin.tutorials.ui.MainLayout;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | Vaadin CRM")
public class DashboardView extends VerticalLayout {

   private CompanyService companyService;
   private ContactService contactService;

   public DashboardView(CompanyService companyService, ContactService contactService) {
      this.companyService = companyService;
      this.contactService = contactService;
      add(getContactStats(), getCompanyChart());
      addClassName("dashboard-view");
      setDefaultHorizontalComponentAlignment(Alignment.CENTER);
   }

   private Component getContactStats() {
      Span stats = new Span(contactService.count() + " contacts");
      return stats;
   }

   private Chart getCompanyChart() {
      Chart chart = new Chart(ChartType.PIE);
      DataSeries dataSeries = new DataSeries();
      Map<String, Integer> companies = companyService.getStatus();
      companies.forEach((company, employees) -> dataSeries.add(new DataSeriesItem(company, employees)));
      chart.getConfiguration().setSeries(dataSeries);
      return chart;
   }
}
