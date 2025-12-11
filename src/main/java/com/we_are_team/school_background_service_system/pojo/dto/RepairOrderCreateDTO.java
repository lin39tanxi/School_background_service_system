package com.we_are_team.school_background_service_system.pojo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RepairOrderCreateDTO {
    private String description;
    private LocalDate startAppointmentTime;
    private LocalDate endAppointmentTime;
}