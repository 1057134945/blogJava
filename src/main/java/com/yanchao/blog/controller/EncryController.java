package com.yanchao.blog.controller;

import com.yanchao.blog.constant.EncryTypeEnum;
import com.yanchao.blog.service.EncryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 加解密调用
 * 
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 24, 2020 6:40:57 PM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 24, 2020 6:40:57 PM
 */
@Slf4j
@Api("加解密")
@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EncryController {

    private final EncryService encryService;

    @ApiOperation(value = "加密")
    @GetMapping("encry")
    public String encry(String decry, EncryTypeEnum encryType) {
        log.info("加密");
        return encryService.encry(decry, encryType);
    }

    @ApiOperation(value = "解密")
    @GetMapping("decry")
    public String decry(String encry, EncryTypeEnum encryType) {
        log.info("解密");
        return encryService.decry(encry, encryType);
    }
}
