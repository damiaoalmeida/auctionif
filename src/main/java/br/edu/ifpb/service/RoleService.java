package br.edu.ifpb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.edu.ifpb.entity.Role;
import br.edu.ifpb.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public Role findUserRole() {
		Role filter = new Role();
		filter.setRole("ROLE_USER");

		Example<Role> example = Example.of(filter);
		List<Role> list = roleRepository.findAll(example);
		return list.get(0);
	}
}