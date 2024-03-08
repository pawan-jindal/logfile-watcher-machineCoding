package com.machinecoding.logwatcher.service.javafilewatcher;

import com.machinecoding.logwatcher.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
@Deprecated
public class FileChangeListenerImpl implements FileChangeListener{

    private FileWatcherService fileWatcherService;
    private FileService fileService;

    @Autowired
    public FileChangeListenerImpl(FileWatcherService fileWatcherService,
                                  FileService fileService) {
        this.fileWatcherService = fileWatcherService;
        this.fileService = fileService;
        fileWatcherService.watchFile(FileService.filePath, this);
    }

    @Override
    public void onFileChange(Path modifiedFilePath, long startLineNumber, long endLineNumber) throws IOException {
        fileService.sendUpdate( String.join("<br>", fileService.readLastLines((int) (endLineNumber - startLineNumber))));
    }
}
