package com.we_are_team.school_background_service_system.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class GetRepairOrderVO {
    private Integer orderId;
    private String description;
    private Integer processStatus;
    private Integer rating;




}

