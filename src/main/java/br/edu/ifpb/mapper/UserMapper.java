package br.edu.ifpb.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.edu.ifpb.entity.User;
import br.edu.ifpb.entity.dto.UserDTO;

@Component
//@Mapper(componentModel = "spring")
public class UserMapper {
	private final ModelMapper mapper = new ModelMapper();

	public UserDTO toDTO(User user) {
		return mapper.map(user, UserDTO.class);
//        return UserDTO.fromEntity(user);
    }

    public List<UserDTO> toDTOList(List<User> users) {
        return users.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}
