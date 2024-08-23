package com.camcorderio.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/check")
public class SampleController {

    @GetMapping("/sample")
    public String sampleEndPoint(){
        return "Working properly";
    }

}
