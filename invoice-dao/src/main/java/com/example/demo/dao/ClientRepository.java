package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.ClientEntity;


/**
 * Jpa Client Repository.
 */
public interface ClientRepository extends JpaRepository<ClientEntity, Integer>{
}