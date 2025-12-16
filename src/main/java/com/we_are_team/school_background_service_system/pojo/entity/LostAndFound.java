package com.we_are_team.school_background_service_system.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LostAndFound {
    private Integer itemId;
    private String itemName;
    private Integer categoryId;
    private Integer locationId;
    private String description;
    private String imageUrls;
    private Integer status;
    private Integer adminId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;
    private String locationName;
    private String categoryName;

}