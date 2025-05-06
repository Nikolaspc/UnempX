package com.myorg.unempx.ui;

import com.myorg.unempx.service.DataProcessor;
import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Map;

public class UnemploymentChartPanel extends JPanel {
    private final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private final JFreeChart chart;

    public UnemploymentChartPanel() {
        chart = ChartFactory.createLineChart(
                "",
                "Period",
                "Unemployment Rate (%)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setDefaultToolTipGenerator(
                new StandardCategoryToolTipGenerator("{1}: {2}%", new DecimalFormat("0.00"))
        );
        plot.setRenderer(renderer);

        CategoryAxis xAxis = plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
        xAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 8));
        xAxis.setCategoryMargin(0.05);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1200, 600));
        add(chartPanel);
    }

    public void updateChart(String analysisType, DataProcessor processor) {
        dataset.clear();
        switch (analysisType) {
            case "Annual Rate":
                processor.getRecords().forEach(r ->
                        dataset.addValue(r.getAnnualAverage(), "Annual Rate", String.valueOf(r.getYear()))
                );
                chart.setTitle("Unemployment Rate by Year");
                break;

            case "Min/Max":
                Map<String, Object> minMax = processor.getMinMax();
                dataset.addValue((Double) minMax.get("minValue"), "Minimum", String.valueOf(minMax.get("minYear")));
                dataset.addValue((Double) minMax.get("maxValue"), "Maximum", String.valueOf(minMax.get("maxYear")));
                chart.setTitle("Historical Minimum and Maximum");
                break;

            case "Moving Average (12mo)":
                Map<String, Double> movingAvg = processor.getMovingAverageWithDates(12);
                movingAvg.forEach((period, avg) ->
                        dataset.addValue(avg, "Moving Average", period)
                );
                chart.setTitle("12-Month Moving Average");
                break;

            case "Top 3 Years":
                processor.getTopYears(3).forEach(record ->
                        dataset.addValue(record.getAnnualAverage(), "Top Years", String.valueOf(record.getYear()))
                );
                chart.setTitle("Top 3 Years with Highest Unemployment");
                break;
        }
        chart.fireChartChanged();
        repaint();
    }
}