package com.example.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author: minton.zhang
 * @since: 2020/6/3 21:18
 */
@FeignClient(name = "provider")
public interface TestApi {

    @GetMapping("/data1")
    List<String> data1();

    @GetMapping("/data2")
    List<String> data2();

    @GetMapping("/data3")
    List<String> data3();
}
