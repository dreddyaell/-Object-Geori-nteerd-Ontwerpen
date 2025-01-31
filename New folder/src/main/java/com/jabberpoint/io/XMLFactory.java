package com.jabberpoint.io;

public class XMLFactory extends AccessorFactory{
    @Override
    public PresentationReader CreateReader() {
        return new XMLPresentationReader();
    }

    @Override
    public Writer CreateWriter() {
        return new XMLWriter();
    }
}