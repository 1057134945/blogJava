package com.yanchao.blog.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 统一异常
 * 
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 30, 2020 2:38:26 PM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 30, 2020 2:38:26 PM
 */
@Getter
@AllArgsConstructor
public enum ErrorEnum {

    SUCCESS(0, "成功"),
    ERROR(1, "系统异常"),
    ENCRE_OR_DECRY_ERROR(2, "加解密异常"),
    WRONG_ID_NO(3, "身份证号码有误"),
    WRONG_TEL_NO(4, "手机号码有误");

    private int code;
    private String message;
}
