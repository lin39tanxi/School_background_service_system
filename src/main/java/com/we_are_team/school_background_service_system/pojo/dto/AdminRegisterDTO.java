package com.we_are_team.school_background_service_system.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRegisterDTO {
    private String username;
    private String password;
    private String permission;
    private String nickname;

}
