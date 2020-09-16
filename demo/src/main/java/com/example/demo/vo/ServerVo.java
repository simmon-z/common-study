package com.example.demo.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @program: demo
 * @description: 服务器信息
 * @author: simmonZ
 * @create: 2020-09-08 14:46
 */
@Data
public class ServerVo {
    private String url;
    private String port;
    private String name;
}
