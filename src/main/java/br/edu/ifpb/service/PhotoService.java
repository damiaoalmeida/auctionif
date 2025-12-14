package br.edu.ifpb.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoService {
	@Value("${path_photo}")
	private String DIRETORIO = "uploads/fotos/";

    public String salvarFoto(MultipartFile arquivo) throws IOException {
        if (arquivo.isEmpty()) {
            throw new RuntimeException("Arquivo vazio");
        }

        // Garante que a pasta existe
        Files.createDirectories(Paths.get(DIRETORIO));

        // Nome único para a foto
        String nomeArquivo = UUID.randomUUID().toString() + "_" + arquivo.getOriginalFilename();

        // Caminho final
        Path caminho = Paths.get(DIRETORIO + nomeArquivo);

        // Salvar no disco
        Files.write(caminho, arquivo.getBytes());

        return caminho.toString(); // retorne apenas o nome se quiser guardar menos info no BD
    }
}