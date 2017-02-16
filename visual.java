package kmeans;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.*;
import javax.swing.JPanel;
import static kmeans.Kmeans.arr;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.ShapeUtilities;

public class visual extends ApplicationFrame {

    public visual(String s) {
        super(s);
        JPanel jpanel = createDemoPanel();
        jpanel.setPreferredSize(new Dimension(500, 500));
      //  jpanel.setSize(null);
        setContentPane(jpanel);
    }

    public static JPanel createDemoPanel() {

        JFreeChart jfreechart = ChartFactory.createScatterPlot("Scatter Plot Demo",
            "X", "Y", samplexydataset2(), PlotOrientation.VERTICAL, true, true, false);
       Shape cross = new Ellipse2D.Double(10,10,100,100);
      

        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        XYItemRenderer renderer = xyPlot.getRenderer();
       
        renderer.setBasePaint(Color.red);
        

renderer = xyPlot.getRenderer();
renderer.setSeriesShape(5, cross);
        //changing the Renderer to XYDotRenderer
        //xyPlot.setRenderer(new XYDotRenderer());
        XYDotRenderer xydotrenderer = new XYDotRenderer();
        xyPlot.setRenderer(xydotrenderer);
       

       // xyPlot.setDomainCrosshairVisible(true);
       // xyPlot.setRangeCrosshairVisible(true);

        return new ChartPanel(jfreechart);
    }

    private static XYDataset samplexydataset2() {
        int cols = 20;
        int rows = 20;
        double[][] values = new double[cols][rows];

        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        XYSeries series = new XYSeries("Random");
        XYSeries series1 = new XYSeries("Random2");
        XYSeries series2 = new XYSeries("Random3");
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
           if(arr[i][2]==0.0){
                double x = arr[i][0];
                double y = arr[i][1];
               

                series.add(x, y);
           }
              
        }
        Random rand2 = new Random();
        for (int i = 0; i < arr.length; i++) {
           if(arr[i][2]==1.0){
                double x = arr[i][0];
                double y = arr[i][1];
               

                series1.add(x, y);
           }
       
        
    }
        Random rand3 = new Random();
         for (int i = 0; i < arr.length; i++) {
           if(arr[i][2]==2.0){
                double x = arr[i][0];
                double y = arr[i][1];
               

                series2.add(x, y);
           }
       
        
    }
         xySeriesCollection.addSeries(series);
         xySeriesCollection.addSeries(series1);
         xySeriesCollection.addSeries(series2);
        return xySeriesCollection;
    }
    

    public static void run() {
        visual scatterplotdemo4 = new visual("Scatter Plot Demo 4");
        scatterplotdemo4.pack();
        RefineryUtilities.centerFrameOnScreen(scatterplotdemo4);
        scatterplotdemo4.setVisible(true);
    }
}