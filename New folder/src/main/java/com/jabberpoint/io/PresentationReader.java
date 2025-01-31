package com.jabberpoint.io;

import com.jabberpoint.model.Presentation;

import java.io.IOException;

public interface PresentationReader {
    void loadFile(Presentation presentation, String filename) throws IOException;
}
