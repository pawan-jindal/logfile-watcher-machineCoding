package com.machinecoding.logwatcher.service.javafilewatcher;

import java.io.IOException;
import java.nio.file.Path;

@Deprecated
public interface FileChangeListener {
    void onFileChange(Path modifiedFilePath, long startLineNumber, long endLineNumber) throws IOException;
}
