package com.dotcom.costaverde.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dotcom.costaverde.model.Controle;

public interface ControleRepository extends JpaRepository<Controle, Long> {
	
}
