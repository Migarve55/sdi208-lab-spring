package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {
	
	User findByDni(String dni);
	
	Page<User> findAll(Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE LOWER(u.name || u.lastName) LIKE LOWER(?1)")
	Page<User> searchByUsername(Pageable pageable, String searchText);

}
