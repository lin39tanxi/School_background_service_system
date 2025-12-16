package com.we_are_team.school_background_service_system.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetLostAndFoundVO {
    private Integer itemId;
    private String itemName;
    private Integer status;
    private String categoryName;
    private String locationName;
    private String imageUrls;

}
