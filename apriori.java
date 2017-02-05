/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Pranjal Mathur
 */
public class Apriori {

    static public int[][] ArrData = new int[15000][7];
    static public int[] one_set = new int[100];
    static public int[] alpha = new int[100];
    static final int support_define = 800;
    static public int[][] subsets;
    static public int[] support;
    static int set_size = 3;
    static public int first_choice = 0;
    static List<Integer> superSet = new ArrayList<>();
    static List<Set<Integer>> res = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void readData() throws IOException {
        BufferedReader dataBR = new BufferedReader(new FileReader(new File("C:\\Users\\Pranjal Mathur\\Desktop\\nrldata.txt")));
        String line = "";
        int o, m = 0;

        while ((line = dataBR.readLine()) != null) {
            String[] value = line.split(",", 100);
            for (int p = 0; p < value.length; p++) {
                o = Integer.parseInt(value[p]);
                //    System.out.println(o);
                ArrData[m][p] = o;
                // System.out.println("Row=" + m + " Coumn=" + p + ": " + o);
            }
            m++;

        }
        // System.out.println(arr_row());
        //System.out.println(arr_col(1));

    }

    static int arr_row() {
        int i;
        for (i = 0; i < ArrData.length; i++) {
            if (ArrData[i][0] == 0) {
                break;
            }
        }
        return i;
    }

    static int arr_col(int row) {
        int i;
        for (i = 0; i < ArrData[row].length; i++) {
            if (ArrData[row][i] == 0) {
                break;
            }
        }
        return i;
    }

    public static int one_itemset() {
        int m = 0;
        for (int i = 0; i < arr_row(); i++) {
            for (int x = 0; x < arr_col(i); x++) {

                if (ArrData[i][x] != 0) {
                    one_set[ArrData[i][x] - 1]++;
                }

            }
        }
        for (int k = 0; k < one_set.length; k++) {
            if (one_set[k] >= support_define) {

                System.out.println("Item " + (k + 1) + " appears " + one_set[k] + " times");
                alpha[m] = k + 1;
                System.out.println("Alpha=" + alpha[m]);
                m++;
            } else {
                one_set[k] = -1;
            }
        }
        //  System.out.println(m);
        return m;

    }

    private static void getSubsets(List<Integer> superSet, int k, int idx, Set<Integer> current, List<Set<Integer>> solution) {
        //successful stop clause
        if (current.size() == k) {
            solution.add(new HashSet<>(current));
            return;
        }
        //unseccessful stop clause
        if (idx == superSet.size()) {
            return;
        }
        Integer x = superSet.get(idx);
        current.add(x);
        //"guess" x is in the subset
        getSubsets(superSet, k, idx + 1, current, solution);
        //System.out.println(superSet);
        //   System.out.println(solution);

        current.remove(x);
        //"guess" x is not in the subset
        getSubsets(superSet, k, idx + 1, current, solution);
        //  System.out.println(solution);
    }

    public static List<Set<Integer>> getSubsets(List<Integer> superSet, int k) {

        getSubsets(superSet, k, 0, new HashSet<Integer>(), res);
        return res;
    }

    static boolean contain(int[] arr, int value) {
        for (int p = 0; p < arr.length; p++) {
            if (arr[p] == value) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        readData();
        int p=0;
        int m = one_itemset();
        for (int i = 0; i < m; i++) {
            superSet.add(alpha[i]);
        }
        List<Set<Integer>> res = getSubsets(superSet, set_size);
        int[][] set = new int[res.size()][set_size];
        support=new int[res.size()];

        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < set_size; j++) {
                set[i][j] = (int) res.get(i).toArray()[j];
            }
            for (int h = 0; h < arr_row(); h++) {
                p = 0;
                for (int z = 0; z < set_size; z++) {
                    if (contain(ArrData[h], set[i][z])) {
                        p++;
                    }
                }
                if (p == set_size) {
                    support[i]++;
                }
            }
            p=0;
        }
        print();
    }
    
    public static void print(){
        for(int i=0;i<res.size();i++){
            System.out.println(res.get(i)+ " "+ support[i]);
        }
    }

}
