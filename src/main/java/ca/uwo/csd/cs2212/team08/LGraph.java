package ca.uwo.csd.cs2212.team08;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Stephane on 2016-03-17.
 */
public class LGraph extends JPanel {



    public LGraph(String title,String yAxis, LinkedList list){

        TimeSeries timeSeriesDataSet = createDataset(list);

        /**
         * Add all the series to the dataset
         */

        TimeSeriesCollection dataSet = new TimeSeriesCollection();

        dataSet.addSeries(timeSeriesDataSet);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                title,
                "Date",
                yAxis,
                dataSet,
                false,
                false,
                false
        );

        chart.setBackgroundPaint(Color.lightGray);

        setLayout(new BorderLayout());

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.WHITE);

        chartPanel.setDisplayToolTips(true);
        chartPanel.setMouseZoomable(true);
        chartPanel.setZoomAroundAnchor(true);



        /**
         * Further customization of the graph
         */

        final XYPlot plot = chart.getXYPlot();

        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);


        add(chartPanel, BorderLayout.CENTER);

    }


    /**
     * Method will loop through the Linkedlist and store the data into a database
     * @param list
     * @return
     */
    private TimeSeries createDataset(LinkedList<TimeSeriesNode> list) {
        TimeSeries series = new TimeSeries("Per Minute Data", Minute.class);
        int hours;
        int minutes;
        float value;
        int day, month, year;


        for(int i=0; i < list.size(); i ++){
            hours = Integer.parseInt(list.get(i).getHour());
            minutes = Integer.parseInt(list.get(i).getMinute());
            value = Float.parseFloat(list.get(i).getValue());
            year = Integer.parseInt(list.get(i).getYear());
            day = Integer.parseInt(list.get(i).getDay());
            month = Integer.parseInt(list.get(i).getMonth());		
            /**
             * TODO add proper minutes, dates...
             */
            series.addOrUpdate(new Minute( minutes, hours, day, month, year), value);

        }
        return series;


    }





}
