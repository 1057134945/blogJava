package com.yanchao.blog.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 加密
 * 
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 24, 2020 5:41:13 PM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 24, 2020 5:41:13 PM
 */
@Slf4j
public final class EncryUtils {

    public static String getMD5(String decry) {
        String encry = null;
        if (StringUtils.isNotBlank(decry)) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                md5.update(decry.getBytes(StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                for (byte b : md5.digest()) {
                    b += 256;
                    sb.append(b);
                }
                encry = sb.toString();
            } catch (NoSuchAlgorithmException e) {
                log.error("MD5加密异常", e);
            }
        }
        return encry;
    }

    public static String getBase64(String decry) {
        String encry = null;
        if (StringUtils.isNotBlank(decry)) {
            encry = Base64.getEncoder().encodeToString(decry.getBytes(StandardCharsets.UTF_8));
        }
        return encry;
    }

    private EncryUtils() {
        throw new IllegalStateException("Utility class");
    }
}
