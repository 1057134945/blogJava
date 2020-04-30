package com.yanchao.blog.vo.dic;

import java.util.Map;

import com.yanchao.blog.vo.ResponseDataVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 数据字典
 * 
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 28, 2020 10:14:55 AM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 28, 2020 10:14:55 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DicVO extends ResponseDataVO {
    private static final long serialVersionUID = 8097332430064550092L;

    private String code;
    private String name;
    private Map<String, DicVO> child;
}
