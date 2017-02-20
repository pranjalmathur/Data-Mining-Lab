/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmeans;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import static kmeans.KMeans.array;
import static kmeans.KMeans.clusters;
import static kmeans.KMeans.dataLen;
import static kmeans.KMeans.point_describe;

/**
 *
 * @author Junaid
 */
public class Table {
    
    private String[] colNames;
    private Object[][] data;
    
    public Table(){
        
        intializeTable();
        JTable table = new JTable(data,colNames);
        
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
        
    }
    
    public void intializeTable(){
       
        colNames = new String[clusters];
        data = new Object[dataLen][clusters];
        
        
        for(int i = 1;i<=clusters;i++){
            colNames[i - 1] = "C " + i;
        }
        
        /*for(int i = 0;i<dataLen;i++){
            
            while(data[i].length < clusters){
                
                data[i][(int) array[j][2]] = (String) point_describe[j];
                j++;
            }
            
        }*/
        int k = 0;
        
        for(int i = 0;i<clusters;i++){
            k = 0;
            for(int j = 0;j<dataLen;j++){
                if(array[j][2] == i){
                    
                    data[k][i] = (String) point_describe[j];
                    k++;
                }
            }
        }
    }
}
