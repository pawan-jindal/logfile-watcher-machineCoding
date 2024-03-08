package com.machinecoding.logwatcher.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
public class FindFileUpdates {

    private FileService fileService;
    private ResourceLoader resourceLoader;
    private static Long noOfLines = 0L;

    @PostConstruct
    @Scheduled(fixedDelay = 3000)
    public void validateFileChange() throws Exception {
        long currentLine = -1;
        System.out.println("Scheduler running");
        Resource resource = resourceLoader.getResource("classpath:" + FileService.filePath);
        File file = resource.getFile();
        try(LineNumberReader lineNumberReader =
                    new LineNumberReader(new FileReader(file, StandardCharsets.UTF_8))) {
            //Skip to last line
            lineNumberReader.skip(Long.MAX_VALUE);
            currentLine = lineNumberReader.getLineNumber() + 1;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        if(currentLine == noOfLines){
            return;
        }
        System.out.println("Found new line: " + (currentLine - noOfLines));
        fileService.sendUpdate( String.join("<br>", fileService.readLastLines((int) (currentLine - noOfLines))));
        noOfLines = currentLine;
    }
}
