package br.edu.ifpb.restcontroller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifpb.entity.Role;
import br.edu.ifpb.entity.User;
import br.edu.ifpb.entity.dto.UserDTO;
import br.edu.ifpb.entity.dto.UserSaveDTO;
import br.edu.ifpb.service.PhotoService;
import br.edu.ifpb.service.RoleService;
import br.edu.ifpb.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserRestController extends RestAppController {

	@Autowired
	protected UserService userService;

	@Autowired
	protected RoleService roleService;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	protected PhotoService photoService;

	@PostMapping("save")
	public ResponseEntity<?> save(
			@Validated @RequestPart("user") UserSaveDTO usersavedto,
			@RequestPart("foto") MultipartFile foto) {

		try {
			UserDTO userdto = userService.save(usersavedto, foto);
			return new ResponseEntity<UserDTO>(userdto, HttpStatus.CREATED);
		} catch(IOException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	//localhost:8080/api/user/find/filter/page?firstName=a&lastName=a&page=0&size=5&sort=lastName,asc
	@GetMapping("page/filter")
	public ResponseEntity<Page<UserDTO>> getAllUsersByFilter(
			@RequestParam(required = false) String firstName,
			@RequestParam(required = false) String lastName, 
			Pageable pageable) {

		User example = new User();
		example.setFirstName(firstName);
		example.setLastName(lastName);

		Page<UserDTO> page = userService.findByExampleMatch(example, pageable);
		return ResponseEntity.ok(page);
	}
	
	

	@PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
		try {
	        // Salvar no banco de dados ou processar
			user.setId(null);
			encriptPassword(user);
			userService.save(user);
			UserDTO udto = UserDTO.fromEntity(user);
			return new ResponseEntity<UserDTO>(udto, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    }

	@PostMapping("basicregister")
    public ResponseEntity<?> basicregister(@RequestBody User user) {
		try {
	        // Salvar no banco de dados ou processar
			user.setId(null);
			user.setRoles(null);

			Role role = roleService.findUserRole();
			user.addRole(role);

			encriptPassword(user);
			userService.save(user);

			UserDTO udto = UserDTO.fromEntity(user);
			return new ResponseEntity<UserDTO>(udto, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    }

	@PutMapping("update")
    public ResponseEntity<?> update(@RequestBody User user) {
		try {
			encriptPassword(user);
			userService.save(user);
			return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    }

	private void encriptPassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}

	//Apenas ADMIN
	@PutMapping("update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody User userRequest){
		try {
			userRequest.setId(id);

			//Verifica se o usuário existe
			User user = userService.findById(id);
			
//			if(hasPermission("ADMIN", "MANAGER") || isAuthenticationUser(id)) {
				//Copia as propriedades do userRequest para a user
				BeanUtils.copyProperties(userRequest, user);
				//Edita usuário
				encriptPassword(user);
				userService.save(user);

				return ResponseEntity.ok(user);
//			}
//			return ResponseEntity.badRequest().body("Você não tem permissão para editar este usuário");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage()); 
		}
	}

	//MANAGER
	@PutMapping("updatenorules/{id}")
	public ResponseEntity<?> updatenorules(@PathVariable Long id, @RequestBody User userRequest){
		try {
			userRequest.setId(id);

			//Verifica se o usuário existe
			User user = userService.findById(id);
			
//			if(hasPermission("ADMIN", "MANAGER") || isAuthenticationUser(id)) {
				//Copia as propriedades do DTO para a entidade
				BeanUtils.copyProperties(userRequest, user);
				user.setRoles(null);
				//Edita usuário
				encriptPassword(user);
				userService.updatenorules(user);

				return ResponseEntity.ok(user);
//			}
//			return ResponseEntity.badRequest().body("Você não tem permissão para editar este usuário");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage()); 
		}
	}
	
	@PutMapping("userupdate/{id}")
	public ResponseEntity<?> userupdate(@PathVariable Long id, @RequestBody User userRequest){
		try {
			if(hasPermission("ADMIN", "MANAGER") || isAuthenticationUser(id)) {
				return updatenorules(id, userRequest);
			}
			return ResponseEntity.badRequest().body("Você não tem permissão para editar este usuário");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage()); 
		}
	}

	@GetMapping("list")
	public ResponseEntity<List<UserDTO>> list() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity
        		.ok()
        		.contentType(MediaType.APPLICATION_JSON)
        		.body(users);
//        return ResponseEntity.ok(users);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<User> get(@PathVariable Long id) {
		User user = userService.findById(id);
        return ResponseEntity.ok(user);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		try {
			userService.deleteById(id);
			
			/*O código 204 No Content de uma resposta HTTP significa 
			que o servidor processou com sucesso a requisição, mas 
			não está retornando nenhum conteúdo no corpo da resposta.*/
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage()); 
		}
	}

	@GetMapping("find")
	public ResponseEntity<?> find(
			@RequestParam(value="firstname", required = false) String firstName,
			@RequestParam(value="lastname", required = false) String lastName,
			@RequestParam(required = false) Long id){

		try {
			User filter = new User();
			filter.setId(id);
			filter.setFirstName(firstName);
			filter.setLastName(lastName);

			List<UserDTO> dtos = userService.findByFilter(filter);

			return ResponseEntity.ok(dtos);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage()); 
		}
	}

	@GetMapping("find/like")
	public ResponseEntity<?> findUsers(
			@RequestParam(value="firstname", required = false) String firstName,
			@RequestParam(value="lastname", required = false) String lastName){

		try {
			User filter = new User();
			filter.setFirstName(firstName);
			filter.setLastName(lastName);

			List<UserDTO> dtos = userService.findByExampleMatch(filter);
			return ResponseEntity.ok(dtos);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("find/page")
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userService.findAllDto(pageable);
    }

	//localhost:8080/api/user/find/filter/page?firstName=a&lastName=a&page=0&size=5&sort=lastName,asc
	@GetMapping("find/filter/page")
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            Pageable pageable) {

		User example = new User();
		example.setFirstName(firstName);
		example.setLastName(lastName);

		Page<UserDTO> page = userService.findByExampleMatch(example, pageable);
		return ResponseEntity.ok(page);
	}
}