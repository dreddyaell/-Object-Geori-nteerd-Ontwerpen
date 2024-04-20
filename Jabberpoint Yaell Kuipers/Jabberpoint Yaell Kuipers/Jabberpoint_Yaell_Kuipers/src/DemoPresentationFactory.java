public class DemoPresentationFactory extends AccessorFactory{
    @Override
    public Reader CreateReader() {
        return new DemoPresentationReader();
    }

    @Override
    public Writer CreateWriter() {
        return new DemoPresentationWriter();
    }
}