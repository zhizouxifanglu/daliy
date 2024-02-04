package com.heath.work.mes;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 顺序号的处理
 *
 * @author hex
 * @date 2024/1/30
 **/
public class SequenceProcessor {

    /**
     * 处理顺序号保存，规则如下：
     * 顺序号的组成：批次号 + 顺序号
     * 1、2024-1-1-1、2024-1-1-2、2024-1-1-3、....、2024-1-1-n，如果是顺序是连续的则保存为: 2024-1-1-(1~n)
     * 2、2024-1-1-1、2024-1-1-3、2024-1-1-5、....、2024-1-1-n，如果是顺序不是连续的则保存为: 2024-1-1-(1/3/5/n)
     * 3、如果只传入一个顺序号则不需要括号直接存储，如: 2024-1-1-1
     *
     * @param orderIndices 传入的顺序号集合
     * @return 在保存的顺序号字符串
     */
    public static String processSequences(List<Sn> orderIndices) {
        if (CollectionUtil.isEmpty(orderIndices)) {
            return "";
        }

        try {
            // 排序
            List<String> sequences = orderIndices.stream()
                    .sorted(Comparator.comparingInt(x -> Integer.parseInt(x.getCode().split("-")[3])))
                    .map(Sn::getCode).collect(Collectors.toList());
            if (CollectionUtil.size(sequences) == 1) {
                String start = sequences.get(0);
                String span = StrUtil.sub(start, 0, start.length() - 1);
                return span + Integer.parseInt(start.split("-")[3]);
            }


            // 是否连续
            boolean isConsecutive = true;

            for (int i = 1; i < sequences.size(); i++) {
                String current = sequences.get(i);
                String previous = sequences.get(i - 1);

                if (!isSequential(previous, current)) {
                    isConsecutive = false;
                    break;
                }
            }


            // 结果
            StringBuilder result = new StringBuilder();
            String start = sequences.get(0);
            String span = StrUtil.sub(start, 0, start.length() - 1);
            result.append(span).append("(");

            if (isConsecutive) {
                // 连续
                String first = sequences.get(0);
                String begin = StrUtil.sub(first, first.length() - 1, first.length());
                String last = sequences.get(sequences.size() - 1);
                String end = StrUtil.sub(last, last.length() - 1, last.length());
                result.append(begin).append("~").append(end);
            } else {
                // 不连续
                String str = sequences.stream()
                        .map(s -> StrUtil.sub(s, s.length() - 1, s.length()))
                        .collect(Collectors.joining("/"));
                result.append(str);
            }

            result.append(")");

            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException("处理制造订单顺序号格式时发生异常");
        }
    }

    /**
     * 判断连续
     *
     * @param previous
     * @param current
     * @return
     */
    private static boolean isSequential(String previous, String current) {
        int previousValue = Integer.parseInt(previous.split("-")[3]);
        int currentValue = Integer.parseInt(current.split("-")[3]);
        return currentValue == previousValue + 1;
    }

    public static void main(String[] args) {

    }
}

@Getter
@Setter
class Sn {

    /**
     * id
     */
    private Long id;

    /**
     * 顺序号
     */
    private String code;

    public Sn(Long id, String code) {
        this.id = id;
        this.code = code;
    }
}
