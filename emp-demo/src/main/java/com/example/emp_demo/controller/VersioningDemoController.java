package com.example.emp_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/version-demo")
public class VersioningDemoController {

    @GetMapping("/v1/employee")
    public String uriVersioning() {
        return "Accessed via URI Versioning (v1)";
    }

    @GetMapping(value = "/param", params = "version=1")
    public String paramVersioning() {
        return "Accessed via Request Param Versioning";
    }

    @GetMapping(value = "/header", headers = "X-API-VERSION=1")
    public String headerVersioning() {
        return "Accessed via Header Versioning";
    }

}