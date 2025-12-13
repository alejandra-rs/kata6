package software.ulpgc.kata6.app;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;

import static java.awt.Color.*;

public class LightPinkTheme {

    private final Plot plot;

    private LightPinkTheme(Plot plot) {
        this.plot = plot;
    }

    public static LightPinkTheme set(Plot plot) {
        return new LightPinkTheme(plot);
    }

    public JFreeChart forXYPlot() {
        XYPlot xyPlot = (XYPlot) plot;
        setBackgroundTheme(xyPlot);
        setForegroundTheme(xyPlot);
        return xyPlot.getChart();
    }

    private void setBackgroundTheme(XYPlot xyPlot) {
        xyPlot.setBackgroundPaint(WHITE);
    }

    private void setForegroundTheme(XYPlot xyPlot) {
        XYBarRenderer renderer = (XYBarRenderer) xyPlot.getRenderer();
        setBarColor(renderer);
        setBarBorder(renderer);
    }

    private void setBarColor(XYBarRenderer renderer) {
        renderer.setSeriesPaint(0, PINK);
    }

    private void setBarBorder(XYBarRenderer renderer) {
        renderer.setDrawBarOutline(true);
        renderer.setDefaultOutlinePaint(BLACK);
    }
}
