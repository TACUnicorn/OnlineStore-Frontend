package com.webside.demo;


import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@RestController
@RequestMapping("/other")
public class OtherController {

    String postUrl = "http://10.0.1.52:8001/express/deliver";
    String fUrl = "http://10.0.1.52:3223/transfer";

    public Integer id = 0;
    public HashMap<String, Boolean> payments = new HashMap<>();

    public Integer num = 1;
    public String firstname = "";
    public String lastname = "";
    public String phone = "13000000000";
    public String country = "";
    public String city = "";
    public  String region = "";
    public String additional = "";
    public String remark = "";


    @PostMapping("/next")
    public Object next(@RequestBody HashMap<Object, Object> req){
        HashMap<Object, Object> response = new HashMap<>();
        String url = "http://qr.liantu.com/api.php?bg=f3f3f3&fg=ff0000&gc=222222&el=l&w=200&m=10&text=" + "http://10.0.1.66:8088/other/pay/";
        try {
            id = id + 1;

            {
                num = (Integer) req.get("num");
                firstname = (String) req.get("firstname");
                lastname = (String) req.get("lastname");
                phone = (String) req.get("phone").toString();
                country = (String) req.get("country");
                city = (String) req.get("city");
                region = (String) req.get("region");
                additional = (String) req.get("additional");
                remark = (String) req.get("remark");
            }
            payments.put(id.toString(), false);
            response.put("code",0);
            response.put("url",url);


        }catch (Exception e){
            response.put("code",1);
            response.put("url", url);
        }


        return response;
    }

    @GetMapping("/check")
    public Object check(){
        HashMap<Object, Object> response = new HashMap<>();
        if (payments.get(id.toString())){
            response.put("flag","1");
            return response;
        }else {
            response.put("flag","0");
            return response;
        }

    }

    @GetMapping("/pay")
    public Object pay(){
        HashMap<Object, Object> response = new HashMap<>();
        try {
            Thread.sleep(1500);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fromUser", "UNICORN");
            jsonObject.put("toUser",lastname + " " + firstname);
            jsonObject.put("source", "TONGJI UNICORN");
            jsonObject.put("destination", region + "," + city + "," + country + " " + additional);
            jsonObject.put("fromUserPhone", "13266666666");
            jsonObject.put("toUserPhone", phone.toString());
            JsonSMS(jsonObject.toString(), postUrl);

            // 调金融
            JSONObject fj = new JSONObject();
            fj.put("fromAccount", "123456");
            fj.put("toAccount", "000000");
            fj.put("sum", 10000);
            JsonSMSFinance(fj.toString(), fUrl);

            payments.put(id.toString(), true);
            response.put("flag","0");
            return response;

        } catch (InterruptedException e) {
            response.put("flag","1");
            return response;
        }catch (Exception e){
            response.put("flag","1");
            return response;
        }
    }

    public static Boolean JsonSMS(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);

            out.flush();
            out.close();
            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return false;
            }
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
//            System.out.println(result);
            System.out.println("E");
            return true;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return false;
    }

    public static Boolean JsonSMSFinance(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);

            out.flush();
            out.close();
            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return false;
            }
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
//            System.out.println(result);
            System.out.println("F");
            return true;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return false;
    }


}
