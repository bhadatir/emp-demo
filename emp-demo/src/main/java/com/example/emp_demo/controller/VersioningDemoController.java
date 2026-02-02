package com.example.emp_demo.controller;


import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/version-demo")
public class VersioningDemoController {

    // 1. URI Versioning (e.g., /v1/test)
    @GetMapping("/v1/employee")
    public String uriVersioning() {
        return "Accessed via URI Versioning (v1)";
    }

    // 2. Request Parameter Versioning (e.g., /test?version=1)
    @GetMapping(value = "/param", params = "version=1")
    public String paramVersioning() {
        return "Accessed via Request Param Versioning";
    }

    // 3. Custom Header Versioning (e.g., Header "X-API-VERSION: 1")
    @GetMapping(value = "/header", headers = "X-API-VERSION=1")
    public String headerVersioning() {
        return "Accessed via Header Versioning";
    }

}