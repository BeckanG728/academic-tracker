package es.bsager.AcademicTracker.modules.grades.mapper;

import es.bsager.AcademicTracker.modules.grades.dto.request.RegisterGradesRequest;
import es.bsager.AcademicTracker.modules.grades.dto.response.RegisterGradesResponse;
import es.bsager.AcademicTracker.modules.grades.entity.Grades;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GradesMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subjectId", source = "subjectId")
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    Grades toEntity(RegisterGradesRequest request, UUID subjectId);

    RegisterGradesResponse toResponse(Grades grades);
}
