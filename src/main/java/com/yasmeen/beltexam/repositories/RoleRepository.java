package com.yasmeen.beltexam.repositories;


import org.springframework.data.repository.CrudRepository;

import com.yasmeen.beltexam.models.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{

	Role findByName(String string);

}
