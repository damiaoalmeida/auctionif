package br.edu.ifpb.entity.dto;

import java.util.Date;
import java.util.List;

import br.edu.ifpb.entity.Role;
import br.edu.ifpb.entity.validation.EqualPasswords;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@EqualPasswords(campoSenha = "password", campoSenha2 = "password2")
public class UserSaveDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private String login;
	private String password;
	private String password2;
	private String gender;
	private Date dateOfBirth;
	private String status;
	private List<Role> roles;
}
