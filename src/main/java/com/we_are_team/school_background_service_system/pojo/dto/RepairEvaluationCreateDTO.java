package com.we_are_team.school_background_service_system.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RepairEvaluationCreateDTO {
    private Integer orderId;
    private Integer rating;
    private String comment;
}