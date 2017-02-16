package kmeans;

import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;

public class Kmeans {

    public static float[][] arr = new float[16000][3];
    public static int dataLen = 0;
    public static int iterations = 100;
    public static int clusters = 3;
    public static float[][] centroids = new float[3][3];
    public static float[] oldCentroids = new float[3];

    public static void read_csv() throws FileNotFoundException, IOException {
        CSVReader reader = new CSVReader(new FileReader("C:\\Users\\Pranjal Mathur\\Desktop\\DATA.csv"));
        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {

           // if (Float.parseFloat(nextLine[0]) > 0) {
                arr[dataLen][0] = Float.parseFloat(nextLine[0]);
                arr[dataLen][1] = Float.parseFloat(nextLine[1]);
                dataLen++;
           // }
        }
    }

    public static void first_iteration() throws InterruptedException {
        visual.run();
        
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
            arr[u][2] = type;
        }

    }

    public static float find_dist(int centeroid, int other) {
        float dist = 0;

        // System.out.println(centeroid+" "+other);
        dist = (float) Math.sqrt(Math.pow((arr[centeroid][0] - arr[other][0]), 2) + Math.pow((arr[centeroid][1] - arr[other][1]), 2));

        return dist;

    }

    public static void calculate_centroid() {

        for (int p = 0; p < clusters; p++) {
            centroids[p][0] = (float) 0.0;
            centroids[p][1] = (float) 0.0;
            centroids[p][2] = (float) 0.0;

        }
        for (int y = 0; y < dataLen; y++) {

            centroids[(int) arr[y][2]][0] = arr[y][0] + centroids[(int) arr[y][2]][0];
            centroids[(int) arr[y][2]][1] = arr[y][1] + centroids[(int) arr[y][2]][1];
            centroids[(int) arr[y][2]][2]++;

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

    public static boolean iterations() {
        visual.run();
        int clusterNo, newIndex;
        float distances[] = new float[clusters];

        calculate_centroid();
        int equal = 0;

        for (int i = 0; i < clusters; i++) {

            if (centroids[i][2] == oldCentroids[i]) {
                equal++;
            }

        }
        if (equal == 3) {
            return false;
        }

        for (int u = 0; u < dataLen; u++) {

            //     clusterNo = (int) arr[u][2];
            for (int k = 0; k < clusters; k++) {
                //   for(int y=0;y<centroids.length;y++)
                distances[k] = find_distance((float) arr[u][0], (float) centroids[k][0], (float) arr[u][1], (float) centroids[k][1]);
              //      System.out.println(distances[k]);

                //  }
            }
            newIndex = maxIndex(distances);
            // if (newIndex != clusterNo) {
            arr[u][2] = newIndex;
        }
        for (int i = 0; i < clusters; i++) {

            oldCentroids[i] = centroids[i][2];
            }
        
        return true;
    }

    /*Find distance for other iterations*/
    public static float find_distance(float x1, float x2, float y1, float y2) {

        return (float) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));

    }

    public static int maxIndex(float[] dist) {

        int max = 0;
        for (int k = 1; k < dist.length; k++) {
            if (dist[k] < dist[max]) {
                max = k;
            }
        }

        return max;
    }

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException, InterruptedException {

        read_csv();
        
        System.out.println(dataLen);
        first_iteration();

        
          while(iterations()){
            Thread.sleep(1000);
                    
          };
       
        for (int k = 0; k < dataLen; k++) {
            System.out.println(arr[k][0] + "    " + arr[k][1] + "    " + arr[k][2]);
        }
        Runtime runtime = Runtime.getRuntime();

NumberFormat format = NumberFormat.getInstance();

StringBuilder sb = new StringBuilder();
long maxMemory = runtime.maxMemory();
long allocatedMemory = runtime.totalMemory();
long freeMemory = runtime.freeMemory();

sb.append("free memory: ").append(format.format(freeMemory / 1024)).append(" KB");
sb.append("allocated memory: ").append(format.format(allocatedMemory / 1024)).append(" KB");
sb.append("max memory: ").append(format.format(maxMemory / 1024)).append(" KB");
sb.append("total free memory: ").append(format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024)).append(" KB");
System.out.println(sb);
visual.run();
    }
   
}
