package com.example.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: minton.zhang
 * @since: 2020/6/3 21:14
 */
@RestController
public class TestController {

    @Autowired
    private TestApi testApi;

    @GetMapping("/data1")
    public List<String> data1() {
        throw new RuntimeException("模拟错误");
//        return testApi.cdata2();
//        return Arrays.asList("张三", "李四", "王五");
    }

    @GetMapping("/data2")
    public List<String> data2() {
        throw new RuntimeException("模拟错误");
//        return Arrays.asList("张三", "李四", "王五");
    }
}
