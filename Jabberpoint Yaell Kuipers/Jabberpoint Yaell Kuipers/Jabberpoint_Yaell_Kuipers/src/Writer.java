import java.io.IOException;

public interface Writer {
   void saveFile(Presentation presentation, String filename) throws IOException;
}

