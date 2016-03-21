package ca.uwo.csd.cs2212.team08;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class PGraph extends JPanel {


    public PGraph(APIData data){

        float fairlyActiveMin = data.getFairlyActiveMin();
        float lightlyActiveMin = data.getLightlyActiveMin();
        float sedentaryMinutes = data.getSendentaryMinutes();
        float veryActive = data.getVeryActiveMin();

        float total = fairlyActiveMin + lightlyActiveMin + sedentaryMinutes + veryActive;

        float fairlyActiveMinPer = fairlyActiveMin / total * 100.0f;
        float lightlyActiveMinPer = lightlyActiveMin / total * 100.0f;
        float sedentaryMinPer = sedentaryMinutes / total * 100.0f;
        float veryActiveMinPerc = veryActive / total * 100.0f;


        setLayout(new BorderLayout());
        DefaultPieDataset dataSet = new DefaultPieDataset();
        dataSet.setValue("Sedentary " + (int)sedentaryMinPer + "%", sedentaryMinPer);
        dataSet.setValue("Fairly Active " + (int)fairlyActiveMinPer + "%", fairlyActiveMinPer);
        dataSet.setValue("Very Active " + (int)veryActiveMinPerc + "%", veryActiveMinPerc);
        dataSet.setValue("Lightly Active " + (int)lightlyActiveMinPer + "%", lightlyActiveMinPer);



        JFreeChart chart = ChartFactory.createPieChart3D("Minutes", dataSet,false,false, false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();

        plot.setForegroundAlpha(0.6f);
        plot.setCircular(true);

        add(new ChartPanel(chart), BorderLayout.CENTER);

        setPreferredSize(new Dimension(600,500));




    }


}
