public class XmlFactory extends Factory{
    @Override
    public XmlReader CreateReader() {
        return new XmlReader();
    }

    @Override
    public XmlWriter CreateWriter() {
        return new XmlWriter();
    }
}