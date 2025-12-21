package com.we_are_team.school_background_service_system.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DormRoom {
    private Integer roomId;
    private Integer buildingId;
    private String floorNumber;
    private String roomNumber;
    private Integer currentPeople;
    private Integer maxPeople;
    private LocalDateTime createTime;
    private String studentArray;
}
