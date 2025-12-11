package com.we_are_team.school_background_service_system.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Student {
    private Integer studentId;
    private String name;
    private String studentNumber;
    private String gender;
    private String className;
    private LocalDate birthDate;
    private String building;
    private String dormitory;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
