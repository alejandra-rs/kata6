package software.ulpgc.kata6.app;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import software.ulpgc.kata6.architecture.io.Store;
import software.ulpgc.kata6.architecture.viewmodel.Histogram;
import software.ulpgc.kata6.architecture.model.Movie;
import software.ulpgc.kata6.architecture.tasks.HistogramBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Stream;

public class Desktop extends JFrame {

    private final Store store;

    private Desktop(Store store) {
        this.store = store;
        this.setTitle("Histogram Display");
        this.setSize(800, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static Desktop with(Store store) {
        return new Desktop(store);
    }

    public Desktop display() {
        display(histogramOf(movies()));
        return this;
    }

    private Stream<Movie> movies() {
        return store.movies()
                .filter(m -> m.year() >= 1900)
                .filter(m -> m.year() <= 2025);
    }

    private static Histogram histogramOf(Stream<Movie> movies) {
        return HistogramBuilder.with(movies)
                .title("Movie count by year")
                .x("Year")
                .legend("Movie count")
                .build(Movie::year);
    }

    private void display(Histogram histogram) {
        this.getContentPane().add(displayOf(histogram));
        this.revalidate();
    }

    private Component displayOf(Histogram histogram) {
        return new ChartPanel(decorate(chartOf(histogram)));
    }

    private JFreeChart decorate(JFreeChart jFreeChart) {
        return LightPinkTheme.set(jFreeChart.getPlot()).forXYPlot();
    }

    private JFreeChart chartOf(Histogram histogram) {
        return ChartFactory.createHistogram(
                histogram.title(),
                histogram.x(),
                "Count",
                datasetOf(histogram)
        );
    }

    private XYSeriesCollection datasetOf(Histogram histogram) {
        XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(seriesOf(histogram));
        return collection;
    }

    private XYSeries seriesOf(Histogram histogram) {
        XYSeries series = new XYSeries(histogram.legend());
        for (int bin : histogram) {
            series.add(bin, histogram.count(bin));
        }
        return series;
    }
}
