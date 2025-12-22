package com.we_are_team.school_background_service_system.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationForm {
    private Integer id;
    private String  title;
    private String content;
    private Integer status;
    private String  createdTime;
    private String  updatedTime;
    private Integer type;
}
