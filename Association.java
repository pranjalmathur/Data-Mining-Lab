/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package association;

 import java.io.*;
import java.util.*;

public class    Association {
   static public ArrayList<String[]> dataArr = new ArrayList<>();
   static public int []oneass=new int[5];
   static final int support=3;
   static public char [] alpha=new char[5];
   

    public static void main(String[] args) throws IOException {
        readData();  
        oneas();
         multiass(9, 2);
         
    }

    public static void readData() throws IOException{
        BufferedReader dataBR = new BufferedReader(new FileReader(new File("C:\\Users\\Pranjal Mathur\\Desktop\\nrldata.txt")));
        String line = "";
        int o;

         //An ArrayList is used because I don't know how many records are in the file.

        while ((line = dataBR.readLine()) != null) { // Read a single line from the file until there are no more lines to read

            String[] club = new String[9]; // Each club has 3 fields, so we need room for the 3 tokens.

           String[] value = line.split(",", 9);                
int n = Math.min(value.length, club.length);
            System.arraycopy(value, 0, club, 0, n); // For each token in the line that we've read:
            // Place the token into the 'i'th "column"

            dataArr.add(club); // Add the "club" info to the list of clubs.
        }

 }
    public static void oneas(){
        for (int i = 0; i < dataArr.size(); i++) {
            for (int x = 1; x < dataArr.get(i).length; x++) {
             //   System.out.printf("dataArr[%d][%d]: ", i, x);
                if(dataArr.get(i)[x]!=null)
                oneass[((int)dataArr.get(i)[x].charAt(0))%97]++;
                
            }
        }
        for(int k=0;k<oneass.length;k++)
             
            if(oneass[k]>=support)
                
            System.out.println("Item "+ (char)(k+97) +" appears "+oneass[k]+" times");
            
            
            else
                oneass[k]=-1;
    }


    public static void multiass(int setsize, int subsetsize){
        int[] binary = new int[(int) Math.pow(2, setsize)];
        for (int i = 0; i < Math.pow(2, setsize); i++) 
        {
            int b = 1;
            binary[i] = 0;
            int num = i, count = 0;
            while (num > 0) 
            {
                if (num % 2 == 1)
                    count++;
                binary[i] += (num % 2) * b;
                num /= 2;
                b = b * 10;
            }
            if (count == subsetsize) 
            {
                System.out.print("{ ");
                for (int j = 0; j < setsize; j++) 
                {
                    if (binary[i] % 10 == 1)
                        System.out.print(oneass[j] + " ");
                    binary[i] /= 10;
                }
                System.out.println("}");
            }
        }

}
}