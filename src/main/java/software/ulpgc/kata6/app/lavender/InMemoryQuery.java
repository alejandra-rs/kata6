package software.ulpgc.kata6.app.lavender;

import software.ulpgc.kata6.architecture.io.Store;
import software.ulpgc.kata6.architecture.model.Movie;
import software.ulpgc.kata6.architecture.view.Query;

import java.util.stream.Stream;

public class InMemoryQuery implements Query {

    private final Store store;

    public InMemoryQuery(Store store) {
        this.store = store;
    }

    @Override
    public Stream<Movie> moviesReleasedBetween(int startYear, int endYear) {
        return store.movies()
                .filter(m -> m.year() >= startYear)
                .filter(m -> m.year() <= endYear);
    }

    @Override
    public Stream<Movie> moviesWithDurationBetween(int min, int max) {
        return store.movies()
                .filter(m -> m.duration() >= min)
                .filter(m -> m.duration() <= max);
    }

    @Override
    public Stream<Movie> moviesWithStartingLetterBetween(char start, char end) {
        return store.movies()
                .filter(m -> m.name().charAt(0) >= start)
                .filter(m -> m.name().charAt(0) <= end);
    }
}
