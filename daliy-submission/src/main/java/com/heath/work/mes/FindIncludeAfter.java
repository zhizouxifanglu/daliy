package com.heath.work.mes;

import cn.hutool.core.collection.ListUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 查询指定包含开始工序号后面的任务
 *
 * @author hex
 * @date 2024/1/29
 **/
public class FindIncludeAfter {
    public static void main(String[] args) {
        // 排序好的任务集合
        List<Task> taskList = ListUtil.of(
                new Task(System.nanoTime(), "21", 1),
                new Task(System.nanoTime(), "25", 2),
                new Task(System.nanoTime(), "35", 3),
                new Task(System.nanoTime(), "25", 4),
                new Task(System.nanoTime(), "40", 5),
                new Task(System.nanoTime(), "55", 6),
                new Task(System.nanoTime(), "60", 7)
        );
        List<Task> result = obtainTasksStartingFrom("140", taskList);
        result.forEach(System.out::println);
    }

    /**
     * 获取指定工序号及其后面的任务
     *
     * @param startProcCode 起始工序号
     * @param taskList      所有已排好序的任务
     * @return 任务集合
     */
    public static List<Task> obtainTasksStartingFrom(String startProcCode, List<Task> taskList) {
        List<Task> resultTasks = new ArrayList<>();
        boolean found = false;

        for (Task task : taskList) {
            if (found) {
                resultTasks.add(task);
            } else if (task.getProcCode().equals(startProcCode)) {
                resultTasks.add(task);
                found = true;
            }
        }
        return resultTasks;
    }

    public static List<Task> obtainTasksStartingFrom2(String startProcCode, List<Task> taskList) {
        // 找到匹配的任务的索引
        int startIndex = IntStream.range(0, taskList.size())
                .filter(i -> taskList.get(i).getProcCode().equals(startProcCode))
                .findFirst()
                .orElse(-1);

        // 使用索引截取列表并生成新的列表
        return (startIndex != -1) ? taskList.subList(startIndex, taskList.size()) : Collections.emptyList();
    }
}

@Getter
@Setter
class Task {
    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 工序号
     */
    private String procCode;

    /**
     * 顺序号
     */
    private Integer orderIndex;

    public Task(Long taskId, String procCode, Integer orderIndex) {
        this.taskId = taskId;
        this.procCode = procCode;
        this.orderIndex = orderIndex;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", procCode='" + procCode + '\'' +
                ", orderIndex=" + orderIndex +
                '}';
    }
}
