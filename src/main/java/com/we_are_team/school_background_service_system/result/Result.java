package com.we_are_team.school_background_service_system.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {
    private String code;  // 状态码：200为成功
    private String message;
    private T data;

    /**
     * 成功,且无数据返回值
     * @param message
     * @return
     */
    public static <T> Result<T> success(String message){
        Result<T> result = new Result<>();
        result.code = "200";
        result.message = message;
        return result;
    }
    /**
     * 成功,有数据返回值
     * @param message
     * @param data
     * @return
     */
    public static <T> Result<T> success(String message, T data){
        Result<T> result = new Result<>();
        result.code = "200";
        result.message = message;
        result.data = data;
        return result;
    }
  /**
     * 失败
     * @param message
     * @return
     */
    public static <T> Result<T> error(String message){
        Result<T> result = new Result<>();
        result.code = "500";
        result.message = message;
        return result;
    }


}

