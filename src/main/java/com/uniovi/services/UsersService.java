package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}

	public Page<User> getUsers(Pageable pageable) {
		return usersRepository.findAll(pageable);
	}

	public User getUser(Long id) {
		return usersRepository.findById(id).orElse(null);
	}

	public void addUser(User user) {
		if (user.getPassword() != null)
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public User getUserByDni(String dni) {
		return usersRepository.findByDni(dni);
	}

	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}

	public Page<User> searchUsersByName(Pageable pageable, String searchText) {
		searchText = "%"+searchText+"%";
		return usersRepository.searchByUsername(pageable, searchText);
	}

}
