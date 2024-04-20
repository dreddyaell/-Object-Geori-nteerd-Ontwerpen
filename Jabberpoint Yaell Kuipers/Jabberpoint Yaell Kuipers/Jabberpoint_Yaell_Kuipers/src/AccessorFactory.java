public abstract class AccessorFactory {

    public static AccessorFactory getFactory(String fileName)
    {
        switch (fileName)
        {
            case "xml":
                return new XMLFactory();
            default:
                return new DemoPresentationFactory();
        }
    }

    public abstract Reader CreateReader();

    public abstract Writer CreateWriter();
}