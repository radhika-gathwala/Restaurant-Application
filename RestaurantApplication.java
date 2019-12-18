import java.sql.*;
import java.util.*;

/**
 * The class implements methods of the Restaurant database interface.
 *
 * All methods of the class receive a Connection object through which all
 * communication to the database should be performed. Note: the
 * Connection object should not be closed by any method.
 *
 * Also, no method should throw any exceptions. In particular, in case
 * an error occurs in the database, then the method should print an
 * error message and call System.exit(-1);
 */

public class RestaurantApplication {

    private Connection connection;

    /*
     * Constructor
     */
    public RestaurantApplication(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection()
    {
        return connection;
    }

    /**
     * getFrequentlyOrderedMenuItems has an integer argument called numMenuItemsOrdered,
     * and returns the menuItemID for each menuItem where the total quantity (adding up quantity 
     * across all billEntry tuples) of that menuItem is greater than or equal to numMenuItemsOrdered.  
     * A value of numMenuItemsOrdered that’s not positive is an error.
     */

    public List<Integer> getFrequentlyOrderedMenuItems(int numMenuItemsOrdered)
    {
        List<Integer> result = new ArrayList<Integer>();
        // your code here

        // print error if numMenuItemsOrdered is less than 0
        if (numMenuItemsOrdered < 0){
            System.out.print("Error: numMenuItemsOrdered is less than 0");
            System.exit(0);
        }
        try{
            // create statement
            PreparedStatement stat = connection.prepareStatement("SELECT b.menuItemID " +
            "FROM billEntry b, menuItem m " + 
            "WHERE b.menuItemID = m.menuItemID GROUP BY b.menuItemID "+
            "HAVING SUM(b.quantity) >= "+numMenuItemsOrdered+"");

            // execute query & store
            ResultSet menuItems = stat.executeQuery();
            while(menuItems.next()){
            // get menuItemID
            int quant = menuItems.getInt(1);
            // add to output arraylist
            result.add(quant);
}
        } catch(SQLException e){
            System.out.print("SQLException: " + e);
        }
        
        // end of your code
        return result;
    }


    /**
     * updateServerName method has two arguments, an integer argument, theServerID, and a string 
     * argument, newServerName.  For the tuple in the server table (if any) whose serverID equals 
     * theServerID, updateServerName should update name to be newServerName.  (Note that there may 
     * not be any tuples whose serverID matches theServerID.)  
     * updateServerName should return the number of tuples that were updated, which will always be 0 or 1.
     */

    public int updateServerName(int theServerID, String newServerName)
    {
        int res = 0;
        // your code here; return 0 appears for now to allow this skeleton to compile.
        try{

            // create query string & statement
            String serverQ = "UPDATE server SET name = '"+newServerName+"' WHERE serverID = '"+theServerID+"'";
            
            Statement stat2 = connection.createStatement();
            // execute statment, variable res will update to 1 if server table is updated
            res = stat2.executeUpdate(serverQ);
        } catch(SQLException e){
            System.out.print("SQLException: " + e);
        }
        return res;
        // end of your code
    }


    /**
     * reduceSomeVisitCosts has an integer parameters, maxVisitCount.  It invokes a stored function
     * reduceSomeCostsFunction that you will need to implement and store in the database according to the
     * description in Section 5.  reduceSomeCostsFunction should have the same parameters, maxVisitCount.
     * A value of maxVisitCount that’s not positive is an error.
     *
     * The visits table has a cost attribute.  reduceSomeCostsFunction will reduce the cost for some 
     * (but not necessarily all) visits; Section 5 explains which visits should have their cost reduced, 
     * and also tells you how much they should be reduced.  The reduceSomeVisitCosts method should return 
     * the same integer result that the reduceSomeCostsFunction stored function returns.
     *
     * The reduceSomeVisitCosts method must only invoke the stored function reduceSomeCostsFunction, which
     * does all of the assignment work; do not implement the reduceSomeVisitCosts method using a bunch
     * of SQL statements through JDBC.
     */

    public int reduceSomeVisitCosts (int maxVisitCount)
    {
        int total = 0;
        // There's nothing special about the name storedFunctionResult
        try{
            // your code here

            // create query string & statement
            String visitQ = "SELECT reduceSomeCostsFunction("+maxVisitCount+")";

            Statement stat3 = connection.createStatement();
            ResultSet visits = stat3.executeQuery(visitQ);
            // get visits
            visits.next();
            // visits resultSet will have 1 value
            total = visits.getInt(1);
            visits.close();
        }catch(SQLException e){
            System.out.print("SQLException: " + e);
        }
        // end of your code
        return total;

    }

};
