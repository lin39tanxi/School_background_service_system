package com.we_are_team.school_background_service_system.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "we.jwt")
public class JwtProperties {
//    用户端生成jwt令牌相关配置
    private String userSecretkey;
    private long  userTtl;
    private String  userTokenName;

//    管理端生成jwt令牌相关配置
    private String adminSecretkey;
    private long  adminTtl;
    private String  adminTokenName;
}
