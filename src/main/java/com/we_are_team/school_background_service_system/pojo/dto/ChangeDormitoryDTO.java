package com.we_are_team.school_background_service_system.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeDormitoryDTO {
    private Integer dormChangeId;
    private String studentNumber;
    private String oldDormAddress;
    private String newDormAddress;
    private String buildingId;
    private String floorId;
    private String roomId;
    private String reason;

}
