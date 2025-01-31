package com.jabberpoint.io;

public class DemoPresentationFactory extends AccessorFactory{
    @Override
    public PresentationReader CreateReader() {
        return new DemoPresentationPresentationReader();
    }

    @Override
    public Writer CreateWriter() {
        return new DemoPresentationWriter();
    }
}