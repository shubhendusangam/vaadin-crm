package com.vaadin.tutorials.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaadin.tutorials.backend.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
