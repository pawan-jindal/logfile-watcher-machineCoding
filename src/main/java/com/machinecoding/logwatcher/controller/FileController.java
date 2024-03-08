package com.machinecoding.logwatcher.controller;


import com.machinecoding.logwatcher.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("/initialData")
    public ResponseEntity<List<String>> getInitialData() {
        try{
            return ResponseEntity.ok(fileService.readLastLines(10));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList("Error appending data"));
        }
    }

    @PostMapping("/appendData")
    public ResponseEntity<String> appendDataToFile(@RequestParam String newData) {
        try {
            fileService.appendDataToFile(newData);
            return ResponseEntity.ok("Data appended successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error");
        }
    }
}
