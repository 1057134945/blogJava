package com.yanchao.blog.vo;

import com.yanchao.blog.constant.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

import static com.yanchao.blog.constant.ErrorEnum.ERROR;
import static com.yanchao.blog.constant.ErrorEnum.SUCCESS;

/**
 * 统一返回值
 *
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 27, 2020 5:32:40 PM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 27, 2020 5:32:40 PM
 */
@Data
@AllArgsConstructor
public class ResponseVO<T extends ResponseDataVO> implements Serializable {
    private static final long serialVersionUID = -6573078433807089391L;

    private int code;
    private String message;
    private T data;

    private ResponseVO(ErrorEnum error) {
        this(error, null);
    }

    private ResponseVO(ErrorEnum error, T data) {
        this(error.getCode(), error.getMessage(), data);
    }

    public static <T extends ResponseDataVO> ResponseVO<T> success(T data) {
        return new ResponseVO<>(SUCCESS, data);
    }

    public static <T extends ResponseDataVO> ResponseVO<T> result(ErrorEnum error, T data) {
        return new ResponseVO<>(error, data);
    }

    public static <T extends ResponseDataVO> ResponseVO<T> error() {
        return new ResponseVO<>(ERROR);
    }
}
