package com.yasmeen.beltexam.repositories;

import org.springframework.data.repository.CrudRepository;

import com.yasmeen.beltexam.models.User;

public interface UserRepository extends CrudRepository<User, Long>{

	User findByEmail(String email);

}
