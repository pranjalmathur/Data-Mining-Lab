# Lab Assignment 2
>Junaid Tinwala `1410110177`
>Pranjal Mathur `1410110296`
>Prerna `1410110306`
### **K- Means Clustering Algorithm Implementation**
##### Data Mining and Data Warehousing.
####
####
###

This implementation of K means clustering algorithm in Data Mining has:

 - The algorithm implemented in Java
 - Visualization using JavaFX Scatter plot
 - Visualization of the results using JTable and JavaFX libraries - Pie Chart
  - Reads dataset from a CSV file using openCSV library
 
with the following limitations:

 - Specific data format `[X coordinate, Y coordinate, Realted Data]`
 - Clusters only 2 dimentional data

and added functionalities of:
 - Algorithm Performance matrix in a file: `performance_kmeans.txt` 
 - Centroids (Cluster Head information) for each iteration.

##### K Means algorithm implementaion
###### Kmeans.java
#
>`Inputs`: Longitudinal and Latitudinal dimentions of the locations in a *.csv file*
>`Outputs`: 2D array (cluster number for each data point), Text File1 (Performance Report), Text File2 (Cluster Head information)
#
The implementation runs in 2 modes:  
  - **Auto Mode**: Takes predefined data set and runs K Means algorithm with parameters as:
       - Clusters = 4
       - Number of iterations = Optimised as per the dataset
       - Requires file path
#
**Example Inputs**
>`Enter the file path`: file_name.csv
#
*This mode shows the final results in terms of a Scatter plot, Table of Clusters and a Pie Chart (proportion of the clusters).*
   - **Manual Mode**: Requires the following information:

       - File path: Absolute path
       - Number of clusters to be formed
       - Number of datapoints  
#
**Example Inputs**
>`Enter the file path`: file_name.csv
>`Enter the number of clusters`: 4
>`Enter the size of the data`: 10000

#
This mode shows the final results in terms of a Scatter plot, Table of Clusters and a Pie Chart (proportion of the clusters) along with the process of clustering.

**Example Input Table:**

| X coordinate        | Y coordinate  |     
| ------------- |:-------------:|
| 23.5   | 90.8 |
| 89.0  | 81.90      |
| -67.9 |    34.87   |

**Example Output Table**
 | X coordinate        | Y coordinate  |  Cluster number|   
| ------------- |:-------------:| :-------------:|
| 23.5   | 90.8 | 2
| 89.0  | 81.90      |1
| -67.9 |    34.87   |3
##### Visualization of the process of clustering
###### Visual.java
#
>`Inputs`: 2D array (cluster number for each data point) - from `Kmeans.java`
>`Outputs`: Scatter Plot with each iteration
#
It takes the results from `the Kmeans.java` and provides process illustartion in the form of:
* Scatter Plot with diferent colors for different clusters.

![alt text](https://onlinecourses.science.psu.edu/stat857/sites/onlinecourses.science.psu.edu.stat857/files/lesson06/k-means_result.gif "Logo Title Text 1")


##### Visualization of the final results
###### visual2.java
#
>`Inputs`: Final 2D array (cluster number for each data point) - from `Kmeans.java`
>`Outputs`: Pie Chart (with propotion of data pints in each cluster) and Table
#
**Example Output**

![alt text](http://robslink.com/SAS/democd7/pie1.png "Logo Title Text 1")

