package com.example.demo.util;

import com.example.demo.vo.ServerVo;
import org.springframework.util.StringUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: demo
 * @description: 生成和读取项目下的txt文件
 * @author: simmonZ
 * @create: 2020-09-11 21:53
 */
public class CreateFileUtil {
    private static String filePath = System.getProperty("user.dir");//项目根路径
    private static int num = 0;//判断头部写入的时机
    public static Map<String, ServerVo> itemMap = new ConcurrentHashMap<>();//对外的值引用

    private static String checkPath=System.getProperty("user.dir")+File.separator+getCurrentDay();
    //封装创建json文件的方法
    public static void createTxtFile(List<String> result,String fileName, String hearder, boolean flag) {
        long start = System.currentTimeMillis();
        BufferedWriter out = null;
        filePath=flag?filePath:checkPath;
        try {
            if (result != null && result.size() > 0) {
//                String fileName = "server.txt";
                File pathFile = new File(filePath);
                if (!pathFile.exists()) {
                    pathFile.mkdirs();
                }
                String relFilePath = filePath + File.separator + fileName;
                File file = new File(relFilePath);
                if (!file.exists()) {
                    file.createNewFile();
                    num = 1;
                }
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, flag), "gbk"));
                if (num == 1 || !flag) {
                    out.write(hearder);
                    out.newLine();
                }
//                //标题头
                for (String info : result) {
                    out.write(info);
                    out.newLine();
                }
                System.out.println("写入文件耗时：*********************************" + (System.currentTimeMillis() - start) + "毫秒");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            num = 0;
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void addServer(ServerVo serverVo) {
        if (itemMap.containsKey(serverVo.getUrl() + serverVo.getPort())) {
            return;
        }
        String string = serverVo.getName() + "|" + serverVo.getUrl() + "|" + serverVo.getPort();
        createTxtFile(Arrays.asList(string), "服务器信息列表.txt", "服务器名称|服务器IP|端口号", true);
        readTxtFile();
    }

    public static void readTxtFile() {
        InputStream is = null;
        InputStreamReader isr = null;
        try {
            File file = new File(filePath + "\\服务器信息列表.txt");
            if (!file.exists()) {
                return;
            }
            is = new FileInputStream(file);
            isr = new InputStreamReader(is, "gbk");
            BufferedReader br = new BufferedReader(isr);
            String lineTxt = null;
            br.readLine();
            while (!StringUtils.isEmpty((lineTxt = br.readLine()))) {
                String[] arrStrings = lineTxt.split("\\|");
                ServerVo serverVo = new ServerVo();
                serverVo.setName(arrStrings[0]);
                serverVo.setUrl(arrStrings[1]);
                serverVo.setPort(arrStrings[2]);
                itemMap.put(serverVo.getUrl() + serverVo.getPort(), serverVo);
            }
            //itemMap.remove("服务器名称服务器IP");
            is.close();
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e1) {
                }
            }
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static void main(String[] args) {
        Date date =new Date();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(date));
        Calendar calendar=Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH)+1);
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
    }

    public static String getCurrentDay(){
        Calendar calendar=Calendar.getInstance();
        String filePath=calendar.get(Calendar.YEAR)+File.separator+(calendar.get(Calendar.MONTH)+1)+File.separator+calendar.get(Calendar.DAY_OF_MONTH)+File.separator;
        return filePath;
    }
}
