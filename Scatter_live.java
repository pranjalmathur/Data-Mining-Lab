/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scatter_live;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 *
 * @author dell pc
 */
public class Scatter_live extends Application {

    private static final int MAX_DATA_POINTS = 50;
    static FileReader fr;
    static BufferedReader br;
    private ConcurrentLinkedQueue<Number> dataQ1 = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Number> dataQ2 = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Number> dataQ3 = new ConcurrentLinkedQueue<>();
    private ExecutorService executor;
    int xSeriesData = 0;
    XYChart.Series series1 = new XYChart.Series();
    XYChart.Series series2 = new XYChart.Series();
    XYChart.Series series3 = new XYChart.Series();
    NumberAxis xAxis, yAxis;
    ScatterChart<Number, Number> lineChart;

    @Override
    public void start(Stage stage) throws Exception {
        fr = new FileReader("input.txt");
        br = new BufferedReader(fr);
        stage.setTitle("Scatter Chart Sample");
        xAxis = new NumberAxis(0, 10, 1);
        yAxis = new NumberAxis(0, 10, 1);
        lineChart = new ScatterChart<Number, Number>(xAxis, yAxis);
        xAxis.setLabel("Age (years)");
        yAxis.setLabel("Returns to date");
        lineChart.setAnimated(false);
        lineChart.setTitle("Animated Line Chart");
        lineChart.setHorizontalGridLinesVisible(true);

        if (lineChart.getData() == null) {
            lineChart.setData(FXCollections.<XYChart.Series<Number, Number>>observableArrayList());
        }

        final ScatterChart.Series<Number, Number> series = new ScatterChart.Series<Number, Number>();
        final ScatterChart.Series<Number, Number> seriesb = new ScatterChart.Series<Number, Number>();
        final ScatterChart.Series<Number, Number> seriesc = new ScatterChart.Series<Number, Number>();

        series.setName("Option " + (lineChart.getData().size() + 1));
        seriesb.setName("Something");
        seriesc.setName("C series");
        for (int i = 0; i < 3; i++) {
            series.getData().add(new ScatterChart.Data<Number, Number>(0, 0));
            seriesb.getData().add(new ScatterChart.Data<Number, Number>(0, 0));
            seriesc.getData().add(new ScatterChart.Data<Number, Number>(0, 0));

        }

        lineChart.getData().add(seriesb);
        lineChart.getData().add(series);
        lineChart.getData().add(seriesc);

        Scene scene = new Scene(lineChart, 500, 400);
        scene.getStylesheets().add("scatter_live/Chart.css");
        stage.setScene(scene);
        stage.show();

        executor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });

        AddToQueue addToQueue = new AddToQueue();
        executor.execute(addToQueue);
        prepareTimeline();

    }

    private class AddToQueue implements Runnable {

        String input;

        public void run() {
            try {
                input = br.readLine();

            } catch (IOException ex) {
                Logger.getLogger(Scatter_live.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (input != null) {
                System.out.println(input);
                if (input.endsWith("" + 1)) {
                    System.out.println(input.charAt(0));
                    System.out.println(input.charAt(2));
                    dataQ1.add(Integer.parseInt("" + input.charAt(0)));
                    dataQ1.add(Integer.parseInt("" + input.charAt(2)));
                } else if (input.endsWith("" + 2)) {
                    dataQ2.add((Number) Integer.parseInt("" + input.charAt(0)));
                    dataQ2.add((Number) Integer.parseInt("" + input.charAt(2)));
                } else if (input.endsWith("" + 3)) {
                    dataQ3.add((Number) Integer.parseInt("" + input.charAt(0)));
                    dataQ3.add((Number) Integer.parseInt("" + input.charAt(2)));

                } else {

                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Scatter_live.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            executor.execute(this);
        }
    }

    private void prepareTimeline() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                addDataToSeries();
            }
        }.start();
    }

    private void addDataToSeries() {
        for (int i = 0; i < 1; i++) { //-- add 20 numbers to the plot+
            //System.out.println("Adding Values to Series");
            //lineChart.getData().removeAll(series1, series2, series3);
            lineChart.setLegendVisible(false);
            
            //lineChart.getData().removeAll(series1,series2,series3);
            if (!dataQ1.isEmpty()) {
                series1.getData().remove(0, series1.getData().size());
                series1.getData().add(new XYChart.Data<>(dataQ1.remove(), (Number) dataQ1.remove()));
                lineChart.getData().remove(series1);
                lineChart.getData().add(series1);
                //lineChart.getData().add(series2);
                //lineChart.getData().add(series3);
                
                System.out.println("1=" + series1.getData());

            }
            if (!dataQ2.isEmpty()) {
                series2.getData().remove(0, series2.getData().size());
                series2.getData().add(new XYChart.Data<>(dataQ2.remove(), (Number) dataQ2.remove()));
                lineChart.getData().remove(series2);
                //lineChart.getData().add(series1);
                lineChart.getData().add(series2);
                //lineChart.getData().add(series3);
                System.out.println("2=" + series2.getData());

            }
            if (!dataQ3.isEmpty()) {
                series3.getData().remove(0, series3.getData().size());
                series3.getData().add(new XYChart.Data<>(dataQ3.remove(), (Number) dataQ3.remove()));
                if(series3.getData().contains(series1.getData())){
                System.out.println("Found a substring");
                }
                lineChart.getData().remove(series3);
                
                //lineChart.getData().add(series1);
                //lineChart.getData().add(series2);
                lineChart.getData().add(series3);
                System.out.println("3=" + series3.getData());

            } else {
                //System.exit(0);
            }

        }
        // remove points to keep us at no more than MAX_DATA_POINTS
        if (series1.getData().size() > MAX_DATA_POINTS) {
            series1.getData().remove(0, series1.getData().size() - MAX_DATA_POINTS);
        }
        if (series2.getData().size() > MAX_DATA_POINTS) {
            series2.getData().remove(0, series2.getData().size() - MAX_DATA_POINTS);
        }
        if (series3.getData().size() > MAX_DATA_POINTS) {
            series3.getData().remove(0, series3.getData().size() - MAX_DATA_POINTS);
        }

        // update
//        xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
        //      xAxis.setUpperBound(xSeriesData - 1);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
