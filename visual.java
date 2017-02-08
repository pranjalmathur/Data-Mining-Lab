
package apriori;

import static apriori.Apriori.set;
import static apriori.Apriori.support;
import java.awt.Color;
import java.awt.Paint;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public class visual extends ApplicationFrame{
    
     static Object[][] rowData = new Object[support.length][2]; // Object to create table
     static int len = support.length; 

     /*---------------------Method to generate graph and table -----------------------*/
     
     private static void sortArrays(int[] arr, int[][] arr2){
         
         int temp;
         int[] tempArray;
         //int max = support[0];
         for(int i = 0;i<arr.length;i++){
             for(int j = 0;j<arr.length;j++){
                 if(arr[j] < arr[i] ){
                     
                     //switching 1d Array
                     temp = arr[i];
                     arr[i] = arr[j];
                     arr[j] = temp;
                     
                     //Switching 2D array
                     tempArray = arr2[i];
                     arr2[i] =  arr2[j];
                     arr2[j] = tempArray;
                 }
                 
                 
                 
                 
                 
             }
         }
     }
    
    public static void run() {
        
        String[] coloumnNames = {"ItemSet","Frequency"};
        
        sortArrays(support,set);
       
        
        if(len > 10){
            len = 10;
        }
        
       /*convert set to object for table*/
        
        
        for(int k = 0;k<len;k++){
            
          
            rowData[k][1] = Integer.toString(support[k]);
            rowData[k][0] = Arrays.toString(set[k]);
            
           
            
        }
        
        
        /*Creating the JTable*/
        JTable table = new JTable(rowData,coloumnNames);
        
        JFrame frame = new JFrame();
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.setVisible(true);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);       
  
                    //center align
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.setAutoCreateRowSorter(true);
        
      /*Creating Chart*/ 
      
      visual chart = new visual("Aproiry Results", "Output");
      chart.pack( );    
      chart.setBackground(Color.yellow);
      chart.setExtendedState(ApplicationFrame.WIDTH);
      RefineryUtilities.centerFrameOnScreen( chart );        
      chart.setVisible( true ); 
            
    }
    
    /*----------------Constructor used to create the chart on applicationFrame------------------*/

   public visual( String applicationTitle , String chartTitle )
   {
      super( applicationTitle );        
      JFreeChart barChart = ChartFactory.createBarChart( chartTitle, "ItemSets", "Frequency",            
         createDataset(),          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
      /*Size of the Chart Panel*/
      ChartPanel chartPanel = new ChartPanel( barChart );        
      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
      setContentPane( chartPanel ); 
      
      /*Setting width of the bar*/
      CategoryPlot categoryPlot = barChart.getCategoryPlot();
      BarRenderer br = (BarRenderer) categoryPlot.getRenderer();
      br.setMaximumBarWidth(0.05);
      
      //categoryPlot.setBackgroundPaint(new Paint() {});
   }
   
   
   /*------------Method to generate dataset----------------*/
   private CategoryDataset createDataset( )
   {
      final String itemSet = "itemSet";        
            
             
      final DefaultCategoryDataset setset = new DefaultCategoryDataset( ); 
      
      for(int i = 0;i<len;i++){
          setset.addValue( support[i] , (String) rowData[i][0], "" ); 
      }
       

                  

      return setset; 
   }
    
}
