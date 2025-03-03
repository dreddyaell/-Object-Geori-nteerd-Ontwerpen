package com.jabberpoint.io;

public class AccessorFactory {


    public static AccessorFactory getFactory(String type) {
        return new AccessorFactory(type);
    }

    private final String type;

    public AccessorFactory(String type) {
        this.type = type.toLowerCase(); //
    }


    public Writer CreateWriter() {
        return new DemoPresentationWriter();
    }


    public PresentationReader CreateReader() {
        switch (type) {
            case "xml":
                return new XMLReader();
            case "demo":
                return new DemoPresentationReader();
            default:
                throw new IllegalArgumentException("Onbekend bestandstype: " + type);
        }
    }
}
