package com.ssafy.TmT.controller.interf;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/mock")
public interface MockDataController {


    @PostMapping("/insert")
    public ResponseEntity<String> insertMockData();
}
