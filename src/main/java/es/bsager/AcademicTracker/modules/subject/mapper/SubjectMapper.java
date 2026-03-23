package es.bsager.AcademicTracker.modules.subject.mapper;

import es.bsager.AcademicTracker.modules.subject.dto.request.SubjectRequest;
import es.bsager.AcademicTracker.modules.subject.dto.response.SubjectResponse;
import es.bsager.AcademicTracker.modules.subject.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "userId", source = "userId")
    Subject toEntity(SubjectRequest request, UUID userId);

    @Mapping(target = "status", expression = "java(entity.getStatus().name())")
    SubjectResponse toResponse(Subject entity);
}
