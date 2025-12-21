package com.we_are_team.school_background_service_system.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RejectionChangeDormitoryDTO {
    private Integer dormChangeId;
    private String rejectionReason;
}
