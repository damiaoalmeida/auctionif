package br.edu.ifpb.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import br.edu.ifpb.entity.User2;


@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE) // Impede o uso do banco embutido
public class User2RepositoryTest {
	
	@Autowired
    private User2Repository repository;
	
	@Test
	@Commit
	@Order(1)
	void testSaveStudentPhoto() throws IOException {
//		///Saving
//		User2 user = new User2();
//		user.setName("Marta");
//
//		assertNull(user.getId());
//		repository.save(user);
//		assertNotNull(user.getId());
//		
//		User2 user2 = new User2();
//		user2.setName("João");
//
//		assertNull(user2.getId());
//		repository.save(user2);
//		assertNotNull(user2.getId());
	}
}
