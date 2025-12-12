package com.we_are_team.school_background_service_system.result;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult {
    private long total; //总记录数

    private List records; //当前页数据集合
}
