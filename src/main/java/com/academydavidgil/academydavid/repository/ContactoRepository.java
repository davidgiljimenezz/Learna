package com.academydavidgil.academydavid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academydavidgil.academydavid.model.Contacto;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {

}
