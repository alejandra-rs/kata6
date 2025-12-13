package software.ulpgc.kata6.app.lily;

import software.ulpgc.kata6.app.Desktop;
import software.ulpgc.kata6.app.MovieDeserializer;
import software.ulpgc.kata6.app.lavender.RemoteStore;
import software.ulpgc.kata6.architecture.io.Store;
import software.ulpgc.kata6.architecture.model.Movie;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Stream;

public class Main {

    private static String database = "movies.db";

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + database)) {
            connection.setAutoCommit(false);
            Desktop.with(moviesIn(connection))
                    .display()
                    .setVisible(true);
        }
    }

    private static Store moviesIn(Connection connection) throws SQLException {
        if (isDatabaseEmpty()) insertMoviesInto(connection);
        return new DatabaseStore(connection);
    }

    private static void insertMoviesInto(Connection connection) throws SQLException {
        Stream<Movie> movies = new RemoteStore(MovieDeserializer::fromTsv).movies();
        new DatabaseRecorder(connection).record(movies);
    }

    private static boolean isDatabaseEmpty() {
        return new File(database).length() == 0;
    }
}
