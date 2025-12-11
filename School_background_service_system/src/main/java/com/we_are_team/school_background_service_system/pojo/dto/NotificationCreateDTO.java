package com.we_are_team.school_background_service_system.pojo.dto;

import lombok.Data;

@Data
public class NotificationCreateDTO {
    private String title;
    private String content;
    private Integer status;
}
