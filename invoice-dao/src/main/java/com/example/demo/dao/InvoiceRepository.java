package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.InvoiceEntity;


/**
 * Jpa Invoice Repository.
 */
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Integer>{
}