package es.bsager.AcademicTracker.modules.auth.mapper;

import es.bsager.AcademicTracker.modules.auth.dto.request.RegisterRequest;
import es.bsager.AcademicTracker.modules.auth.dto.response.RegisterResponse;
import es.bsager.AcademicTracker.modules.auth.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", constant = "STUDENT")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserEntity toEntity(RegisterRequest request);
    
    @Mapping(target = "role", expression = "java(entity.getRole().name())")
    @Mapping(target = "status", expression = "java(entity.getStatus().name())")
    RegisterResponse toRegisterResponse(UserEntity entity);
}
