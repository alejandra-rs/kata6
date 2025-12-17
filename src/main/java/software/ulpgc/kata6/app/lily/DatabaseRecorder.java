package software.ulpgc.kata6.app.lily;

import software.ulpgc.kata6.architecture.io.Recorder;
import software.ulpgc.kata6.architecture.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Stream;

public class DatabaseRecorder implements Recorder {

    private final Connection connection;
    private final PreparedStatement preparedStatement;

    public DatabaseRecorder(Connection connection) throws SQLException {
        this.connection = connection;
        createTableIfNotExists();
        this.preparedStatement = connection.prepareStatement("INSERT INTO movies (title, year, duration) VALUES (?, ?, ?)");
    }

    @Override
    public void record(Stream<Movie> movies) {
        try {
            movies.forEach(this::record);
            flushBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void record(Movie movie) {
        try {
            insert(movie);
            flushBatchIfRequired();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insert(Movie movie) throws SQLException {
        preparedStatement.setString(1, movie.name());
        preparedStatement.setInt(2, movie.year());
        preparedStatement.setInt(3, movie.duration());
        preparedStatement.addBatch();
    }

    private void flushBatch() throws SQLException {
        preparedStatement.executeBatch();
        connection.commit();
    }

    private int counter = 0;
    private void flushBatchIfRequired() throws SQLException {
        if (++counter % 10000 == 0) preparedStatement.executeBatch();
    }

    private void createTableIfNotExists() throws SQLException {
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS movies (title TEXT, year INTEGER, duration INTEGER)");
        createIndexes();
    }

    private void createIndexes() throws SQLException {
        connection.createStatement().execute("CREATE INDEX IF NOT EXISTS year_index INTO movies(year)");
        connection.createStatement().execute("CREATE INDEX IF NOT EXISTS duration_index INTO movies(duration)");
        connection.createStatement().execute("CREATE INDEX IF NOT EXISTS title_index INTO movies(title)");
    }
}
