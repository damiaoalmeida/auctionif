package br.edu.ifpb.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ifpb.entity.Role;
import br.edu.ifpb.entity.User;
import br.edu.ifpb.entity.dto.UserDTO;
import br.edu.ifpb.repository.RoleRepository;

@Service
public class SuapService {

	@Value("${obtainTokenUrl}")
	public String OBTAIN_TOKEN_URL;

	@Value("${employeesUrl}")
	private String EMPLOYEES_URL;

	@Value("${studentsUrl}")
	private String STUDENTS_URL;
	
	private String TOKEN_HEADER_NAME = "Authorization";
	private String TOKEN_HEADER_VALUE = "Bearer %s";
	
	private String ROLE_MANAGER = "ROLE_MANAGER";
	private String ROLE_USER = "ROLE_USER";

	public String findUrl;
	
	public static final Map<String, String> DEFAULT_HEADERS = Map.of("Content-Type", "application/json");

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;

	public Authentication login(String login, String password) throws IOException, InterruptedException, URISyntaxException, NoSuchFieldException {
//		String response = "{\"refresh\":\"eyJhbGciOiJIUzI1NiIsRInR5cCI6IkpXVCJ9.eyRJ0b2tlbl90eXBlIjoicmVmcmVzaCIsImV4cCI6MTc1Mjg2NjI1OSwiaWF0IjoxNzUyNzc5ODU5LCJqdGkiOiJhNjc5MTJmN2Y2ZWU0MzhiYTE1ZDE1NGZlYmVmYjU5MiIsInVzZXJfaWQiOjE3MDcwN30.Vs_3GJslZ6EhMKrKbChkK5MD4ITNKA_k39ronNrSUsw\",\r\n"
//				+ "\"access\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbRl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzUyNzg5MDAxLCJpYXQiOjE3NTI3Nzk4NTksImp0aSI6IjhjYWMzZTNmMzM0NjRiMjk4OGY5NTUyZjU3YjEyMTI5IiwidXNlcl9pZCI6MTcwNzA3fQ.46GUGFo03ifJwbVBHHXZqLzjpTYJI3MAbf7x1ayZUMI\"}";
//		String token = "Bearer " + extractToken(response);

		String response = suapLogin(login, password);
		String token = "Bearer " + extractToken(response);

		User user = findUserSuap(token, login);
		UserDTO userBD = userService.findDtoByLogin(login);
		if(userBD == null)
			userService.save(user);
		else
			user.setId(userBD.getId());

		Authentication auth =new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
		return auth;
	}

	private String extractToken(String json) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        String accessToken = root.get("access").asText();
		return accessToken;
	}

	public String suapLogin(String login, String password) throws IOException, InterruptedException, URISyntaxException {
		Map<String, String> body = Map.of("username", login, "password", password);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(body);

		HttpRequest url = generatePostUrl(OBTAIN_TOKEN_URL, null, json);
		return sendRequest(url);
	}

	private HttpRequest generatePostUrl(String url, Map<String, String> headers, String body) throws URISyntaxException {
		Builder builder = HttpRequest.newBuilder().uri(new URI(url));
		
		for (Map.Entry<String, String> h : DEFAULT_HEADERS.entrySet()) {
			builder.setHeader(h.getKey(), h.getValue());
		}
		
		if (headers != null) {
			for (Map.Entry<String, String> header : headers.entrySet()) {
				builder.setHeader(header.getKey(), header.getValue());
			}
		}

		HttpRequest request = builder.POST(BodyPublishers.ofString(body)).build();
		return request;
	}

	private String sendRequest(HttpRequest httRequest) throws IOException, InterruptedException {
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpResponse<String> httResponse = httpClient.send(httRequest, HttpResponse.BodyHandlers.ofString());
		String response = httResponse.body();
		System.out.println("response status" + httResponse.statusCode());
		System.out.println(response);
		return response;
	}

	private User findUserSuap(String token, String userName) throws URISyntaxException, IOException, InterruptedException, NoSuchFieldException {
		User user = findEmployee(token, userName);
		if(user == null) {
			user = findStudent(token, userName);
		}
		
		return user;
	}

	/**
	 * Tenta encontrar no SUAP um servidor com nome igual a userName
	 * 
	 * @param token
	 * @param login
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws NoSuchFieldException 
	 */
	private User findEmployee(String token, String login) throws URISyntaxException, IOException, InterruptedException, NoSuchFieldException {
		String url = String.format("%s?search=%s", EMPLOYEES_URL, login);
		String json = find(token, url);

		User user = jsonToUser(json, login);
		Role role = roleRepository.findByRole(ROLE_MANAGER);
		user.addRole(role);

		return user;
	}
	
	/**
	 * Tenta encontrar um estudante com nome igual a userName
	 * 
	 * @param token
	 * @param userName
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws NoSuchFieldException 
	 */
	private User findStudent(String token, String userName) throws URISyntaxException, IOException, InterruptedException, NoSuchFieldException {
		String url = String.format("%s?search=%s", STUDENTS_URL, userName);
		String json = find(token, url);

		User user = jsonToUser(json, userName);
		Role role = roleRepository.findByRole(ROLE_USER);
		user.addRole(role);

		return user;
	}

	private String find(String token, String findUrl) throws URISyntaxException, IOException, InterruptedException {
		HttpRequest url = generateGetUrl(findUrl, 
				Map.of(TOKEN_HEADER_NAME, String.format(token)));
		return sendRequest(url);
	}

	private HttpRequest generateGetUrl(String url, Map<String, String> headers) throws URISyntaxException {
		Builder builder = HttpRequest.newBuilder().uri(new URI(url));
		
		for (Map.Entry<String, String> h : DEFAULT_HEADERS.entrySet()) {
			builder.setHeader(h.getKey(), h.getValue());
		}

		if (headers != null) {
			for (Map.Entry<String, String> header : headers.entrySet()) {
				builder.setHeader(header.getKey(), header.getValue());
			}
		}

		HttpRequest request = builder.GET().build();
		return request;
	}

	private User jsonToUser(String json, String login) throws NoSuchFieldException, JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        String nome = null;

        // Acessa a lista "results"
        JsonNode results = root.get("results");

        // Verifica se há pelo menos um item
        if (results.isArray() && results.size() > 0) {
            JsonNode primeiroServidor = results.get(0);
            nome = primeiroServidor.get("nome").asText();
        } else {
        	throw new NoSuchFieldException("Nenhum servidor encontrado.");
        }

        String[] split = nome.split(" ");
        String firstName = split[0];
        String lastName = String.join(" ", Arrays.copyOfRange(split, 1, split.length));

        User u = new User();
        u.setLogin(login);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        return u;
	}
}
