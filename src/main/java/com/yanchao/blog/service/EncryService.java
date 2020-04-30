package com.yanchao.blog.service;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yanchao.blog.config.ResultException;
import com.yanchao.blog.constant.EncryTypeEnum;
import com.yanchao.blog.constant.ErrorEnum;
import com.yanchao.blog.constant.SystemConstant;
import com.yanchao.blog.dao.bas.EncryDAO;
import com.yanchao.blog.po.bas.EncryPO;
import com.yanchao.blog.util.EncryUtils;
import com.yanchao.blog.util.IdentityUtils;

/**
 * 加解密
 * 
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 27, 2020 5:00:01 PM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 27, 2020 5:00:01 PM
 */
@Service
public class EncryService {

    @Autowired
    private EncryDAO encryDAO;

    /**
     * 加密
     * 
     * @param decry
     * @param encryType
     * @return
     */
    public String encry(String decry, EncryTypeEnum encryType) {
        switch (encryType) {
            case ID_NO:
                if (!IdentityUtils.check(decry)) {
                    throw new ResultException(ErrorEnum.WRONG_ID_NO);
                }
                break;
            case TEL_NO:
                if (!Pattern.matches("^1[3-9]\\d{9}$", decry)) {
                    throw new ResultException(ErrorEnum.WRONG_TEL_NO);
                }
                break;
            default:
                throw new ResultException(ErrorEnum.ENCRE_OR_DECRY_ERROR);
        }

        List<EncryPO> encryPOs = encryDAO.findByDecryAndType(decry, encryType);

        if (encryPOs == null || encryPOs.isEmpty()) {
            String encry = EncryUtils.getMD5(decry);
            encryDAO.save(EncryPO.builder().decry(decry).encry(encry).type(encryType).createTime(new Date())
                    .creator(SystemConstant.SYSTEM_NAME).build());
            return encry;
        }

        if (encryPOs.size() > 1) {
            throw new ResultException(ErrorEnum.ENCRE_OR_DECRY_ERROR);
        }

        return encryPOs.get(0).getEncry();
    }

    /**
     * 解密
     * 
     * @param encry
     * @param encryType
     * @return
     */
    public String decry(String encry, EncryTypeEnum encryType) {
        List<EncryPO> encryPOs = encryDAO.findByDecryAndType(encry, encryType);

        if (encryPOs == null || encryPOs.size() > 1) {
            throw new ResultException(ErrorEnum.ENCRE_OR_DECRY_ERROR);
        }

        return encryPOs.get(0).getDecry();
    }
}
