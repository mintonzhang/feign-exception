package com.example.provider;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author: minton.zhang
 * @since: 2020/6/3 21:18
 */
@FeignClient(name = "consumer")
public interface TestApi {

    @GetMapping("/cdata1")
    List<String> cdata1();

    @GetMapping("/cdata2")
    List<String> cdata2();

    @GetMapping("/cdata3")
    List<String> cdata3();
}
