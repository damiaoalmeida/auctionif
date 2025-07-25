package br.edu.ifpb.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Commit;
import org.springframework.util.ResourceUtils;

import br.edu.ifpb.entity.User;
import br.edu.ifpb.entity.UserContent;
import jakarta.transaction.Transactional;


@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE) // Impede o uso do banco embutido
public class UserRepositoryTest {
	
	@Autowired
    private UserRepository repository;
	
	@Test
	@Commit
	@Order(1)
	void testSaveStudentPhoto() throws IOException {
		///Saving
		// Caminho para o arquivo PNG
		File file = ResourceUtils.getFile("classpath:images/marta.png");
        // Lê o arquivo como bytes
        byte[] imageBytes = Files.readAllBytes(file.toPath());

		User user = new User();
		user.setFirstName("Marta");
		user.setLastName("da Silva");

		UserContent content = new UserContent();
		content.setPhoto(imageBytes);

		user.setContent(content);

		assertNull(user.getId());
		repository.save(user);
		assertNotNull(user.getId());
	}
	
	@Test
	@Order(2)
	@Transactional
	void testReadStudentPhoto() throws IOException {		
		Iterable<User> it = repository.findAll();
		User user = it.iterator().next();

//		List<User> it = repository.findAllWithoutContent();
//		User user = it.get(0);

		System.out.println(user.getContent());

		// Gravar a foto em um arquivo
        String filePath = "src/test/resources/images/saved-image.png"; // Caminho onde a imagem será salva
        try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
            fos.write(user.getContent().getPhoto());
        }
	}
}
