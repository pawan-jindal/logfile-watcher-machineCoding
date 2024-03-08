package com.machinecoding.logwatcher.service;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.io.input.ReversedLinesFileReader;

@Service
public class FileService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ResourceLoader resourceLoader;
    public static final String filePath = "static/data.txt";

    @Autowired
    public FileService(SimpMessagingTemplate messagingTemplate,
                       ResourceLoader resourceLoader) {
        this.messagingTemplate = messagingTemplate;
        this.resourceLoader = resourceLoader;
    }


    public List<String> readLastLines(int linesToRead) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filePath);
        File file = resource.getFile();
        List<String> result = new ArrayList<>();
        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(file, StandardCharsets.UTF_8)) {
            String line = "";
            while ((line = reader.readLine()) != null && result.size() < linesToRead) {
                result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.reverse(result);
        return result;
    }

    public void appendDataToFile(String content) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filePath);
        File file = resource.getFile();

        Files.write(file.toPath(), Collections.singletonList(content), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public void sendUpdate(Object message){
        messagingTemplate.convertAndSend("/topic/fileUpdates", message);
    }

}
