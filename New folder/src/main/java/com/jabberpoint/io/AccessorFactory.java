package com.jabberpoint.io;

public class AccessorFactory {

    public static AccessorFactory getFactory(String type) {
        // Return the appropriate factory instance based on the type
        return new AccessorFactory();  // Simplified for this example
    }

    public Writer CreateWriter() {
        // Return an instance of DemoPresentationWriter or another Writer based on the type
        return new DemoPresentationWriter();
    }

    public PresentationReader CreateReader() {
        // Return an instance of DemoPresentationReader or another Reader based on the type
        return new DemoPresentationPresentationReader();
    }
}
