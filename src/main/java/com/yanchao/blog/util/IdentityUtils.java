package com.yanchao.blog.util;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.yanchao.blog.constant.DateFormatConstant;
import com.yanchao.blog.constant.SexEnum;
import com.yanchao.blog.vo.dic.DicVO;
import com.yanchao.blog.vo.user.IdentityInfoVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 身份证相关工具类
 * 
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 27, 2020 5:12:13 PM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 27, 2020 5:12:13 PM
 */
@Slf4j
public final class IdentityUtils {

    /** 身份证校验码 */
    private static final int[] COEFFICIENT_ARRAY = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
    /** 身份证号的尾数规则 */
    private static final String[] IDENTITY_MANTISSA = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
    /** 身份证号的正则表达式 */
    private static final String IDENTITY_PATTERN = "^[0-9]{17}[0-9Xx]$";
    /** 身份证前六位对应地址 */
    private static final Map<String, DicVO> ADDRESS_BY_IDENTITY = new HashMap<>();

    public static boolean check(String idNo) {
        if (StringUtils.isBlank(idNo) || idNo.length() != 18 || !idNo.matches(IDENTITY_PATTERN)) {
            return false;
        }

        return idNo.substring(17)
                .equalsIgnoreCase(IDENTITY_MANTISSA[(int) (IntStream.range(0, 17)
                        .map(o -> Character.digit(idNo.toCharArray()[o], 10) * COEFFICIENT_ARRAY[o]).summaryStatistics()
                        .getSum() % 11)]);
    }

    public static IdentityInfoVO analysis(String idNo) {
        if (!check(idNo)) {
            // TODO 异常
        }

        Date birthDate = null;
        try {
            birthDate = DateUtils.parseDate(idNo.substring(6, 14), DateFormatConstant.NOTHING_BY_DATE);
        } catch (ParseException e) {
            log.error("时间格式转换异常", e);
        }

        IdentityInfoVO idInfo = IdentityInfoVO.builder().birthDate(birthDate)
                .sex((Integer.parseInt(idNo.substring(16, 17)) % 2 != 0) ? SexEnum.MALE : SexEnum.FEMALE).build();
        idInfo.setProv(ADDRESS_BY_IDENTITY.get(idNo.substring(0, 2)));
        if (idInfo.getProv() != null) {
            idInfo.setCity(idInfo.getProv().getChild().get(idNo.substring(2, 4)));
            if (idInfo.getCity() != null) {
                idInfo.setArea(idInfo.getCity().getChild().get(idNo.substring(2, 4)));
            }
        }
        return idInfo;
    }

    private IdentityUtils() {
        throw new IllegalStateException("Utility class");
    }

    static {
        ADDRESS_BY_IDENTITY.put("81", DicVO.builder().code("81").name("香港特别行政区").build());
        ADDRESS_BY_IDENTITY.put("71", DicVO.builder().code("71").name("台湾省").build());
        xinJiang(ADDRESS_BY_IDENTITY);
        ningXia(ADDRESS_BY_IDENTITY);
        qingHai(ADDRESS_BY_IDENTITY);
        ganSu(ADDRESS_BY_IDENTITY);
        shanXi(ADDRESS_BY_IDENTITY);
        xiZang(ADDRESS_BY_IDENTITY);
        yunNan(ADDRESS_BY_IDENTITY);
        guiZhou(ADDRESS_BY_IDENTITY);
        siChuan(ADDRESS_BY_IDENTITY);
        chongQin(ADDRESS_BY_IDENTITY);
        haiNan(ADDRESS_BY_IDENTITY);
        guanXi(ADDRESS_BY_IDENTITY);
        guangdong(ADDRESS_BY_IDENTITY);
        huNan(ADDRESS_BY_IDENTITY);
        huBei(ADDRESS_BY_IDENTITY);
        heNan(ADDRESS_BY_IDENTITY);
        shanDong(ADDRESS_BY_IDENTITY);
        jiangXi(ADDRESS_BY_IDENTITY);
        fuJian(ADDRESS_BY_IDENTITY);
        anHui(ADDRESS_BY_IDENTITY);
        zheJiang(ADDRESS_BY_IDENTITY);
        jiangSu(ADDRESS_BY_IDENTITY);
        shangHai(ADDRESS_BY_IDENTITY);
        heiLongJiang(ADDRESS_BY_IDENTITY);
        jiLin(ADDRESS_BY_IDENTITY);
        liaoNing(ADDRESS_BY_IDENTITY);
        neiMengGu(ADDRESS_BY_IDENTITY);
        heBei(ADDRESS_BY_IDENTITY);
        tianJin(ADDRESS_BY_IDENTITY);
        beiJing(ADDRESS_BY_IDENTITY);
    }

    private static void beiJing(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("29", DicVO.builder().code("29").name("延庆县").build());
        area.put("28", DicVO.builder().code("28").name("密云县").build());
        city.put("02", DicVO.builder().code("02").name("北京县").child(area).build());

        area = new HashMap<>();
        area.put("17", DicVO.builder().code("17").name("平谷区").build());
        area.put("16", DicVO.builder().code("16").name("怀柔区").build());
        area.put("15", DicVO.builder().code("15").name("大兴区").build());
        area.put("14", DicVO.builder().code("14").name("昌平区").build());
        area.put("13", DicVO.builder().code("13").name("顺义区").build());
        area.put("12", DicVO.builder().code("12").name("通州区").build());
        area.put("11", DicVO.builder().code("11").name("房山区").build());
        area.put("09", DicVO.builder().code("09").name("门头沟区").build());
        area.put("08", DicVO.builder().code("08").name("海淀区").build());
        area.put("07", DicVO.builder().code("07").name("石景山区").build());
        area.put("06", DicVO.builder().code("06").name("丰台区").build());
        area.put("05", DicVO.builder().code("05").name("朝阳区").build());
        area.put("04", DicVO.builder().code("04").name("宣武区").build());
        area.put("03", DicVO.builder().code("03").name("崇文区").build());
        area.put("02", DicVO.builder().code("02").name("西城区").build());
        area.put("01", DicVO.builder().code("01").name("东城区").build());
        city.put("01", DicVO.builder().code("01").name("市辖区").child(area).build());
        prov.put("11", DicVO.builder().code("11").name("北京市").child(city).build());
    }

    private static void tianJin(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("25", DicVO.builder().code("25").name("蓟县").build());
        area.put("23", DicVO.builder().code("23").name("静海县").build());
        area.put("21", DicVO.builder().code("21").name("宁河县").build());
        city.put("02", DicVO.builder().code("02").name("天津县").child(area).build());

        area = new HashMap<>();
        area.put("15", DicVO.builder().code("15").name("宝坻区").build());
        area.put("14", DicVO.builder().code("14").name("武清区").build());
        area.put("13", DicVO.builder().code("13").name("北辰区").build());
        area.put("12", DicVO.builder().code("12").name("津南区").build());
        area.put("11", DicVO.builder().code("11").name("西青区").build());
        area.put("10", DicVO.builder().code("10").name("东丽区").build());
        area.put("09", DicVO.builder().code("09").name("大港区").build());
        area.put("08", DicVO.builder().code("08").name("汉沽区").build());
        area.put("07", DicVO.builder().code("07").name("塘沽区").build());
        area.put("06", DicVO.builder().code("06").name("红桥区").build());
        area.put("05", DicVO.builder().code("05").name("河北区").build());
        area.put("04", DicVO.builder().code("04").name("南开区").build());
        area.put("03", DicVO.builder().code("03").name("河西区").build());
        area.put("02", DicVO.builder().code("02").name("河东区").build());
        area.put("01", DicVO.builder().code("01").name("和平区").build());
        city.put("01", DicVO.builder().code("01").name("市辖区").child(area).build());
        prov.put("12", DicVO.builder().code("12").name("天津市").child(city).build());
    }

    private static void heBei(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("82", DicVO.builder().code("82").name("深州市").build());
        area.put("81", DicVO.builder().code("81").name("冀州市").build());
        area.put("28", DicVO.builder().code("28").name("阜城县").build());
        area.put("27", DicVO.builder().code("27").name("景县").build());
        area.put("26", DicVO.builder().code("26").name("故城县").build());
        area.put("25", DicVO.builder().code("25").name("安平县").build());
        area.put("24", DicVO.builder().code("24").name("饶阳县").build());
        area.put("23", DicVO.builder().code("23").name("武强县").build());
        area.put("22", DicVO.builder().code("22").name("武邑县").build());
        area.put("21", DicVO.builder().code("21").name("枣强县").build());
        area.put("02", DicVO.builder().code("02").name("桃城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("11", DicVO.builder().code("11").name("衡水市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("三河市").build());
        area.put("81", DicVO.builder().code("81").name("霸州市").build());
        area.put("28", DicVO.builder().code("28").name("大厂回族自治县").build());
        area.put("26", DicVO.builder().code("26").name("文安县").build());
        area.put("25", DicVO.builder().code("25").name("大城县").build());
        area.put("24", DicVO.builder().code("24").name("香河县").build());
        area.put("23", DicVO.builder().code("23").name("永清县").build());
        area.put("22", DicVO.builder().code("22").name("固安县").build());
        area.put("03", DicVO.builder().code("03").name("广阳区").build());
        area.put("02", DicVO.builder().code("02").name("安次区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("10", DicVO.builder().code("10").name("廊坊市").child(area).build());

        area = new HashMap<>();
        area.put("84", DicVO.builder().code("84").name("河间市").build());
        area.put("83", DicVO.builder().code("83").name("黄骅市").build());
        area.put("82", DicVO.builder().code("82").name("任丘市").build());
        area.put("81", DicVO.builder().code("81").name("泊头市").build());
        area.put("30", DicVO.builder().code("30").name("孟村回族自治县").build());
        area.put("29", DicVO.builder().code("29").name("献县").build());
        area.put("28", DicVO.builder().code("28").name("吴桥县").build());
        area.put("27", DicVO.builder().code("27").name("南皮县").build());
        area.put("26", DicVO.builder().code("26").name("肃宁县").build());
        area.put("25", DicVO.builder().code("25").name("盐山县").build());
        area.put("24", DicVO.builder().code("24").name("海兴县").build());
        area.put("23", DicVO.builder().code("23").name("东光县").build());
        area.put("22", DicVO.builder().code("22").name("青县").build());
        area.put("21", DicVO.builder().code("21").name("沧县").build());
        area.put("03", DicVO.builder().code("03").name("运河区").build());
        area.put("02", DicVO.builder().code("02").name("新华区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("沧州市").child(area).build());

        area = new HashMap<>();
        area.put("28", DicVO.builder().code("28").name("围场满族蒙古族自治县").build());
        area.put("27", DicVO.builder().code("27").name("宽城满族自治县").build());
        area.put("26", DicVO.builder().code("26").name("丰宁满族自治县").build());
        area.put("25", DicVO.builder().code("25").name("隆化县").build());
        area.put("24", DicVO.builder().code("24").name("滦平县").build());
        area.put("23", DicVO.builder().code("23").name("平泉县").build());
        area.put("22", DicVO.builder().code("22").name("兴隆县").build());
        area.put("21", DicVO.builder().code("21").name("承德县").build());
        area.put("04", DicVO.builder().code("04").name("鹰手营子矿区").build());
        area.put("03", DicVO.builder().code("03").name("双滦区").build());
        area.put("02", DicVO.builder().code("02").name("双桥区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("承德市").child(area).build());

        area = new HashMap<>();
        area.put("33", DicVO.builder().code("33").name("崇礼县").build());
        area.put("32", DicVO.builder().code("32").name("赤城县").build());
        area.put("31", DicVO.builder().code("31").name("涿鹿县").build());
        area.put("30", DicVO.builder().code("30").name("怀来县").build());
        area.put("29", DicVO.builder().code("29").name("万全县").build());
        area.put("28", DicVO.builder().code("28").name("怀安县").build());
        area.put("27", DicVO.builder().code("27").name("阳原县").build());
        area.put("26", DicVO.builder().code("26").name("蔚县").build());
        area.put("25", DicVO.builder().code("25").name("尚义县").build());
        area.put("24", DicVO.builder().code("24").name("沽源县").build());
        area.put("23", DicVO.builder().code("23").name("康保县").build());
        area.put("22", DicVO.builder().code("22").name("张北县").build());
        area.put("21", DicVO.builder().code("21").name("宣化县").build());
        area.put("06", DicVO.builder().code("06").name("下花园区").build());
        area.put("05", DicVO.builder().code("05").name("宣化区").build());
        area.put("03", DicVO.builder().code("03").name("桥西区").build());
        area.put("02", DicVO.builder().code("02").name("桥东区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("张家口市").child(area).build());

        area = new HashMap<>();
        area.put("84", DicVO.builder().code("84").name("高碑店市").build());
        area.put("83", DicVO.builder().code("83").name("安国市").build());
        area.put("82", DicVO.builder().code("82").name("定州市").build());
        area.put("81", DicVO.builder().code("81").name("涿州市").build());
        area.put("38", DicVO.builder().code("38").name("雄县").build());
        area.put("37", DicVO.builder().code("37").name("博野县").build());
        area.put("36", DicVO.builder().code("36").name("顺平县").build());
        area.put("35", DicVO.builder().code("35").name("蠡县").build());
        area.put("34", DicVO.builder().code("34").name("曲阳县").build());
        area.put("32", DicVO.builder().code("32").name("安新县").build());
        area.put("31", DicVO.builder().code("31").name("望都县").build());
        area.put("30", DicVO.builder().code("30").name("涞源县").build());
        area.put("29", DicVO.builder().code("29").name("容城县").build());
        area.put("28", DicVO.builder().code("28").name("高阳县").build());
        area.put("27", DicVO.builder().code("27").name("唐县").build());
        area.put("26", DicVO.builder().code("26").name("定兴县").build());
        area.put("25", DicVO.builder().code("25").name("徐水县").build());
        area.put("24", DicVO.builder().code("24").name("阜平县").build());
        area.put("23", DicVO.builder().code("23").name("涞水县").build());
        area.put("22", DicVO.builder().code("22").name("清苑县").build());
        area.put("21", DicVO.builder().code("21").name("满城县").build());
        area.put("04", DicVO.builder().code("04").name("南市区").build());
        area.put("03", DicVO.builder().code("03").name("北市区").build());
        area.put("02", DicVO.builder().code("02").name("新市区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("保定市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("沙河市").build());
        area.put("81", DicVO.builder().code("81").name("南宫市").build());
        area.put("35", DicVO.builder().code("35").name("临西县").build());
        area.put("34", DicVO.builder().code("34").name("清河县").build());
        area.put("33", DicVO.builder().code("33").name("威县").build());
        area.put("32", DicVO.builder().code("32").name("平乡县").build());
        area.put("31", DicVO.builder().code("31").name("广宗县").build());
        area.put("30", DicVO.builder().code("30").name("新河县").build());
        area.put("29", DicVO.builder().code("29").name("巨鹿县").build());
        area.put("28", DicVO.builder().code("28").name("宁晋县").build());
        area.put("27", DicVO.builder().code("27").name("南和县").build());
        area.put("26", DicVO.builder().code("26").name("任县").build());
        area.put("25", DicVO.builder().code("25").name("隆尧县").build());
        area.put("24", DicVO.builder().code("24").name("柏乡县").build());
        area.put("23", DicVO.builder().code("23").name("内丘县").build());
        area.put("22", DicVO.builder().code("22").name("临城县").build());
        area.put("21", DicVO.builder().code("21").name("邢台县").build());
        area.put("03", DicVO.builder().code("03").name("桥西区").build());
        area.put("02", DicVO.builder().code("02").name("桥东区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("邢台市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("武安市").build());
        area.put("35", DicVO.builder().code("35").name("曲周县").build());
        area.put("34", DicVO.builder().code("34").name("魏县").build());
        area.put("33", DicVO.builder().code("33").name("馆陶县").build());
        area.put("32", DicVO.builder().code("32").name("广平县").build());
        area.put("31", DicVO.builder().code("31").name("鸡泽县").build());
        area.put("30", DicVO.builder().code("30").name("邱县").build());
        area.put("29", DicVO.builder().code("29").name("永年县").build());
        area.put("28", DicVO.builder().code("28").name("肥乡县").build());
        area.put("27", DicVO.builder().code("27").name("磁县").build());
        area.put("26", DicVO.builder().code("26").name("涉县").build());
        area.put("25", DicVO.builder().code("25").name("大名县").build());
        area.put("24", DicVO.builder().code("24").name("成安县").build());
        area.put("23", DicVO.builder().code("23").name("临漳县").build());
        area.put("21", DicVO.builder().code("21").name("邯郸县").build());
        area.put("06", DicVO.builder().code("06").name("峰峰矿区").build());
        area.put("04", DicVO.builder().code("04").name("复兴区").build());
        area.put("03", DicVO.builder().code("03").name("丛台区").build());
        area.put("02", DicVO.builder().code("02").name("邯山区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("邯郸市").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("卢龙县").build());
        area.put("23", DicVO.builder().code("23").name("抚宁县").build());
        area.put("22", DicVO.builder().code("22").name("昌黎县").build());
        area.put("21", DicVO.builder().code("21").name("青龙满族自治县").build());
        area.put("04", DicVO.builder().code("04").name("北戴河区").build());
        area.put("03", DicVO.builder().code("03").name("山海关区").build());
        area.put("02", DicVO.builder().code("02").name("海港区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("秦皇岛市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("迁安市").build());
        area.put("81", DicVO.builder().code("81").name("遵化市").build());
        area.put("30", DicVO.builder().code("30").name("唐海县").build());
        area.put("29", DicVO.builder().code("29").name("玉田县").build());
        area.put("27", DicVO.builder().code("27").name("迁西县").build());
        area.put("25", DicVO.builder().code("25").name("乐亭县").build());
        area.put("24", DicVO.builder().code("24").name("滦南县").build());
        area.put("23", DicVO.builder().code("23").name("滦县").build());
        area.put("08", DicVO.builder().code("08").name("丰润区").build());
        area.put("07", DicVO.builder().code("07").name("丰南区").build());
        area.put("05", DicVO.builder().code("05").name("开平区").build());
        area.put("04", DicVO.builder().code("04").name("古冶区").build());
        area.put("03", DicVO.builder().code("03").name("路北区").build());
        area.put("02", DicVO.builder().code("02").name("路南区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("唐山市").child(area).build());

        area = new HashMap<>();
        area.put("85", DicVO.builder().code("85").name("鹿泉市").build());
        area.put("84", DicVO.builder().code("84").name("新乐市").build());
        area.put("83", DicVO.builder().code("83").name("晋州市").build());
        area.put("82", DicVO.builder().code("82").name("藁城市").build());
        area.put("81", DicVO.builder().code("81").name("辛集市").build());
        area.put("33", DicVO.builder().code("33").name("赵县").build());
        area.put("32", DicVO.builder().code("32").name("元氏县").build());
        area.put("31", DicVO.builder().code("31").name("平山县").build());
        area.put("30", DicVO.builder().code("30").name("无极县").build());
        area.put("29", DicVO.builder().code("29").name("赞皇县").build());
        area.put("28", DicVO.builder().code("28").name("深泽县").build());
        area.put("27", DicVO.builder().code("27").name("高邑县").build());
        area.put("26", DicVO.builder().code("26").name("灵寿县").build());
        area.put("25", DicVO.builder().code("25").name("行唐县").build());
        area.put("24", DicVO.builder().code("24").name("栾城县").build());
        area.put("23", DicVO.builder().code("23").name("正定县").build());
        area.put("21", DicVO.builder().code("21").name("井陉县").build());
        area.put("08", DicVO.builder().code("08").name("裕华区").build());
        area.put("07", DicVO.builder().code("07").name("井陉矿区").build());
        area.put("05", DicVO.builder().code("05").name("新华区").build());
        area.put("04", DicVO.builder().code("04").name("桥西区").build());
        area.put("03", DicVO.builder().code("03").name("桥东区").build());
        area.put("02", DicVO.builder().code("02").name("长安区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("石家庄市").child(area).build());
        prov.put("13", DicVO.builder().code("13").name("河北省").child(city).build());
    }

    private static void neiMengGu(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("23", DicVO.builder().code("23").name("额济纳旗").build());
        area.put("22", DicVO.builder().code("22").name("阿拉善右旗").build());
        area.put("21", DicVO.builder().code("21").name("阿拉善左旗").build());
        city.put("29", DicVO.builder().code("29").name("阿拉善盟").child(area).build());

        area = new HashMap<>();
        city.put("26", DicVO.builder().code("26").name("乌兰察布盟").child(area).build());

        area = new HashMap<>();
        area.put("31", DicVO.builder().code("31").name("多伦县").build());
        area.put("30", DicVO.builder().code("30").name("正蓝旗").build());
        area.put("29", DicVO.builder().code("29").name("正镶白旗").build());
        area.put("28", DicVO.builder().code("28").name("镶黄旗").build());
        area.put("27", DicVO.builder().code("27").name("太仆寺旗").build());
        area.put("26", DicVO.builder().code("26").name("西乌珠穆沁旗").build());
        area.put("25", DicVO.builder().code("25").name("东乌珠穆沁旗").build());
        area.put("24", DicVO.builder().code("24").name("苏尼特右旗").build());
        area.put("23", DicVO.builder().code("23").name("苏尼特左旗").build());
        area.put("22", DicVO.builder().code("22").name("阿巴嘎旗").build());
        area.put("02", DicVO.builder().code("02").name("锡林浩特市").build());
        area.put("01", DicVO.builder().code("01").name("二连浩特市").build());
        city.put("25", DicVO.builder().code("25").name("锡林郭勒盟").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("突泉县").build());
        area.put("23", DicVO.builder().code("23").name("扎赉特旗").build());
        area.put("22", DicVO.builder().code("22").name("科尔沁右翼中旗").build());
        area.put("21", DicVO.builder().code("21").name("科尔沁右翼前旗").build());
        area.put("02", DicVO.builder().code("02").name("阿尔山市").build());
        area.put("01", DicVO.builder().code("01").name("乌兰浩特市").build());
        city.put("22", DicVO.builder().code("22").name("兴安盟").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("丰镇市").build());
        area.put("29", DicVO.builder().code("29").name("四子王旗").build());
        area.put("28", DicVO.builder().code("28").name("察哈尔右翼后").build());
        area.put("27", DicVO.builder().code("27").name("察哈尔右翼中旗").build());
        area.put("26", DicVO.builder().code("26").name("察哈尔右翼前").build());
        area.put("25", DicVO.builder().code("25").name("凉城县").build());
        area.put("24", DicVO.builder().code("24").name("兴和县").build());
        area.put("23", DicVO.builder().code("23").name("商都县").build());
        area.put("22", DicVO.builder().code("22").name("化德县").build());
        area.put("21", DicVO.builder().code("21").name("卓资县").build());
        area.put("02", DicVO.builder().code("02").name("集宁区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("乌兰察布市").child(area).build());

        area = new HashMap<>();
        area.put("26", DicVO.builder().code("26").name("杭锦后旗").build());
        area.put("25", DicVO.builder().code("25").name("乌拉特后旗").build());
        area.put("24", DicVO.builder().code("24").name("乌拉特中旗").build());
        area.put("23", DicVO.builder().code("23").name("乌拉特前旗").build());
        area.put("22", DicVO.builder().code("22").name("磴口县").build());
        area.put("21", DicVO.builder().code("21").name("五原县").build());
        area.put("02", DicVO.builder().code("02").name("临河区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("巴彦淖尔市").child(area).build());

        area = new HashMap<>();
        area.put("85", DicVO.builder().code("85").name("根河市").build());
        area.put("84", DicVO.builder().code("84").name("额尔古纳市").build());
        area.put("83", DicVO.builder().code("83").name("扎兰屯市").build());
        area.put("82", DicVO.builder().code("82").name("牙克石市").build());
        area.put("81", DicVO.builder().code("81").name("满洲里市").build());
        area.put("27", DicVO.builder().code("27").name("新巴尔虎右旗").build());
        area.put("26", DicVO.builder().code("26").name("新巴尔虎左旗").build());
        area.put("25", DicVO.builder().code("25").name("陈巴尔虎旗").build());
        area.put("24", DicVO.builder().code("24").name("鄂温克族自治旗").build());
        area.put("23", DicVO.builder().code("23").name("鄂伦春自治旗").build());
        area.put("22", DicVO.builder().code("22").name("莫力达瓦达斡尔").build());
        area.put("21", DicVO.builder().code("21").name("阿荣旗").build());
        area.put("02", DicVO.builder().code("02").name("海拉尔区族自治旗").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("呼伦贝尔市").child(area).build());

        area = new HashMap<>();
        area.put("27", DicVO.builder().code("27").name("伊金霍洛旗").build());
        area.put("26", DicVO.builder().code("26").name("乌审旗").build());
        area.put("25", DicVO.builder().code("25").name("杭锦旗").build());
        area.put("24", DicVO.builder().code("24").name("鄂托克旗").build());
        area.put("23", DicVO.builder().code("23").name("鄂托克前旗").build());
        area.put("22", DicVO.builder().code("22").name("准格尔旗").build());
        area.put("21", DicVO.builder().code("21").name("达拉特旗").build());
        area.put("02", DicVO.builder().code("02").name("东胜区").build());
        city.put("06", DicVO.builder().code("06").name("鄂尔多斯市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("霍林郭勒市").build());
        area.put("26", DicVO.builder().code("26").name("扎鲁特旗").build());
        area.put("25", DicVO.builder().code("25").name("奈曼旗").build());
        area.put("24", DicVO.builder().code("24").name("库伦旗").build());
        area.put("23", DicVO.builder().code("23").name("开鲁县").build());
        area.put("22", DicVO.builder().code("22").name("科尔沁左翼后旗").build());
        area.put("21", DicVO.builder().code("21").name("科尔沁左翼中旗").build());
        area.put("02", DicVO.builder().code("02").name("科尔沁区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("通辽市").child(area).build());

        area = new HashMap<>();
        area.put("30", DicVO.builder().code("30").name("敖汉旗").build());
        area.put("29", DicVO.builder().code("29").name("宁城县").build());
        area.put("28", DicVO.builder().code("28").name("喀喇沁旗").build());
        area.put("26", DicVO.builder().code("26").name("翁牛特旗").build());
        area.put("25", DicVO.builder().code("25").name("克什克腾旗").build());
        area.put("24", DicVO.builder().code("24").name("林西县").build());
        area.put("23", DicVO.builder().code("23").name("巴林右旗").build());
        area.put("22", DicVO.builder().code("22").name("巴林左旗").build());
        area.put("21", DicVO.builder().code("21").name("阿鲁科尔沁旗").build());
        area.put("04", DicVO.builder().code("04").name("松山区").build());
        area.put("03", DicVO.builder().code("03").name("元宝山区").build());
        area.put("02", DicVO.builder().code("02").name("红山区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("赤峰市").child(area).build());

        area = new HashMap<>();
        area.put("04", DicVO.builder().code("04").name("乌达区").build());
        area.put("03", DicVO.builder().code("03").name("海南区").build());
        area.put("02", DicVO.builder().code("02").name("海勃湾区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("乌海市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("达尔罕茂明安联").build());
        area.put("22", DicVO.builder().code("22").name("固阳县").build());
        area.put("21", DicVO.builder().code("21").name("土默特右旗合旗").build());
        area.put("07", DicVO.builder().code("07").name("九原区").build());
        area.put("06", DicVO.builder().code("06").name("白云矿区").build());
        area.put("05", DicVO.builder().code("05").name("石拐区").build());
        area.put("04", DicVO.builder().code("04").name("青山区").build());
        area.put("03", DicVO.builder().code("03").name("昆都仑区").build());
        area.put("02", DicVO.builder().code("02").name("东河区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("包头市").child(area).build());

        area = new HashMap<>();
        area.put("25", DicVO.builder().code("25").name("武川县").build());
        area.put("24", DicVO.builder().code("24").name("清水河县").build());
        area.put("23", DicVO.builder().code("23").name("和林格尔县").build());
        area.put("22", DicVO.builder().code("22").name("托克托县").build());
        area.put("21", DicVO.builder().code("21").name("土默特左旗").build());
        area.put("05", DicVO.builder().code("05").name("赛罕区").build());
        area.put("04", DicVO.builder().code("04").name("玉泉区").build());
        area.put("03", DicVO.builder().code("03").name("回民区").build());
        area.put("02", DicVO.builder().code("02").name("新城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("呼和浩特市").child(area).build());
        prov.put("15", DicVO.builder().code("15").name("内蒙古自治区").child(city).build());
    }

    private static void liaoNing(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("81", DicVO.builder().code("81").name("兴城市").build());
        area.put("22", DicVO.builder().code("22").name("建昌县").build());
        area.put("21", DicVO.builder().code("21").name("绥中县").build());
        area.put("04", DicVO.builder().code("04").name("南票区").build());
        area.put("03", DicVO.builder().code("03").name("龙港区").build());
        area.put("02", DicVO.builder().code("02").name("连山区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("14", DicVO.builder().code("14").name("葫芦岛市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("凌源市").build());
        area.put("81", DicVO.builder().code("81").name("北票市").build());
        area.put("24", DicVO.builder().code("24").name("喀喇沁左翼蒙古族自治县").build());
        area.put("22", DicVO.builder().code("22").name("建平县").build());
        area.put("21", DicVO.builder().code("21").name("朝阳县").build());
        area.put("03", DicVO.builder().code("03").name("龙城区").build());
        area.put("02", DicVO.builder().code("02").name("双塔区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("13", DicVO.builder().code("13").name("朝阳市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("开原市").build());
        area.put("81", DicVO.builder().code("81").name("调兵山市").build());
        area.put("24", DicVO.builder().code("24").name("昌图县").build());
        area.put("23", DicVO.builder().code("23").name("西丰县").build());
        area.put("21", DicVO.builder().code("21").name("铁岭县").build());
        area.put("04", DicVO.builder().code("04").name("清河区").build());
        area.put("02", DicVO.builder().code("02").name("银州区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("12", DicVO.builder().code("12").name("铁岭市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("盘山县").build());
        area.put("21", DicVO.builder().code("21").name("大洼县").build());
        area.put("03", DicVO.builder().code("03").name("兴隆台区").build());
        area.put("02", DicVO.builder().code("02").name("双台子区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("11", DicVO.builder().code("11").name("盘锦市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("灯塔市").build());
        area.put("21", DicVO.builder().code("21").name("辽阳县").build());
        area.put("11", DicVO.builder().code("11").name("太子河区").build());
        area.put("05", DicVO.builder().code("05").name("弓长岭区").build());
        area.put("04", DicVO.builder().code("04").name("宏伟区").build());
        area.put("03", DicVO.builder().code("03").name("文圣区").build());
        area.put("02", DicVO.builder().code("02").name("白塔区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("10", DicVO.builder().code("10").name("辽阳市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("彰武县").build());
        area.put("21", DicVO.builder().code("21").name("阜新蒙古族自治县").build());
        area.put("11", DicVO.builder().code("11").name("细河区").build());
        area.put("05", DicVO.builder().code("05").name("清河门区").build());
        area.put("04", DicVO.builder().code("04").name("太平区").build());
        area.put("03", DicVO.builder().code("03").name("新邱区").build());
        area.put("02", DicVO.builder().code("02").name("海州区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("阜新市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("大石桥市").build());
        area.put("81", DicVO.builder().code("81").name("盖州市").build());
        area.put("11", DicVO.builder().code("11").name("老边区").build());
        area.put("04", DicVO.builder().code("04").name("鲅鱼圈区").build());
        area.put("03", DicVO.builder().code("03").name("西市区").build());
        area.put("02", DicVO.builder().code("02").name("站前区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("营口市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("北宁市").build());
        area.put("81", DicVO.builder().code("81").name("凌海市").build());
        area.put("27", DicVO.builder().code("27").name("义县").build());
        area.put("26", DicVO.builder().code("26").name("黑山县").build());
        area.put("11", DicVO.builder().code("11").name("太和区").build());
        area.put("03", DicVO.builder().code("03").name("凌河区").build());
        area.put("02", DicVO.builder().code("02").name("古塔区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("锦州市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("凤城市").build());
        area.put("81", DicVO.builder().code("81").name("东港市").build());
        area.put("24", DicVO.builder().code("24").name("宽甸满族自治县").build());
        area.put("04", DicVO.builder().code("04").name("振安区").build());
        area.put("03", DicVO.builder().code("03").name("振兴区").build());
        area.put("02", DicVO.builder().code("02").name("元宝区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("丹东市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("桓仁满族自治县").build());
        area.put("21", DicVO.builder().code("21").name("本溪满族自治县").build());
        area.put("05", DicVO.builder().code("05").name("南芬区").build());
        area.put("04", DicVO.builder().code("04").name("明山区").build());
        area.put("03", DicVO.builder().code("03").name("溪湖区").build());
        area.put("02", DicVO.builder().code("02").name("平山区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("本溪市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("清原满族自治县").build());
        area.put("22", DicVO.builder().code("22").name("新宾满族自治县").build());
        area.put("21", DicVO.builder().code("21").name("抚顺县").build());
        area.put("11", DicVO.builder().code("11").name("顺城区").build());
        area.put("04", DicVO.builder().code("04").name("望花区").build());
        area.put("03", DicVO.builder().code("03").name("东洲区").build());
        area.put("02", DicVO.builder().code("02").name("新抚区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("抚顺市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("海城市").build());
        area.put("23", DicVO.builder().code("23").name("岫岩满族自治县").build());
        area.put("21", DicVO.builder().code("21").name("台安县").build());
        area.put("11", DicVO.builder().code("11").name("千山区").build());
        area.put("04", DicVO.builder().code("04").name("立山区").build());
        area.put("03", DicVO.builder().code("03").name("铁西区").build());
        area.put("02", DicVO.builder().code("02").name("铁东区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("鞍山市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("庄河市").build());
        area.put("82", DicVO.builder().code("82").name("普兰店市").build());
        area.put("81", DicVO.builder().code("81").name("瓦房店市").build());
        area.put("24", DicVO.builder().code("24").name("长海县").build());
        area.put("13", DicVO.builder().code("13").name("金州区").build());
        area.put("12", DicVO.builder().code("12").name("旅顺口区").build());
        area.put("11", DicVO.builder().code("11").name("甘井子区").build());
        area.put("04", DicVO.builder().code("04").name("沙河口区").build());
        area.put("03", DicVO.builder().code("03").name("西岗区").build());
        area.put("02", DicVO.builder().code("02").name("中山区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("大连市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("新民市").build());
        area.put("24", DicVO.builder().code("24").name("法库县").build());
        area.put("23", DicVO.builder().code("23").name("康平县").build());
        area.put("22", DicVO.builder().code("22").name("辽中县").build());
        area.put("14", DicVO.builder().code("14").name("于洪区").build());
        area.put("13", DicVO.builder().code("13").name("新城子区").build());
        area.put("12", DicVO.builder().code("12").name("东陵区").build());
        area.put("11", DicVO.builder().code("11").name("苏家屯区").build());
        area.put("06", DicVO.builder().code("06").name("铁西区").build());
        area.put("05", DicVO.builder().code("05").name("皇姑区").build());
        area.put("04", DicVO.builder().code("04").name("大东区").build());
        area.put("03", DicVO.builder().code("03").name("沈河区").build());
        area.put("02", DicVO.builder().code("02").name("和平区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("沈阳市").child(area).build());
        prov.put("21", DicVO.builder().code("21").name("辽宁省").child(city).build());
    }

    private static void jiLin(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("26", DicVO.builder().code("26").name("安图县").build());
        area.put("24", DicVO.builder().code("24").name("汪清县").build());
        area.put("06", DicVO.builder().code("06").name("和龙市").build());
        area.put("05", DicVO.builder().code("05").name("龙井市").build());
        area.put("04", DicVO.builder().code("04").name("珲春市").build());
        area.put("03", DicVO.builder().code("03").name("敦化市").build());
        area.put("02", DicVO.builder().code("02").name("图们市").build());
        area.put("01", DicVO.builder().code("01").name("延吉市").build());
        city.put("24", DicVO.builder().code("24").name("延边朝鲜族自治州").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("大安市").build());
        area.put("81", DicVO.builder().code("81").name("洮南市").build());
        area.put("22", DicVO.builder().code("22").name("通榆县").build());
        area.put("21", DicVO.builder().code("21").name("镇赉县").build());
        area.put("02", DicVO.builder().code("02").name("洮北区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("白城市").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("扶余县").build());
        area.put("23", DicVO.builder().code("23").name("乾安县").build());
        area.put("22", DicVO.builder().code("22").name("长岭县").build());
        area.put("21", DicVO.builder().code("21").name("前郭尔罗斯蒙古族自治县").build());
        area.put("02", DicVO.builder().code("02").name("宁江区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("松原市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("临江市").build());
        area.put("25", DicVO.builder().code("25").name("江源县").build());
        area.put("23", DicVO.builder().code("23").name("长白朝鲜族自治县").build());
        area.put("22", DicVO.builder().code("22").name("靖宇县").build());
        area.put("21", DicVO.builder().code("21").name("抚松县").build());
        area.put("02", DicVO.builder().code("02").name("八道江区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("白山市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("集安市").build());
        area.put("81", DicVO.builder().code("81").name("梅河口市").build());
        area.put("24", DicVO.builder().code("24").name("柳河县").build());
        area.put("23", DicVO.builder().code("23").name("辉南县").build());
        area.put("21", DicVO.builder().code("21").name("通化县").build());
        area.put("03", DicVO.builder().code("03").name("二道江区").build());
        area.put("02", DicVO.builder().code("02").name("东昌区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("通化市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("东辽县").build());
        area.put("21", DicVO.builder().code("21").name("东丰县").build());
        area.put("03", DicVO.builder().code("03").name("西安区").build());
        area.put("02", DicVO.builder().code("02").name("龙山区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("辽源市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("双辽市").build());
        area.put("81", DicVO.builder().code("81").name("公主岭市").build());
        area.put("23", DicVO.builder().code("23").name("伊通满族自治县").build());
        area.put("22", DicVO.builder().code("22").name("梨树县").build());
        area.put("03", DicVO.builder().code("03").name("铁东区").build());
        area.put("02", DicVO.builder().code("02").name("铁西区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("四平市").child(area).build());

        area = new HashMap<>();
        area.put("84", DicVO.builder().code("84").name("磐石市").build());
        area.put("83", DicVO.builder().code("83").name("舒兰市").build());
        area.put("82", DicVO.builder().code("82").name("桦甸市").build());
        area.put("81", DicVO.builder().code("81").name("蛟河市").build());
        area.put("21", DicVO.builder().code("21").name("永吉县").build());
        area.put("11", DicVO.builder().code("11").name("丰满区").build());
        area.put("04", DicVO.builder().code("04").name("船营区").build());
        area.put("03", DicVO.builder().code("03").name("龙潭区").build());
        area.put("02", DicVO.builder().code("02").name("昌邑区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("吉林市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("德惠市").build());
        area.put("82", DicVO.builder().code("82").name("榆树市").build());
        area.put("81", DicVO.builder().code("81").name("九台市").build());
        area.put("22", DicVO.builder().code("22").name("农安县").build());
        area.put("12", DicVO.builder().code("12").name("双阳区").build());
        area.put("06", DicVO.builder().code("06").name("绿园区").build());
        area.put("05", DicVO.builder().code("05").name("二道区").build());
        area.put("04", DicVO.builder().code("04").name("朝阳区").build());
        area.put("03", DicVO.builder().code("03").name("宽城区").build());
        area.put("02", DicVO.builder().code("02").name("南关区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("长春市").child(area).build());
        prov.put("22", DicVO.builder().code("22").name("吉林省").child(city).build());
    }

    private static void heiLongJiang(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("23", DicVO.builder().code("23").name("漠河县").build());
        area.put("22", DicVO.builder().code("22").name("塔河县").build());
        area.put("21", DicVO.builder().code("21").name("呼玛县").build());
        city.put("27", DicVO.builder().code("27").name("大兴安岭地区").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("海伦市").build());
        area.put("82", DicVO.builder().code("82").name("肇东市").build());
        area.put("81", DicVO.builder().code("81").name("安达市").build());
        area.put("26", DicVO.builder().code("26").name("绥棱县").build());
        area.put("25", DicVO.builder().code("25").name("明水县").build());
        area.put("24", DicVO.builder().code("24").name("庆安县").build());
        area.put("23", DicVO.builder().code("23").name("青冈县").build());
        area.put("22", DicVO.builder().code("22").name("兰西县").build());
        area.put("21", DicVO.builder().code("21").name("望奎县").build());
        area.put("02", DicVO.builder().code("02").name("北林区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("12", DicVO.builder().code("12").name("绥化市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("五大连池市").build());
        area.put("81", DicVO.builder().code("81").name("北安市").build());
        area.put("24", DicVO.builder().code("24").name("孙吴县").build());
        area.put("23", DicVO.builder().code("23").name("逊克县").build());
        area.put("21", DicVO.builder().code("21").name("嫩江县").build());
        area.put("02", DicVO.builder().code("02").name("爱辉区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("11", DicVO.builder().code("11").name("黑河市").child(area).build());

        area = new HashMap<>();
        area.put("85", DicVO.builder().code("85").name("穆棱市").build());
        area.put("84", DicVO.builder().code("84").name("宁安市").build());
        area.put("83", DicVO.builder().code("83").name("海林市").build());
        area.put("81", DicVO.builder().code("81").name("绥芬河市").build());
        area.put("25", DicVO.builder().code("25").name("林口县").build());
        area.put("24", DicVO.builder().code("24").name("东宁县").build());
        area.put("05", DicVO.builder().code("05").name("西安区").build());
        area.put("04", DicVO.builder().code("04").name("爱民区").build());
        area.put("03", DicVO.builder().code("03").name("阳明区").build());
        area.put("02", DicVO.builder().code("02").name("东安区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("10", DicVO.builder().code("10").name("牡丹江市").child(area).build());

        area = new HashMap<>();
        area.put("21", DicVO.builder().code("21").name("勃利县").build());
        area.put("04", DicVO.builder().code("04").name("茄子河区").build());
        area.put("03", DicVO.builder().code("03").name("桃山区").build());
        area.put("02", DicVO.builder().code("02").name("新兴区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("七台河市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("富锦市").build());
        area.put("81", DicVO.builder().code("81").name("同江市").build());
        area.put("33", DicVO.builder().code("33").name("抚远县").build());
        area.put("28", DicVO.builder().code("28").name("汤原县").build());
        area.put("26", DicVO.builder().code("26").name("桦川县").build());
        area.put("22", DicVO.builder().code("22").name("桦南县").build());
        area.put("11", DicVO.builder().code("11").name("郊区").build());
        area.put("05", DicVO.builder().code("05").name("东风区").build());
        area.put("04", DicVO.builder().code("04").name("前进区").build());
        area.put("03", DicVO.builder().code("03").name("向阳区").build());
        area.put("02", DicVO.builder().code("02").name("永红区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("佳木斯市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("铁力市").build());
        area.put("22", DicVO.builder().code("22").name("嘉荫县").build());
        area.put("16", DicVO.builder().code("16").name("上甘岭区").build());
        area.put("15", DicVO.builder().code("15").name("红星区").build());
        area.put("14", DicVO.builder().code("14").name("乌伊岭区").build());
        area.put("13", DicVO.builder().code("13").name("带岭区").build());
        area.put("12", DicVO.builder().code("12").name("汤旺河区").build());
        area.put("11", DicVO.builder().code("11").name("乌马河区").build());
        area.put("10", DicVO.builder().code("10").name("五营区").build());
        area.put("09", DicVO.builder().code("09").name("金山屯区").build());
        area.put("08", DicVO.builder().code("08").name("美溪区").build());
        area.put("07", DicVO.builder().code("07").name("新青区").build());
        area.put("06", DicVO.builder().code("06").name("翠峦区").build());
        area.put("05", DicVO.builder().code("05").name("西林区").build());
        area.put("04", DicVO.builder().code("04").name("友好区").build());
        area.put("03", DicVO.builder().code("03").name("南岔区").build());
        area.put("02", DicVO.builder().code("02").name("伊春区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("伊春市").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("杜尔伯特蒙古族自治县").build());
        area.put("23", DicVO.builder().code("23").name("林甸县").build());
        area.put("22", DicVO.builder().code("22").name("肇源县").build());
        area.put("21", DicVO.builder().code("21").name("肇州县").build());
        area.put("06", DicVO.builder().code("06").name("大同区").build());
        area.put("05", DicVO.builder().code("05").name("红岗区").build());
        area.put("04", DicVO.builder().code("04").name("让胡路区").build());
        area.put("03", DicVO.builder().code("03").name("龙凤区").build());
        area.put("02", DicVO.builder().code("02").name("萨尔图区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("大庆市").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("饶河县").build());
        area.put("23", DicVO.builder().code("23").name("宝清县").build());
        area.put("22", DicVO.builder().code("22").name("友谊县").build());
        area.put("21", DicVO.builder().code("21").name("集贤县").build());
        area.put("06", DicVO.builder().code("06").name("宝山区").build());
        area.put("05", DicVO.builder().code("05").name("四方台区").build());
        area.put("03", DicVO.builder().code("03").name("岭东区").build());
        area.put("02", DicVO.builder().code("02").name("尖山区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("双鸭山市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("绥滨县").build());
        area.put("21", DicVO.builder().code("21").name("萝北县").build());
        area.put("07", DicVO.builder().code("07").name("兴山区").build());
        area.put("06", DicVO.builder().code("06").name("东山区").build());
        area.put("05", DicVO.builder().code("05").name("兴安区").build());
        area.put("04", DicVO.builder().code("04").name("南山区").build());
        area.put("03", DicVO.builder().code("03").name("工农区").build());
        area.put("02", DicVO.builder().code("02").name("向阳区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("鹤岗市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("密山市").build());
        area.put("81", DicVO.builder().code("81").name("虎林市").build());
        area.put("21", DicVO.builder().code("21").name("鸡东县").build());
        area.put("07", DicVO.builder().code("07").name("麻山区").build());
        area.put("06", DicVO.builder().code("06").name("城子河区").build());
        area.put("05", DicVO.builder().code("05").name("梨树区").build());
        area.put("04", DicVO.builder().code("04").name("滴道区").build());
        area.put("03", DicVO.builder().code("03").name("恒山区").build());
        area.put("02", DicVO.builder().code("02").name("鸡冠区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("鸡西市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("讷河市").build());
        area.put("31", DicVO.builder().code("31").name("拜泉县").build());
        area.put("30", DicVO.builder().code("30").name("克东县").build());
        area.put("29", DicVO.builder().code("29").name("克山县").build());
        area.put("27", DicVO.builder().code("27").name("富裕县").build());
        area.put("25", DicVO.builder().code("25").name("甘南县").build());
        area.put("24", DicVO.builder().code("24").name("泰来县").build());
        area.put("23", DicVO.builder().code("23").name("依安县").build());
        area.put("21", DicVO.builder().code("21").name("龙江县").build());
        area.put("08", DicVO.builder().code("08").name("梅里斯达斡尔族区").build());
        area.put("07", DicVO.builder().code("07").name("碾子山区").build());
        area.put("06", DicVO.builder().code("06").name("富拉尔基区").build());
        area.put("05", DicVO.builder().code("05").name("昂昂溪区").build());
        area.put("04", DicVO.builder().code("04").name("铁锋区").build());
        area.put("03", DicVO.builder().code("03").name("建华区").build());
        area.put("02", DicVO.builder().code("02").name("龙沙区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("齐齐哈尔市").child(area).build());

        area = new HashMap<>();
        area.put("84", DicVO.builder().code("84").name("五常市").build());
        area.put("83", DicVO.builder().code("83").name("尚志市").build());
        area.put("82", DicVO.builder().code("82").name("双城市").build());
        area.put("81", DicVO.builder().code("81").name("阿城市").build());
        area.put("29", DicVO.builder().code("29").name("延寿县").build());
        area.put("28", DicVO.builder().code("28").name("通河县").build());
        area.put("27", DicVO.builder().code("27").name("木兰县").build());
        area.put("26", DicVO.builder().code("26").name("巴彦县").build());
        area.put("25", DicVO.builder().code("25").name("宾县").build());
        area.put("24", DicVO.builder().code("24").name("方正县").build());
        area.put("23", DicVO.builder().code("23").name("依兰县").build());
        area.put("21", DicVO.builder().code("21").name("呼兰县").build());
        area.put("08", DicVO.builder().code("08").name("平房区").build());
        area.put("07", DicVO.builder().code("07").name("动力区").build());
        area.put("06", DicVO.builder().code("06").name("香坊区").build());
        area.put("05", DicVO.builder().code("05").name("太平区").build());
        area.put("04", DicVO.builder().code("04").name("道外区").build());
        area.put("03", DicVO.builder().code("03").name("南岗区").build());
        area.put("02", DicVO.builder().code("02").name("道里区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("哈尔滨市").child(area).build());
        prov.put("23", DicVO.builder().code("23").name("黑龙江省").child(city).build());
    }

    private static void shangHai(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("30", DicVO.builder().code("30").name("崇明县").build());
        city.put("02", DicVO.builder().code("02").name("上海县").child(area).build());

        area = new HashMap<>();
        area.put("20", DicVO.builder().code("20").name("奉贤区").build());
        area.put("19", DicVO.builder().code("19").name("南汇区").build());
        area.put("18", DicVO.builder().code("18").name("青浦区").build());
        area.put("17", DicVO.builder().code("17").name("松江区").build());
        area.put("16", DicVO.builder().code("16").name("金山区").build());
        area.put("15", DicVO.builder().code("15").name("浦东新区").build());
        area.put("14", DicVO.builder().code("14").name("嘉定区").build());
        area.put("13", DicVO.builder().code("13").name("宝山区").build());
        area.put("12", DicVO.builder().code("12").name("闵行区").build());
        area.put("10", DicVO.builder().code("10").name("杨浦区").build());
        area.put("09", DicVO.builder().code("09").name("虹口区").build());
        area.put("08", DicVO.builder().code("08").name("闸北区").build());
        area.put("07", DicVO.builder().code("07").name("普陀区").build());
        area.put("06", DicVO.builder().code("06").name("静安区").build());
        area.put("05", DicVO.builder().code("05").name("长宁区").build());
        area.put("04", DicVO.builder().code("04").name("徐汇区").build());
        area.put("03", DicVO.builder().code("03").name("卢湾区").build());
        area.put("01", DicVO.builder().code("01").name("黄浦区").build());
        city.put("01", DicVO.builder().code("01").name("市辖区").child(area).build());
        prov.put("31", DicVO.builder().code("31").name("上海市").child(city).build());
    }

    private static void jiangSu(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("24", DicVO.builder().code("24").name("泗洪县").build());
        area.put("23", DicVO.builder().code("23").name("泗阳县").build());
        area.put("22", DicVO.builder().code("22").name("沭阳县").build());
        area.put("21", DicVO.builder().code("21").name("宿豫县").build());
        area.put("02", DicVO.builder().code("02").name("宿城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("13", DicVO.builder().code("13").name("宿迁市").child(area).build());

        area = new HashMap<>();
        area.put("84", DicVO.builder().code("84").name("姜堰市").build());
        area.put("83", DicVO.builder().code("83").name("泰兴市").build());
        area.put("82", DicVO.builder().code("82").name("靖江市").build());
        area.put("81", DicVO.builder().code("81").name("兴化市").build());
        area.put("03", DicVO.builder().code("03").name("高港区").build());
        area.put("02", DicVO.builder().code("02").name("海陵区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("12", DicVO.builder().code("12").name("泰州市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("句容市").build());
        area.put("82", DicVO.builder().code("82").name("扬中市").build());
        area.put("81", DicVO.builder().code("81").name("丹阳市").build());
        area.put("12", DicVO.builder().code("12").name("丹徒区").build());
        area.put("11", DicVO.builder().code("11").name("润州区").build());
        area.put("02", DicVO.builder().code("02").name("京口区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("11", DicVO.builder().code("11").name("镇江市").child(area).build());

        area = new HashMap<>();
        area.put("88", DicVO.builder().code("88").name("江都市").build());
        area.put("84", DicVO.builder().code("84").name("高邮市").build());
        area.put("81", DicVO.builder().code("81").name("仪征市").build());
        area.put("23", DicVO.builder().code("23").name("宝应县").build());
        area.put("11", DicVO.builder().code("11").name("郊区").build());
        area.put("03", DicVO.builder().code("03").name("邗江区").build());
        area.put("02", DicVO.builder().code("02").name("广陵区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("10", DicVO.builder().code("10").name("扬州市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("大丰市").build());
        area.put("81", DicVO.builder().code("81").name("东台市").build());
        area.put("25", DicVO.builder().code("25").name("建湖县").build());
        area.put("24", DicVO.builder().code("24").name("射阳县").build());
        area.put("23", DicVO.builder().code("23").name("阜宁县").build());
        area.put("22", DicVO.builder().code("22").name("滨海县").build());
        area.put("21", DicVO.builder().code("21").name("响水县").build());
        area.put("03", DicVO.builder().code("03").name("盐都区").build());
        area.put("02", DicVO.builder().code("02").name("亭湖区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("盐城市").child(area).build());

        area = new HashMap<>();
        area.put("31", DicVO.builder().code("31").name("金湖县").build());
        area.put("30", DicVO.builder().code("30").name("盱眙县").build());
        area.put("29", DicVO.builder().code("29").name("洪泽县").build());
        area.put("26", DicVO.builder().code("26").name("涟水县").build());
        area.put("11", DicVO.builder().code("11").name("清浦区").build());
        area.put("04", DicVO.builder().code("04").name("淮阴区").build());
        area.put("03", DicVO.builder().code("03").name("楚州区").build());
        area.put("02", DicVO.builder().code("02").name("清河区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("淮安市").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("灌南县").build());
        area.put("23", DicVO.builder().code("23").name("灌云县").build());
        area.put("22", DicVO.builder().code("22").name("东海县").build());
        area.put("21", DicVO.builder().code("21").name("赣榆县").build());
        area.put("06", DicVO.builder().code("06").name("海州区").build());
        area.put("05", DicVO.builder().code("05").name("新浦区").build());
        area.put("03", DicVO.builder().code("03").name("连云区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("连云港市").child(area).build());

        area = new HashMap<>();
        area.put("84", DicVO.builder().code("84").name("海门市").build());
        area.put("83", DicVO.builder().code("83").name("通州市").build());
        area.put("82", DicVO.builder().code("82").name("如皋市").build());
        area.put("81", DicVO.builder().code("81").name("启东市").build());
        area.put("23", DicVO.builder().code("23").name("如东县").build());
        area.put("21", DicVO.builder().code("21").name("海安县").build());
        area.put("11", DicVO.builder().code("11").name("港闸区").build());
        area.put("02", DicVO.builder().code("02").name("崇川区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("南通市").child(area).build());

        area = new HashMap<>();
        area.put("85", DicVO.builder().code("85").name("太仓市").build());
        area.put("84", DicVO.builder().code("84").name("吴江市").build());
        area.put("83", DicVO.builder().code("83").name("昆山市").build());
        area.put("82", DicVO.builder().code("82").name("张家港市").build());
        area.put("81", DicVO.builder().code("81").name("常熟市").build());
        area.put("07", DicVO.builder().code("07").name("相城区").build());
        area.put("06", DicVO.builder().code("06").name("吴中区").build());
        area.put("05", DicVO.builder().code("05").name("虎丘区").build());
        area.put("04", DicVO.builder().code("04").name("金阊区").build());
        area.put("03", DicVO.builder().code("03").name("平江区").build());
        area.put("02", DicVO.builder().code("02").name("沧浪区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("苏州市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("金坛市").build());
        area.put("81", DicVO.builder().code("81").name("溧阳市").build());
        area.put("12", DicVO.builder().code("12").name("武进区").build());
        area.put("11", DicVO.builder().code("11").name("新北区").build());
        area.put("05", DicVO.builder().code("05").name("戚墅堰区").build());
        area.put("04", DicVO.builder().code("04").name("钟楼区").build());
        area.put("02", DicVO.builder().code("02").name("天宁区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("常州市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("邳州市").build());
        area.put("81", DicVO.builder().code("81").name("新沂市").build());
        area.put("24", DicVO.builder().code("24").name("睢宁县").build());
        area.put("23", DicVO.builder().code("23").name("铜山县").build());
        area.put("22", DicVO.builder().code("22").name("沛县").build());
        area.put("21", DicVO.builder().code("21").name("丰县").build());
        area.put("11", DicVO.builder().code("11").name("泉山区").build());
        area.put("05", DicVO.builder().code("05").name("贾汪区").build());
        area.put("04", DicVO.builder().code("04").name("九里区").build());
        area.put("03", DicVO.builder().code("03").name("云龙区").build());
        area.put("02", DicVO.builder().code("02").name("鼓楼区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("徐州市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("宜兴市").build());
        area.put("81", DicVO.builder().code("81").name("江阴市").build());
        area.put("11", DicVO.builder().code("11").name("滨湖区").build());
        area.put("06", DicVO.builder().code("06").name("惠山区").build());
        area.put("05", DicVO.builder().code("05").name("锡山区").build());
        area.put("04", DicVO.builder().code("04").name("北塘区").build());
        area.put("03", DicVO.builder().code("03").name("南长区").build());
        area.put("02", DicVO.builder().code("02").name("崇安区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("无锡市").child(area).build());

        area = new HashMap<>();
        area.put("25", DicVO.builder().code("25").name("高淳县").build());
        area.put("24", DicVO.builder().code("24").name("溧水县").build());
        area.put("16", DicVO.builder().code("16").name("六合区").build());
        area.put("15", DicVO.builder().code("15").name("江宁区").build());
        area.put("14", DicVO.builder().code("14").name("雨花台区").build());
        area.put("13", DicVO.builder().code("13").name("栖霞区").build());
        area.put("11", DicVO.builder().code("11").name("浦口区").build());
        area.put("07", DicVO.builder().code("07").name("下关区").build());
        area.put("06", DicVO.builder().code("06").name("鼓楼区").build());
        area.put("05", DicVO.builder().code("05").name("建邺区").build());
        area.put("04", DicVO.builder().code("04").name("秦淮区").build());
        area.put("03", DicVO.builder().code("03").name("白下区").build());
        area.put("02", DicVO.builder().code("02").name("玄武区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("南京市").child(area).build());
        prov.put("32", DicVO.builder().code("32").name("江苏省").child(city).build());
    }

    private static void zheJiang(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("81", DicVO.builder().code("81").name("龙泉市").build());
        area.put("27", DicVO.builder().code("27").name("景宁畲族自治县").build());
        area.put("26", DicVO.builder().code("26").name("庆元县").build());
        area.put("25", DicVO.builder().code("25").name("云和县").build());
        area.put("24", DicVO.builder().code("24").name("松阳县").build());
        area.put("23", DicVO.builder().code("23").name("遂昌县").build());
        area.put("22", DicVO.builder().code("22").name("缙云县").build());
        area.put("21", DicVO.builder().code("21").name("青田县").build());
        area.put("02", DicVO.builder().code("02").name("莲都区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("11", DicVO.builder().code("11").name("丽水市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("临海市").build());
        area.put("81", DicVO.builder().code("81").name("温岭市").build());
        area.put("24", DicVO.builder().code("24").name("仙居县").build());
        area.put("23", DicVO.builder().code("23").name("天台县").build());
        area.put("22", DicVO.builder().code("22").name("三门县").build());
        area.put("21", DicVO.builder().code("21").name("玉环县").build());
        area.put("04", DicVO.builder().code("04").name("路桥区").build());
        area.put("03", DicVO.builder().code("03").name("黄岩区").build());
        area.put("02", DicVO.builder().code("02").name("椒江区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("10", DicVO.builder().code("10").name("台州市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("嵊泗县").build());
        area.put("21", DicVO.builder().code("21").name("岱山县").build());
        area.put("03", DicVO.builder().code("03").name("普陀区").build());
        area.put("02", DicVO.builder().code("02").name("定海区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("舟山市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("江山市").build());
        area.put("25", DicVO.builder().code("25").name("龙游县").build());
        area.put("24", DicVO.builder().code("24").name("开化县").build());
        area.put("22", DicVO.builder().code("22").name("常山县").build());
        area.put("03", DicVO.builder().code("03").name("衢江区").build());
        area.put("02", DicVO.builder().code("02").name("柯城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("衢州市").child(area).build());

        area = new HashMap<>();
        area.put("84", DicVO.builder().code("84").name("永康市").build());
        area.put("83", DicVO.builder().code("83").name("东阳市").build());
        area.put("82", DicVO.builder().code("82").name("义乌市").build());
        area.put("81", DicVO.builder().code("81").name("兰溪市").build());
        area.put("27", DicVO.builder().code("27").name("磐安县").build());
        area.put("26", DicVO.builder().code("26").name("浦江县").build());
        area.put("23", DicVO.builder().code("23").name("武义县").build());
        area.put("03", DicVO.builder().code("03").name("金东区").build());
        area.put("02", DicVO.builder().code("02").name("婺城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("金华市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("嵊州市").build());
        area.put("82", DicVO.builder().code("82").name("上虞市").build());
        area.put("81", DicVO.builder().code("81").name("诸暨市").build());
        area.put("24", DicVO.builder().code("24").name("新昌县").build());
        area.put("21", DicVO.builder().code("21").name("绍兴县").build());
        area.put("02", DicVO.builder().code("02").name("越城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("绍兴市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("安吉县").build());
        area.put("22", DicVO.builder().code("22").name("长兴县").build());
        area.put("21", DicVO.builder().code("21").name("德清县").build());
        area.put("03", DicVO.builder().code("03").name("南浔区").build());
        area.put("02", DicVO.builder().code("02").name("吴兴区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("湖州市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("桐乡市").build());
        area.put("82", DicVO.builder().code("82").name("平湖市").build());
        area.put("81", DicVO.builder().code("81").name("海宁市").build());
        area.put("24", DicVO.builder().code("24").name("海盐县").build());
        area.put("21", DicVO.builder().code("21").name("嘉善县").build());
        area.put("11", DicVO.builder().code("11").name("秀洲区").build());
        area.put("02", DicVO.builder().code("02").name("秀城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("嘉兴市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("乐清市").build());
        area.put("81", DicVO.builder().code("81").name("瑞安市").build());
        area.put("29", DicVO.builder().code("29").name("泰顺县").build());
        area.put("28", DicVO.builder().code("28").name("文成县").build());
        area.put("27", DicVO.builder().code("27").name("苍南县").build());
        area.put("26", DicVO.builder().code("26").name("平阳县").build());
        area.put("24", DicVO.builder().code("24").name("永嘉县").build());
        area.put("22", DicVO.builder().code("22").name("洞头县").build());
        area.put("04", DicVO.builder().code("04").name("瓯海区").build());
        area.put("03", DicVO.builder().code("03").name("龙湾区").build());
        area.put("02", DicVO.builder().code("02").name("鹿城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("温州市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("奉化市").build());
        area.put("82", DicVO.builder().code("82").name("慈溪市").build());
        area.put("81", DicVO.builder().code("81").name("余姚市").build());
        area.put("26", DicVO.builder().code("26").name("宁海县").build());
        area.put("25", DicVO.builder().code("25").name("象山县").build());
        area.put("12", DicVO.builder().code("12").name("鄞州区").build());
        area.put("11", DicVO.builder().code("11").name("镇海区").build());
        area.put("06", DicVO.builder().code("06").name("北仑区").build());
        area.put("05", DicVO.builder().code("05").name("江北区").build());
        area.put("04", DicVO.builder().code("04").name("江东区").build());
        area.put("03", DicVO.builder().code("03").name("海曙区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("宁波市").child(area).build());

        area = new HashMap<>();
        area.put("85", DicVO.builder().code("85").name("临安市").build());
        area.put("83", DicVO.builder().code("83").name("富阳市").build());
        area.put("82", DicVO.builder().code("82").name("建德市").build());
        area.put("27", DicVO.builder().code("27").name("淳安县").build());
        area.put("22", DicVO.builder().code("22").name("桐庐县").build());
        area.put("10", DicVO.builder().code("10").name("余杭区").build());
        area.put("09", DicVO.builder().code("09").name("萧山区").build());
        area.put("08", DicVO.builder().code("08").name("滨江区").build());
        area.put("06", DicVO.builder().code("06").name("西湖区").build());
        area.put("05", DicVO.builder().code("05").name("拱墅区").build());
        area.put("04", DicVO.builder().code("04").name("江干区").build());
        area.put("03", DicVO.builder().code("03").name("下城区").build());
        area.put("02", DicVO.builder().code("02").name("上城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("杭州市").child(area).build());
        prov.put("33", DicVO.builder().code("33").name("浙江省").child(city).build());
    }

    private static void anHui(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("81", DicVO.builder().code("81").name("宁国市").build());
        area.put("25", DicVO.builder().code("25").name("旌德县").build());
        area.put("24", DicVO.builder().code("24").name("绩溪县").build());
        area.put("23", DicVO.builder().code("23").name("泾县").build());
        area.put("22", DicVO.builder().code("22").name("广德县").build());
        area.put("21", DicVO.builder().code("21").name("郎溪县").build());
        area.put("02", DicVO.builder().code("02").name("宣州区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("18", DicVO.builder().code("18").name("宣城市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("青阳县").build());
        area.put("22", DicVO.builder().code("22").name("石台县").build());
        area.put("21", DicVO.builder().code("21").name("东至县").build());
        area.put("02", DicVO.builder().code("02").name("贵池区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("17", DicVO.builder().code("17").name("池州市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("利辛县").build());
        area.put("22", DicVO.builder().code("22").name("蒙城县").build());
        area.put("21", DicVO.builder().code("21").name("涡阳县").build());
        area.put("02", DicVO.builder().code("02").name("谯城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("16", DicVO.builder().code("16").name("亳州市").child(area).build());

        area = new HashMap<>();
        area.put("25", DicVO.builder().code("25").name("霍山县").build());
        area.put("24", DicVO.builder().code("24").name("金寨县").build());
        area.put("23", DicVO.builder().code("23").name("舒城县").build());
        area.put("22", DicVO.builder().code("22").name("霍邱县").build());
        area.put("21", DicVO.builder().code("21").name("寿县").build());
        area.put("03", DicVO.builder().code("03").name("裕安区").build());
        area.put("02", DicVO.builder().code("02").name("金安区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("15", DicVO.builder().code("15").name("六安市").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("和县").build());
        area.put("23", DicVO.builder().code("23").name("含山县").build());
        area.put("22", DicVO.builder().code("22").name("无为县").build());
        area.put("21", DicVO.builder().code("21").name("庐江县").build());
        area.put("02", DicVO.builder().code("02").name("居巢区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("14", DicVO.builder().code("14").name("巢湖市").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("泗县").build());
        area.put("23", DicVO.builder().code("23").name("灵璧县").build());
        area.put("22", DicVO.builder().code("22").name("萧县").build());
        area.put("21", DicVO.builder().code("21").name("砀山县").build());
        area.put("02", DicVO.builder().code("02").name("墉桥区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("13", DicVO.builder().code("13").name("宿州市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("界首市").build());
        area.put("26", DicVO.builder().code("26").name("颍上县").build());
        area.put("25", DicVO.builder().code("25").name("阜南县").build());
        area.put("22", DicVO.builder().code("22").name("太和县").build());
        area.put("21", DicVO.builder().code("21").name("临泉县").build());
        area.put("04", DicVO.builder().code("04").name("颍泉区").build());
        area.put("03", DicVO.builder().code("03").name("颍东区").build());
        area.put("02", DicVO.builder().code("02").name("颍州区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("12", DicVO.builder().code("12").name("阜阳市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("明光市").build());
        area.put("81", DicVO.builder().code("81").name("天长市").build());
        area.put("26", DicVO.builder().code("26").name("凤阳县").build());
        area.put("25", DicVO.builder().code("25").name("定远县").build());
        area.put("24", DicVO.builder().code("24").name("全椒县").build());
        area.put("22", DicVO.builder().code("22").name("来安县").build());
        area.put("03", DicVO.builder().code("03").name("南谯区").build());
        area.put("02", DicVO.builder().code("02").name("琅琊区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("11", DicVO.builder().code("11").name("滁州市").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("祁门县").build());
        area.put("23", DicVO.builder().code("23").name("黟县").build());
        area.put("22", DicVO.builder().code("22").name("休宁县").build());
        area.put("21", DicVO.builder().code("21").name("歙县").build());
        area.put("04", DicVO.builder().code("04").name("徽州区").build());
        area.put("03", DicVO.builder().code("03").name("黄山区").build());
        area.put("02", DicVO.builder().code("02").name("屯溪区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("10", DicVO.builder().code("10").name("黄山市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("桐城市").build());
        area.put("28", DicVO.builder().code("28").name("岳西县").build());
        area.put("27", DicVO.builder().code("27").name("望江县").build());
        area.put("26", DicVO.builder().code("26").name("宿松县").build());
        area.put("25", DicVO.builder().code("25").name("太湖县").build());
        area.put("24", DicVO.builder().code("24").name("潜山县").build());
        area.put("23", DicVO.builder().code("23").name("枞阳县").build());
        area.put("22", DicVO.builder().code("22").name("怀宁县").build());
        area.put("11", DicVO.builder().code("11").name("郊区").build());
        area.put("03", DicVO.builder().code("03").name("大观区").build());
        area.put("02", DicVO.builder().code("02").name("迎江区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("安庆市").child(area).build());

        area = new HashMap<>();
        area.put("21", DicVO.builder().code("21").name("铜陵县").build());
        area.put("11", DicVO.builder().code("11").name("郊区").build());
        area.put("03", DicVO.builder().code("03").name("狮子山区").build());
        area.put("02", DicVO.builder().code("02").name("铜官山区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("铜陵市").child(area).build());

        area = new HashMap<>();
        area.put("21", DicVO.builder().code("21").name("濉溪县").build());
        area.put("04", DicVO.builder().code("04").name("烈山区").build());
        area.put("03", DicVO.builder().code("03").name("相山区").build());
        area.put("02", DicVO.builder().code("02").name("杜集区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("淮北市").child(area).build());

        area = new HashMap<>();
        area.put("21", DicVO.builder().code("21").name("当涂县").build());
        area.put("04", DicVO.builder().code("04").name("雨山区").build());
        area.put("03", DicVO.builder().code("03").name("花山区").build());
        area.put("02", DicVO.builder().code("02").name("金家庄区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("马鞍山市").child(area).build());

        area = new HashMap<>();
        area.put("21", DicVO.builder().code("21").name("凤台县").build());
        area.put("06", DicVO.builder().code("06").name("潘集区").build());
        area.put("05", DicVO.builder().code("05").name("八公山区").build());
        area.put("04", DicVO.builder().code("04").name("谢家集区").build());
        area.put("03", DicVO.builder().code("03").name("田家庵区").build());
        area.put("02", DicVO.builder().code("02").name("大通区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("淮南市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("固镇县").build());
        area.put("22", DicVO.builder().code("22").name("五河县").build());
        area.put("21", DicVO.builder().code("21").name("怀远县").build());
        area.put("11", DicVO.builder().code("11").name("郊区").build());
        area.put("04", DicVO.builder().code("04").name("西市区").build());
        area.put("03", DicVO.builder().code("03").name("中市区").build());
        area.put("02", DicVO.builder().code("02").name("东市区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("蚌埠市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("南陵县").build());
        area.put("22", DicVO.builder().code("22").name("繁昌县").build());
        area.put("21", DicVO.builder().code("21").name("芜湖县").build());
        area.put("07", DicVO.builder().code("07").name("鸠江区").build());
        area.put("04", DicVO.builder().code("04").name("新芜区").build());
        area.put("03", DicVO.builder().code("03").name("马塘区").build());
        area.put("02", DicVO.builder().code("02").name("镜湖区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("芜湖市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("肥西县").build());
        area.put("22", DicVO.builder().code("22").name("肥东县").build());
        area.put("21", DicVO.builder().code("21").name("长丰县").build());
        area.put("11", DicVO.builder().code("11").name("包河区").build());
        area.put("04", DicVO.builder().code("04").name("蜀山区").build());
        area.put("03", DicVO.builder().code("03").name("庐阳区").build());
        area.put("02", DicVO.builder().code("02").name("瑶海区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("合肥市").child(area).build());
        prov.put("34", DicVO.builder().code("34").name("安徽省").child(city).build());
    }

    private static void fuJian(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("82", DicVO.builder().code("82").name("福鼎市").build());
        area.put("81", DicVO.builder().code("81").name("福安市").build());
        area.put("26", DicVO.builder().code("26").name("柘荣县").build());
        area.put("25", DicVO.builder().code("25").name("周宁县").build());
        area.put("24", DicVO.builder().code("24").name("寿宁县").build());
        area.put("23", DicVO.builder().code("23").name("屏南县").build());
        area.put("22", DicVO.builder().code("22").name("古田县").build());
        area.put("21", DicVO.builder().code("21").name("霞浦县").build());
        area.put("02", DicVO.builder().code("02").name("蕉城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("宁德市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("漳平市").build());
        area.put("25", DicVO.builder().code("25").name("连城县").build());
        area.put("24", DicVO.builder().code("24").name("武平县").build());
        area.put("23", DicVO.builder().code("23").name("上杭县").build());
        area.put("22", DicVO.builder().code("22").name("永定县").build());
        area.put("21", DicVO.builder().code("21").name("长汀县").build());
        area.put("02", DicVO.builder().code("02").name("新罗区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("龙岩市").child(area).build());

        area = new HashMap<>();
        area.put("84", DicVO.builder().code("84").name("建阳市").build());
        area.put("83", DicVO.builder().code("83").name("建瓯市").build());
        area.put("82", DicVO.builder().code("82").name("武夷山市").build());
        area.put("81", DicVO.builder().code("81").name("邵武市").build());
        area.put("25", DicVO.builder().code("25").name("政和县").build());
        area.put("24", DicVO.builder().code("24").name("松溪县").build());
        area.put("23", DicVO.builder().code("23").name("光泽县").build());
        area.put("22", DicVO.builder().code("22").name("浦城县").build());
        area.put("21", DicVO.builder().code("21").name("顺昌县").build());
        area.put("02", DicVO.builder().code("02").name("延平区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("南平市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("龙海市").build());
        area.put("29", DicVO.builder().code("29").name("华安县").build());
        area.put("28", DicVO.builder().code("28").name("平和县").build());
        area.put("27", DicVO.builder().code("27").name("南靖县").build());
        area.put("26", DicVO.builder().code("26").name("东山县").build());
        area.put("25", DicVO.builder().code("25").name("长泰县").build());
        area.put("24", DicVO.builder().code("24").name("诏安县").build());
        area.put("23", DicVO.builder().code("23").name("漳浦县").build());
        area.put("22", DicVO.builder().code("22").name("云霄县").build());
        area.put("03", DicVO.builder().code("03").name("龙文区").build());
        area.put("02", DicVO.builder().code("02").name("芗城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("漳州市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("南安市").build());
        area.put("82", DicVO.builder().code("82").name("晋江市").build());
        area.put("81", DicVO.builder().code("81").name("石狮市").build());
        area.put("27", DicVO.builder().code("27").name("金门县").build());
        area.put("26", DicVO.builder().code("26").name("德化县").build());
        area.put("25", DicVO.builder().code("25").name("永春县").build());
        area.put("24", DicVO.builder().code("24").name("安溪县").build());
        area.put("21", DicVO.builder().code("21").name("惠安县").build());
        area.put("05", DicVO.builder().code("05").name("泉港区").build());
        area.put("04", DicVO.builder().code("04").name("洛江区").build());
        area.put("03", DicVO.builder().code("03").name("丰泽区").build());
        area.put("02", DicVO.builder().code("02").name("鲤城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("泉州市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("永安市").build());
        area.put("30", DicVO.builder().code("30").name("建宁县").build());
        area.put("29", DicVO.builder().code("29").name("泰宁县").build());
        area.put("28", DicVO.builder().code("28").name("将乐县").build());
        area.put("27", DicVO.builder().code("27").name("沙县").build());
        area.put("26", DicVO.builder().code("26").name("尤溪县").build());
        area.put("25", DicVO.builder().code("25").name("大田县").build());
        area.put("24", DicVO.builder().code("24").name("宁化县").build());
        area.put("23", DicVO.builder().code("23").name("清流县").build());
        area.put("21", DicVO.builder().code("21").name("明溪县").build());
        area.put("03", DicVO.builder().code("03").name("三元区").build());
        area.put("02", DicVO.builder().code("02").name("梅列区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("三明市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("仙游县").build());
        area.put("05", DicVO.builder().code("05").name("秀屿区").build());
        area.put("04", DicVO.builder().code("04").name("荔城区").build());
        area.put("03", DicVO.builder().code("03").name("涵江区").build());
        area.put("02", DicVO.builder().code("02").name("城厢区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("莆田市").child(area).build());

        area = new HashMap<>();
        area.put("13", DicVO.builder().code("13").name("翔安区").build());
        area.put("12", DicVO.builder().code("12").name("同安区").build());
        area.put("11", DicVO.builder().code("11").name("集美区").build());
        area.put("06", DicVO.builder().code("06").name("湖里区").build());
        area.put("05", DicVO.builder().code("05").name("海沧区").build());
        area.put("03", DicVO.builder().code("03").name("思明区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("厦门市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("长乐市").build());
        area.put("81", DicVO.builder().code("81").name("福清市").build());
        area.put("28", DicVO.builder().code("28").name("平潭县").build());
        area.put("25", DicVO.builder().code("25").name("永泰县").build());
        area.put("24", DicVO.builder().code("24").name("闽清县").build());
        area.put("23", DicVO.builder().code("23").name("罗源县").build());
        area.put("22", DicVO.builder().code("22").name("连江县").build());
        area.put("21", DicVO.builder().code("21").name("闽侯县").build());
        area.put("11", DicVO.builder().code("11").name("晋安区").build());
        area.put("05", DicVO.builder().code("05").name("马尾区").build());
        area.put("04", DicVO.builder().code("04").name("仓山区").build());
        area.put("03", DicVO.builder().code("03").name("台江区").build());
        area.put("02", DicVO.builder().code("02").name("鼓楼区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("福州市").child(area).build());
        prov.put("35", DicVO.builder().code("35").name("福建省").child(city).build());
    }

    private static void jiangXi(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("81", DicVO.builder().code("81").name("德兴市").build());
        area.put("30", DicVO.builder().code("30").name("婺源县").build());
        area.put("29", DicVO.builder().code("29").name("万年县").build());
        area.put("28", DicVO.builder().code("28").name("鄱阳县").build());
        area.put("27", DicVO.builder().code("27").name("余干县").build());
        area.put("26", DicVO.builder().code("26").name("弋阳县").build());
        area.put("25", DicVO.builder().code("25").name("横峰县").build());
        area.put("24", DicVO.builder().code("24").name("铅山县").build());
        area.put("23", DicVO.builder().code("23").name("玉山县").build());
        area.put("22", DicVO.builder().code("22").name("广丰县").build());
        area.put("21", DicVO.builder().code("21").name("上饶县").build());
        area.put("02", DicVO.builder().code("02").name("信州区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("11", DicVO.builder().code("11").name("上饶市").child(area).build());

        area = new HashMap<>();
        area.put("30", DicVO.builder().code("30").name("广昌县").build());
        area.put("29", DicVO.builder().code("29").name("东乡县").build());
        area.put("28", DicVO.builder().code("28").name("资溪县").build());
        area.put("27", DicVO.builder().code("27").name("金溪县").build());
        area.put("26", DicVO.builder().code("26").name("宜黄县").build());
        area.put("25", DicVO.builder().code("25").name("乐安县").build());
        area.put("24", DicVO.builder().code("24").name("崇仁县").build());
        area.put("23", DicVO.builder().code("23").name("南丰县").build());
        area.put("22", DicVO.builder().code("22").name("黎川县").build());
        area.put("21", DicVO.builder().code("21").name("南城县").build());
        area.put("02", DicVO.builder().code("02").name("临川区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("10", DicVO.builder().code("10").name("抚州市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("高安市").build());
        area.put("82", DicVO.builder().code("82").name("樟树市").build());
        area.put("81", DicVO.builder().code("81").name("丰城市").build());
        area.put("26", DicVO.builder().code("26").name("铜鼓县").build());
        area.put("25", DicVO.builder().code("25").name("靖安县").build());
        area.put("24", DicVO.builder().code("24").name("宜丰县").build());
        area.put("23", DicVO.builder().code("23").name("上高县").build());
        area.put("22", DicVO.builder().code("22").name("万载县").build());
        area.put("21", DicVO.builder().code("21").name("奉新县").build());
        area.put("02", DicVO.builder().code("02").name("袁州区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("宜春市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("井冈山市").build());
        area.put("30", DicVO.builder().code("30").name("永新县").build());
        area.put("29", DicVO.builder().code("29").name("安福县").build());
        area.put("28", DicVO.builder().code("28").name("万安县").build());
        area.put("27", DicVO.builder().code("27").name("遂川县").build());
        area.put("26", DicVO.builder().code("26").name("泰和县").build());
        area.put("25", DicVO.builder().code("25").name("永丰县").build());
        area.put("24", DicVO.builder().code("24").name("新干县").build());
        area.put("23", DicVO.builder().code("23").name("峡江县").build());
        area.put("22", DicVO.builder().code("22").name("吉水县").build());
        area.put("21", DicVO.builder().code("21").name("吉安县").build());
        area.put("03", DicVO.builder().code("03").name("青原区").build());
        area.put("02", DicVO.builder().code("02").name("吉州区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("吉安市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("南康市").build());
        area.put("81", DicVO.builder().code("81").name("瑞金市").build());
        area.put("35", DicVO.builder().code("35").name("石城县").build());
        area.put("34", DicVO.builder().code("34").name("寻乌县").build());
        area.put("33", DicVO.builder().code("33").name("会昌县").build());
        area.put("32", DicVO.builder().code("32").name("兴国县").build());
        area.put("31", DicVO.builder().code("31").name("于都县").build());
        area.put("30", DicVO.builder().code("30").name("宁都县").build());
        area.put("29", DicVO.builder().code("29").name("全南县").build());
        area.put("28", DicVO.builder().code("28").name("定南县").build());
        area.put("27", DicVO.builder().code("27").name("龙南县").build());
        area.put("26", DicVO.builder().code("26").name("安远县").build());
        area.put("25", DicVO.builder().code("25").name("崇义县").build());
        area.put("24", DicVO.builder().code("24").name("上犹县").build());
        area.put("23", DicVO.builder().code("23").name("大余县").build());
        area.put("22", DicVO.builder().code("22").name("信丰县").build());
        area.put("21", DicVO.builder().code("21").name("赣县").build());
        area.put("02", DicVO.builder().code("02").name("章贡区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("赣州市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("贵溪市").build());
        area.put("22", DicVO.builder().code("22").name("余江县").build());
        area.put("02", DicVO.builder().code("02").name("月湖区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("鹰潭市").child(area).build());

        area = new HashMap<>();
        area.put("21", DicVO.builder().code("21").name("分宜县").build());
        area.put("02", DicVO.builder().code("02").name("渝水区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("新余市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("瑞昌市").build());
        area.put("30", DicVO.builder().code("30").name("彭泽县").build());
        area.put("29", DicVO.builder().code("29").name("湖口县").build());
        area.put("28", DicVO.builder().code("28").name("都昌县").build());
        area.put("27", DicVO.builder().code("27").name("星子县").build());
        area.put("26", DicVO.builder().code("26").name("德安县").build());
        area.put("25", DicVO.builder().code("25").name("永修县").build());
        area.put("24", DicVO.builder().code("24").name("修水县").build());
        area.put("23", DicVO.builder().code("23").name("武宁县").build());
        area.put("21", DicVO.builder().code("21").name("九江县").build());
        area.put("03", DicVO.builder().code("03").name("浔阳区").build());
        area.put("02", DicVO.builder().code("02").name("庐山区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("九江市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("芦溪县").build());
        area.put("22", DicVO.builder().code("22").name("上栗县").build());
        area.put("21", DicVO.builder().code("21").name("莲花县").build());
        area.put("13", DicVO.builder().code("13").name("湘东区").build());
        area.put("02", DicVO.builder().code("02").name("安源区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("萍乡市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("乐平市").build());
        area.put("22", DicVO.builder().code("22").name("浮梁县").build());
        area.put("03", DicVO.builder().code("03").name("珠山区").build());
        area.put("02", DicVO.builder().code("02").name("昌江区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("景德镇市").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("进贤县").build());
        area.put("23", DicVO.builder().code("23").name("安义县").build());
        area.put("22", DicVO.builder().code("22").name("新建县").build());
        area.put("21", DicVO.builder().code("21").name("南昌县").build());
        area.put("11", DicVO.builder().code("11").name("青山湖区").build());
        area.put("05", DicVO.builder().code("05").name("湾里区").build());
        area.put("04", DicVO.builder().code("04").name("青云谱区").build());
        area.put("03", DicVO.builder().code("03").name("西湖区").build());
        area.put("02", DicVO.builder().code("02").name("东湖区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("南昌市").child(area).build());
        prov.put("36", DicVO.builder().code("36").name("江西省").child(city).build());
    }

    private static void shanDong(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("28", DicVO.builder().code("28").name("东明县").build());
        area.put("27", DicVO.builder().code("27").name("定陶县").build());
        area.put("26", DicVO.builder().code("26").name("鄄城县").build());
        area.put("25", DicVO.builder().code("25").name("郓城县").build());
        area.put("24", DicVO.builder().code("24").name("巨野县").build());
        area.put("23", DicVO.builder().code("23").name("成武县").build());
        area.put("22", DicVO.builder().code("22").name("单县").build());
        area.put("21", DicVO.builder().code("21").name("曹县").build());
        area.put("02", DicVO.builder().code("02").name("牡丹区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("17", DicVO.builder().code("17").name("荷泽市").child(area).build());

        area = new HashMap<>();
        area.put("26", DicVO.builder().code("26").name("邹平县").build());
        area.put("25", DicVO.builder().code("25").name("博兴县").build());
        area.put("24", DicVO.builder().code("24").name("沾化县").build());
        area.put("23", DicVO.builder().code("23").name("无棣县").build());
        area.put("22", DicVO.builder().code("22").name("阳信县").build());
        area.put("21", DicVO.builder().code("21").name("惠民县").build());
        area.put("02", DicVO.builder().code("02").name("滨城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("16", DicVO.builder().code("16").name("滨州市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("临清市").build());
        area.put("26", DicVO.builder().code("26").name("高唐县").build());
        area.put("25", DicVO.builder().code("25").name("冠县").build());
        area.put("24", DicVO.builder().code("24").name("东阿县").build());
        area.put("23", DicVO.builder().code("23").name("茌平县").build());
        area.put("22", DicVO.builder().code("22").name("莘县").build());
        area.put("21", DicVO.builder().code("21").name("阳谷县").build());
        area.put("02", DicVO.builder().code("02").name("东昌府区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("15", DicVO.builder().code("15").name("聊城市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("禹城市").build());
        area.put("81", DicVO.builder().code("81").name("乐陵市").build());
        area.put("28", DicVO.builder().code("28").name("武城县").build());
        area.put("27", DicVO.builder().code("27").name("夏津县").build());
        area.put("26", DicVO.builder().code("26").name("平原县").build());
        area.put("25", DicVO.builder().code("25").name("齐河县").build());
        area.put("24", DicVO.builder().code("24").name("临邑县").build());
        area.put("23", DicVO.builder().code("23").name("庆云县").build());
        area.put("22", DicVO.builder().code("22").name("宁津县").build());
        area.put("21", DicVO.builder().code("21").name("陵县").build());
        area.put("02", DicVO.builder().code("02").name("德城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("14", DicVO.builder().code("14").name("德州市").child(area).build());

        area = new HashMap<>();
        area.put("29", DicVO.builder().code("29").name("临沭县").build());
        area.put("28", DicVO.builder().code("28").name("蒙阴县").build());
        area.put("27", DicVO.builder().code("27").name("莒南县").build());
        area.put("26", DicVO.builder().code("26").name("平邑县").build());
        area.put("25", DicVO.builder().code("25").name("费县").build());
        area.put("24", DicVO.builder().code("24").name("苍山县").build());
        area.put("23", DicVO.builder().code("23").name("沂水县").build());
        area.put("22", DicVO.builder().code("22").name("郯城县").build());
        area.put("21", DicVO.builder().code("21").name("沂南县").build());
        area.put("12", DicVO.builder().code("12").name("河东区").build());
        area.put("11", DicVO.builder().code("11").name("罗庄区").build());
        area.put("02", DicVO.builder().code("02").name("兰山区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("13", DicVO.builder().code("13").name("临沂市").child(area).build());

        area = new HashMap<>();
        area.put("03", DicVO.builder().code("03").name("钢城区").build());
        area.put("02", DicVO.builder().code("02").name("莱城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("12", DicVO.builder().code("12").name("莱芜市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("莒县").build());
        area.put("21", DicVO.builder().code("21").name("五莲县").build());
        area.put("02", DicVO.builder().code("02").name("东港区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("11", DicVO.builder().code("11").name("日照市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("乳山市").build());
        area.put("82", DicVO.builder().code("82").name("荣成市").build());
        area.put("81", DicVO.builder().code("81").name("文登市").build());
        area.put("02", DicVO.builder().code("02").name("环翠区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("10", DicVO.builder().code("10").name("威海市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("肥城市").build());
        area.put("82", DicVO.builder().code("82").name("新泰市").build());
        area.put("23", DicVO.builder().code("23").name("东平县").build());
        area.put("21", DicVO.builder().code("21").name("宁阳县").build());
        area.put("03", DicVO.builder().code("03").name("岱岳区").build());
        area.put("02", DicVO.builder().code("02").name("泰山区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("泰安市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("邹城市").build());
        area.put("82", DicVO.builder().code("82").name("兖州市").build());
        area.put("81", DicVO.builder().code("81").name("曲阜市").build());
        area.put("32", DicVO.builder().code("32").name("梁山县").build());
        area.put("31", DicVO.builder().code("31").name("泗水县").build());
        area.put("30", DicVO.builder().code("30").name("汶上县").build());
        area.put("29", DicVO.builder().code("29").name("嘉祥县").build());
        area.put("28", DicVO.builder().code("28").name("金乡县").build());
        area.put("27", DicVO.builder().code("27").name("鱼台县").build());
        area.put("26", DicVO.builder().code("26").name("微山县").build());
        area.put("11", DicVO.builder().code("11").name("任城区").build());
        area.put("02", DicVO.builder().code("02").name("市中区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("济宁市").child(area).build());

        area = new HashMap<>();
        area.put("86", DicVO.builder().code("86").name("昌邑市").build());
        area.put("85", DicVO.builder().code("85").name("高密市").build());
        area.put("84", DicVO.builder().code("84").name("安丘市").build());
        area.put("83", DicVO.builder().code("83").name("寿光市").build());
        area.put("82", DicVO.builder().code("82").name("诸城市").build());
        area.put("81", DicVO.builder().code("81").name("青州市").build());
        area.put("25", DicVO.builder().code("25").name("昌乐县").build());
        area.put("24", DicVO.builder().code("24").name("临朐县").build());
        area.put("05", DicVO.builder().code("05").name("奎文区").build());
        area.put("04", DicVO.builder().code("04").name("坊子区").build());
        area.put("03", DicVO.builder().code("03").name("寒亭区").build());
        area.put("02", DicVO.builder().code("02").name("潍城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("潍坊市").child(area).build());

        area = new HashMap<>();
        area.put("87", DicVO.builder().code("87").name("海阳市").build());
        area.put("86", DicVO.builder().code("86").name("栖霞市").build());
        area.put("85", DicVO.builder().code("85").name("招远市").build());
        area.put("84", DicVO.builder().code("84").name("蓬莱市").build());
        area.put("83", DicVO.builder().code("83").name("莱州市").build());
        area.put("82", DicVO.builder().code("82").name("莱阳市").build());
        area.put("81", DicVO.builder().code("81").name("龙口市").build());
        area.put("34", DicVO.builder().code("34").name("长岛县").build());
        area.put("13", DicVO.builder().code("13").name("莱山区").build());
        area.put("12", DicVO.builder().code("12").name("牟平区").build());
        area.put("11", DicVO.builder().code("11").name("福山区").build());
        area.put("02", DicVO.builder().code("02").name("芝罘区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("烟台市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("广饶县").build());
        area.put("22", DicVO.builder().code("22").name("利津县").build());
        area.put("21", DicVO.builder().code("21").name("垦利县").build());
        area.put("03", DicVO.builder().code("03").name("河口区").build());
        area.put("02", DicVO.builder().code("02").name("东营区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("东营市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("滕州市").build());
        area.put("06", DicVO.builder().code("06").name("山亭区").build());
        area.put("05", DicVO.builder().code("05").name("台儿庄区").build());
        area.put("04", DicVO.builder().code("04").name("峄城区").build());
        area.put("03", DicVO.builder().code("03").name("薛城区").build());
        area.put("02", DicVO.builder().code("02").name("市中区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("枣庄市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("沂源县").build());
        area.put("22", DicVO.builder().code("22").name("高青县").build());
        area.put("21", DicVO.builder().code("21").name("桓台县").build());
        area.put("06", DicVO.builder().code("06").name("周村区").build());
        area.put("05", DicVO.builder().code("05").name("临淄区").build());
        area.put("04", DicVO.builder().code("04").name("博山区").build());
        area.put("03", DicVO.builder().code("03").name("张店区").build());
        area.put("02", DicVO.builder().code("02").name("淄川区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("淄博市").child(area).build());

        area = new HashMap<>();
        area.put("85", DicVO.builder().code("85").name("莱西市").build());
        area.put("84", DicVO.builder().code("84").name("胶南市").build());
        area.put("83", DicVO.builder().code("83").name("平度市").build());
        area.put("82", DicVO.builder().code("82").name("即墨市").build());
        area.put("81", DicVO.builder().code("81").name("胶州市").build());
        area.put("14", DicVO.builder().code("14").name("城阳区").build());
        area.put("13", DicVO.builder().code("13").name("李沧区").build());
        area.put("12", DicVO.builder().code("12").name("崂山区").build());
        area.put("11", DicVO.builder().code("11").name("黄岛区").build());
        area.put("05", DicVO.builder().code("05").name("四方区").build());
        area.put("03", DicVO.builder().code("03").name("市北区").build());
        area.put("02", DicVO.builder().code("02").name("市南区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("青岛市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("章丘市").build());
        area.put("26", DicVO.builder().code("26").name("商河县").build());
        area.put("25", DicVO.builder().code("25").name("济阳县").build());
        area.put("24", DicVO.builder().code("24").name("平阴县").build());
        area.put("13", DicVO.builder().code("13").name("长清区").build());
        area.put("12", DicVO.builder().code("12").name("历城区").build());
        area.put("05", DicVO.builder().code("05").name("天桥区").build());
        area.put("04", DicVO.builder().code("04").name("槐荫区").build());
        area.put("03", DicVO.builder().code("03").name("市中区").build());
        area.put("02", DicVO.builder().code("02").name("历下区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("济南市").child(area).build());
        prov.put("37", DicVO.builder().code("37").name("山东省").child(city).build());
    }

    private static void heNan(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("29", DicVO.builder().code("29").name("新蔡县").build());
        area.put("28", DicVO.builder().code("28").name("遂平县").build());
        area.put("27", DicVO.builder().code("27").name("汝南县").build());
        area.put("26", DicVO.builder().code("26").name("泌阳县").build());
        area.put("25", DicVO.builder().code("25").name("确山县").build());
        area.put("24", DicVO.builder().code("24").name("正阳县").build());
        area.put("23", DicVO.builder().code("23").name("平舆县").build());
        area.put("22", DicVO.builder().code("22").name("上蔡县").build());
        area.put("21", DicVO.builder().code("21").name("西平县").build());
        area.put("02", DicVO.builder().code("02").name("驿城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("17", DicVO.builder().code("17").name("驻马店市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("项城市").build());
        area.put("28", DicVO.builder().code("28").name("鹿邑县").build());
        area.put("27", DicVO.builder().code("27").name("太康县").build());
        area.put("26", DicVO.builder().code("26").name("淮阳县").build());
        area.put("25", DicVO.builder().code("25").name("郸城县").build());
        area.put("24", DicVO.builder().code("24").name("沈丘县").build());
        area.put("23", DicVO.builder().code("23").name("商水县").build());
        area.put("22", DicVO.builder().code("22").name("西华县").build());
        area.put("21", DicVO.builder().code("21").name("扶沟县").build());
        area.put("02", DicVO.builder().code("02").name("川汇区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("16", DicVO.builder().code("16").name("周口市").child(area).build());

        area = new HashMap<>();
        area.put("28", DicVO.builder().code("28").name("息县").build());
        area.put("27", DicVO.builder().code("27").name("淮滨县").build());
        area.put("26", DicVO.builder().code("26").name("潢川县").build());
        area.put("25", DicVO.builder().code("25").name("固始县").build());
        area.put("24", DicVO.builder().code("24").name("商城县").build());
        area.put("23", DicVO.builder().code("23").name("新县").build());
        area.put("22", DicVO.builder().code("22").name("光山县").build());
        area.put("21", DicVO.builder().code("21").name("罗山县").build());
        area.put("03", DicVO.builder().code("03").name("平桥区").build());
        area.put("02", DicVO.builder().code("02").name("师河区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("15", DicVO.builder().code("15").name("信阳市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("永城市").build());
        area.put("26", DicVO.builder().code("26").name("夏邑县").build());
        area.put("25", DicVO.builder().code("25").name("虞城县").build());
        area.put("24", DicVO.builder().code("24").name("柘城县").build());
        area.put("23", DicVO.builder().code("23").name("宁陵县").build());
        area.put("22", DicVO.builder().code("22").name("睢县").build());
        area.put("21", DicVO.builder().code("21").name("民权县").build());
        area.put("03", DicVO.builder().code("03").name("睢阳区").build());
        area.put("02", DicVO.builder().code("02").name("梁园区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("14", DicVO.builder().code("14").name("商丘市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("邓州市").build());
        area.put("30", DicVO.builder().code("30").name("桐柏县").build());
        area.put("29", DicVO.builder().code("29").name("新野县").build());
        area.put("28", DicVO.builder().code("28").name("唐河县").build());
        area.put("27", DicVO.builder().code("27").name("社旗县").build());
        area.put("26", DicVO.builder().code("26").name("淅川县").build());
        area.put("25", DicVO.builder().code("25").name("内乡县").build());
        area.put("24", DicVO.builder().code("24").name("镇平县").build());
        area.put("23", DicVO.builder().code("23").name("西峡县").build());
        area.put("22", DicVO.builder().code("22").name("方城县").build());
        area.put("21", DicVO.builder().code("21").name("南召县").build());
        area.put("03", DicVO.builder().code("03").name("卧龙区").build());
        area.put("02", DicVO.builder().code("02").name("宛城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("13", DicVO.builder().code("13").name("南阳市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("灵宝市").build());
        area.put("81", DicVO.builder().code("81").name("义马市").build());
        area.put("24", DicVO.builder().code("24").name("卢氏县").build());
        area.put("22", DicVO.builder().code("22").name("陕县").build());
        area.put("21", DicVO.builder().code("21").name("渑池县").build());
        area.put("02", DicVO.builder().code("02").name("湖滨区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("12", DicVO.builder().code("12").name("三门峡市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("郾城县").build());
        area.put("22", DicVO.builder().code("22").name("临颍县").build());
        area.put("21", DicVO.builder().code("21").name("舞阳县").build());
        area.put("02", DicVO.builder().code("02").name("源汇区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("11", DicVO.builder().code("11").name("漯河市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("长葛市").build());
        area.put("81", DicVO.builder().code("81").name("禹州市").build());
        area.put("25", DicVO.builder().code("25").name("襄城县").build());
        area.put("24", DicVO.builder().code("24").name("鄢陵县").build());
        area.put("23", DicVO.builder().code("23").name("许昌县").build());
        area.put("02", DicVO.builder().code("02").name("魏都区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("10", DicVO.builder().code("10").name("许昌市").child(area).build());

        area = new HashMap<>();
        area.put("28", DicVO.builder().code("28").name("濮阳县").build());
        area.put("27", DicVO.builder().code("27").name("台前县").build());
        area.put("26", DicVO.builder().code("26").name("范县").build());
        area.put("23", DicVO.builder().code("23").name("南乐县").build());
        area.put("22", DicVO.builder().code("22").name("清丰县").build());
        area.put("02", DicVO.builder().code("02").name("华龙区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("濮阳市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("孟州市").build());
        area.put("82", DicVO.builder().code("82").name("沁阳市").build());
        area.put("81", DicVO.builder().code("81").name("济源市").build());
        area.put("25", DicVO.builder().code("25").name("温县").build());
        area.put("23", DicVO.builder().code("23").name("武陟县").build());
        area.put("22", DicVO.builder().code("22").name("博爱县").build());
        area.put("21", DicVO.builder().code("21").name("修武县").build());
        area.put("11", DicVO.builder().code("11").name("山阳区").build());
        area.put("04", DicVO.builder().code("04").name("马村区").build());
        area.put("03", DicVO.builder().code("03").name("中站区").build());
        area.put("02", DicVO.builder().code("02").name("解放区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("焦作市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("辉县市").build());
        area.put("81", DicVO.builder().code("81").name("卫辉市").build());
        area.put("28", DicVO.builder().code("28").name("长垣县").build());
        area.put("27", DicVO.builder().code("27").name("封丘县").build());
        area.put("26", DicVO.builder().code("26").name("延津县").build());
        area.put("25", DicVO.builder().code("25").name("原阳县").build());
        area.put("24", DicVO.builder().code("24").name("获嘉县").build());
        area.put("21", DicVO.builder().code("21").name("新乡县").build());
        area.put("11", DicVO.builder().code("11").name("牧野区").build());
        area.put("04", DicVO.builder().code("04").name("凤泉区").build());
        area.put("03", DicVO.builder().code("03").name("卫滨区").build());
        area.put("02", DicVO.builder().code("02").name("红旗区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("新乡市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("淇县").build());
        area.put("21", DicVO.builder().code("21").name("浚县").build());
        area.put("11", DicVO.builder().code("11").name("淇滨区").build());
        area.put("03", DicVO.builder().code("03").name("山城区").build());
        area.put("02", DicVO.builder().code("02").name("鹤山区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("鹤壁市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("林州市").build());
        area.put("27", DicVO.builder().code("27").name("内黄县").build());
        area.put("26", DicVO.builder().code("26").name("滑县").build());
        area.put("23", DicVO.builder().code("23").name("汤阴县").build());
        area.put("22", DicVO.builder().code("22").name("安阳县").build());
        area.put("06", DicVO.builder().code("06").name("龙安区").build());
        area.put("05", DicVO.builder().code("05").name("殷都区").build());
        area.put("03", DicVO.builder().code("03").name("北关区").build());
        area.put("02", DicVO.builder().code("02").name("文峰区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("安阳市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("汝州市").build());
        area.put("81", DicVO.builder().code("81").name("舞钢市").build());
        area.put("25", DicVO.builder().code("25").name("郏县").build());
        area.put("23", DicVO.builder().code("23").name("鲁山县").build());
        area.put("22", DicVO.builder().code("22").name("叶县").build());
        area.put("21", DicVO.builder().code("21").name("宝丰县").build());
        area.put("11", DicVO.builder().code("11").name("湛河区").build());
        area.put("04", DicVO.builder().code("04").name("石龙区").build());
        area.put("03", DicVO.builder().code("03").name("卫东区").build());
        area.put("02", DicVO.builder().code("02").name("新华区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("平顶山市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("偃师市").build());
        area.put("29", DicVO.builder().code("29").name("伊川县").build());
        area.put("28", DicVO.builder().code("28").name("洛宁县").build());
        area.put("27", DicVO.builder().code("27").name("宜阳县").build());
        area.put("26", DicVO.builder().code("26").name("汝阳县").build());
        area.put("25", DicVO.builder().code("25").name("嵩县").build());
        area.put("24", DicVO.builder().code("24").name("栾川县").build());
        area.put("23", DicVO.builder().code("23").name("新安县").build());
        area.put("22", DicVO.builder().code("22").name("孟津县").build());
        area.put("07", DicVO.builder().code("07").name("洛龙区").build());
        area.put("06", DicVO.builder().code("06").name("吉利区").build());
        area.put("05", DicVO.builder().code("05").name("涧西区").build());
        area.put("04", DicVO.builder().code("04").name("廛河回族区").build());
        area.put("03", DicVO.builder().code("03").name("西工区").build());
        area.put("02", DicVO.builder().code("02").name("老城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("洛阳市").child(area).build());

        area = new HashMap<>();
        area.put("25", DicVO.builder().code("25").name("兰考县").build());
        area.put("24", DicVO.builder().code("24").name("开封县").build());
        area.put("23", DicVO.builder().code("23").name("尉氏县").build());
        area.put("22", DicVO.builder().code("22").name("通许县").build());
        area.put("21", DicVO.builder().code("21").name("杞县").build());
        area.put("11", DicVO.builder().code("11").name("郊区").build());
        area.put("05", DicVO.builder().code("05").name("南关区").build());
        area.put("04", DicVO.builder().code("04").name("鼓楼区").build());
        area.put("03", DicVO.builder().code("03").name("顺河回族区").build());
        area.put("02", DicVO.builder().code("02").name("龙亭区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("开封市").child(area).build());

        area = new HashMap<>();
        area.put("85", DicVO.builder().code("85").name("登封市").build());
        area.put("84", DicVO.builder().code("84").name("新郑市").build());
        area.put("83", DicVO.builder().code("83").name("新密市").build());
        area.put("82", DicVO.builder().code("82").name("荥阳市").build());
        area.put("81", DicVO.builder().code("81").name("巩义市").build());
        area.put("22", DicVO.builder().code("22").name("中牟县").build());
        area.put("08", DicVO.builder().code("08").name("邙山区").build());
        area.put("06", DicVO.builder().code("06").name("上街区").build());
        area.put("05", DicVO.builder().code("05").name("金水区").build());
        area.put("04", DicVO.builder().code("04").name("管城回族区").build());
        area.put("03", DicVO.builder().code("03").name("二七区").build());
        area.put("02", DicVO.builder().code("02").name("中原区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("郑州市").child(area).build());
        prov.put("41", DicVO.builder().code("41").name("河南省").child(city).build());
    }

    private static void huBei(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("21", DicVO.builder().code("21").name("神农架林区").build());
        area.put("06", DicVO.builder().code("06").name("天门市").build());
        area.put("05", DicVO.builder().code("05").name("潜江市").build());
        area.put("04", DicVO.builder().code("04").name("仙桃市").build());
        city.put("90", DicVO.builder().code("90").name("省直辖行政单位").child(area).build());

        area = new HashMap<>();
        area.put("28", DicVO.builder().code("28").name("鹤峰县").build());
        area.put("27", DicVO.builder().code("27").name("来凤县").build());
        area.put("26", DicVO.builder().code("26").name("咸丰县").build());
        area.put("25", DicVO.builder().code("25").name("宣恩县").build());
        area.put("23", DicVO.builder().code("23").name("巴东县").build());
        area.put("22", DicVO.builder().code("22").name("建始县").build());
        area.put("02", DicVO.builder().code("02").name("利川市").build());
        area.put("01", DicVO.builder().code("01").name("恩施市").build());
        city.put("28", DicVO.builder().code("28").name("恩施土家族苗族自治州").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("广水市").build());
        area.put("02", DicVO.builder().code("02").name("曾都区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("13", DicVO.builder().code("13").name("随州市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("赤壁市").build());
        area.put("24", DicVO.builder().code("24").name("通山县").build());
        area.put("23", DicVO.builder().code("23").name("崇阳县").build());
        area.put("22", DicVO.builder().code("22").name("通城县").build());
        area.put("21", DicVO.builder().code("21").name("嘉鱼县").build());
        area.put("02", DicVO.builder().code("02").name("咸安区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("12", DicVO.builder().code("12").name("咸宁市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("武穴市").build());
        area.put("81", DicVO.builder().code("81").name("麻城市").build());
        area.put("27", DicVO.builder().code("27").name("黄梅县").build());
        area.put("26", DicVO.builder().code("26").name("蕲春县").build());
        area.put("25", DicVO.builder().code("25").name("浠水县").build());
        area.put("24", DicVO.builder().code("24").name("英山县").build());
        area.put("23", DicVO.builder().code("23").name("罗田县").build());
        area.put("22", DicVO.builder().code("22").name("红安县").build());
        area.put("21", DicVO.builder().code("21").name("团风县").build());
        area.put("02", DicVO.builder().code("02").name("黄州区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("11", DicVO.builder().code("11").name("黄冈市").child(area).build());

        area = new HashMap<>();
        area.put("87", DicVO.builder().code("87").name("松滋市").build());
        area.put("83", DicVO.builder().code("83").name("洪湖市").build());
        area.put("81", DicVO.builder().code("81").name("石首市").build());
        area.put("24", DicVO.builder().code("24").name("江陵县").build());
        area.put("23", DicVO.builder().code("23").name("监利县").build());
        area.put("22", DicVO.builder().code("22").name("公安县").build());
        area.put("03", DicVO.builder().code("03").name("荆州区").build());
        area.put("02", DicVO.builder().code("02").name("沙市区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("10", DicVO.builder().code("10").name("荆州市").child(area).build());

        area = new HashMap<>();
        area.put("84", DicVO.builder().code("84").name("汉川市").build());
        area.put("82", DicVO.builder().code("82").name("安陆市").build());
        area.put("81", DicVO.builder().code("81").name("应城市").build());
        area.put("23", DicVO.builder().code("23").name("云梦县").build());
        area.put("22", DicVO.builder().code("22").name("大悟县").build());
        area.put("21", DicVO.builder().code("21").name("孝昌县").build());
        area.put("02", DicVO.builder().code("02").name("孝南区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("孝感市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("钟祥市").build());
        area.put("22", DicVO.builder().code("22").name("沙洋县").build());
        area.put("21", DicVO.builder().code("21").name("京山县").build());
        area.put("04", DicVO.builder().code("04").name("掇刀区").build());
        area.put("02", DicVO.builder().code("02").name("东宝区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("荆门市").child(area).build());

        area = new HashMap<>();
        area.put("04", DicVO.builder().code("04").name("鄂城区").build());
        area.put("03", DicVO.builder().code("03").name("华容区").build());
        area.put("02", DicVO.builder().code("02").name("梁子湖区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("鄂州市").child(area).build());

        area = new HashMap<>();
        area.put("84", DicVO.builder().code("84").name("宜城市").build());
        area.put("83", DicVO.builder().code("83").name("枣阳市").build());
        area.put("82", DicVO.builder().code("82").name("老河口市").build());
        area.put("26", DicVO.builder().code("26").name("保康县").build());
        area.put("25", DicVO.builder().code("25").name("谷城县").build());
        area.put("24", DicVO.builder().code("24").name("南漳县").build());
        area.put("07", DicVO.builder().code("07").name("襄阳区").build());
        area.put("06", DicVO.builder().code("06").name("樊城区").build());
        area.put("02", DicVO.builder().code("02").name("襄城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("襄樊市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("枝江市").build());
        area.put("82", DicVO.builder().code("82").name("当阳市").build());
        area.put("81", DicVO.builder().code("81").name("宜都市").build());
        area.put("29", DicVO.builder().code("29").name("五峰土家族自治县").build());
        area.put("28", DicVO.builder().code("28").name("长阳土家族自治县").build());
        area.put("27", DicVO.builder().code("27").name("秭归县").build());
        area.put("26", DicVO.builder().code("26").name("兴山县").build());
        area.put("25", DicVO.builder().code("25").name("远安县").build());
        area.put("06", DicVO.builder().code("06").name("夷陵区").build());
        area.put("05", DicVO.builder().code("05").name("虎亭区").build());
        area.put("04", DicVO.builder().code("04").name("点军区").build());
        area.put("03", DicVO.builder().code("03").name("伍家岗区").build());
        area.put("02", DicVO.builder().code("02").name("西陵区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("宜昌市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("丹江口市").build());
        area.put("25", DicVO.builder().code("25").name("房县").build());
        area.put("24", DicVO.builder().code("24").name("竹溪县").build());
        area.put("23", DicVO.builder().code("23").name("竹山县").build());
        area.put("22", DicVO.builder().code("22").name("郧西县").build());
        area.put("21", DicVO.builder().code("21").name("郧县").build());
        area.put("03", DicVO.builder().code("03").name("张湾区").build());
        area.put("02", DicVO.builder().code("02").name("茅箭区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("十堰市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("大冶市").build());
        area.put("22", DicVO.builder().code("22").name("阳新县").build());
        area.put("05", DicVO.builder().code("05").name("铁山区").build());
        area.put("04", DicVO.builder().code("04").name("下陆区").build());
        area.put("03", DicVO.builder().code("03").name("西塞山区").build());
        area.put("02", DicVO.builder().code("02").name("黄石港区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("黄石市").child(area).build());

        area = new HashMap<>();
        area.put("17", DicVO.builder().code("17").name("新洲区").build());
        area.put("16", DicVO.builder().code("16").name("黄陂区").build());
        area.put("15", DicVO.builder().code("15").name("江夏区").build());
        area.put("14", DicVO.builder().code("14").name("蔡甸区").build());
        area.put("13", DicVO.builder().code("13").name("汉南区").build());
        area.put("12", DicVO.builder().code("12").name("东西湖区").build());
        area.put("11", DicVO.builder().code("11").name("洪山区").build());
        area.put("07", DicVO.builder().code("07").name("青山区").build());
        area.put("06", DicVO.builder().code("06").name("武昌区").build());
        area.put("05", DicVO.builder().code("05").name("汉阳区").build());
        area.put("04", DicVO.builder().code("04").name("乔口区").build());
        area.put("03", DicVO.builder().code("03").name("江汉区").build());
        area.put("02", DicVO.builder().code("02").name("江岸区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("武汉市").child(area).build());
        prov.put("42", DicVO.builder().code("42").name("湖北省").child(city).build());
    }

    private static void huNan(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("30", DicVO.builder().code("30").name("龙山县").build());
        area.put("27", DicVO.builder().code("27").name("永顺县").build());
        area.put("26", DicVO.builder().code("26").name("古丈县").build());
        area.put("25", DicVO.builder().code("25").name("保靖县").build());
        area.put("24", DicVO.builder().code("24").name("花垣县").build());
        area.put("23", DicVO.builder().code("23").name("凤凰县").build());
        area.put("22", DicVO.builder().code("22").name("泸溪县").build());
        area.put("01", DicVO.builder().code("01").name("吉首市").build());
        city.put("31", DicVO.builder().code("31").name("湘西土家族苗族自治州").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("涟源市").build());
        area.put("81", DicVO.builder().code("81").name("冷水江市").build());
        area.put("22", DicVO.builder().code("22").name("新化县").build());
        area.put("21", DicVO.builder().code("21").name("双峰县").build());
        area.put("02", DicVO.builder().code("02").name("娄星区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("13", DicVO.builder().code("13").name("娄底市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("洪江市").build());
        area.put("30", DicVO.builder().code("30").name("通道侗族自治县").build());
        area.put("29", DicVO.builder().code("29").name("靖州苗族侗族自治县").build());
        area.put("28", DicVO.builder().code("28").name("芷江侗族自治县").build());
        area.put("27", DicVO.builder().code("27").name("新晃侗族自治县").build());
        area.put("26", DicVO.builder().code("26").name("麻阳苗族自治县").build());
        area.put("25", DicVO.builder().code("25").name("会同县").build());
        area.put("24", DicVO.builder().code("24").name("溆浦县").build());
        area.put("23", DicVO.builder().code("23").name("辰溪县").build());
        area.put("22", DicVO.builder().code("22").name("沅陵县").build());
        area.put("21", DicVO.builder().code("21").name("中方县").build());
        area.put("02", DicVO.builder().code("02").name("鹤城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("12", DicVO.builder().code("12").name("怀化市").child(area).build());

        area = new HashMap<>();
        area.put("29", DicVO.builder().code("29").name("江华瑶族自治县").build());
        area.put("28", DicVO.builder().code("28").name("新田县").build());
        area.put("27", DicVO.builder().code("27").name("蓝山县").build());
        area.put("26", DicVO.builder().code("26").name("宁远县").build());
        area.put("25", DicVO.builder().code("25").name("江永县").build());
        area.put("24", DicVO.builder().code("24").name("道县").build());
        area.put("23", DicVO.builder().code("23").name("双牌县").build());
        area.put("22", DicVO.builder().code("22").name("东安县").build());
        area.put("21", DicVO.builder().code("21").name("祁阳县").build());
        area.put("03", DicVO.builder().code("03").name("冷水滩区").build());
        area.put("02", DicVO.builder().code("02").name("芝山区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("11", DicVO.builder().code("11").name("永州市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("资兴市").build());
        area.put("28", DicVO.builder().code("28").name("安仁县").build());
        area.put("27", DicVO.builder().code("27").name("桂东县").build());
        area.put("26", DicVO.builder().code("26").name("汝城县").build());
        area.put("25", DicVO.builder().code("25").name("临武县").build());
        area.put("24", DicVO.builder().code("24").name("嘉禾县").build());
        area.put("23", DicVO.builder().code("23").name("永兴县").build());
        area.put("22", DicVO.builder().code("22").name("宜章县").build());
        area.put("21", DicVO.builder().code("21").name("桂阳县").build());
        area.put("03", DicVO.builder().code("03").name("苏仙区").build());
        area.put("02", DicVO.builder().code("02").name("北湖区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("10", DicVO.builder().code("10").name("郴州市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("沅江市").build());
        area.put("23", DicVO.builder().code("23").name("安化县").build());
        area.put("22", DicVO.builder().code("22").name("桃江县").build());
        area.put("21", DicVO.builder().code("21").name("南县").build());
        area.put("03", DicVO.builder().code("03").name("赫山区").build());
        area.put("02", DicVO.builder().code("02").name("资阳区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("益阳市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("桑植县").build());
        area.put("21", DicVO.builder().code("21").name("慈利县").build());
        area.put("11", DicVO.builder().code("11").name("武陵源区").build());
        area.put("02", DicVO.builder().code("02").name("永定区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("张家界市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("津市市").build());
        area.put("26", DicVO.builder().code("26").name("石门县").build());
        area.put("25", DicVO.builder().code("25").name("桃源县").build());
        area.put("24", DicVO.builder().code("24").name("临澧县").build());
        area.put("23", DicVO.builder().code("23").name("澧县").build());
        area.put("22", DicVO.builder().code("22").name("汉寿县").build());
        area.put("21", DicVO.builder().code("21").name("安乡县").build());
        area.put("03", DicVO.builder().code("03").name("鼎城区").build());
        area.put("02", DicVO.builder().code("02").name("武陵区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("常德市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("临湘市").build());
        area.put("81", DicVO.builder().code("81").name("汨罗市").build());
        area.put("26", DicVO.builder().code("26").name("平江县").build());
        area.put("24", DicVO.builder().code("24").name("湘阴县").build());
        area.put("23", DicVO.builder().code("23").name("华容县").build());
        area.put("21", DicVO.builder().code("21").name("岳阳县").build());
        area.put("11", DicVO.builder().code("11").name("君山区").build());
        area.put("03", DicVO.builder().code("03").name("云溪区").build());
        area.put("02", DicVO.builder().code("02").name("岳阳楼区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("岳阳市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("武冈市").build());
        area.put("29", DicVO.builder().code("29").name("城步苗族自治县").build());
        area.put("28", DicVO.builder().code("28").name("新宁县").build());
        area.put("27", DicVO.builder().code("27").name("绥宁县").build());
        area.put("25", DicVO.builder().code("25").name("洞口县").build());
        area.put("24", DicVO.builder().code("24").name("隆回县").build());
        area.put("23", DicVO.builder().code("23").name("邵阳县").build());
        area.put("22", DicVO.builder().code("22").name("新邵县").build());
        area.put("21", DicVO.builder().code("21").name("邵东县").build());
        area.put("11", DicVO.builder().code("11").name("北塔区").build());
        area.put("03", DicVO.builder().code("03").name("大祥区").build());
        area.put("02", DicVO.builder().code("02").name("双清区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("邵阳市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("常宁市").build());
        area.put("81", DicVO.builder().code("81").name("耒阳市").build());
        area.put("26", DicVO.builder().code("26").name("祁东县").build());
        area.put("24", DicVO.builder().code("24").name("衡东县").build());
        area.put("23", DicVO.builder().code("23").name("衡山县").build());
        area.put("22", DicVO.builder().code("22").name("衡南县").build());
        area.put("21", DicVO.builder().code("21").name("衡阳县").build());
        area.put("12", DicVO.builder().code("12").name("南岳区").build());
        area.put("08", DicVO.builder().code("08").name("蒸湘区").build());
        area.put("07", DicVO.builder().code("07").name("石鼓区").build());
        area.put("06", DicVO.builder().code("06").name("雁峰区").build());
        area.put("05", DicVO.builder().code("05").name("珠晖区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("衡阳市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("韶山市").build());
        area.put("81", DicVO.builder().code("81").name("湘乡市").build());
        area.put("21", DicVO.builder().code("21").name("湘潭县").build());
        area.put("04", DicVO.builder().code("04").name("岳塘区").build());
        area.put("02", DicVO.builder().code("02").name("雨湖区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("湘潭市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("醴陵市").build());
        area.put("25", DicVO.builder().code("25").name("炎陵县").build());
        area.put("24", DicVO.builder().code("24").name("茶陵县").build());
        area.put("23", DicVO.builder().code("23").name("攸县").build());
        area.put("21", DicVO.builder().code("21").name("株洲县").build());
        area.put("11", DicVO.builder().code("11").name("天元区").build());
        area.put("04", DicVO.builder().code("04").name("石峰区").build());
        area.put("03", DicVO.builder().code("03").name("芦淞区").build());
        area.put("02", DicVO.builder().code("02").name("荷塘区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("株洲市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("浏阳市").build());
        area.put("24", DicVO.builder().code("24").name("宁乡县").build());
        area.put("22", DicVO.builder().code("22").name("望城县").build());
        area.put("21", DicVO.builder().code("21").name("长沙县").build());
        area.put("11", DicVO.builder().code("11").name("雨花区").build());
        area.put("05", DicVO.builder().code("05").name("开福区").build());
        area.put("04", DicVO.builder().code("04").name("岳麓区").build());
        area.put("03", DicVO.builder().code("03").name("天心区").build());
        area.put("02", DicVO.builder().code("02").name("芙蓉区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("长沙市").child(area).build());
        prov.put("43", DicVO.builder().code("43").name("湖南省").child(city).build());
    }

    private static void guangdong(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("81", DicVO.builder().code("81").name("罗定市").build());
        area.put("23", DicVO.builder().code("23").name("云安县").build());
        area.put("22", DicVO.builder().code("22").name("郁南县").build());
        area.put("21", DicVO.builder().code("21").name("新兴县").build());
        area.put("02", DicVO.builder().code("02").name("云城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("53", DicVO.builder().code("53").name("云浮市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("普宁市").build());
        area.put("24", DicVO.builder().code("24").name("惠来县").build());
        area.put("22", DicVO.builder().code("22").name("揭西县").build());
        area.put("21", DicVO.builder().code("21").name("揭东县").build());
        area.put("02", DicVO.builder().code("02").name("榕城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("52", DicVO.builder().code("52").name("揭阳市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("饶平县").build());
        area.put("21", DicVO.builder().code("21").name("潮安县").build());
        area.put("02", DicVO.builder().code("02").name("湘桥区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("51", DicVO.builder().code("51").name("潮州市").child(area).build());

        area = new HashMap<>();
        city.put("20", DicVO.builder().code("20").name("中山市").child(area).build());

        area = new HashMap<>();
        city.put("19", DicVO.builder().code("19").name("东莞市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("连州市").build());
        area.put("81", DicVO.builder().code("81").name("英德市").build());
        area.put("27", DicVO.builder().code("27").name("清新县").build());
        area.put("26", DicVO.builder().code("26").name("连南瑶族自治县").build());
        area.put("25", DicVO.builder().code("25").name("连山壮族瑶族自治县").build());
        area.put("23", DicVO.builder().code("23").name("阳山县").build());
        area.put("21", DicVO.builder().code("21").name("佛冈县").build());
        area.put("02", DicVO.builder().code("02").name("清城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("18", DicVO.builder().code("18").name("清远市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("阳春市").build());
        area.put("23", DicVO.builder().code("23").name("阳东县").build());
        area.put("21", DicVO.builder().code("21").name("阳西县").build());
        area.put("02", DicVO.builder().code("02").name("江城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("17", DicVO.builder().code("17").name("阳江市").child(area).build());

        area = new HashMap<>();
        area.put("25", DicVO.builder().code("25").name("东源县").build());
        area.put("24", DicVO.builder().code("24").name("和平县").build());
        area.put("23", DicVO.builder().code("23").name("连平县").build());
        area.put("22", DicVO.builder().code("22").name("龙川县").build());
        area.put("21", DicVO.builder().code("21").name("紫金县").build());
        area.put("02", DicVO.builder().code("02").name("源城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("16", DicVO.builder().code("16").name("河源市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("陆丰市").build());
        area.put("23", DicVO.builder().code("23").name("陆河县").build());
        area.put("21", DicVO.builder().code("21").name("海丰县").build());
        area.put("02", DicVO.builder().code("02").name("城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("15", DicVO.builder().code("15").name("汕尾市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("兴宁市").build());
        area.put("27", DicVO.builder().code("27").name("蕉岭县").build());
        area.put("26", DicVO.builder().code("26").name("平远县").build());
        area.put("24", DicVO.builder().code("24").name("五华县").build());
        area.put("23", DicVO.builder().code("23").name("丰顺县").build());
        area.put("22", DicVO.builder().code("22").name("大埔县").build());
        area.put("21", DicVO.builder().code("21").name("梅县").build());
        area.put("02", DicVO.builder().code("02").name("梅江区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("14", DicVO.builder().code("14").name("梅州市").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("龙门县").build());
        area.put("23", DicVO.builder().code("23").name("惠东县").build());
        area.put("22", DicVO.builder().code("22").name("博罗县").build());
        area.put("03", DicVO.builder().code("03").name("惠阳区").build());
        area.put("02", DicVO.builder().code("02").name("惠城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("13", DicVO.builder().code("13").name("惠州市").child(area).build());

        area = new HashMap<>();
        area.put("84", DicVO.builder().code("84").name("四会市").build());
        area.put("83", DicVO.builder().code("83").name("高要市").build());
        area.put("26", DicVO.builder().code("26").name("德庆县").build());
        area.put("25", DicVO.builder().code("25").name("封开县").build());
        area.put("24", DicVO.builder().code("24").name("怀集县").build());
        area.put("23", DicVO.builder().code("23").name("广宁县").build());
        area.put("03", DicVO.builder().code("03").name("鼎湖区").build());
        area.put("02", DicVO.builder().code("02").name("端州区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("12", DicVO.builder().code("12").name("肇庆市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("信宜市").build());
        area.put("82", DicVO.builder().code("82").name("化州市").build());
        area.put("81", DicVO.builder().code("81").name("高州市").build());
        area.put("23", DicVO.builder().code("23").name("电白县").build());
        area.put("03", DicVO.builder().code("03").name("茂港区").build());
        area.put("02", DicVO.builder().code("02").name("茂南区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("茂名市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("吴川市").build());
        area.put("82", DicVO.builder().code("82").name("雷州市").build());
        area.put("81", DicVO.builder().code("81").name("廉江市").build());
        area.put("25", DicVO.builder().code("25").name("徐闻县").build());
        area.put("23", DicVO.builder().code("23").name("遂溪县").build());
        area.put("11", DicVO.builder().code("11").name("麻章区").build());
        area.put("04", DicVO.builder().code("04").name("坡头区").build());
        area.put("03", DicVO.builder().code("03").name("霞山区").build());
        area.put("02", DicVO.builder().code("02").name("赤坎区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("湛江市").child(area).build());

        area = new HashMap<>();
        area.put("85", DicVO.builder().code("85").name("恩平市").build());
        area.put("84", DicVO.builder().code("84").name("鹤山市").build());
        area.put("83", DicVO.builder().code("83").name("开平市").build());
        area.put("81", DicVO.builder().code("81").name("台山市").build());
        area.put("05", DicVO.builder().code("05").name("新会区").build());
        area.put("04", DicVO.builder().code("04").name("江海区").build());
        area.put("03", DicVO.builder().code("03").name("蓬江区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("江门市").child(area).build());

        area = new HashMap<>();
        area.put("08", DicVO.builder().code("08").name("高明区").build());
        area.put("07", DicVO.builder().code("07").name("三水区").build());
        area.put("06", DicVO.builder().code("06").name("顺德区").build());
        area.put("05", DicVO.builder().code("05").name("南海区").build());
        area.put("04", DicVO.builder().code("04").name("禅城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("佛山市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("南澳县").build());
        area.put("15", DicVO.builder().code("15").name("澄海区").build());
        area.put("14", DicVO.builder().code("14").name("潮南区").build());
        area.put("13", DicVO.builder().code("13").name("潮阳区").build());
        area.put("12", DicVO.builder().code("12").name("濠江区").build());
        area.put("11", DicVO.builder().code("11").name("金平区").build());
        area.put("07", DicVO.builder().code("07").name("龙湖区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("汕头市").child(area).build());

        area = new HashMap<>();
        area.put("04", DicVO.builder().code("04").name("金湾区").build());
        area.put("03", DicVO.builder().code("03").name("斗门区").build());
        area.put("02", DicVO.builder().code("02").name("香洲区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("珠海市").child(area).build());

        area = new HashMap<>();
        area.put("08", DicVO.builder().code("08").name("盐田区").build());
        area.put("07", DicVO.builder().code("07").name("龙岗区").build());
        area.put("06", DicVO.builder().code("06").name("宝安区").build());
        area.put("05", DicVO.builder().code("05").name("南山区").build());
        area.put("04", DicVO.builder().code("04").name("福田区").build());
        area.put("03", DicVO.builder().code("03").name("罗湖区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("深圳市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("南雄市").build());
        area.put("81", DicVO.builder().code("81").name("乐昌市").build());
        area.put("33", DicVO.builder().code("33").name("新丰县").build());
        area.put("32", DicVO.builder().code("32").name("乳源瑶族自治县").build());
        area.put("29", DicVO.builder().code("29").name("翁源县").build());
        area.put("24", DicVO.builder().code("24").name("仁化县").build());
        area.put("22", DicVO.builder().code("22").name("始兴县").build());
        area.put("21", DicVO.builder().code("21").name("曲江县").build());
        area.put("04", DicVO.builder().code("04").name("浈江区").build());
        area.put("03", DicVO.builder().code("03").name("武江区").build());
        area.put("02", DicVO.builder().code("02").name("北江区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("韶关市").child(area).build());

        area = new HashMap<>();
        area.put("84", DicVO.builder().code("84").name("从化市").build());
        area.put("83", DicVO.builder().code("83").name("增城市").build());
        area.put("14", DicVO.builder().code("14").name("花都区").build());
        area.put("13", DicVO.builder().code("13").name("番禺区").build());
        area.put("12", DicVO.builder().code("12").name("黄埔区").build());
        area.put("11", DicVO.builder().code("11").name("白云区").build());
        area.put("07", DicVO.builder().code("07").name("芳村区").build());
        area.put("06", DicVO.builder().code("06").name("天河区").build());
        area.put("05", DicVO.builder().code("05").name("海珠区").build());
        area.put("04", DicVO.builder().code("04").name("越秀区").build());
        area.put("03", DicVO.builder().code("03").name("荔湾区").build());
        area.put("02", DicVO.builder().code("02").name("东山区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("广州市").child(area).build());
        prov.put("44", DicVO.builder().code("44").name("广东省").child(city).build());
    }

    private static void guanXi(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("81", DicVO.builder().code("81").name("凭祥市").build());
        area.put("25", DicVO.builder().code("25").name("天等县").build());
        area.put("24", DicVO.builder().code("24").name("大新县").build());
        area.put("23", DicVO.builder().code("23").name("龙州县").build());
        area.put("22", DicVO.builder().code("22").name("宁明县").build());
        area.put("21", DicVO.builder().code("21").name("扶绥县").build());
        area.put("02", DicVO.builder().code("02").name("江洲区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("14", DicVO.builder().code("14").name("崇左市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("合山市").build());
        area.put("24", DicVO.builder().code("24").name("金秀瑶族自治县").build());
        area.put("23", DicVO.builder().code("23").name("武宣县").build());
        area.put("22", DicVO.builder().code("22").name("象州县").build());
        area.put("21", DicVO.builder().code("21").name("忻城县").build());
        area.put("02", DicVO.builder().code("02").name("兴宾区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("13", DicVO.builder().code("13").name("来宾市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("宜州市").build());
        area.put("29", DicVO.builder().code("29").name("大化瑶族自治县").build());
        area.put("28", DicVO.builder().code("28").name("都安瑶族自治县").build());
        area.put("27", DicVO.builder().code("27").name("巴马瑶族自治县").build());
        area.put("26", DicVO.builder().code("26").name("环江毛南族自治县").build());
        area.put("25", DicVO.builder().code("25").name("罗城仫佬族自治县").build());
        area.put("24", DicVO.builder().code("24").name("东兰县").build());
        area.put("23", DicVO.builder().code("23").name("凤山县").build());
        area.put("22", DicVO.builder().code("22").name("天峨县").build());
        area.put("21", DicVO.builder().code("21").name("南丹县").build());
        area.put("02", DicVO.builder().code("02").name("金城江区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("12", DicVO.builder().code("12").name("河池市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("富川瑶族自治县").build());
        area.put("22", DicVO.builder().code("22").name("钟山县").build());
        area.put("21", DicVO.builder().code("21").name("昭平县").build());
        area.put("02", DicVO.builder().code("02").name("八步区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("11", DicVO.builder().code("11").name("贺州市").child(area).build());

        area = new HashMap<>();
        area.put("31", DicVO.builder().code("31").name("隆林各族自治县").build());
        area.put("30", DicVO.builder().code("30").name("西林县").build());
        area.put("29", DicVO.builder().code("29").name("田林县").build());
        area.put("28", DicVO.builder().code("28").name("乐业县").build());
        area.put("27", DicVO.builder().code("27").name("凌云县").build());
        area.put("26", DicVO.builder().code("26").name("那坡县").build());
        area.put("25", DicVO.builder().code("25").name("靖西县").build());
        area.put("24", DicVO.builder().code("24").name("德保县").build());
        area.put("23", DicVO.builder().code("23").name("平果县").build());
        area.put("22", DicVO.builder().code("22").name("田东县").build());
        area.put("21", DicVO.builder().code("21").name("田阳县").build());
        area.put("02", DicVO.builder().code("02").name("右江区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("10", DicVO.builder().code("10").name("百色市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("北流市").build());
        area.put("24", DicVO.builder().code("24").name("兴业县").build());
        area.put("23", DicVO.builder().code("23").name("博白县").build());
        area.put("22", DicVO.builder().code("22").name("陆川县").build());
        area.put("21", DicVO.builder().code("21").name("容县").build());
        area.put("02", DicVO.builder().code("02").name("玉州区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("玉林市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("桂平市").build());
        area.put("21", DicVO.builder().code("21").name("平南县").build());
        area.put("04", DicVO.builder().code("04").name("覃塘区").build());
        area.put("03", DicVO.builder().code("03").name("港南区").build());
        area.put("02", DicVO.builder().code("02").name("港北区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("贵港市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("浦北县").build());
        area.put("21", DicVO.builder().code("21").name("灵山县").build());
        area.put("03", DicVO.builder().code("03").name("钦北区").build());
        area.put("02", DicVO.builder().code("02").name("钦南区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("钦州市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("东兴市").build());
        area.put("21", DicVO.builder().code("21").name("上思县").build());
        area.put("03", DicVO.builder().code("03").name("防城区").build());
        area.put("02", DicVO.builder().code("02").name("港口区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("防城港市").child(area).build());

        area = new HashMap<>();
        area.put("21", DicVO.builder().code("21").name("合浦县").build());
        area.put("12", DicVO.builder().code("12").name("铁山港区").build());
        area.put("03", DicVO.builder().code("03").name("银海区").build());
        area.put("02", DicVO.builder().code("02").name("海城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("北海市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("岑溪市").build());
        area.put("23", DicVO.builder().code("23").name("蒙山县").build());
        area.put("22", DicVO.builder().code("22").name("藤县").build());
        area.put("21", DicVO.builder().code("21").name("苍梧县").build());
        area.put("05", DicVO.builder().code("05").name("长洲区").build());
        area.put("04", DicVO.builder().code("04").name("蝶山区").build());
        area.put("03", DicVO.builder().code("03").name("万秀区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("梧州市").child(area).build());

        area = new HashMap<>();
        area.put("32", DicVO.builder().code("32").name("恭城瑶族自治县").build());
        area.put("31", DicVO.builder().code("31").name("荔蒲县").build());
        area.put("30", DicVO.builder().code("30").name("平乐县").build());
        area.put("29", DicVO.builder().code("29").name("资源县").build());
        area.put("28", DicVO.builder().code("28").name("龙胜各族自治县").build());
        area.put("27", DicVO.builder().code("27").name("灌阳县").build());
        area.put("26", DicVO.builder().code("26").name("永福县").build());
        area.put("25", DicVO.builder().code("25").name("兴安县").build());
        area.put("24", DicVO.builder().code("24").name("全州县").build());
        area.put("23", DicVO.builder().code("23").name("灵川县").build());
        area.put("22", DicVO.builder().code("22").name("临桂县").build());
        area.put("21", DicVO.builder().code("21").name("阳朔县").build());
        area.put("11", DicVO.builder().code("11").name("雁山区").build());
        area.put("05", DicVO.builder().code("05").name("七星区").build());
        area.put("04", DicVO.builder().code("04").name("象山区").build());
        area.put("03", DicVO.builder().code("03").name("叠彩区").build());
        area.put("02", DicVO.builder().code("02").name("秀峰区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("桂林市").child(area).build());

        area = new HashMap<>();
        area.put("26", DicVO.builder().code("26").name("三江侗族自治县").build());
        area.put("25", DicVO.builder().code("25").name("融水苗族自治县").build());
        area.put("24", DicVO.builder().code("24").name("融安县").build());
        area.put("23", DicVO.builder().code("23").name("鹿寨县").build());
        area.put("22", DicVO.builder().code("22").name("柳城县").build());
        area.put("21", DicVO.builder().code("21").name("柳江县").build());
        area.put("05", DicVO.builder().code("05").name("柳北区").build());
        area.put("04", DicVO.builder().code("04").name("柳南区").build());
        area.put("03", DicVO.builder().code("03").name("鱼峰区").build());
        area.put("02", DicVO.builder().code("02").name("城中区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("柳州市").child(area).build());

        area = new HashMap<>();
        area.put("27", DicVO.builder().code("27").name("横县").build());
        area.put("26", DicVO.builder().code("26").name("宾阳县").build());
        area.put("25", DicVO.builder().code("25").name("上林县").build());
        area.put("24", DicVO.builder().code("24").name("马山县").build());
        area.put("23", DicVO.builder().code("23").name("隆安县").build());
        area.put("22", DicVO.builder().code("22").name("武鸣县").build());
        area.put("21", DicVO.builder().code("21").name("邕宁县").build());
        area.put("06", DicVO.builder().code("06").name("永新区").build());
        area.put("05", DicVO.builder().code("05").name("江南区").build());
        area.put("04", DicVO.builder().code("04").name("城北区").build());
        area.put("03", DicVO.builder().code("03").name("新城区").build());
        area.put("02", DicVO.builder().code("02").name("兴宁区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("南宁市").child(area).build());
        prov.put("45", DicVO.builder().code("45").name("广西壮族自治区").child(city).build());
    }

    private static void haiNan(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("39", DicVO.builder().code("39").name("中沙群岛的岛礁及其海域").build());
        area.put("38", DicVO.builder().code("38").name("南沙群岛").build());
        area.put("37", DicVO.builder().code("37").name("西沙群岛").build());
        area.put("36", DicVO.builder().code("36").name("琼中黎族苗族自治县").build());
        area.put("35", DicVO.builder().code("35").name("保亭黎族苗族自治县").build());
        area.put("34", DicVO.builder().code("34").name("陵水黎族自治县").build());
        area.put("33", DicVO.builder().code("33").name("乐东黎族自治县").build());
        area.put("31", DicVO.builder().code("31").name("昌江黎族自治县").build());
        area.put("30", DicVO.builder().code("30").name("白沙黎族自治县").build());
        area.put("28", DicVO.builder().code("28").name("临高县").build());
        area.put("27", DicVO.builder().code("27").name("澄迈县").build());
        area.put("26", DicVO.builder().code("26").name("屯昌县").build());
        area.put("25", DicVO.builder().code("25").name("定安县").build());
        area.put("07", DicVO.builder().code("07").name("东方市").build());
        area.put("06", DicVO.builder().code("06").name("万宁市").build());
        area.put("05", DicVO.builder().code("05").name("文昌市").build());
        area.put("03", DicVO.builder().code("03").name("儋州市").build());
        area.put("02", DicVO.builder().code("02").name("琼海市").build());
        area.put("01", DicVO.builder().code("01").name("五指山市").build());
        city.put("90", DicVO.builder().code("90").name("省直辖县级行政单位").child(area).build());

        area = new HashMap<>();
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("三亚市").child(area).build());

        area = new HashMap<>();
        area.put("08", DicVO.builder().code("08").name("美兰区").build());
        area.put("07", DicVO.builder().code("07").name("琼山区").build());
        area.put("06", DicVO.builder().code("06").name("龙华区").build());
        area.put("05", DicVO.builder().code("05").name("秀英区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("海口市").child(area).build());
        prov.put("46", DicVO.builder().code("46").name("海南省").child(city).build());
    }

    private static void chongQin(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("84", DicVO.builder().code("84").name("南川市").build());
        area.put("83", DicVO.builder().code("83").name("永川市").build());
        area.put("82", DicVO.builder().code("82").name("合川市").build());
        area.put("81", DicVO.builder().code("81").name("江津市").build());
        city.put("03", DicVO.builder().code("03").name("重庆市").child(area).build());

        area = new HashMap<>();
        area.put("43", DicVO.builder().code("43").name("彭水苗族土家族自治县").build());
        area.put("42", DicVO.builder().code("42").name("酉阳土家族苗族自治县").build());
        area.put("41", DicVO.builder().code("41").name("秀山土家族苗族自治县").build());
        area.put("40", DicVO.builder().code("40").name("石柱土家族自治县").build());
        area.put("38", DicVO.builder().code("38").name("巫溪县").build());
        area.put("37", DicVO.builder().code("37").name("巫山县").build());
        area.put("36", DicVO.builder().code("36").name("奉节县").build());
        area.put("35", DicVO.builder().code("35").name("云阳县").build());
        area.put("34", DicVO.builder().code("34").name("开县").build());
        area.put("33", DicVO.builder().code("33").name("忠县").build());
        area.put("32", DicVO.builder().code("32").name("武隆县").build());
        area.put("31", DicVO.builder().code("31").name("垫江县").build());
        area.put("30", DicVO.builder().code("30").name("丰都县").build());
        area.put("29", DicVO.builder().code("29").name("城口县").build());
        area.put("28", DicVO.builder().code("28").name("梁平县").build());
        area.put("27", DicVO.builder().code("27").name("璧山县").build());
        area.put("26", DicVO.builder().code("26").name("荣昌县").build());
        area.put("25", DicVO.builder().code("25").name("大足县").build());
        area.put("24", DicVO.builder().code("24").name("铜梁县").build());
        area.put("23", DicVO.builder().code("23").name("潼南县").build());
        area.put("22", DicVO.builder().code("22").name("綦江县").build());
        city.put("02", DicVO.builder().code("02").name("重庆县").child(area).build());

        area = new HashMap<>();
        area.put("15", DicVO.builder().code("15").name("长寿区").build());
        area.put("14", DicVO.builder().code("14").name("黔江区").build());
        area.put("13", DicVO.builder().code("13").name("巴南区").build());
        area.put("12", DicVO.builder().code("12").name("渝北区").build());
        area.put("11", DicVO.builder().code("11").name("双桥区").build());
        area.put("10", DicVO.builder().code("10").name("万盛区").build());
        area.put("09", DicVO.builder().code("09").name("北碚区").build());
        area.put("08", DicVO.builder().code("08").name("南岸区").build());
        area.put("07", DicVO.builder().code("07").name("九龙坡区").build());
        area.put("06", DicVO.builder().code("06").name("沙坪坝区").build());
        area.put("05", DicVO.builder().code("05").name("江北区").build());
        area.put("04", DicVO.builder().code("04").name("大渡口区").build());
        area.put("03", DicVO.builder().code("03").name("渝中区").build());
        area.put("02", DicVO.builder().code("02").name("涪陵区").build());
        area.put("01", DicVO.builder().code("01").name("万州区").build());
        city.put("01", DicVO.builder().code("01").name("市辖区").child(area).build());
        prov.put("50", DicVO.builder().code("50").name("重庆市").child(city).build());
    }

    private static void siChuan(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("37", DicVO.builder().code("37").name("雷波县").build());
        area.put("36", DicVO.builder().code("36").name("美姑县").build());
        area.put("35", DicVO.builder().code("35").name("甘洛县").build());
        area.put("34", DicVO.builder().code("34").name("越西县").build());
        area.put("33", DicVO.builder().code("33").name("冕宁县").build());
        area.put("32", DicVO.builder().code("32").name("喜德县").build());
        area.put("31", DicVO.builder().code("31").name("昭觉县").build());
        area.put("30", DicVO.builder().code("30").name("金阳县").build());
        area.put("29", DicVO.builder().code("29").name("布拖县").build());
        area.put("28", DicVO.builder().code("28").name("普格县").build());
        area.put("27", DicVO.builder().code("27").name("宁南县").build());
        area.put("26", DicVO.builder().code("26").name("会东县").build());
        area.put("25", DicVO.builder().code("25").name("会理县").build());
        area.put("24", DicVO.builder().code("24").name("德昌县").build());
        area.put("23", DicVO.builder().code("23").name("盐源县").build());
        area.put("22", DicVO.builder().code("22").name("木里藏族自治县").build());
        area.put("01", DicVO.builder().code("01").name("西昌市").build());
        city.put("34", DicVO.builder().code("34").name("凉山彝族自治州").child(area).build());

        area = new HashMap<>();
        area.put("38", DicVO.builder().code("38").name("得荣县").build());
        area.put("37", DicVO.builder().code("37").name("稻城县").build());
        area.put("36", DicVO.builder().code("36").name("乡城县").build());
        area.put("35", DicVO.builder().code("35").name("巴塘县").build());
        area.put("34", DicVO.builder().code("34").name("理塘县").build());
        area.put("33", DicVO.builder().code("33").name("色达县").build());
        area.put("32", DicVO.builder().code("32").name("石渠县").build());
        area.put("31", DicVO.builder().code("31").name("白玉县").build());
        area.put("30", DicVO.builder().code("30").name("德格县").build());
        area.put("29", DicVO.builder().code("29").name("新龙县").build());
        area.put("28", DicVO.builder().code("28").name("甘孜县").build());
        area.put("27", DicVO.builder().code("27").name("炉霍县").build());
        area.put("26", DicVO.builder().code("26").name("道孚县").build());
        area.put("25", DicVO.builder().code("25").name("雅江县").build());
        area.put("24", DicVO.builder().code("24").name("九龙县").build());
        area.put("23", DicVO.builder().code("23").name("丹巴县").build());
        area.put("22", DicVO.builder().code("22").name("泸定县").build());
        area.put("21", DicVO.builder().code("21").name("康定县").build());
        city.put("33", DicVO.builder().code("33").name("甘孜藏族自治州").child(area).build());

        area = new HashMap<>();
        area.put("33", DicVO.builder().code("33").name("红原县").build());
        area.put("32", DicVO.builder().code("32").name("若尔盖县").build());
        area.put("31", DicVO.builder().code("31").name("阿坝县").build());
        area.put("30", DicVO.builder().code("30").name("壤塘县").build());
        area.put("29", DicVO.builder().code("29").name("马尔康县").build());
        area.put("28", DicVO.builder().code("28").name("黑水县").build());
        area.put("27", DicVO.builder().code("27").name("小金县").build());
        area.put("26", DicVO.builder().code("26").name("金川县").build());
        area.put("25", DicVO.builder().code("25").name("九寨沟县").build());
        area.put("24", DicVO.builder().code("24").name("松潘县").build());
        area.put("23", DicVO.builder().code("23").name("茂县").build());
        area.put("22", DicVO.builder().code("22").name("理县").build());
        area.put("21", DicVO.builder().code("21").name("汶川县").build());
        city.put("32", DicVO.builder().code("32").name("阿坝藏族羌族自治州").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("简阳市").build());
        area.put("22", DicVO.builder().code("22").name("乐至县").build());
        area.put("21", DicVO.builder().code("21").name("安岳县").build());
        area.put("02", DicVO.builder().code("02").name("雁江区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("20", DicVO.builder().code("20").name("资阳市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("平昌县").build());
        area.put("22", DicVO.builder().code("22").name("南江县").build());
        area.put("21", DicVO.builder().code("21").name("通江县").build());
        area.put("02", DicVO.builder().code("02").name("巴州区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("19", DicVO.builder().code("19").name("巴中市").child(area).build());

        area = new HashMap<>();
        area.put("27", DicVO.builder().code("27").name("宝兴县").build());
        area.put("26", DicVO.builder().code("26").name("芦山县").build());
        area.put("25", DicVO.builder().code("25").name("天全县").build());
        area.put("24", DicVO.builder().code("24").name("石棉县").build());
        area.put("23", DicVO.builder().code("23").name("汉源县").build());
        area.put("22", DicVO.builder().code("22").name("荥经县").build());
        area.put("21", DicVO.builder().code("21").name("名山县").build());
        area.put("02", DicVO.builder().code("02").name("雨城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("18", DicVO.builder().code("18").name("雅安市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("万源市").build());
        area.put("25", DicVO.builder().code("25").name("渠县").build());
        area.put("24", DicVO.builder().code("24").name("大竹县").build());
        area.put("23", DicVO.builder().code("23").name("开江县").build());
        area.put("22", DicVO.builder().code("22").name("宣汉县").build());
        area.put("21", DicVO.builder().code("21").name("达县").build());
        area.put("02", DicVO.builder().code("02").name("通川区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("17", DicVO.builder().code("17").name("达州市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("华莹市").build());
        area.put("23", DicVO.builder().code("23").name("邻水县").build());
        area.put("22", DicVO.builder().code("22").name("武胜县").build());
        area.put("21", DicVO.builder().code("21").name("岳池县").build());
        area.put("02", DicVO.builder().code("02").name("广安区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("16", DicVO.builder().code("16").name("广安市").child(area).build());

        area = new HashMap<>();
        area.put("29", DicVO.builder().code("29").name("屏山县").build());
        area.put("28", DicVO.builder().code("28").name("兴文县").build());
        area.put("27", DicVO.builder().code("27").name("筠连县").build());
        area.put("26", DicVO.builder().code("26").name("珙县").build());
        area.put("25", DicVO.builder().code("25").name("高县").build());
        area.put("24", DicVO.builder().code("24").name("长宁县").build());
        area.put("23", DicVO.builder().code("23").name("江安县").build());
        area.put("22", DicVO.builder().code("22").name("南溪县").build());
        area.put("21", DicVO.builder().code("21").name("宜宾县").build());
        area.put("02", DicVO.builder().code("02").name("翠屏区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("15", DicVO.builder().code("15").name("宜宾市").child(area).build());

        area = new HashMap<>();
        area.put("25", DicVO.builder().code("25").name("青神县").build());
        area.put("24", DicVO.builder().code("24").name("丹棱县").build());
        area.put("23", DicVO.builder().code("23").name("洪雅县").build());
        area.put("22", DicVO.builder().code("22").name("彭山县").build());
        area.put("21", DicVO.builder().code("21").name("仁寿县").build());
        area.put("02", DicVO.builder().code("02").name("东坡区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("14", DicVO.builder().code("14").name("眉山市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("阆中市").build());
        area.put("25", DicVO.builder().code("25").name("西充县").build());
        area.put("24", DicVO.builder().code("24").name("仪陇县").build());
        area.put("23", DicVO.builder().code("23").name("蓬安县").build());
        area.put("22", DicVO.builder().code("22").name("营山县").build());
        area.put("21", DicVO.builder().code("21").name("南部县").build());
        area.put("04", DicVO.builder().code("04").name("嘉陵区").build());
        area.put("03", DicVO.builder().code("03").name("高坪区").build());
        area.put("02", DicVO.builder().code("02").name("顺庆区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("13", DicVO.builder().code("13").name("南充市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("峨眉山市").build());
        area.put("33", DicVO.builder().code("33").name("马边彝族自治县").build());
        area.put("32", DicVO.builder().code("32").name("峨边彝族自治县").build());
        area.put("29", DicVO.builder().code("29").name("沐川县").build());
        area.put("26", DicVO.builder().code("26").name("夹江县").build());
        area.put("24", DicVO.builder().code("24").name("井研县").build());
        area.put("23", DicVO.builder().code("23").name("犍为县").build());
        area.put("13", DicVO.builder().code("13").name("金口河区").build());
        area.put("12", DicVO.builder().code("12").name("五通桥区").build());
        area.put("11", DicVO.builder().code("11").name("沙湾区").build());
        area.put("02", DicVO.builder().code("02").name("市中区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("11", DicVO.builder().code("11").name("乐山市").child(area).build());

        area = new HashMap<>();
        area.put("28", DicVO.builder().code("28").name("隆昌县").build());
        area.put("25", DicVO.builder().code("25").name("资中县").build());
        area.put("24", DicVO.builder().code("24").name("威远县").build());
        area.put("11", DicVO.builder().code("11").name("东兴区").build());
        area.put("02", DicVO.builder().code("02").name("市中区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("10", DicVO.builder().code("10").name("内江市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("大英县").build());
        area.put("22", DicVO.builder().code("22").name("射洪县").build());
        area.put("21", DicVO.builder().code("21").name("蓬溪县").build());
        area.put("04", DicVO.builder().code("04").name("安居区").build());
        area.put("03", DicVO.builder().code("03").name("船山区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("遂宁市").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("苍溪县").build());
        area.put("23", DicVO.builder().code("23").name("剑阁县").build());
        area.put("22", DicVO.builder().code("22").name("青川县").build());
        area.put("21", DicVO.builder().code("21").name("旺苍县").build());
        area.put("12", DicVO.builder().code("12").name("朝天区").build());
        area.put("11", DicVO.builder().code("11").name("元坝区").build());
        area.put("02", DicVO.builder().code("02").name("市中区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("广元市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("江油市").build());
        area.put("27", DicVO.builder().code("27").name("平武县").build());
        area.put("26", DicVO.builder().code("26").name("北川羌族自治县").build());
        area.put("25", DicVO.builder().code("25").name("梓潼县").build());
        area.put("24", DicVO.builder().code("24").name("安县").build());
        area.put("23", DicVO.builder().code("23").name("盐亭县").build());
        area.put("22", DicVO.builder().code("22").name("三台县").build());
        area.put("04", DicVO.builder().code("04").name("游仙区").build());
        area.put("03", DicVO.builder().code("03").name("涪城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("绵阳市").child(area).build());

        area = new HashMap<>();
        area.put("83", DicVO.builder().code("83").name("绵竹市").build());
        area.put("82", DicVO.builder().code("82").name("什邡市").build());
        area.put("81", DicVO.builder().code("81").name("广汉市").build());
        area.put("26", DicVO.builder().code("26").name("罗江县").build());
        area.put("23", DicVO.builder().code("23").name("中江县").build());
        area.put("03", DicVO.builder().code("03").name("旌阳区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("德阳市").child(area).build());

        area = new HashMap<>();
        area.put("25", DicVO.builder().code("25").name("古蔺县").build());
        area.put("24", DicVO.builder().code("24").name("叙永县").build());
        area.put("22", DicVO.builder().code("22").name("合江县").build());
        area.put("21", DicVO.builder().code("21").name("泸县").build());
        area.put("04", DicVO.builder().code("04").name("龙马潭区").build());
        area.put("03", DicVO.builder().code("03").name("纳溪区").build());
        area.put("02", DicVO.builder().code("02").name("江阳区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("泸州市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("盐边县").build());
        area.put("21", DicVO.builder().code("21").name("米易县").build());
        area.put("11", DicVO.builder().code("11").name("仁和区").build());
        area.put("03", DicVO.builder().code("03").name("西区").build());
        area.put("02", DicVO.builder().code("02").name("东区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("攀枝花市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("富顺县").build());
        area.put("21", DicVO.builder().code("21").name("荣县").build());
        area.put("11", DicVO.builder().code("11").name("沿滩区").build());
        area.put("04", DicVO.builder().code("04").name("大安区").build());
        area.put("03", DicVO.builder().code("03").name("贡井区").build());
        area.put("02", DicVO.builder().code("02").name("自流井区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("自贡市").child(area).build());

        area = new HashMap<>();
        area.put("84", DicVO.builder().code("84").name("崇州市").build());
        area.put("83", DicVO.builder().code("83").name("邛崃市").build());
        area.put("82", DicVO.builder().code("82").name("彭州市").build());
        area.put("81", DicVO.builder().code("81").name("都江堰市").build());
        area.put("32", DicVO.builder().code("32").name("新津县").build());
        area.put("31", DicVO.builder().code("31").name("蒲江县").build());
        area.put("29", DicVO.builder().code("29").name("大邑县").build());
        area.put("24", DicVO.builder().code("24").name("郫县").build());
        area.put("22", DicVO.builder().code("22").name("双流县").build());
        area.put("21", DicVO.builder().code("21").name("金堂县").build());
        area.put("15", DicVO.builder().code("15").name("温江区").build());
        area.put("14", DicVO.builder().code("14").name("新都区").build());
        area.put("13", DicVO.builder().code("13").name("青白江区").build());
        area.put("12", DicVO.builder().code("12").name("龙泉驿区").build());
        area.put("08", DicVO.builder().code("08").name("成华区").build());
        area.put("07", DicVO.builder().code("07").name("武侯区").build());
        area.put("06", DicVO.builder().code("06").name("金牛区").build());
        area.put("05", DicVO.builder().code("05").name("青羊区").build());
        area.put("04", DicVO.builder().code("04").name("锦江区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("成都市").child(area).build());
        prov.put("51", DicVO.builder().code("51").name("四川省").child(city).build());
    }

    private static void guiZhou(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("32", DicVO.builder().code("32").name("三都水族自治县").build());
        area.put("31", DicVO.builder().code("31").name("惠水县").build());
        area.put("30", DicVO.builder().code("30").name("龙里县").build());
        area.put("29", DicVO.builder().code("29").name("长顺县").build());
        area.put("28", DicVO.builder().code("28").name("罗甸县").build());
        area.put("27", DicVO.builder().code("27").name("平塘县").build());
        area.put("26", DicVO.builder().code("26").name("独山县").build());
        area.put("25", DicVO.builder().code("25").name("瓮安县").build());
        area.put("23", DicVO.builder().code("23").name("贵定县").build());
        area.put("22", DicVO.builder().code("22").name("荔波县").build());
        area.put("02", DicVO.builder().code("02").name("福泉市").build());
        area.put("01", DicVO.builder().code("01").name("都匀市").build());
        city.put("27", DicVO.builder().code("27").name("黔南布依族苗族自治州").child(area).build());

        area = new HashMap<>();
        area.put("36", DicVO.builder().code("36").name("丹寨县").build());
        area.put("35", DicVO.builder().code("35").name("麻江县").build());
        area.put("34", DicVO.builder().code("34").name("雷山县").build());
        area.put("33", DicVO.builder().code("33").name("从江县").build());
        area.put("32", DicVO.builder().code("32").name("榕江县").build());
        area.put("31", DicVO.builder().code("31").name("黎平县").build());
        area.put("30", DicVO.builder().code("30").name("台江县").build());
        area.put("29", DicVO.builder().code("29").name("剑河县").build());
        area.put("28", DicVO.builder().code("28").name("锦屏县").build());
        area.put("27", DicVO.builder().code("27").name("天柱县").build());
        area.put("26", DicVO.builder().code("26").name("岑巩县").build());
        area.put("25", DicVO.builder().code("25").name("镇远县").build());
        area.put("24", DicVO.builder().code("24").name("三穗县").build());
        area.put("23", DicVO.builder().code("23").name("施秉县").build());
        area.put("22", DicVO.builder().code("22").name("黄平县").build());
        area.put("01", DicVO.builder().code("01").name("凯里市").build());
        city.put("26", DicVO.builder().code("26").name("黔东南苗族侗族自治州").child(area).build());

        area = new HashMap<>();
        area.put("28", DicVO.builder().code("28").name("赫章县").build());
        area.put("27", DicVO.builder().code("27").name("威宁彝族回族苗族自治县").build());
        area.put("26", DicVO.builder().code("26").name("纳雍县").build());
        area.put("25", DicVO.builder().code("25").name("织金县").build());
        area.put("24", DicVO.builder().code("24").name("金沙县").build());
        area.put("23", DicVO.builder().code("23").name("黔西县").build());
        area.put("22", DicVO.builder().code("22").name("大方县").build());
        area.put("01", DicVO.builder().code("01").name("毕节市").build());
        city.put("24", DicVO.builder().code("24").name("毕节地区").child(area).build());

        area = new HashMap<>();
        area.put("28", DicVO.builder().code("28").name("安龙县").build());
        area.put("27", DicVO.builder().code("27").name("册亨县").build());
        area.put("26", DicVO.builder().code("26").name("望谟县").build());
        area.put("25", DicVO.builder().code("25").name("贞丰县").build());
        area.put("24", DicVO.builder().code("24").name("晴隆县").build());
        area.put("23", DicVO.builder().code("23").name("普安县").build());
        area.put("22", DicVO.builder().code("22").name("兴仁县").build());
        area.put("01", DicVO.builder().code("01").name("兴义市").build());
        city.put("23", DicVO.builder().code("23").name("黔西南布依族苗族自治州").child(area).build());

        area = new HashMap<>();
        area.put("30", DicVO.builder().code("30").name("万山特区").build());
        area.put("29", DicVO.builder().code("29").name("松桃苗族自治县").build());
        area.put("28", DicVO.builder().code("28").name("沿河土家族自治县").build());
        area.put("27", DicVO.builder().code("27").name("德江县").build());
        area.put("26", DicVO.builder().code("26").name("印江土家族苗族自治县").build());
        area.put("25", DicVO.builder().code("25").name("思南县").build());
        area.put("24", DicVO.builder().code("24").name("石阡县").build());
        area.put("23", DicVO.builder().code("23").name("玉屏侗族自治县").build());
        area.put("22", DicVO.builder().code("22").name("江口县").build());
        area.put("01", DicVO.builder().code("01").name("铜仁市").build());
        city.put("22", DicVO.builder().code("22").name("铜仁地区").child(area).build());

        area = new HashMap<>();
        area.put("25", DicVO.builder().code("25").name("紫云苗族布依族自治县").build());
        area.put("24", DicVO.builder().code("24").name("关岭布依族苗族自治县").build());
        area.put("23", DicVO.builder().code("23").name("镇宁布依族苗族自治县").build());
        area.put("22", DicVO.builder().code("22").name("普定县").build());
        area.put("21", DicVO.builder().code("21").name("平坝县").build());
        area.put("02", DicVO.builder().code("02").name("西秀区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("安顺市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("仁怀市").build());
        area.put("81", DicVO.builder().code("81").name("赤水市").build());
        area.put("30", DicVO.builder().code("30").name("习水县").build());
        area.put("29", DicVO.builder().code("29").name("余庆县").build());
        area.put("28", DicVO.builder().code("28").name("湄潭县").build());
        area.put("27", DicVO.builder().code("27").name("凤冈县").build());
        area.put("26", DicVO.builder().code("26").name("务川仡佬族苗族自治县").build());
        area.put("25", DicVO.builder().code("25").name("道真仡佬族苗族自治县").build());
        area.put("24", DicVO.builder().code("24").name("正安县").build());
        area.put("23", DicVO.builder().code("23").name("绥阳县").build());
        area.put("22", DicVO.builder().code("22").name("桐梓县").build());
        area.put("21", DicVO.builder().code("21").name("遵义县").build());
        area.put("03", DicVO.builder().code("03").name("汇川区").build());
        area.put("02", DicVO.builder().code("02").name("红花岗区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("遵义市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("盘县").build());
        area.put("21", DicVO.builder().code("21").name("水城县").build());
        area.put("03", DicVO.builder().code("03").name("六枝特区").build());
        area.put("01", DicVO.builder().code("01").name("钟山区").build());
        city.put("02", DicVO.builder().code("02").name("六盘水市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("清镇市").build());
        area.put("23", DicVO.builder().code("23").name("修文县").build());
        area.put("22", DicVO.builder().code("22").name("息烽县").build());
        area.put("21", DicVO.builder().code("21").name("开阳县").build());
        area.put("14", DicVO.builder().code("14").name("小河区").build());
        area.put("13", DicVO.builder().code("13").name("白云区").build());
        area.put("12", DicVO.builder().code("12").name("乌当区").build());
        area.put("11", DicVO.builder().code("11").name("花溪区").build());
        area.put("03", DicVO.builder().code("03").name("云岩区").build());
        area.put("02", DicVO.builder().code("02").name("南明区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("贵阳市").child(area).build());
        prov.put("52", DicVO.builder().code("52").name("贵州省").child(city).build());
    }

    private static void yunNan(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("23", DicVO.builder().code("23").name("维西傈僳族自治县").build());
        area.put("22", DicVO.builder().code("22").name("德钦县").build());
        area.put("21", DicVO.builder().code("21").name("香格里拉县").build());
        city.put("34", DicVO.builder().code("34").name("迪庆藏族自治州").child(area).build());

        area = new HashMap<>();
        area.put("25", DicVO.builder().code("25").name("兰坪白族普米族自治县").build());
        area.put("24", DicVO.builder().code("24").name("贡山独龙族怒族自治县").build());
        area.put("23", DicVO.builder().code("23").name("福贡县").build());
        area.put("21", DicVO.builder().code("21").name("泸水县").build());
        city.put("33", DicVO.builder().code("33").name("怒江傈僳族自治州").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("陇川县").build());
        area.put("23", DicVO.builder().code("23").name("盈江县").build());
        area.put("22", DicVO.builder().code("22").name("梁河县").build());
        area.put("03", DicVO.builder().code("03").name("潞西市").build());
        area.put("02", DicVO.builder().code("02").name("瑞丽市").build());
        city.put("31", DicVO.builder().code("31").name("德宏傣族景颇族自治州").child(area).build());

        area = new HashMap<>();
        area.put("32", DicVO.builder().code("32").name("鹤庆县").build());
        area.put("31", DicVO.builder().code("31").name("剑川县").build());
        area.put("30", DicVO.builder().code("30").name("洱源县").build());
        area.put("29", DicVO.builder().code("29").name("云龙县").build());
        area.put("28", DicVO.builder().code("28").name("永平县").build());
        area.put("27", DicVO.builder().code("27").name("巍山彝族回族自治县").build());
        area.put("26", DicVO.builder().code("26").name("南涧彝族自治县").build());
        area.put("25", DicVO.builder().code("25").name("弥渡县").build());
        area.put("24", DicVO.builder().code("24").name("宾川县").build());
        area.put("23", DicVO.builder().code("23").name("祥云县").build());
        area.put("22", DicVO.builder().code("22").name("漾濞彝族自治县").build());
        area.put("01", DicVO.builder().code("01").name("大理市").build());
        city.put("29", DicVO.builder().code("29").name("大理白族自治州").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("勐腊县").build());
        area.put("22", DicVO.builder().code("22").name("勐海县").build());
        area.put("01", DicVO.builder().code("01").name("景洪市").build());
        city.put("28", DicVO.builder().code("28").name("西双版纳傣族自治州").child(area).build());

        area = new HashMap<>();
        area.put("28", DicVO.builder().code("28").name("富宁县").build());
        area.put("27", DicVO.builder().code("27").name("广南县").build());
        area.put("26", DicVO.builder().code("26").name("丘北县").build());
        area.put("25", DicVO.builder().code("25").name("马关县").build());
        area.put("24", DicVO.builder().code("24").name("麻栗坡县").build());
        area.put("23", DicVO.builder().code("23").name("西畴县").build());
        area.put("22", DicVO.builder().code("22").name("砚山县").build());
        area.put("21", DicVO.builder().code("21").name("文山县").build());
        city.put("26", DicVO.builder().code("26").name("文山壮族苗族自治州").child(area).build());

        area = new HashMap<>();
        area.put("32", DicVO.builder().code("32").name("河口瑶族自治县").build());
        area.put("31", DicVO.builder().code("31").name("绿春县").build());
        area.put("30", DicVO.builder().code("30").name("金平苗族瑶族傣族自治县").build());
        area.put("29", DicVO.builder().code("29").name("红河县").build());
        area.put("28", DicVO.builder().code("28").name("元阳县").build());
        area.put("27", DicVO.builder().code("27").name("泸西县").build());
        area.put("26", DicVO.builder().code("26").name("弥勒县").build());
        area.put("25", DicVO.builder().code("25").name("石屏县").build());
        area.put("24", DicVO.builder().code("24").name("建水县").build());
        area.put("23", DicVO.builder().code("23").name("屏边苗族自治县").build());
        area.put("22", DicVO.builder().code("22").name("蒙自县").build());
        area.put("02", DicVO.builder().code("02").name("开远市").build());
        area.put("01", DicVO.builder().code("01").name("个旧市").build());
        city.put("25", DicVO.builder().code("25").name("红河哈尼族彝族自治州").child(area).build());

        area = new HashMap<>();
        area.put("31", DicVO.builder().code("31").name("禄丰县").build());
        area.put("29", DicVO.builder().code("29").name("武定县").build());
        area.put("28", DicVO.builder().code("28").name("元谋县").build());
        area.put("27", DicVO.builder().code("27").name("永仁县").build());
        area.put("26", DicVO.builder().code("26").name("大姚县").build());
        area.put("25", DicVO.builder().code("25").name("姚安县").build());
        area.put("24", DicVO.builder().code("24").name("南华县").build());
        area.put("23", DicVO.builder().code("23").name("牟定县").build());
        area.put("22", DicVO.builder().code("22").name("双柏县").build());
        area.put("01", DicVO.builder().code("01").name("楚雄市").build());
        city.put("23", DicVO.builder().code("23").name("楚雄彝族自治州").child(area).build());

        area = new HashMap<>();
        area.put("27", DicVO.builder().code("27").name("沧源佤族自治县").build());
        area.put("26", DicVO.builder().code("26").name("耿马傣族佤族自治县").build());
        area.put("25", DicVO.builder().code("25").name("双江拉祜族佤族布朗族傣族自治县").build());
        area.put("24", DicVO.builder().code("24").name("镇康县").build());
        area.put("23", DicVO.builder().code("23").name("永德县").build());
        area.put("22", DicVO.builder().code("22").name("云县").build());
        area.put("21", DicVO.builder().code("21").name("凤庆县").build());
        area.put("02", DicVO.builder().code("02").name("临翔区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("临沧市").child(area).build());

        area = new HashMap<>();
        area.put("29", DicVO.builder().code("29").name("西盟佤族自治县").build());
        area.put("28", DicVO.builder().code("28").name("澜沧拉祜族自治县").build());
        area.put("27", DicVO.builder().code("27").name("孟连傣族拉祜族佤族自治县").build());
        area.put("26", DicVO.builder().code("26").name("江城哈尼族彝族自治县").build());
        area.put("25", DicVO.builder().code("25").name("镇沅彝族哈尼族拉祜族自治县").build());
        area.put("24", DicVO.builder().code("24").name("景谷傣族彝族自治县").build());
        area.put("23", DicVO.builder().code("23").name("景东彝族自治县").build());
        area.put("22", DicVO.builder().code("22").name("墨江哈尼族自治县").build());
        area.put("21", DicVO.builder().code("21").name("普洱哈尼族彝族自治县").build());
        area.put("02", DicVO.builder().code("02").name("翠云区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("思茅市").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("宁蒗彝族自治县").build());
        area.put("23", DicVO.builder().code("23").name("华坪县").build());
        area.put("22", DicVO.builder().code("22").name("永胜县").build());
        area.put("21", DicVO.builder().code("21").name("玉龙纳西族自治县").build());
        area.put("02", DicVO.builder().code("02").name("古城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("丽江市").child(area).build());

        area = new HashMap<>();
        area.put("30", DicVO.builder().code("30").name("水富县").build());
        area.put("29", DicVO.builder().code("29").name("威信县").build());
        area.put("28", DicVO.builder().code("28").name("彝良县").build());
        area.put("27", DicVO.builder().code("27").name("镇雄县").build());
        area.put("26", DicVO.builder().code("26").name("绥江县").build());
        area.put("25", DicVO.builder().code("25").name("永善县").build());
        area.put("24", DicVO.builder().code("24").name("大关县").build());
        area.put("23", DicVO.builder().code("23").name("盐津县").build());
        area.put("22", DicVO.builder().code("22").name("巧家县").build());
        area.put("21", DicVO.builder().code("21").name("鲁甸县").build());
        area.put("02", DicVO.builder().code("02").name("昭阳区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("昭通市").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("昌宁县").build());
        area.put("23", DicVO.builder().code("23").name("龙陵县").build());
        area.put("22", DicVO.builder().code("22").name("腾冲县").build());
        area.put("21", DicVO.builder().code("21").name("施甸县").build());
        area.put("02", DicVO.builder().code("02").name("隆阳区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("保山市").child(area).build());

        area = new HashMap<>();
        area.put("28", DicVO.builder().code("28").name("元江哈尼族彝族傣族自治县").build());
        area.put("27", DicVO.builder().code("27").name("新平彝族傣族自治县").build());
        area.put("26", DicVO.builder().code("26").name("峨山彝族自治县").build());
        area.put("25", DicVO.builder().code("25").name("易门县").build());
        area.put("24", DicVO.builder().code("24").name("华宁县").build());
        area.put("23", DicVO.builder().code("23").name("通海县").build());
        area.put("22", DicVO.builder().code("22").name("澄江县").build());
        area.put("21", DicVO.builder().code("21").name("江川县").build());
        area.put("02", DicVO.builder().code("02").name("红塔区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("玉溪市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("宣威市").build());
        area.put("28", DicVO.builder().code("28").name("沾益县").build());
        area.put("26", DicVO.builder().code("26").name("会泽县").build());
        area.put("25", DicVO.builder().code("25").name("富源县").build());
        area.put("24", DicVO.builder().code("24").name("罗平县").build());
        area.put("23", DicVO.builder().code("23").name("师宗县").build());
        area.put("22", DicVO.builder().code("22").name("陆良县").build());
        area.put("21", DicVO.builder().code("21").name("马龙县").build());
        area.put("02", DicVO.builder().code("02").name("麒麟区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("曲靖市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("安宁市").build());
        area.put("29", DicVO.builder().code("29").name("寻甸回族彝族自治县").build());
        area.put("28", DicVO.builder().code("28").name("禄劝彝族苗族自治县").build());
        area.put("27", DicVO.builder().code("27").name("嵩明县").build());
        area.put("26", DicVO.builder().code("26").name("石林彝族自治县").build());
        area.put("25", DicVO.builder().code("25").name("宜良县").build());
        area.put("24", DicVO.builder().code("24").name("富民县").build());
        area.put("22", DicVO.builder().code("22").name("晋宁县").build());
        area.put("21", DicVO.builder().code("21").name("呈贡县").build());
        area.put("13", DicVO.builder().code("13").name("东川区").build());
        area.put("12", DicVO.builder().code("12").name("西山区").build());
        area.put("11", DicVO.builder().code("11").name("官渡区").build());
        area.put("03", DicVO.builder().code("03").name("盘龙区").build());
        area.put("02", DicVO.builder().code("02").name("五华区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("昆明市").child(area).build());
        prov.put("53", DicVO.builder().code("53").name("云南省").child(city).build());
    }

    private static void xiZang(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("27", DicVO.builder().code("27").name("朗县").build());
        area.put("26", DicVO.builder().code("26").name("察隅县").build());
        area.put("25", DicVO.builder().code("25").name("波密县").build());
        area.put("24", DicVO.builder().code("24").name("墨脱县").build());
        area.put("23", DicVO.builder().code("23").name("米林县").build());
        area.put("22", DicVO.builder().code("22").name("工布江达县").build());
        area.put("21", DicVO.builder().code("21").name("林芝县").build());
        city.put("26", DicVO.builder().code("26").name("林芝地区").child(area).build());

        area = new HashMap<>();
        area.put("27", DicVO.builder().code("27").name("措勤县").build());
        area.put("26", DicVO.builder().code("26").name("改则县").build());
        area.put("25", DicVO.builder().code("25").name("革吉县").build());
        area.put("24", DicVO.builder().code("24").name("日土县").build());
        area.put("23", DicVO.builder().code("23").name("噶尔县").build());
        area.put("22", DicVO.builder().code("22").name("札达县").build());
        area.put("21", DicVO.builder().code("21").name("普兰县").build());
        city.put("25", DicVO.builder().code("25").name("阿里地区").child(area).build());

        area = new HashMap<>();
        area.put("30", DicVO.builder().code("30").name("尼玛县").build());
        area.put("29", DicVO.builder().code("29").name("巴青县").build());
        area.put("28", DicVO.builder().code("28").name("班戈县").build());
        area.put("27", DicVO.builder().code("27").name("索县").build());
        area.put("26", DicVO.builder().code("26").name("申扎县").build());
        area.put("25", DicVO.builder().code("25").name("安多县").build());
        area.put("24", DicVO.builder().code("24").name("聂荣县").build());
        area.put("23", DicVO.builder().code("23").name("比如县").build());
        area.put("22", DicVO.builder().code("22").name("嘉黎县").build());
        area.put("21", DicVO.builder().code("21").name("那曲县").build());
        city.put("24", DicVO.builder().code("24").name("那曲地区").child(area).build());

        area = new HashMap<>();
        area.put("38", DicVO.builder().code("38").name("岗巴县").build());
        area.put("37", DicVO.builder().code("37").name("萨嘎县").build());
        area.put("36", DicVO.builder().code("36").name("聂拉木县").build());
        area.put("35", DicVO.builder().code("35").name("吉隆县").build());
        area.put("34", DicVO.builder().code("34").name("亚东县").build());
        area.put("33", DicVO.builder().code("33").name("仲巴县").build());
        area.put("32", DicVO.builder().code("32").name("定结县").build());
        area.put("31", DicVO.builder().code("31").name("康马县").build());
        area.put("30", DicVO.builder().code("30").name("仁布县").build());
        area.put("29", DicVO.builder().code("29").name("白朗县").build());
        area.put("28", DicVO.builder().code("28").name("谢通门县").build());
        area.put("27", DicVO.builder().code("27").name("昂仁县").build());
        area.put("26", DicVO.builder().code("26").name("拉孜县").build());
        area.put("25", DicVO.builder().code("25").name("萨迦县").build());
        area.put("24", DicVO.builder().code("24").name("定日县").build());
        area.put("23", DicVO.builder().code("23").name("江孜县").build());
        area.put("22", DicVO.builder().code("22").name("南木林县").build());
        area.put("01", DicVO.builder().code("01").name("日喀则市").build());
        city.put("23", DicVO.builder().code("23").name("日喀则地区").child(area).build());

        area = new HashMap<>();
        area.put("33", DicVO.builder().code("33").name("浪卡子县").build());
        area.put("32", DicVO.builder().code("32").name("错那县").build());
        area.put("31", DicVO.builder().code("31").name("隆子县").build());
        area.put("29", DicVO.builder().code("29").name("加查县").build());
        area.put("28", DicVO.builder().code("28").name("洛扎县").build());
        area.put("27", DicVO.builder().code("27").name("措美县").build());
        area.put("26", DicVO.builder().code("26").name("曲松县").build());
        area.put("25", DicVO.builder().code("25").name("琼结县").build());
        area.put("24", DicVO.builder().code("24").name("桑日县").build());
        area.put("23", DicVO.builder().code("23").name("贡嘎县").build());
        area.put("22", DicVO.builder().code("22").name("扎囊县").build());
        area.put("21", DicVO.builder().code("21").name("乃东县").build());
        city.put("22", DicVO.builder().code("22").name("山南地区").child(area).build());

        area = new HashMap<>();
        area.put("33", DicVO.builder().code("33").name("边坝县").build());
        area.put("32", DicVO.builder().code("32").name("洛隆县").build());
        area.put("29", DicVO.builder().code("29").name("芒康县").build());
        area.put("28", DicVO.builder().code("28").name("左贡县").build());
        area.put("27", DicVO.builder().code("27").name("八宿县").build());
        area.put("26", DicVO.builder().code("26").name("察雅县").build());
        area.put("25", DicVO.builder().code("25").name("丁青县").build());
        area.put("24", DicVO.builder().code("24").name("类乌齐县").build());
        area.put("23", DicVO.builder().code("23").name("贡觉县").build());
        area.put("22", DicVO.builder().code("22").name("江达县").build());
        area.put("21", DicVO.builder().code("21").name("昌都县").build());
        city.put("21", DicVO.builder().code("21").name("昌都地区").child(area).build());

        area = new HashMap<>();
        area.put("27", DicVO.builder().code("27").name("墨竹工卡县").build());
        area.put("26", DicVO.builder().code("26").name("达孜县").build());
        area.put("25", DicVO.builder().code("25").name("堆龙德庆县").build());
        area.put("24", DicVO.builder().code("24").name("曲水县").build());
        area.put("23", DicVO.builder().code("23").name("尼木县").build());
        area.put("22", DicVO.builder().code("22").name("当雄县").build());
        area.put("21", DicVO.builder().code("21").name("林周县").build());
        area.put("02", DicVO.builder().code("02").name("城关区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("拉萨市").child(area).build());
        prov.put("54", DicVO.builder().code("54").name("西藏自治区").child(city).build());
    }

    private static void shanXi(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("82", DicVO.builder().code("82").name("汾阳市").build());
        area.put("81", DicVO.builder().code("81").name("孝义市").build());
        area.put("30", DicVO.builder().code("30").name("交口县").build());
        area.put("29", DicVO.builder().code("29").name("中阳县").build());
        area.put("28", DicVO.builder().code("28").name("方山县").build());
        area.put("27", DicVO.builder().code("27").name("岚县").build());
        area.put("26", DicVO.builder().code("26").name("石楼县").build());
        area.put("25", DicVO.builder().code("25").name("柳林县").build());
        area.put("24", DicVO.builder().code("24").name("临县").build());
        area.put("23", DicVO.builder().code("23").name("兴县").build());
        area.put("22", DicVO.builder().code("22").name("交城县").build());
        area.put("21", DicVO.builder().code("21").name("文水县").build());
        area.put("02", DicVO.builder().code("02").name("离石区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("11", DicVO.builder().code("11").name("吕梁市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("霍州市").build());
        area.put("81", DicVO.builder().code("81").name("侯马市").build());
        area.put("34", DicVO.builder().code("34").name("汾西县").build());
        area.put("33", DicVO.builder().code("33").name("蒲县").build());
        area.put("32", DicVO.builder().code("32").name("永和县").build());
        area.put("31", DicVO.builder().code("31").name("隰县").build());
        area.put("30", DicVO.builder().code("30").name("大宁县").build());
        area.put("29", DicVO.builder().code("29").name("乡宁县").build());
        area.put("28", DicVO.builder().code("28").name("吉县").build());
        area.put("27", DicVO.builder().code("27").name("浮山县").build());
        area.put("26", DicVO.builder().code("26").name("安泽县").build());
        area.put("25", DicVO.builder().code("25").name("古县").build());
        area.put("24", DicVO.builder().code("24").name("洪洞县").build());
        area.put("23", DicVO.builder().code("23").name("襄汾县").build());
        area.put("22", DicVO.builder().code("22").name("翼城县").build());
        area.put("21", DicVO.builder().code("21").name("曲沃县").build());
        area.put("02", DicVO.builder().code("02").name("尧都区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("10", DicVO.builder().code("10").name("临汾市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("原平市").build());
        area.put("32", DicVO.builder().code("32").name("偏关县").build());
        area.put("31", DicVO.builder().code("31").name("保德县").build());
        area.put("30", DicVO.builder().code("30").name("河曲县").build());
        area.put("29", DicVO.builder().code("29").name("岢岚县").build());
        area.put("28", DicVO.builder().code("28").name("五寨县").build());
        area.put("27", DicVO.builder().code("27").name("神池县").build());
        area.put("26", DicVO.builder().code("26").name("静乐县").build());
        area.put("25", DicVO.builder().code("25").name("宁武县").build());
        area.put("24", DicVO.builder().code("24").name("繁峙县").build());
        area.put("23", DicVO.builder().code("23").name("代县").build());
        area.put("22", DicVO.builder().code("22").name("五台县").build());
        area.put("21", DicVO.builder().code("21").name("定襄县").build());
        area.put("02", DicVO.builder().code("02").name("忻府区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("忻州市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("河津市").build());
        area.put("81", DicVO.builder().code("81").name("永济市").build());
        area.put("30", DicVO.builder().code("30").name("芮城县").build());
        area.put("29", DicVO.builder().code("29").name("平陆县").build());
        area.put("28", DicVO.builder().code("28").name("夏县").build());
        area.put("27", DicVO.builder().code("27").name("垣曲县").build());
        area.put("26", DicVO.builder().code("26").name("绛县").build());
        area.put("25", DicVO.builder().code("25").name("新绛县").build());
        area.put("24", DicVO.builder().code("24").name("稷山县").build());
        area.put("23", DicVO.builder().code("23").name("闻喜县").build());
        area.put("22", DicVO.builder().code("22").name("万荣县").build());
        area.put("21", DicVO.builder().code("21").name("临猗县").build());
        area.put("02", DicVO.builder().code("02").name("盐湖区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("运城市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("介休市").build());
        area.put("29", DicVO.builder().code("29").name("灵石县").build());
        area.put("28", DicVO.builder().code("28").name("平遥县").build());
        area.put("27", DicVO.builder().code("27").name("祁县").build());
        area.put("26", DicVO.builder().code("26").name("太谷县").build());
        area.put("25", DicVO.builder().code("25").name("寿阳县").build());
        area.put("24", DicVO.builder().code("24").name("昔阳县").build());
        area.put("23", DicVO.builder().code("23").name("和顺县").build());
        area.put("22", DicVO.builder().code("22").name("左权县").build());
        area.put("21", DicVO.builder().code("21").name("榆社县").build());
        area.put("02", DicVO.builder().code("02").name("榆次区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("晋中市").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("怀仁县").build());
        area.put("23", DicVO.builder().code("23").name("右玉县").build());
        area.put("22", DicVO.builder().code("22").name("应县").build());
        area.put("21", DicVO.builder().code("21").name("山阴县").build());
        area.put("03", DicVO.builder().code("03").name("平鲁区").build());
        area.put("02", DicVO.builder().code("02").name("朔城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("朔州市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("高平市").build());
        area.put("25", DicVO.builder().code("25").name("泽州县").build());
        area.put("24", DicVO.builder().code("24").name("陵川县").build());
        area.put("22", DicVO.builder().code("22").name("阳城县").build());
        area.put("21", DicVO.builder().code("21").name("沁水县").build());
        area.put("02", DicVO.builder().code("02").name("城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("晋城市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("潞城市").build());
        area.put("31", DicVO.builder().code("31").name("沁源县").build());
        area.put("30", DicVO.builder().code("30").name("沁县").build());
        area.put("29", DicVO.builder().code("29").name("武乡县").build());
        area.put("28", DicVO.builder().code("28").name("长子县").build());
        area.put("27", DicVO.builder().code("27").name("壶关县").build());
        area.put("26", DicVO.builder().code("26").name("黎城县").build());
        area.put("25", DicVO.builder().code("25").name("平顺县").build());
        area.put("24", DicVO.builder().code("24").name("屯留县").build());
        area.put("23", DicVO.builder().code("23").name("襄垣县").build());
        area.put("21", DicVO.builder().code("21").name("长治县").build());
        area.put("11", DicVO.builder().code("11").name("郊区").build());
        area.put("02", DicVO.builder().code("02").name("城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("长治市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("盂县").build());
        area.put("21", DicVO.builder().code("21").name("平定县").build());
        area.put("11", DicVO.builder().code("11").name("郊区").build());
        area.put("03", DicVO.builder().code("03").name("矿区").build());
        area.put("02", DicVO.builder().code("02").name("城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("阳泉市").child(area).build());

        area = new HashMap<>();
        area.put("27", DicVO.builder().code("27").name("大同县").build());
        area.put("26", DicVO.builder().code("26").name("左云县").build());
        area.put("25", DicVO.builder().code("25").name("浑源县").build());
        area.put("24", DicVO.builder().code("24").name("灵丘县").build());
        area.put("23", DicVO.builder().code("23").name("广灵县").build());
        area.put("22", DicVO.builder().code("22").name("天镇县").build());
        area.put("21", DicVO.builder().code("21").name("阳高县").build());
        area.put("12", DicVO.builder().code("12").name("新荣区").build());
        area.put("11", DicVO.builder().code("11").name("南郊区").build());
        area.put("03", DicVO.builder().code("03").name("矿区").build());
        area.put("02", DicVO.builder().code("02").name("城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("大同市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("古交市").build());
        area.put("23", DicVO.builder().code("23").name("娄烦县").build());
        area.put("22", DicVO.builder().code("22").name("阳曲县").build());
        area.put("21", DicVO.builder().code("21").name("清徐县").build());
        area.put("10", DicVO.builder().code("10").name("晋源区").build());
        area.put("09", DicVO.builder().code("09").name("万柏林区").build());
        area.put("08", DicVO.builder().code("08").name("尖草坪区").build());
        area.put("07", DicVO.builder().code("07").name("杏花岭区").build());
        area.put("06", DicVO.builder().code("06").name("迎泽区").build());
        area.put("05", DicVO.builder().code("05").name("小店区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("太原市").child(area).build());
        prov.put("14", DicVO.builder().code("14").name("山西省").child(city).build());

        area = new HashMap<>();
        city = new HashMap<>();
        area.put("26", DicVO.builder().code("26").name("柞水县").build());
        area.put("25", DicVO.builder().code("25").name("镇安县").build());
        area.put("24", DicVO.builder().code("24").name("山阳县").build());
        area.put("23", DicVO.builder().code("23").name("商南县").build());
        area.put("22", DicVO.builder().code("22").name("丹凤县").build());
        area.put("21", DicVO.builder().code("21").name("洛南县").build());
        area.put("02", DicVO.builder().code("02").name("商州区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("10", DicVO.builder().code("10").name("商洛市").child(area).build());

        area = new HashMap<>();
        area.put("29", DicVO.builder().code("29").name("白河县").build());
        area.put("28", DicVO.builder().code("28").name("旬阳县").build());
        area.put("27", DicVO.builder().code("27").name("镇坪县").build());
        area.put("26", DicVO.builder().code("26").name("平利县").build());
        area.put("25", DicVO.builder().code("25").name("岚皋县").build());
        area.put("24", DicVO.builder().code("24").name("紫阳县").build());
        area.put("23", DicVO.builder().code("23").name("宁陕县").build());
        area.put("22", DicVO.builder().code("22").name("石泉县").build());
        area.put("21", DicVO.builder().code("21").name("汉阴县").build());
        area.put("02", DicVO.builder().code("02").name("汉滨区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("安康市").child(area).build());

        area = new HashMap<>();
        area.put("31", DicVO.builder().code("31").name("子洲县").build());
        area.put("30", DicVO.builder().code("30").name("清涧县").build());
        area.put("29", DicVO.builder().code("29").name("吴堡县").build());
        area.put("28", DicVO.builder().code("28").name("佳县").build());
        area.put("27", DicVO.builder().code("27").name("米脂县").build());
        area.put("26", DicVO.builder().code("26").name("绥德县").build());
        area.put("25", DicVO.builder().code("25").name("定边县").build());
        area.put("24", DicVO.builder().code("24").name("靖边县").build());
        area.put("23", DicVO.builder().code("23").name("横山县").build());
        area.put("22", DicVO.builder().code("22").name("府谷县").build());
        area.put("21", DicVO.builder().code("21").name("神木县").build());
        area.put("02", DicVO.builder().code("02").name("榆阳区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("榆林市").child(area).build());

        area = new HashMap<>();
        area.put("30", DicVO.builder().code("30").name("佛坪县").build());
        area.put("29", DicVO.builder().code("29").name("留坝县").build());
        area.put("28", DicVO.builder().code("28").name("镇巴县").build());
        area.put("27", DicVO.builder().code("27").name("略阳县").build());
        area.put("26", DicVO.builder().code("26").name("宁强县").build());
        area.put("25", DicVO.builder().code("25").name("勉县").build());
        area.put("24", DicVO.builder().code("24").name("西乡县").build());
        area.put("23", DicVO.builder().code("23").name("洋县").build());
        area.put("22", DicVO.builder().code("22").name("城固县").build());
        area.put("21", DicVO.builder().code("21").name("南郑县").build());
        area.put("02", DicVO.builder().code("02").name("汉台区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("汉中市").child(area).build());

        area = new HashMap<>();
        area.put("32", DicVO.builder().code("32").name("黄陵县").build());
        area.put("31", DicVO.builder().code("31").name("黄龙县").build());
        area.put("30", DicVO.builder().code("30").name("宜川县").build());
        area.put("29", DicVO.builder().code("29").name("洛川县").build());
        area.put("28", DicVO.builder().code("28").name("富县").build());
        area.put("27", DicVO.builder().code("27").name("甘泉县").build());
        area.put("26", DicVO.builder().code("26").name("吴旗县").build());
        area.put("25", DicVO.builder().code("25").name("志丹县").build());
        area.put("23", DicVO.builder().code("23").name("子长县").build());
        area.put("22", DicVO.builder().code("22").name("延川县").build());
        area.put("21", DicVO.builder().code("21").name("延长县").build());
        area.put("02", DicVO.builder().code("02").name("宝塔区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("延安市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("华阴市").build());
        area.put("81", DicVO.builder().code("81").name("韩城市").build());
        area.put("28", DicVO.builder().code("28").name("富平县").build());
        area.put("27", DicVO.builder().code("27").name("白水县").build());
        area.put("26", DicVO.builder().code("26").name("蒲城县").build());
        area.put("25", DicVO.builder().code("25").name("澄城县").build());
        area.put("24", DicVO.builder().code("24").name("合阳县").build());
        area.put("23", DicVO.builder().code("23").name("大荔县").build());
        area.put("22", DicVO.builder().code("22").name("潼关县").build());
        area.put("21", DicVO.builder().code("21").name("华县").build());
        area.put("02", DicVO.builder().code("02").name("临渭区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("渭南市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("兴平市").build());
        area.put("31", DicVO.builder().code("31").name("武功县").build());
        area.put("30", DicVO.builder().code("30").name("淳化县").build());
        area.put("29", DicVO.builder().code("29").name("旬邑县").build());
        area.put("28", DicVO.builder().code("28").name("长武县").build());
        area.put("27", DicVO.builder().code("27").name("彬县").build());
        area.put("26", DicVO.builder().code("26").name("永寿县").build());
        area.put("25", DicVO.builder().code("25").name("礼泉县").build());
        area.put("24", DicVO.builder().code("24").name("乾县").build());
        area.put("23", DicVO.builder().code("23").name("泾阳县").build());
        area.put("22", DicVO.builder().code("22").name("三原县").build());
        area.put("04", DicVO.builder().code("04").name("渭城区").build());
        area.put("03", DicVO.builder().code("03").name("杨凌区").build());
        area.put("02", DicVO.builder().code("02").name("秦都区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("咸阳市").child(area).build());

        area = new HashMap<>();
        area.put("31", DicVO.builder().code("31").name("太白县").build());
        area.put("30", DicVO.builder().code("30").name("凤县").build());
        area.put("29", DicVO.builder().code("29").name("麟游县").build());
        area.put("28", DicVO.builder().code("28").name("千阳县").build());
        area.put("27", DicVO.builder().code("27").name("陇县").build());
        area.put("26", DicVO.builder().code("26").name("眉县").build());
        area.put("24", DicVO.builder().code("24").name("扶风县").build());
        area.put("23", DicVO.builder().code("23").name("岐山县").build());
        area.put("22", DicVO.builder().code("22").name("凤翔县").build());
        area.put("04", DicVO.builder().code("04").name("陈仓区").build());
        area.put("03", DicVO.builder().code("03").name("金台区").build());
        area.put("02", DicVO.builder().code("02").name("渭滨区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("宝鸡市").child(area).build());

        area = new HashMap<>();
        area.put("22", DicVO.builder().code("22").name("宜君县").build());
        area.put("04", DicVO.builder().code("04").name("耀州区").build());
        area.put("03", DicVO.builder().code("03").name("印台区").build());
        area.put("02", DicVO.builder().code("02").name("王益区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("铜川市").child(area).build());

        area = new HashMap<>();
        area.put("26", DicVO.builder().code("26").name("高陵县").build());
        area.put("25", DicVO.builder().code("25").name("户县").build());
        area.put("24", DicVO.builder().code("24").name("周至县").build());
        area.put("22", DicVO.builder().code("22").name("蓝田县").build());
        area.put("16", DicVO.builder().code("16").name("长安区").build());
        area.put("15", DicVO.builder().code("15").name("临潼区").build());
        area.put("14", DicVO.builder().code("14").name("阎良区").build());
        area.put("13", DicVO.builder().code("13").name("雁塔区").build());
        area.put("12", DicVO.builder().code("12").name("未央区").build());
        area.put("11", DicVO.builder().code("11").name("灞桥区").build());
        area.put("04", DicVO.builder().code("04").name("莲湖区").build());
        area.put("03", DicVO.builder().code("03").name("碑林区").build());
        area.put("02", DicVO.builder().code("02").name("新城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("西安市").child(area).build());
        prov.put("61", DicVO.builder().code("61").name("陕西省").child(city).build());
    }

    private static void ganSu(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("27", DicVO.builder().code("27").name("夏河县").build());
        area.put("26", DicVO.builder().code("26").name("碌曲县").build());
        area.put("25", DicVO.builder().code("25").name("玛曲县").build());
        area.put("24", DicVO.builder().code("24").name("迭部县").build());
        area.put("23", DicVO.builder().code("23").name("舟曲县").build());
        area.put("22", DicVO.builder().code("22").name("卓尼县").build());
        area.put("21", DicVO.builder().code("21").name("临潭县").build());
        area.put("01", DicVO.builder().code("01").name("合作市").build());
        city.put("30", DicVO.builder().code("30").name("甘南藏族自治州").child(area).build());

        area = new HashMap<>();
        area.put("27", DicVO.builder().code("27").name("积石山保安族东乡族撒拉族自治县").build());
        area.put("26", DicVO.builder().code("26").name("东乡族自治县").build());
        area.put("25", DicVO.builder().code("25").name("和政县").build());
        area.put("24", DicVO.builder().code("24").name("广河县").build());
        area.put("23", DicVO.builder().code("23").name("永靖县").build());
        area.put("22", DicVO.builder().code("22").name("康乐县").build());
        area.put("21", DicVO.builder().code("21").name("临夏县").build());
        area.put("01", DicVO.builder().code("01").name("临夏市").build());
        city.put("29", DicVO.builder().code("29").name("临夏回族自治州").child(area).build());

        area = new HashMap<>();
        area.put("30", DicVO.builder().code("30").name("徽县").build());
        area.put("29", DicVO.builder().code("29").name("两当县").build());
        area.put("28", DicVO.builder().code("28").name("礼县").build());
        area.put("27", DicVO.builder().code("27").name("西和县").build());
        area.put("26", DicVO.builder().code("26").name("文县").build());
        area.put("25", DicVO.builder().code("25").name("康县").build());
        area.put("24", DicVO.builder().code("24").name("成县").build());
        area.put("23", DicVO.builder().code("23").name("宕昌县").build());
        area.put("21", DicVO.builder().code("21").name("武都县").build());
        city.put("26", DicVO.builder().code("26").name("陇南地区").child(area).build());

        area = new HashMap<>();
        area.put("26", DicVO.builder().code("26").name("岷县").build());
        area.put("25", DicVO.builder().code("25").name("漳县").build());
        area.put("24", DicVO.builder().code("24").name("临洮县").build());
        area.put("23", DicVO.builder().code("23").name("渭源县").build());
        area.put("22", DicVO.builder().code("22").name("陇西县").build());
        area.put("21", DicVO.builder().code("21").name("通渭县").build());
        area.put("02", DicVO.builder().code("02").name("安定区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("11", DicVO.builder().code("11").name("定西市").child(area).build());

        area = new HashMap<>();
        area.put("27", DicVO.builder().code("27").name("镇原县").build());
        area.put("26", DicVO.builder().code("26").name("宁县").build());
        area.put("25", DicVO.builder().code("25").name("正宁县").build());
        area.put("24", DicVO.builder().code("24").name("合水县").build());
        area.put("23", DicVO.builder().code("23").name("华池县").build());
        area.put("22", DicVO.builder().code("22").name("环县").build());
        area.put("21", DicVO.builder().code("21").name("庆城县").build());
        area.put("02", DicVO.builder().code("02").name("西峰区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("10", DicVO.builder().code("10").name("庆阳市").child(area).build());

        area = new HashMap<>();
        area.put("82", DicVO.builder().code("82").name("敦煌市").build());
        area.put("81", DicVO.builder().code("81").name("玉门市").build());
        area.put("24", DicVO.builder().code("24").name("阿克塞哈萨克族自治县").build());
        area.put("23", DicVO.builder().code("23").name("肃北蒙古族自治县").build());
        area.put("22", DicVO.builder().code("22").name("安西县").build());
        area.put("21", DicVO.builder().code("21").name("金塔县").build());
        area.put("02", DicVO.builder().code("02").name("肃州区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("09", DicVO.builder().code("09").name("酒泉市").child(area).build());

        area = new HashMap<>();
        area.put("26", DicVO.builder().code("26").name("静宁县").build());
        area.put("25", DicVO.builder().code("25").name("庄浪县").build());
        area.put("24", DicVO.builder().code("24").name("华亭县").build());
        area.put("23", DicVO.builder().code("23").name("崇信县").build());
        area.put("22", DicVO.builder().code("22").name("灵台县").build());
        area.put("21", DicVO.builder().code("21").name("泾川县").build());
        area.put("02", DicVO.builder().code("02").name("崆峒区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("08", DicVO.builder().code("08").name("平凉市").child(area).build());

        area = new HashMap<>();
        area.put("25", DicVO.builder().code("25").name("山丹县").build());
        area.put("24", DicVO.builder().code("24").name("高台县").build());
        area.put("23", DicVO.builder().code("23").name("临泽县").build());
        area.put("22", DicVO.builder().code("22").name("民乐县").build());
        area.put("21", DicVO.builder().code("21").name("肃南裕固族自治县").build());
        area.put("02", DicVO.builder().code("02").name("甘州区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("07", DicVO.builder().code("07").name("张掖市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("天祝藏族自治县").build());
        area.put("22", DicVO.builder().code("22").name("古浪县").build());
        area.put("21", DicVO.builder().code("21").name("民勤县").build());
        area.put("02", DicVO.builder().code("02").name("凉州区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("06", DicVO.builder().code("06").name("武威市").child(area).build());

        area = new HashMap<>();
        area.put("25", DicVO.builder().code("25").name("张家川回族自治县").build());
        area.put("24", DicVO.builder().code("24").name("武山县").build());
        area.put("23", DicVO.builder().code("23").name("甘谷县").build());
        area.put("22", DicVO.builder().code("22").name("秦安县").build());
        area.put("21", DicVO.builder().code("21").name("清水县").build());
        area.put("03", DicVO.builder().code("03").name("北道区").build());
        area.put("02", DicVO.builder().code("02").name("秦城区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("天水市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("景泰县").build());
        area.put("22", DicVO.builder().code("22").name("会宁县").build());
        area.put("21", DicVO.builder().code("21").name("靖远县").build());
        area.put("03", DicVO.builder().code("03").name("平川区").build());
        area.put("02", DicVO.builder().code("02").name("白银区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("白银市").child(area).build());

        area = new HashMap<>();
        area.put("21", DicVO.builder().code("21").name("永昌县").build());
        area.put("02", DicVO.builder().code("02").name("金川区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("金昌市").child(area).build());

        area = new HashMap<>();
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("嘉峪关市").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("榆中县").build());
        area.put("22", DicVO.builder().code("22").name("皋兰县").build());
        area.put("21", DicVO.builder().code("21").name("永登县").build());
        area.put("11", DicVO.builder().code("11").name("红古区").build());
        area.put("05", DicVO.builder().code("05").name("安宁区").build());
        area.put("04", DicVO.builder().code("04").name("西固区").build());
        area.put("03", DicVO.builder().code("03").name("七里河区").build());
        area.put("02", DicVO.builder().code("02").name("城关区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("兰州市").child(area).build());
        prov.put("62", DicVO.builder().code("62").name("甘肃省").child(city).build());
    }

    private static void qingHai(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("23", DicVO.builder().code("23").name("天峻县").build());
        area.put("22", DicVO.builder().code("22").name("都兰县").build());
        area.put("21", DicVO.builder().code("21").name("乌兰县").build());
        area.put("02", DicVO.builder().code("02").name("德令哈市").build());
        area.put("01", DicVO.builder().code("01").name("格尔木市").build());
        city.put("28", DicVO.builder().code("28").name("海西蒙古族藏族自治州").child(area).build());

        area = new HashMap<>();
        area.put("26", DicVO.builder().code("26").name("曲麻莱县").build());
        area.put("25", DicVO.builder().code("25").name("囊谦县").build());
        area.put("24", DicVO.builder().code("24").name("治多县").build());
        area.put("23", DicVO.builder().code("23").name("称多县").build());
        area.put("22", DicVO.builder().code("22").name("杂多县").build());
        area.put("21", DicVO.builder().code("21").name("玉树县").build());
        city.put("27", DicVO.builder().code("27").name("玉树藏族自治州").child(area).build());

        area = new HashMap<>();
        area.put("26", DicVO.builder().code("26").name("玛多县").build());
        area.put("25", DicVO.builder().code("25").name("久治县").build());
        area.put("24", DicVO.builder().code("24").name("达日县").build());
        area.put("23", DicVO.builder().code("23").name("甘德县").build());
        area.put("22", DicVO.builder().code("22").name("班玛县").build());
        area.put("21", DicVO.builder().code("21").name("玛沁县").build());
        city.put("26", DicVO.builder().code("26").name("果洛藏族自治州").child(area).build());

        area = new HashMap<>();
        area.put("25", DicVO.builder().code("25").name("贵南县").build());
        area.put("24", DicVO.builder().code("24").name("兴海县").build());
        area.put("23", DicVO.builder().code("23").name("贵德县").build());
        area.put("22", DicVO.builder().code("22").name("同德县").build());
        area.put("21", DicVO.builder().code("21").name("共和县").build());
        city.put("25", DicVO.builder().code("25").name("海南藏族自治州").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("河南蒙古族自治县").build());
        area.put("23", DicVO.builder().code("23").name("泽库县").build());
        area.put("22", DicVO.builder().code("22").name("尖扎县").build());
        area.put("21", DicVO.builder().code("21").name("同仁县").build());
        city.put("23", DicVO.builder().code("23").name("黄南藏族自治州").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("刚察县").build());
        area.put("23", DicVO.builder().code("23").name("海晏县").build());
        area.put("22", DicVO.builder().code("22").name("祁连县").build());
        area.put("21", DicVO.builder().code("21").name("门源回族自治县").build());
        city.put("22", DicVO.builder().code("22").name("海北藏族自治州").child(area).build());

        area = new HashMap<>();
        area.put("28", DicVO.builder().code("28").name("循化撒拉族自治县").build());
        area.put("27", DicVO.builder().code("27").name("化隆回族自治县").build());
        area.put("26", DicVO.builder().code("26").name("互助土族自治县").build());
        area.put("23", DicVO.builder().code("23").name("乐都县").build());
        area.put("22", DicVO.builder().code("22").name("民和回族土族自治县").build());
        area.put("21", DicVO.builder().code("21").name("平安县").build());
        city.put("21", DicVO.builder().code("21").name("海东地区").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("湟源县").build());
        area.put("22", DicVO.builder().code("22").name("湟中县").build());
        area.put("21", DicVO.builder().code("21").name("大通回族土族自治县").build());
        area.put("05", DicVO.builder().code("05").name("城北区").build());
        area.put("04", DicVO.builder().code("04").name("城西区").build());
        area.put("03", DicVO.builder().code("03").name("城中区").build());
        area.put("02", DicVO.builder().code("02").name("城东区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("西宁市").child(area).build());
        prov.put("63", DicVO.builder().code("63").name("青海省").child(city).build());
    }

    private static void ningXia(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("22", DicVO.builder().code("22").name("海原县").build());
        area.put("21", DicVO.builder().code("21").name("中宁县").build());
        area.put("02", DicVO.builder().code("02").name("沙坡头区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("05", DicVO.builder().code("05").name("中卫市").child(area).build());

        area = new HashMap<>();
        area.put("25", DicVO.builder().code("25").name("彭阳县").build());
        area.put("24", DicVO.builder().code("24").name("泾源县").build());
        area.put("23", DicVO.builder().code("23").name("隆德县").build());
        area.put("22", DicVO.builder().code("22").name("西吉县").build());
        area.put("02", DicVO.builder().code("02").name("原州区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("04", DicVO.builder().code("04").name("固原市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("青铜峡市").build());
        area.put("24", DicVO.builder().code("24").name("同心县").build());
        area.put("23", DicVO.builder().code("23").name("盐池县").build());
        area.put("02", DicVO.builder().code("02").name("利通区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("03", DicVO.builder().code("03").name("吴忠市").child(area).build());

        area = new HashMap<>();
        area.put("21", DicVO.builder().code("21").name("平罗县").build());
        area.put("05", DicVO.builder().code("05").name("惠农区").build());
        area.put("02", DicVO.builder().code("02").name("大武口区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("石嘴山市").child(area).build());

        area = new HashMap<>();
        area.put("81", DicVO.builder().code("81").name("灵武市").build());
        area.put("22", DicVO.builder().code("22").name("贺兰县").build());
        area.put("06", DicVO.builder().code("06").name("金凤区").build());
        area.put("05", DicVO.builder().code("05").name("西夏区").build());
        area.put("04", DicVO.builder().code("04").name("兴庆区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("银川市").child(area).build());
        prov.put("64", DicVO.builder().code("64").name("宁夏回族自治区").child(city).build());
    }

    private static void xinJiang(Map<String, DicVO> prov) {
        Map<String, DicVO> area = new HashMap<>();
        Map<String, DicVO> city = new HashMap<>();

        area.put("04", DicVO.builder().code("04").name("五家渠市").build());
        area.put("03", DicVO.builder().code("03").name("图木舒克市").build());
        area.put("02", DicVO.builder().code("02").name("阿拉尔市").build());
        area.put("01", DicVO.builder().code("01").name("石河子市").build());
        city.put("90", DicVO.builder().code("90").name("省直辖行政单位").child(area).build());

        area = new HashMap<>();
        area.put("26", DicVO.builder().code("26").name("吉木乃县").build());
        area.put("25", DicVO.builder().code("25").name("青河县").build());
        area.put("24", DicVO.builder().code("24").name("哈巴河县").build());
        area.put("23", DicVO.builder().code("23").name("福海县").build());
        area.put("22", DicVO.builder().code("22").name("富蕴县").build());
        area.put("21", DicVO.builder().code("21").name("布尔津县").build());
        area.put("01", DicVO.builder().code("01").name("阿勒泰市").build());
        city.put("43", DicVO.builder().code("43").name("阿勒泰地区").child(area).build());

        area = new HashMap<>();
        area.put("26", DicVO.builder().code("26").name("和布克赛尔蒙古自治县").build());
        area.put("25", DicVO.builder().code("25").name("裕民县").build());
        area.put("24", DicVO.builder().code("24").name("托里县").build());
        area.put("23", DicVO.builder().code("23").name("沙湾县").build());
        area.put("21", DicVO.builder().code("21").name("额敏县").build());
        area.put("02", DicVO.builder().code("02").name("乌苏市").build());
        area.put("01", DicVO.builder().code("01").name("塔城市").build());
        city.put("42", DicVO.builder().code("42").name("塔城地区").child(area).build());

        area = new HashMap<>();
        area.put("28", DicVO.builder().code("28").name("尼勒克县").build());
        area.put("27", DicVO.builder().code("27").name("特克斯县").build());
        area.put("26", DicVO.builder().code("26").name("昭苏县").build());
        area.put("25", DicVO.builder().code("25").name("新源县").build());
        area.put("24", DicVO.builder().code("24").name("巩留县").build());
        area.put("23", DicVO.builder().code("23").name("霍城县").build());
        area.put("22", DicVO.builder().code("22").name("察布查尔锡伯自治县").build());
        area.put("21", DicVO.builder().code("21").name("伊宁县").build());
        area.put("03", DicVO.builder().code("03").name("奎屯市").build());
        area.put("02", DicVO.builder().code("02").name("伊宁市").build());
        city.put("40", DicVO.builder().code("40").name("伊犁哈萨克自治州").child(area).build());

        area = new HashMap<>();
        area.put("27", DicVO.builder().code("27").name("民丰县").build());
        area.put("26", DicVO.builder().code("26").name("于田县").build());
        area.put("25", DicVO.builder().code("25").name("策勒县").build());
        area.put("24", DicVO.builder().code("24").name("洛浦县").build());
        area.put("23", DicVO.builder().code("23").name("皮山县").build());
        area.put("22", DicVO.builder().code("22").name("墨玉县").build());
        area.put("21", DicVO.builder().code("21").name("和田县").build());
        area.put("01", DicVO.builder().code("01").name("和田市").build());
        city.put("32", DicVO.builder().code("32").name("和田地区").child(area).build());

        area = new HashMap<>();
        area.put("31", DicVO.builder().code("31").name("塔什库尔干塔吉克自治县").build());
        area.put("30", DicVO.builder().code("30").name("巴楚县").build());
        area.put("29", DicVO.builder().code("29").name("伽师县").build());
        area.put("28", DicVO.builder().code("28").name("岳普湖县").build());
        area.put("27", DicVO.builder().code("27").name("麦盖提县").build());
        area.put("26", DicVO.builder().code("26").name("叶城县").build());
        area.put("25", DicVO.builder().code("25").name("莎车县").build());
        area.put("24", DicVO.builder().code("24").name("泽普县").build());
        area.put("23", DicVO.builder().code("23").name("英吉沙县").build());
        area.put("22", DicVO.builder().code("22").name("疏勒县").build());
        area.put("21", DicVO.builder().code("21").name("疏附县").build());
        area.put("01", DicVO.builder().code("01").name("喀什市").build());
        city.put("31", DicVO.builder().code("31").name("喀什地区").child(area).build());

        area = new HashMap<>();
        area.put("24", DicVO.builder().code("24").name("乌恰县").build());
        area.put("23", DicVO.builder().code("23").name("阿合奇县").build());
        area.put("22", DicVO.builder().code("22").name("阿克陶县").build());
        area.put("01", DicVO.builder().code("01").name("阿图什市").build());
        city.put("30", DicVO.builder().code("30").name("克孜勒苏柯尔克孜自治州").child(area).build());

        area = new HashMap<>();
        area.put("29", DicVO.builder().code("29").name("柯坪县").build());
        area.put("28", DicVO.builder().code("28").name("阿瓦提县").build());
        area.put("27", DicVO.builder().code("27").name("乌什县").build());
        area.put("26", DicVO.builder().code("26").name("拜城县").build());
        area.put("25", DicVO.builder().code("25").name("新和县").build());
        area.put("24", DicVO.builder().code("24").name("沙雅县").build());
        area.put("23", DicVO.builder().code("23").name("库车县").build());
        area.put("22", DicVO.builder().code("22").name("温宿县").build());
        area.put("01", DicVO.builder().code("01").name("阿克苏市").build());
        city.put("29", DicVO.builder().code("29").name("阿克苏地区").child(area).build());

        area = new HashMap<>();
        area.put("29", DicVO.builder().code("29").name("博湖县").build());
        area.put("28", DicVO.builder().code("28").name("和硕县").build());
        area.put("27", DicVO.builder().code("27").name("和静县").build());
        area.put("26", DicVO.builder().code("26").name("焉耆回族自治县").build());
        area.put("25", DicVO.builder().code("25").name("且末县").build());
        area.put("24", DicVO.builder().code("24").name("若羌县").build());
        area.put("23", DicVO.builder().code("23").name("尉犁县").build());
        area.put("22", DicVO.builder().code("22").name("轮台县").build());
        area.put("01", DicVO.builder().code("01").name("库尔勒市").build());
        city.put("28", DicVO.builder().code("28").name("巴音郭楞蒙古自治州").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("温泉县").build());
        area.put("22", DicVO.builder().code("22").name("精河县").build());
        area.put("01", DicVO.builder().code("01").name("博乐市").build());
        city.put("27", DicVO.builder().code("27").name("博尔塔拉蒙古自治州").child(area).build());

        area = new HashMap<>();
        area.put("28", DicVO.builder().code("28").name("木垒哈萨克自治县").build());
        area.put("27", DicVO.builder().code("27").name("吉木萨尔县").build());
        area.put("25", DicVO.builder().code("25").name("奇台县").build());
        area.put("24", DicVO.builder().code("24").name("玛纳斯县").build());
        area.put("23", DicVO.builder().code("23").name("呼图壁县").build());
        area.put("03", DicVO.builder().code("03").name("米泉市").build());
        area.put("02", DicVO.builder().code("02").name("阜康市").build());
        area.put("01", DicVO.builder().code("01").name("昌吉市").build());
        city.put("23", DicVO.builder().code("23").name("昌吉回族自治州").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("伊吾县").build());
        area.put("22", DicVO.builder().code("22").name("巴里坤哈萨克自治县").build());
        area.put("01", DicVO.builder().code("01").name("哈密市").build());
        city.put("22", DicVO.builder().code("22").name("哈密地区").child(area).build());

        area = new HashMap<>();
        area.put("23", DicVO.builder().code("23").name("托克逊县").build());
        area.put("22", DicVO.builder().code("22").name("鄯善县").build());
        area.put("01", DicVO.builder().code("01").name("吐鲁番市").build());
        city.put("21", DicVO.builder().code("21").name("吐鲁番地区").child(area).build());

        area = new HashMap<>();
        area.put("05", DicVO.builder().code("05").name("乌尔禾区").build());
        area.put("04", DicVO.builder().code("04").name("白碱滩区").build());
        area.put("03", DicVO.builder().code("03").name("克拉玛依区").build());
        area.put("02", DicVO.builder().code("02").name("独山子区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("02", DicVO.builder().code("02").name("克拉玛依市").child(area).build());

        area = new HashMap<>();
        area.put("21", DicVO.builder().code("21").name("乌鲁木齐县").build());
        area.put("08", DicVO.builder().code("08").name("东山区").build());
        area.put("07", DicVO.builder().code("07").name("达坂城区").build());
        area.put("06", DicVO.builder().code("06").name("头屯河区").build());
        area.put("05", DicVO.builder().code("05").name("水磨沟区").build());
        area.put("04", DicVO.builder().code("04").name("新市区").build());
        area.put("03", DicVO.builder().code("03").name("沙依巴克区").build());
        area.put("02", DicVO.builder().code("02").name("天山区").build());
        area.put("01", DicVO.builder().code("01").name("市辖区").build());
        city.put("01", DicVO.builder().code("01").name("乌鲁木齐市").child(area).build());
        prov.put("65", DicVO.builder().code("65").name("新疆维吾尔自治区").child(city).build());
    }
}
