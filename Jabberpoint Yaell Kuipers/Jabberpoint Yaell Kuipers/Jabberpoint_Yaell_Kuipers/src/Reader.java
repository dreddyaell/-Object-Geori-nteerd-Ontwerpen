import java.io.IOException;

public interface Reader {
    void loadFile(Presentation p, String fn) throws IOException;
}
