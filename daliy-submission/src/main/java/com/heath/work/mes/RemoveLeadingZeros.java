package com.heath.work.mes;

import cn.hutool.core.collection.ListUtil;

import java.util.List;

/**
 * 从字母数字文本中删除前导零
 * 应用：处理大修展开策略-工序物料-组号
 * <a href="https://stackoverflow.com/questions/2800739/how-to-remove-leading-zeros-from-alphanumeric-text">Java字符串删除前导零</a>
 *
 * @author hex
 * @date 2024/2/2
 **/
public class RemoveLeadingZeros {
    public static void main(String[] args) {
        List<String> padGroupNos = ListUtil.of("01", "02", "015", "110", "101");
        padGroupNos.stream().map(x -> x.replaceFirst("^0+(?!$)", "") + " ").forEach(System.out::print);
    }
}
