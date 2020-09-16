package com.example.demo.task;

import com.example.demo.util.CreateFileUtil;
import com.example.demo.util.ServerConnectUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @program: demo
 * @description: 定时任务生成当前所有服务器的检测状态
 * @author: simmonZ
 * @create: 2020-09-12 09:43
 */
@Component
public class ServerCheckTask {
    @Async
    @Scheduled(cron = "${sheduleTime}")
    public void CheckStatus() {
        System.out.println("定时任务执行了");
        List<String> result = new ArrayList<>();
        if (CreateFileUtil.itemMap.size() == 0) {
            CreateFileUtil.readTxtFile();
        }
        for (String key : CreateFileUtil.itemMap.keySet()) {
            boolean flag = ServerConnectUtil.isReachable(CreateFileUtil.itemMap.get(key).getUrl(), CreateFileUtil.itemMap.get(key).getPort(), 10000);
            String serverInfo = CreateFileUtil.itemMap.get(key).getName() + "|" +
                    CreateFileUtil.itemMap.get(key).getUrl() + "|" +
                    CreateFileUtil.itemMap.get(key).getPort() + "|" +
                    (flag ? "服务正常" : "服务出现问题");
            result.add(serverInfo);
        }
        if (result.size() > 0) {
            CreateFileUtil.createTxtFile(result, "服务检测信息详细" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + "时.txt", "服务器名称|服务器IP|端口号|服务器状态", false);
        }
    }
}
