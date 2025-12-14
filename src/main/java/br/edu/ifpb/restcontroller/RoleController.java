package br.edu.ifpb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.entity.Role;
import br.edu.ifpb.service.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {
	
	@Autowired
	protected RoleService roleService;
	
	@GetMapping("list")
	public ResponseEntity<List<Role>> list() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity
        		.ok()
        		.contentType(MediaType.APPLICATION_JSON)
        		.body(roles);
	}
}