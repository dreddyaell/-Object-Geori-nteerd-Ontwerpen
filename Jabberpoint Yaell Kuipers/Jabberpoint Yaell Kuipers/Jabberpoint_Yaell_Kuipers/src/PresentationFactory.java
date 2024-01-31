public class PresentationFactory extends Factory {
    @Override
    public InterfaceReader createReader() {
        return new PresentationReader();
    }

    @Override
    public XmlWriter createWriter() {
        return new PresentationWriter();
    }
}
