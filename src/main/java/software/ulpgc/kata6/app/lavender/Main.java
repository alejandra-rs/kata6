package software.ulpgc.kata6.app.lavender;

import software.ulpgc.kata6.app.Desktop;
import software.ulpgc.kata6.app.MovieDeserializer;

public class Main {
    public static void main(String[] args) {
        Desktop.with(new RemoteStore(MovieDeserializer::fromTsv))
                .display()
                .setVisible(true);
    }
}
