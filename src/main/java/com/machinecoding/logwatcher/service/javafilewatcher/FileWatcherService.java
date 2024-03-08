package com.machinecoding.logwatcher.service.javafilewatcher;

import com.machinecoding.logwatcher.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Deprecated
public class FileWatcherService {

    private final Map<Path, Long> previousLineNumbers = new HashMap<>();
    private ResourceLoader resourceLoader;

    // commented this code due to it is currently affecting complete/main application,
    // application getting down after this file gets runs
//    public void watchFile(String filePath, FileChangeListener listener) {
//        try {
//            Resource resource = resourceLoader.getResource("classpath:" + filePath);
//            Path path = Paths.get(resource.getURI());
//            long initialLineNumber = getLineCount();
//            previousLineNumbers.put(path, initialLineNumber);
//            WatchService watchService = FileSystems.getDefault().newWatchService();
//            path.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
//
//            while (true) {
//                WatchKey key = watchService.take();
//                for (WatchEvent<?> event : key.pollEvents()) {
//                    if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
//                        Path modifiedFilePath = path.resolve((Path) event.context());
//                        long currentLineNumber = getLineCount();
//
//                        if (currentLineNumber > previousLineNumbers.get(path)) {
//                            listener.onFileChange(modifiedFilePath, previousLineNumbers.get(path) + 1, currentLineNumber);
//                            previousLineNumbers.put(path, currentLineNumber);
//                        }
//                    }
//                }
//                key.reset();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private long getLineCount() throws IOException {
        long currentLine = -1L;
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
        return currentLine;
    }
}
