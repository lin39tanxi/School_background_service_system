package com.we_are_team.school_background_service_system.pojo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LostAndFoundCreateDTO {
    private String itemName;
    private Integer categoryId;
    private Integer locationId;
    private String description;
    private String specificLocation;
}