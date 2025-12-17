package software.ulpgc.kata6.app.lily;

import software.ulpgc.kata6.architecture.model.Movie;
import software.ulpgc.kata6.architecture.view.Query;

import java.sql.SQLException;
import java.util.stream.Stream;

public class DatabaseQuery implements Query {

    private final DatabaseStore store;

    public DatabaseQuery(DatabaseStore store) {
        this.store = store;
    }

    @Override
    public Stream<Movie> moviesReleasedBetween(int startYear, int endYear) {
        try {
            return store.moviesFilteredBy("year BETWEEN ? AND ?", startYear, endYear);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stream<Movie> moviesWithDurationBetween(int min, int max) {
        try {
            return store.moviesFilteredBy("duration BETWEEN ? AND ?", min, max);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stream<Movie> moviesWithStartingLetterBetween(char start, char end) {
        try {
            return store.moviesFilteredBy("UPPER(SUBSTR(title, 1, 1)) BETWEEN ? AND ?", start, end);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
