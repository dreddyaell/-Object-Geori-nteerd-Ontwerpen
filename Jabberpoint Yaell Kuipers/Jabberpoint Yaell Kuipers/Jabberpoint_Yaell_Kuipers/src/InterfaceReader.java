import java.io.IOException;

public interface InterfaceReader {
    void loadFile(Presentation p, String fn) throws IOException;

    void saveFile(Presentation p, String demo);
}
