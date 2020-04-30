package com.yanchao.blog.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;

/**
 * 解密
 * 
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 24, 2020 5:41:13 PM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 24, 2020 5:41:13 PM
 */
public final class DecryUtils {

    public static String getBase64(String encry) {
        String decry = null;
        if (StringUtils.isNotBlank(encry)) {
            decry = new String(Base64.getDecoder().decode(encry), StandardCharsets.UTF_8);
        }
        return decry;
    }

    private DecryUtils() {
        throw new IllegalStateException("Utility class");
    }
}
