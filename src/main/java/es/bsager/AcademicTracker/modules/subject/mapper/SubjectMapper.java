package es.bsager.AcademicTracker.modules.subject.mapper;

import es.bsager.AcademicTracker.modules.subject.dto.request.SubjectRequest;
import es.bsager.AcademicTracker.modules.subject.dto.response.SubjectResponse;
import es.bsager.AcademicTracker.modules.subject.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "userId", ignore = true)
    Subject toEntity(SubjectRequest request);

    SubjectResponse toResponse(Subject entity);
}
