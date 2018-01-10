package com.webside.demo;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/other")
public class OtherController {

    public Integer id = 0;
    public HashMap<String, Boolean> payments = new HashMap<>();


    @GetMapping("/next")
    public Object next(){

        id = id + 1;

        payments.put(id.toString(), false);

        HashMap<Object, Object> response = new HashMap<>();

        String url = "http://qr.liantu.com/api.php?bg=f3f3f3&fg=ff0000&gc=222222&el=l&w=200&m=10&text=" + "http://10.0.1.2:8088/other/pay/";

        response.put("url",url);

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
        payments.put(id.toString(), true);
        try {
            Thread.sleep(1500);
            response.put("flag","1");
            return response;
        } catch (InterruptedException e) {
            response.put("flag","1");
            return response;
        }
    }


}
