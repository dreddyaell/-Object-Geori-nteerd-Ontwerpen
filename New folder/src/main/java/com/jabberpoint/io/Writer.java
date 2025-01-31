package com.jabberpoint.io;

import com.jabberpoint.model.Presentation;

import java.io.IOException;

public interface Writer {
    void saveFile(Presentation presentation, String filename) throws IOException;
}
