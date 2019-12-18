import java.sql.*;
import java.io.*;
import java.util.*;

/**
 * A class that connects to PostgreSQL and disconnects.
 * You will need to change your credentials below, to match the usename and password of your account
 * in the PostgreSQL server.
 * The name of your database in the server is the same as your username.
 * You are asked to include code that tests the methods of the RestaurantApplication class
 * in a similar manner to the sample RunFilmsApplication.java program.
*/


public class RunRestaurantApplication
{
    public static void main(String[] args) {
    	
    	Connection connection = null;
    	try {
    	    //Register the driver
    		Class.forName("org.postgresql.Driver"); 
    	    // Make the connection.
            // You will need to fill in your real username (twice) and password for your
            // Postgres account in the arguments of the getConnection method below.
            connection = DriverManager.getConnection(
                                                     "jdbc:postgresql://cse180-db.lt.ucsc.edu/ragathwa",
                                                     "username",
                                                     "password");
            
            if (connection != null)
                System.out.println("Connected to the database!");

			RestaurantApplication app = new RestaurantApplication(connection);

            /* Include your code below to test the methods of the RestaurantApplication class
             * The sample code in RunFilmsApplication.java should be useful.
             * That code tests other methods for a different database schema.
             * Your code below: */
            
			int numMenuItemsOrdered = 65;
			List<Integer> items = app.getFrequentlyOrderedMenuItems(numMenuItemsOrdered);	
			System.out.println("Output of getFrequentlyOrderedMenuItems when the parameter numMenuItemsOrdered is " + numMenuItemsOrdered);
			System.out.println(items);
			/*
			* Output of getFrequentlyOrderedMenuItems
			* when the parameter numMenuItemsOrdered is 65.
			* [7, 1, 4, 2]
			*/

			int theServerID1 = 3;
			String newServerName1 = "Phileas Fogg";
			int result1 = app.updateServerName(theServerID1, newServerName1);
			System.out.println("Output of updateServerName when theServerID is " + theServerID1 +
			" and newServerName is " + newServerName1);
			System.out.println(result1);
			/*
			* Output of updateServerName when theServerID is 3
			* and newServerName is ‘Phileas Fogg’
			output here
			*/

			int theServerID2 = 10;
			String newServerName2 = "John Smith";
			int result2 = app.updateServerName(theServerID2, newServerName2);
			System.out.println("Output of updateServerName when theServerID is " + theServerID2 +
			" and newServerName is " + newServerName2);
			System.out.println(result2);
			/*
			* Output of updateServerName when theServerID is 10
			* and newServerName is ‘John Smith’
			output here
			*/

			int maxVisitCount1 = 10;
			int output1 = app.reduceSomeVisitCosts(maxVisitCount1);
			System.out.println("Output of reduceSomeVisitCosts when maxVisitCount is " + maxVisitCount1);
			System.out.println(output1);
			/*
			* Output of reduceSomeVisitCosts when maxVisitCount is 10
			output here
			*/

			int maxVisitCount2 = 95;
			int output2 = app.reduceSomeVisitCosts(maxVisitCount2);
			System.out.println("Output of reduceSomeVisitCosts when maxVisitCount is " + maxVisitCount2);
			System.out.println(output2);
			/*
			* Output of reduceSomeVisitCosts when maxVisitCount is 95
			output here
			*/

            /*******************
            * Your code ends here */
            
    	}
    	catch (SQLException | ClassNotFoundException e) {
    		System.out.println("Error while connecting to database: " + e);
    		e.printStackTrace();
    	}
    	finally {
    		if (connection != null) {
    			// Closing Connection
    			try {
					connection.close();
				} catch (SQLException e) {
					System.out.println("Failed to close connection: " + e);
					e.printStackTrace();
				}
    		}
    	}
    }
}
