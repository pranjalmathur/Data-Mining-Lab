package association;

import java.io.*;
import java.util.*;

public class Association {

    static public int[][] ArrData = new int[20000][100];
    static public int[] support;
    static public int[] oneass = new int[5];
    static final int support_define = 3;
    static public int[] alpha = new int[5];
    static public int[][] subsets;

//main function
    public static void main(String[] args) throws IOException {
      //  menu();
        readData();
        int j = one_itemset();
        multi_itemset(j, 2);

    }

//function to read the transaction data in the Arraylist
    public static void readData() throws IOException {
        BufferedReader dataBR = new BufferedReader(new FileReader(new File("C:\\Users\\Pranjal Mathur\\Desktop\\nrldata.txt")));
        String line = "";
        int o, m = 0;

        while ((line = dataBR.readLine()) != null) {

            int[] club = new int[9];

            String[] value = line.split(",", 9);
            for (int p = 0; p < value.length; p++) {
                o = Integer.parseInt(value[p]);
                System.out.println(o);
                ArrData[m][p] = o;
                System.out.println("Row=" + m + " Coumn=" + p + ": " + o);
            }
            m++;

        }
        System.out.println(arr_row());
        System.out.println(arr_col(1));

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
//to extract the one item set from the given set of transaction

    public static int one_itemset() {
        int m = 0;
        for (int i = 0; i < arr_row(); i++) {
            for (int x = 0; x < arr_col(i); x++) {

                if (ArrData[i][x] != 0) {
                    oneass[ArrData[i][x] - 1]++;
                }

            }
        }
        for (int k = 0; k < oneass.length; k++) {
            if (oneass[k] >= support_define) {

                System.out.println("Item " + (k + 1) + " appears " + oneass[k] + " times");
                alpha[m] = k + 1;
                m++;
            } else {
                oneass[k] = -1;
            }
        }
        System.out.println(m);
        return m;

    }

// to calculate factorial
    public static int calc_fac(long n) {
        if (n <= 1) {
            return 1;
        } else {
            return (int) (n * calc_fac(n - 1));
        }
    }

//to extract the multi item set from the given set of transaction
    public static void multi_itemset(int setsize, int subsetsize) {
        int[] binary = new int[(int) Math.pow(2, setsize)];

        int u = 0, v = 0, p = 0;
        //calculate total combinations possible of the set of items
        int total_ss = calc_fac(setsize) / (calc_fac(setsize - subsetsize) * calc_fac(subsetsize));
        System.out.println(total_ss);
        subsets = new int[total_ss][subsetsize];
        //array

        int[] support = new int[total_ss];
        for (int i = 0; i < Math.pow(2, setsize); i++) {

            int b = 1;

            binary[i] = 0;
            int num = i, count = 0;
            while (num > 0) {
                if (num % 2 == 1) {
                    count++;
                }
                binary[i] += (num % 2) * b;
                num /= 2;
                b = b * 10;
            }
            if (count == subsetsize) {
                System.out.print("{ ");
                for (int j = 0; j < setsize; j++) {
                    if (binary[i] % 10 == 1) {
                        System.out.print(alpha[j] + " ");
                        if (v < subsetsize) {
                            subsets[u][v] = alpha[j];
                            v++;
                        }
                    }
                    binary[i] /= 10;

                }
                System.out.println("}");
                for (int h = 0; h<arr_row(); h++) {
                    p = 0;
                    for (int z = 0; z < v; z++) {
                        if (contain(ArrData[h],subsets[u][z])) {
                            p++;
                        }
                    }
                    if (p == subsetsize) {
                        support[u]++;
                    }
                }
                System.out.println(support[u]);
                u++;
                v = 0;
            }
        }
    }
    
   static boolean contain(int [] arr, int value){
        for(int p=0;p<arr.length;p++)
            if(arr[p]==value)
                return true;
       
        return false;
    }
static void menu(){
    
    System.out.println("\t\t\tAPRIORI ASSOCIATION RULE MINING\n\n");
    System.out.println("1. Auto Test Mode\n >> 21Lk pool of items\n >> About 30K transactions\n >> Support value=10\n\n");
    System.out.println("2. Manual Mode\n >> You will be asked to enter size of pool of items\n >> No of distinct items\n >> Support value\n\n");
    
    Scanner reader = new Scanner(System.in);  // Reading from System.in
System.out.println("Enter your selection: ");
int n = reader.nextInt();

if(n==1){
    
}
else if(n==2){
    System.out.println("\n\n\n MANUAL MODE");
    
    
}
    
}

}
