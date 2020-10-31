package com.vaadin.tutorials.backend.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.vaadin.tutorials.backend.entity.Company;
import com.vaadin.tutorials.backend.repository.CompanyRepository;

@Service
public class CompanyService {

   private CompanyRepository companyRepository;
   public CompanyService(CompanyRepository companyRepository) {
      this.companyRepository = companyRepository;
   }
   public List<Company> findAll() {
      return companyRepository.findAll();
   }
   public Map<String, Integer> getStatus() {
      HashMap<String, Integer> stats = new HashMap<>();
      findAll().forEach(company -> stats.put(company.getName(), company.getEmployees().size()));
      return stats;
   }

}
