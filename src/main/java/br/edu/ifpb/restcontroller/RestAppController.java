package br.edu.ifpb.restcontroller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import br.edu.ifpb.entity.User;

public class RestAppController {

	protected boolean hasPermission(String... roles){
		User u = authenticatedUser();
		for (GrantedAuthority ga : u.getAuthorities()) {
			for (String role : roles) {
				if (ga.getAuthority().equalsIgnoreCase(role))
					return true;
			}
		}
		return false;
	}

	protected boolean isAuthenticationUser(Long id) {
		User u = authenticatedUser();
		if(id.equals(u.getId()))
			return true;
		return false;
	}
	
	protected User authenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (User)(authentication.getPrincipal());
	}
}