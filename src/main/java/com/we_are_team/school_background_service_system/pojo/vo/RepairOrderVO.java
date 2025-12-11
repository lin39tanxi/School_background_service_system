package com.we_are_team.school_background_service_system.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RepairOrderVO {
//    还得添加一个学号
    private Integer orderId;
    private Integer userId;
    private String username;
    private String nickname;
    private String dormitoryAddress;
    private String description;
    private LocalDate startAppointmentTime;
    private LocalDate endAppointmentTime;
    private Integer processStatus;
    private String processStatusText;
    private Integer adminId;
    private String adminName;
    private String rejectReason;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completedTime;
    private String[] imageUrls;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;
    private RepairEvaluationVO evaluation;
}