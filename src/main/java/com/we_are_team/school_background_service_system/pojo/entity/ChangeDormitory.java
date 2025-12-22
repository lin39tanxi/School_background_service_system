package com.we_are_team.school_background_service_system.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeDormitory {
    private Integer dormChangeId;
    private String studentNumber;
    private String oldDormAddress;
    private String newDormAddress;
    private String buildingId;
    private String floorId;
    private String roomId;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;
    private String reason;
    private Integer adminId;
    private Integer userId;
    private String rejectionReason;
}
