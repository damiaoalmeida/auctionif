package br.edu.ifpb.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="TB_USER")
@SuppressWarnings("serial")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
		    generator = "user_seq_gen")
	@SequenceGenerator(name = "user_seq_gen", 
				sequenceName = "tb_user_seq", 
				allocationSize = 1)
	private Long id;
	
	private String firstName;
	
	private String lastName;

	@Column(unique = true)
	private String login;
//	private String email;

	private String password;

	@OneToMany(fetch = FetchType.EAGER, 
			cascade = {CascadeType.ALL}, 
			mappedBy = "owner")
	private List<Book> books;

	@OneToOne(cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY, 
			orphanRemoval = true)
	@JoinColumn(name = "ID_USER_CONTENT_FK")
	private UserContent content;

	@Enumerated(EnumType.STRING)
	private GenderType gender;
	
	@Column(name="date_birth")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "tb_user_role",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private List<Role> roles;

	@Transient
	private Integer age;
	
	public User (Long id, String firstName, String lastName, String login) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
//		this.email = email;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		calcAge();
	}
	
	private void calcAge() {
		Calendar dob = Calendar.getInstance();
		dob.setTime(dateOfBirth);
		Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        // Ajustar a diferença se a data inicial ainda não chegou no mesmo ano da data final
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        this.age = age;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}

	public String[] arrayRoles() {
		int size = roles.size();
		String[] array = new String[size];

		for(int i = 0; i < size; i++) {
			array[i] = roles.get(i).getRole();
		}
		return array;
	}

	@Override
	public String getUsername() {
		return login;
//		return email;
	}

	public void addRole(Role role) {
		if(roles == null)
			roles = new ArrayList<Role>();
		roles.add(role);
	}
}