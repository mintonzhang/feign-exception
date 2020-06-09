package com.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: minton.zhang
 * @since: 2020/6/3 21:18
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private TestApi testApi;

    @GetMapping("/cdata1")
    public List<String> cdata1() {
        return testApi.data1();
    }

    @GetMapping("/cdata2")
    public List<String> cdata2() {
        return testApi.data2();
    }
}
