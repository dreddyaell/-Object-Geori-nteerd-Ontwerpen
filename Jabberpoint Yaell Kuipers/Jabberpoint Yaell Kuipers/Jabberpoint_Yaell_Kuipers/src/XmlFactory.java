public class XMLFactory extends AccessorFactory{
    @Override
    public Reader CreateReader() {
        return new XMLReader();
    }

    @Override
    public Writer CreateWriter() {
        return new XMLWriter();
    }
}