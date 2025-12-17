package software.ulpgc.kata6.app.rose;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import software.ulpgc.kata6.app.lily.DatabaseQuery;
import software.ulpgc.kata6.app.lily.DatabaseStore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    private final DatabaseQuery query;

    public Main(Connection connection) {
        this.query = new DatabaseQuery(new DatabaseStore(connection));
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:movies.db");
        Main main = new Main(connection);
        Javalin app = Javalin.create();
        app.start(8080);
        app.get("/movies/title", main::getMoviesFilteredByTitle);
        app.get("/movies/year", main::getMoviesFilteredByYear);
        app.get("/movies/duration", main::getMoviesFilteredByDuration);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> close(connection)));
    }

    private void getMoviesFilteredByTitle(Context context) {
        try {
            char start = parseCharacterIn(context, "start");
            char end = parseCharacterIn(context, "end");
            context.json(query.moviesWithStartingLetterBetween(start, end).toList());
        } catch (Exception e) {
            context.status(400);
            context.result("Start and end must be alphabetical characters.");
        }
    }

    private char parseCharacterIn(Context context, String key) {
        if (context.queryParam(key).length() != 1) throw new RuntimeException();
        char parsedCharacter = context.queryParam(key).toUpperCase().charAt(0);
        if (!Character.isLetter(parsedCharacter)) throw new RuntimeException();
        return parsedCharacter;
    }

    private void getMoviesFilteredByYear(Context context) {
        try {
            int from = Integer.parseInt(context.queryParam("from"));
            int to = Integer.parseInt(context.queryParam("to"));
            context.json(query.moviesReleasedBetween(from, to).toList());
        } catch (Exception e) {
            context.status(400);
            context.result("From and to parameters must be integers.");
        }
    }

    private void getMoviesFilteredByDuration(Context context) {
        try {
            int min = Integer.parseInt(context.queryParam("min"));
            int max = Integer.parseInt(context.queryParam("max"));
            context.json(query.moviesWithDurationBetween(min, max).toList());
        } catch (Exception e) {
            context.status(400);
            context.result("Min and max parameters must be integers.");
        }
    }

    private static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
