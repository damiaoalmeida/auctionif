package br.edu.ifpb.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.edu.ifpb.entity.User;
import br.edu.ifpb.entity.dto.UserDTO;

@Component
public class UserMapper {
	public UserDTO toDTO(User user) {
        return UserDTO.fromEntity(user);
    }

    public List<UserDTO> toDTOList(List<User> users) {
        return users.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}
