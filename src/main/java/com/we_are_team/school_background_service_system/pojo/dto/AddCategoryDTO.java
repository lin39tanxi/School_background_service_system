package com.we_are_team.school_background_service_system.pojo.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
public class AddCategoryDTO {
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
