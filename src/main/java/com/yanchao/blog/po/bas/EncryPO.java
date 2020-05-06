package com.yanchao.blog.po.bas;

import com.yanchao.blog.constant.EncryTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 加解密表-实体
 * 
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 24, 2020 5:08:47 PM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 24, 2020 5:08:47 PM
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bas_encry", uniqueConstraints = { @UniqueConstraint(columnNames = { "encry", "type" }),
        @UniqueConstraint(columnNames = "encry"), @UniqueConstraint(columnNames = "decry"),
        @UniqueConstraint(columnNames = { "decry", "type" }) })
public class EncryPO implements Serializable {
    private static final long serialVersionUID = -2118148820663319937L;

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private Long id;
    /** 密文 */
    @Column(name = "encry", nullable = false, updatable = false)
    private String encry;
    /** 明文 */
    @Column(name = "decry", nullable = false, updatable = false)
    private String decry;
    /** 加密类型 */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private EncryTypeEnum type;
    /** 创建时间 */
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;
    /** 创建人 */
    @Column(name = "creator", nullable = false, updatable = false)
    private String creator;
    /** 修改时间 */
    @Column(name = "update_time")
    private Date updateTime;
    /** 修改人 */
    @Column(name = "updator")
    private String updator;
}
