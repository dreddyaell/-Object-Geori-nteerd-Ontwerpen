package com.jabberpoint.io;

public class DemoPresentationFactory extends AccessorFactory{
    public DemoPresentationFactory(String type) {
        super(type);
    }

    @Override
    public PresentationReader CreateReader() {
        return new DemoPresentationReader();
    }

    @Override
    public Writer CreateWriter() {
        return new DemoPresentationWriter();
    }
}