package com.municipalidad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.municipalidad.model.Role;

 public interface IRoleRepository extends JpaRepository <Role , Integer> {
	 Role findByName(String name);
}
