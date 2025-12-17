package com.we_are_team.school_background_service_system.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePermissionDTO {
    private String newPermission;
    private String userId;
}
