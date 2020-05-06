package com.yanchao.blog.mapper.bas;

import com.yanchao.blog.dao.bas.EncryDAO;
import com.yanchao.blog.po.bas.EncryPO;
import com.yanchao.blog.util.EncryUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.yanchao.blog.constant.EncryTypeEnum.TEL_NO;

/**
 * 加解密表-Mapper-测试类
 *
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 24, 2020 5:34:14 PM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 24, 2020 5:34:14 PM
 */
@SpringBootTest
public class EncryServiceTest {

    @Autowired
    private EncryDAO encryDAO;

    @Test
    void save() {
        final String decry = "13263351668";
        encryDAO.save(EncryPO.builder().decry(decry).encry(EncryUtils.getMD5(decry)).type(TEL_NO).build());
    }
}
