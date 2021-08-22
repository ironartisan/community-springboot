package com.halo.blog.dto;

import com.halo.blog.exception.CustomizeErrorCode;
import com.halo.blog.exception.CustomizeException;
import lombok.Data;

/**
 * Created by halo on 2019/8/9.
 */

@Data
public class ResultDTO<T> {
    private String message;
    private Integer code;
    private T data;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功 ");
        return resultDTO;

    }

    public static <T> ResultDTO okOf(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功 ");
        resultDTO.setData(t);
        return resultDTO;

    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(), e.getMessage());

    }
}