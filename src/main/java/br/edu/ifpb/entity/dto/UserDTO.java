package br.edu.ifpb.entity.dto;

import java.util.Date;
import java.util.List;

import br.edu.ifpb.entity.Role;
import br.edu.ifpb.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String login;
	private String gender;
	private Date dateOfBirth;
	private String status;
	private List<Role> roles;

	public UserDTO() {
		
	}

	public UserDTO(Long id, String firstName, String lastName, String login) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
	}
	
	public static UserDTO fromEntity(User user) {
//        return new UserDTO(
//        		user.getId(), 
//        		user.getFirstName(), 
//        		user.getLastName(),
//        		user.getLogin(),
//        		user.getRoles());
		return null;
    }
	
	public User fromDTO() {
		User u = new User();
		u.setId(id);
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setLogin(login);
		u.setRoles(roles);
		return u;
	}
}
