package br.edu.ifpb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.*;

import br.edu.ifpb.entity.StatusType;
import br.edu.ifpb.entity.User;
import br.edu.ifpb.entity.dto.UserDTO;
import br.edu.ifpb.entity.dto.UserSaveDTO;

@Mapper(componentModel = "spring")
public interface UserMapperIF {
	UserSaveDTO toDTO(User entity);

	@Mapping(source = "status", target = "status")
    User toEntity(UserSaveDTO dto);

    @ValueMappings({
        @ValueMapping(source = "Ativo", target = "ATIVO"),
        @ValueMapping(source = "Inativo", target = "INATIVO"),
        @ValueMapping(source = "Bloqueado", target = "BLOQUEADO"),
        @ValueMapping(source = "Pendente", target = "PENDENTE")
    })
    StatusType mapTipo(String value);

    @Mapping(source = "status", target = "status")
    UserDTO toEntity(User user);
}