package com.yanchao.blog.dao.bas;

import com.yanchao.blog.constant.EncryTypeEnum;
import com.yanchao.blog.po.bas.EncryPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 加解密表-Dao
 * 
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 24, 2020 5:30:05 PM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 24, 2020 5:30:05 PM
 */
@Repository
public interface EncryDAO extends JpaRepository<EncryPO, Long> {

    List<EncryPO> findByDecryAndType(String decry, EncryTypeEnum type);

    List<EncryPO> findByEncryAndType(String encry, EncryTypeEnum type);
}
