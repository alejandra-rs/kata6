package software.ulpgc.kata6.architecture.io;

import software.ulpgc.kata6.architecture.model.Movie;

import java.util.stream.Stream;

public interface Recorder {
    void record(Stream<Movie> movies);
}
