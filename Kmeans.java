package kmeans;

/*==================================================================================
 KMEANS CLUSTERING ALGORITHM IMPLEMENTATION

 Required Data File Format:

 |----------|---------|-----------------|
 | X Cood.  | Y Cood. |  Other Info.    |
 |----------|---------|-----------------|

 Dependencies:

 OpenCSV: to read CSV files
 JavaFX: For GUI

 Tested on Dataset Size = 30,000
 Dimensions = 2
 Iterations: Self Configuring

 ===================================================================================*/
import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Scanner;

/* 
 Class definition for KMeans Algorithm
 */
public class Kmeans {

// Gloabal Variable
    public static float[][] array;
    public static int dataLen = 0;
    public static int clusters;
    public static float[][] centroids;
    public static float[] oldCentroids;
    public static String[] point_describe;
    public static String fileName;
    public static int cluster; //for menu
    public static int size;//for menu
    
    
// Constructor for developer mode    
    public Kmeans(int array_size, int clusters) {
        
        Kmeans.array = new float[array_size][3];
        Kmeans.centroids = new float[clusters][3];
        Kmeans.oldCentroids = new float[clusters];
        Kmeans.clusters = clusters;
        Kmeans.point_describe=new String[array_size];
       

    }

// Constructor defined for Auto Mode
    public Kmeans(int clust) {
        Kmeans.array = new float[16000][3];
        Kmeans.centroids = new float[clust][3];
        Kmeans.oldCentroids = new float[clust];
        Kmeans.clusters = clust;
        Kmeans.point_describe=new String[16000];
    }

    /* ----------------------------------------------------------------
    
     Method to read CSV file in an array.
     Input: CSV file path
     Output: An array with all the data entries of Spatial Database
    
     -------------------------------------------------------------------*/
    public static void read_csv(String file) throws FileNotFoundException, IOException {
        CSVReader reader = new CSVReader(new FileReader(file));
        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {

            // if (Float.parseFloat(nextLine[0]) > 0) {
            array[dataLen][0] = Float.parseFloat(nextLine[0]);
            array[dataLen][1] = Float.parseFloat(nextLine[1]);
            point_describe[dataLen]=nextLine[2];
            dataLen++;
            // }
        }
    }

    /* ----------------------------------------------------------------
    
     Method to select random cluster points and allocate every data entry 
     with a cluster based on Euclidean's Distance
     Input: Data Array
     Output: Data Array with initial cluster allocation
    
     -------------------------------------------------------------------*/
    public static void first_iteration() throws InterruptedException {
//        visual.run();

        int[] rand_points = new int[clusters];

        for (int y = 0; y < clusters; y++) {
            int rand = (int) (Math.random() * dataLen);
            rand_points[y] = rand;
        }

        int type = 0;
        float overall_min = 0;
        for (int u = 0; u < dataLen; u++) {
            type = 0;
            for (int y = 0; y < clusters; y++) {
                float local_min = find_dist(rand_points[y], u);
                if (y == 0) {
                    overall_min = local_min;
                }

                if (overall_min > local_min) {
                    overall_min = local_min;
                    type = y;

                }
            }
            array[u][2] = type;
        }

    }

    /* ----------------------------------------------------------------
    
     Method to calculate Euclidean distance between two points
     Input: 2 set of cooradinates
     Output: Distance between the points
    
     -------------------------------------------------------------------*/
    public static float find_dist(int centeroid, int other) {
        float dist = 0;

        // System.out.println(centeroid+" "+other);
        dist = (float) Math.sqrt(Math.pow((array[centeroid][0] - array[other][0]), 2) + Math.pow((array[centeroid][1] - array[other][1]), 2));

        return dist;

    }

    /* ----------------------------------------------------------------
    
     Method to calculate centroids of the clusters
     Input: Set of points in a cluster
     Output: Cluster Head (centeroid of the cluster "k")
    
     -------------------------------------------------------------------*/
    public static void calculate_centroid() {

        for (int p = 0; p < clusters; p++) {
            centroids[p][0] = (float) 0.0;
            centroids[p][1] = (float) 0.0;
            centroids[p][2] = (float) 0.0;

        }
        for (int y = 0; y < dataLen; y++) {

            centroids[(int) array[y][2]][0] = array[y][0] + centroids[(int) array[y][2]][0];
            centroids[(int) array[y][2]][1] = array[y][1] + centroids[(int) array[y][2]][1];
            centroids[(int) array[y][2]][2]++;

        }
        for (int u = 0; u < clusters; u++) {
            centroids[u][0] = centroids[u][0] / centroids[u][2];
            centroids[u][1] = centroids[u][1] / centroids[u][2];
        }
        for (int i = 0; i < clusters; i++) {
            System.out.println(centroids[i][0] + " " + centroids[i][1] + "  " + centroids[i][2]);
        }
        System.out.println();
    }

    /* ----------------------------------------------------------------
    
     Method to allocate cluster ID to all the data points based on the 
     euclidean distances from the different cluster heads  
     Input: Centroids and Data set
     Output: Cluster to which each data point belongs to.
    
     -------------------------------------------------------------------*/
    public static boolean iterations() {
        //isual.run();
        int clusterNo, newIndex;
        float distances[] = new float[clusters];

        calculate_centroid();
        int equal = 0;

        for (int i = 0; i < clusters; i++) {

            if (centroids[i][2] == oldCentroids[i]) {
                equal++;
            }

        }
        if (equal == clusters) {
            return false;
        }

        for (int u = 0; u < dataLen; u++) {

            //     clusterNo = (int) array[u][2];
            for (int k = 0; k < clusters; k++) {
                //   for(int y=0;y<centroids.length;y++)
                distances[k] = find_distance((float) array[u][0], (float) centroids[k][0], (float) array[u][1], (float) centroids[k][1]);
              //      System.out.println(distances[k]);

                //  }
            }
            newIndex = minIndex(distances);
            // if (newIndex != clusterNo) {
            array[u][2] = newIndex;
        }
        for (int i = 0; i < clusters; i++) {

            oldCentroids[i] = centroids[i][2];
        }

        return true;
    }

   /* ----------------------------------------------------------------
  Method to calculate Euclidean distance between two points
     Input: 2 set of cooradinates
     Output: Distance between the points
    
     -------------------------------------------------------------------*/
    public static float find_distance(float x1, float x2, float y1, float y2) {

        return (float) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));

    }

    /* Calculates minimm of the cluster to piint distances*/
    
    public static int minIndex(float[] dist) {

        int min = 0;
        for (int k = 1; k < dist.length; k++) {
            if (dist[k] < dist[min]) {
                min = k;
            }
        }

        return min;
    }

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException, InterruptedException {
int i=menu();
long startTime = System.nanoTime();
if(i==1){
        Kmeans algo = new Kmeans(4);
System.out.println("-------- Running in Auto Mode ---------");
        read_csv("C:\\Users\\Pranjal Mathur\\Desktop\\DATA.csv");
}


if(i==2){
    menu2();
    System.out.println("-------- Running in Auto Mode ---------");
    Kmeans algo=new Kmeans(size,cluster);
    read_csv(fileName);
    
}
   System.out.println("\nNumber of Locations points in the datafile: " + dataLen);
        first_iteration();

        while (iterations()) {
        };
        long endTime = System.nanoTime();

        Runtime runtime = Runtime.getRuntime();

        NumberFormat format = NumberFormat.getInstance();

        StringBuilder sb = new StringBuilder();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        System.out.println("============= PERFORMANCE MATRIX ===============\n\n \t See performance_matrix.txt");
        sb.append("Free memory: ").append(format.format(freeMemory / (1024*1024))).append(" MB \n");
        sb.append("Allocated memory: ").append(format.format(allocatedMemory / (1024*1024))).append(" MB\n");
        sb.append("Max memory: ").append(format.format(maxMemory / (1024*1024))).append(" MB");

         FileWriter write = new FileWriter("dataset.txt");
        PrintWriter fw = new PrintWriter(write);
        
        fw.print("Execution Time: "+(endTime - startTime) /  1000000000+" sec");
        fw.print(sb+"\n");
        System.out.println("================================================\n");
       // visual.run();
        NewFXMain.run();

    }
    
    static int menu(){
        
        System.out.println("=================   K-Means Clustering  ================\n \n   Choose one:\n  [1] Auto Mode\n  [2] Manual Mode\n");
        System.out.print("Enter your preference: ");
        Scanner i=new Scanner(System.in);
        int choice=i.nextInt();
        System.out.println("======================================================\n");
 return choice;
}
    static void menu2(){
        System.out.println("========== MANUAL MODE ==========\n");
        System.out.print("Enter the file path: ");
        Scanner i=new Scanner(System.in);
        fileName=i.nextLine();
        System.out.print("Enter the number of clusters: ");
        cluster=i.nextInt();
        System.out.print("Enter the size of data: ");
        size=i.nextInt();
          System.out.println("======================================================\n\n");
        
        
    }
}
