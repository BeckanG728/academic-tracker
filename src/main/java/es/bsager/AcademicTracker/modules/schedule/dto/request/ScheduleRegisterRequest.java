package es.bsager.AcademicTracker.modules.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record ScheduleRegisterRequest(
        @NotNull(message = "El dia de la semana es obligatorio")
        DayOfWeek dayOfWeek,

        @NotNull(message = "La hora de inicio es obligatoria")
        LocalTime startTime,

        @NotNull(message = "La hora de finalizacion es obligatoria")
        LocalTime endTime,

        @NotBlank(message = "El nombre del salon es obligatorio")
        String classroom
) {
}
