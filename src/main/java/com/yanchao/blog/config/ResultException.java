package com.yanchao.blog.config;

import com.yanchao.blog.constant.ErrorEnum;
import com.yanchao.blog.vo.ResponseDataVO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 统一异常
 * 
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 30, 2020 1:59:30 PM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 30, 2020 1:59:30 PM
 */
@Getter
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ResultException extends RuntimeException {
    private static final long serialVersionUID = 971287796314593497L;

    private final int code;
    private final String message;
    private final ResponseDataVO data;

    public ResultException(ErrorEnum error) {
        this(error, null);
    }

    private ResultException(ErrorEnum error, ResponseDataVO data) {
        this(error.getCode(), error.getMessage(), data);
    }
}
