public abstract class Factory {
    public static Factory getFactory(String type) {
        switch (type.toLowerCase()) {
            case "xml":
                return new XmlFactory();
            default:
                return new PresentationFactory();
        }
    }

    public abstract InterfaceReader createReader();

    public abstract XmlWriter createWriter();
}
