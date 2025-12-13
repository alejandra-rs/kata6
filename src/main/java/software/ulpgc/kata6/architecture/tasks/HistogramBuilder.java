package software.ulpgc.kata6.architecture.tasks;

import software.ulpgc.kata6.architecture.viewmodel.Histogram;
import software.ulpgc.kata6.architecture.model.Movie;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class HistogramBuilder {

    private final Stream<Movie> movies;
    private final Map<String, String> labels;

    private HistogramBuilder(Stream<Movie> movies) {
        this.movies = movies;
        this.labels = new HashMap<>();
    }

    public static HistogramBuilder with(Stream<Movie> movies) {
        return new HistogramBuilder(movies);
    }

    public HistogramBuilder title(String title) {
        labels.put("title", title);
        return this;
    }

    public HistogramBuilder x(String x) {
        labels.put("x", x);
        return this;
    }

    public HistogramBuilder legend(String legend) {
        labels.put("legend", legend);
        return this;
    }

    public Histogram build(Function<Movie, Integer> binarize) {
        Histogram histogram = new Histogram(labels);
        movies.map(binarize).forEach(histogram::add);
        return histogram;
    }
}
