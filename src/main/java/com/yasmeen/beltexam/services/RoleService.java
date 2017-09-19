package com.yasmeen.beltexam.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yasmeen.beltexam.models.Role;
import com.yasmeen.beltexam.repositories.RoleRepository;

@Service
public class RoleService {
	private RoleRepository roleRepository;
	
	public RoleService(RoleRepository roleRepository){
		this.roleRepository = roleRepository;
	}
	
	public List<Role> all(){
		return (List<Role>) roleRepository.findAll();
	}
	
	public Role findByName(String name){
		return (Role) roleRepository.findByName(name);
	}
	
	public void create(Role role){
		roleRepository.save(role);
	}
	public void update(Role role){
		roleRepository.save(role);
	}
	public void destroy(long id){
		roleRepository.delete(id);
	}
}
