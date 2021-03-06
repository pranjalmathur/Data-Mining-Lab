/*
 Data mining and warehousing
 Graded Lab Assignment 1 
 Authors:
 Junaid Tinwala 
 Pranjal Mathur
 Prerna 

 */
package datasetgenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author dell pc
 */
public class DatasetGenerator {

    private Random rnd = new Random(0);
    private static int size;
    private static double skew;
    private static int numOfTransactions;
    private double bottom = 0;

    public DatasetGenerator(int size, double skew) {
        this.size = size;
        this.skew = skew;

        for (int i = 1; i <= size; i++) {
            this.bottom += (1 / Math.pow(i, this.skew));
        }
    }

    public double getProbability(int rank) {
        return (1.0d / Math.pow(rank, this.skew)) / this.bottom;
    }

    public static void main(String[] args) throws IOException {

        FileWriter write = new FileWriter("dataset.txt");
        PrintWriter fw = new PrintWriter(write);

        if (args.length != 3) {
            size = 100;
            skew = 0.5;
            numOfTransactions = 10000;
        } else {
            size = Integer.parseInt(args[0]);
            skew = Double.parseDouble(args[1]);
            numOfTransactions = Integer.parseInt(args[2]);
        }

        DatasetGenerator zipf = new DatasetGenerator(100, 0.5);
        ArrayList<Integer> array_of_items = new ArrayList<Integer>();
        int cumm_prob = 0;
        for (int i = 1; i <= 100; i++) {
            //This returns me the probability of each item in my dataset
            cumm_prob += Math.round(zipf.getProbability(i) * 100);
            array_of_items.add(cumm_prob);
        }
        int itemsetsize = 0;
        int k, trans_item;
        boolean flag;

        //generates k-sized itemsets with k between 1 and 7
        Set<Integer> transaction = new HashSet<Integer>();

        for (int j = 0; j < 15000; j++) {
            itemsetsize = (int) ((Math.round(Math.random() * 100) % 7) + 1);
            k = 1;
            while (k <= itemsetsize) {
                trans_item = (int) Math.round(Math.random() * 100);
                for (int c = 0; c < 100; c++) {
                    if ((array_of_items.get(c)) >= trans_item) {
                        flag = transaction.add(c + 1);
                        if (flag == true) {
                            k++;
                        }
                        break;
                    }
                }
            }

            Iterator it = transaction.iterator();
            while (it.hasNext()) {
                fw.print(it.next() + "");
                if (it.hasNext()) {
                    fw.print(",");
                }
            }
            transaction.clear();
            fw.println();
        }
        fw.close();
    }
}
