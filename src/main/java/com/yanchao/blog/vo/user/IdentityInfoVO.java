package com.yanchao.blog.vo.user;

import java.io.Serializable;
import java.util.Date;

import com.yanchao.blog.constant.SexEnum;
import com.yanchao.blog.vo.dic.DicVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 身份证解析数据
 * 
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 27, 2020 5:24:14 PM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 27, 2020 5:24:14 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentityInfoVO implements Serializable {
    private static final long serialVersionUID = 1885424195257248036L;

    private DicVO prov;
    private DicVO city;
    private DicVO area;
    private Date birthDate;
    private SexEnum sex;
}
