package software.ulpgc.kata6.architecture.view;

import software.ulpgc.kata6.architecture.model.Movie;

import java.util.stream.Stream;

public interface Query {
    Stream<Movie> moviesReleasedBetween(int startYear, int endYear);
    Stream<Movie> moviesWithDurationBetween(int min, int max);
    Stream<Movie> moviesWithStartingLetterBetween(char start, char end);
}
