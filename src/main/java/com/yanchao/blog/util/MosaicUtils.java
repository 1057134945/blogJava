package com.yanchao.blog.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据掩码
 * 
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 27, 2020 3:04:38 PM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 27, 2020 3:04:38 PM
 */
public final class MosaicUtils {

    /** 双姓 */
    private static final List<String> SURNAME_LIST = Arrays.asList("欧阳", "太史", "端木", "上官", "司马", "东方", "独孤", "南宫", "万俟",
            "闻人", "夏侯", "诸葛", "尉迟", "公羊", "赫连", "澹台", "皇甫", "宗政", "濮阳", "公冶", "太叔", "申屠", "公孙", "慕容", "仲孙", "钟离", "长孙",
            "宇文", "城池", "司徒", "鲜于", "司空", "汝嫣", "闾丘", "子车", "亓官", "司寇", "巫马", "公西", "颛孙", "壤驷", "公良", "漆雕", "乐正", "宰父",
            "谷梁", "拓跋", "夹谷", "轩辕", "令狐", "段干", "百里", "呼延", "东郭", "南门", "羊舌", "微生", "公户", "公玉", "公仪", "梁丘", "公仲", "公上",
            "公门", "公山", "公坚", "左丘", "公伯", "西门", "公祖", "第五", "公乘", "贯丘", "公皙", "南荣", "东里", "东宫", "仲长", "子书", "子桑", "即墨",
            "达奚", "褚师");

    public static String mosaicTelNo(String telNo) {
        String telNoHid = null;
        if (telNo.length() == 11) {
            telNoHid = telNo.replaceAll("(?<=[\\\\d]{3})\\\\d(?=[\\\\d]{4})", "*");
        }
        return telNoHid;
    }

    public static String mosaicIdNo(String idNo) {
        String idNoHid = null;
        if (idNo.length() == 18) {
            idNoHid = idNo.replaceAll("(?<=[\\\\d]{4})\\\\d(?=[\\\\d]{4})", "*");
        }
        return idNoHid;
    }

    public static String mosaicUserName(String userName) {
        String userNameHid = null;
        if (userName.length() == 2) {
            userNameHid = StringUtils.rightPad(StringUtils.left(userName, 1), userName.length(), "*");
        } else if (userName.length() > 2) {
            if (SURNAME_LIST.contains(userName.substring(0, 2))) {
                userNameHid = StringUtils.rightPad(StringUtils.left(userName, 2), userName.length(), "*");
            } else {
                userNameHid = StringUtils.rightPad(StringUtils.left(userName, 1), userName.length(), "*");
            }
        }
        return userNameHid;
    }

    private MosaicUtils() {
        throw new IllegalStateException("Utility class");
    }
}
