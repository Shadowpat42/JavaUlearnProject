package org.example.visualization;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.Map;

public class ChartBuilder {
    public static void createChart(Map<String, Double> data, String title) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Internet Users %", entry.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                title,
                "Subregion",
                "Percentage",
                dataset
        );

        JFrame frame = new JFrame();
        frame.setContentPane(new ChartPanel(barChart));
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
