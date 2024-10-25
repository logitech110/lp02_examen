package com.municipalidad.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.municipalidad.model.Person;

public interface IPersonRepository extends JpaRepository<Person , Integer>{

}
 