package com.we_are_team.school_background_service_system.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class dormRoom {
    private Integer roomId;
    private Integer buildingId;
    private String floorNumber;
    private String roomNumber;
    private String currentPeople;
    private String sumPeople;
    private LocalDateTime createTime;
}
