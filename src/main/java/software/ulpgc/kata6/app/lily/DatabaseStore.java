package software.ulpgc.kata6.app.lily;

import software.ulpgc.kata6.architecture.io.Store;
import software.ulpgc.kata6.architecture.model.Movie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Stream;

public class DatabaseStore implements Store {

    private final Connection connection;

    public DatabaseStore(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Stream<Movie> movies() {
        try {
            return moviesIn(resultSet());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<Movie> moviesIn(ResultSet resultSet) {
        return Stream.generate(() -> nextMovieIn(resultSet))
                .onClose(() -> close(resultSet))
                .takeWhile(Objects::nonNull);
    }

    private Movie nextMovieIn(ResultSet resultSet) {
        try {
            return resultSet.next() ? readMovieFrom(resultSet) : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Movie readMovieFrom(ResultSet resultSet) throws SQLException {
        return new Movie(
                resultSet.getString(1),
                resultSet.getInt(2),
                resultSet.getInt(3)
        );
    }

    private void close(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ResultSet resultSet() throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM movies");
    }
}
