package com.example.demo.controller;

import com.example.demo.util.CreateFileUtil;
import com.example.demo.util.ServerConnectUtil;
import com.example.demo.vo.ServerVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/server")
public class ServerRequestController {
    /**
     * 页面跳转
     *
     * @return
     */
    @GetMapping("mainPage")
    public String jumpMainPage() {
        return "main";
    }

    /**
     * 服务器列表查询
     *
     * @return
     */
    @ResponseBody
    @PostMapping("query")
    public Object queryList() {
        List<ServerVo> list =new ArrayList<>();
        if(CreateFileUtil.itemMap.size()==0){
            CreateFileUtil.readTxtFile();
        }
        for(String key : CreateFileUtil.itemMap.keySet()){
            list.add(CreateFileUtil.itemMap.get(key));
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", "0");
        resultMap.put("msg", "");
        resultMap.put("data", list);
        resultMap.put("count", list.size());
        return resultMap;
    }

    /**
     * 添加新的服务器
     *
     * @param serverVo
     * @return
     */
    @ResponseBody
    @PostMapping("add")
    public String add(ServerVo serverVo) {
        CreateFileUtil.addServer(serverVo);
        return "200";
    }
    @ResponseBody
    @GetMapping("reload")
    public String reload() {
        CreateFileUtil.itemMap.clear();
        CreateFileUtil.readTxtFile();
        return "200";
    }
    /**
     * 验证服务器端口是否开启
     *
     * @param serverVo
     * @return
     */
    @ResponseBody
    @PostMapping("monitor")
    public Boolean monitor(ServerVo serverVo) {
        boolean result = ServerConnectUtil.isReachable(serverVo.getUrl(), serverVo.getPort(), 10000);
        return result;
    }
}
