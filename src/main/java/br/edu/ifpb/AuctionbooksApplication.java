package br.edu.ifpb;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import br.edu.ifpb.entity.User;
import br.edu.ifpb.entity.UserContent;
import br.edu.ifpb.repository.UserRepository;

@SpringBootApplication
public class AuctionbooksApplication implements CommandLineRunner {

	@Autowired
    private UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(AuctionbooksApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
//		///Saving
//		// Caminho para o arquivo PNG
//		File file = ResourceUtils.getFile("classpath:images/marta.png");
//        // Lê o arquivo como bytes
//        byte[] imageBytes = Files.readAllBytes(file.toPath());
//
//		User user = new User();
//		user.setFirstName("Marta");
//		user.setLastName("da Silva");
//
//		UserContent content = new UserContent();
//		content.setPhoto(imageBytes);
//		user.setContent(content);
//		repository.save(user);
//
//
//		Iterable<User> it = repository.findAll();
//		user = it.iterator().next();
//
////		List<User> it = repository.findAllWithoutContent();
////		User user = it.get(0);
//
//		System.out.println(user.getContent());
//
//		// Gravar a foto em um arquivo
//        String filePath = "src/test/resources/images/saved-image.png"; // Caminho onde a imagem será salva
//        try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
//            fos.write(user.getContent().getPhoto());
//        }
	}

}
