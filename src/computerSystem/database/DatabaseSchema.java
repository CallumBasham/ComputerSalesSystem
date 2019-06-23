package computerSystem.database;
import java.sql.*;


public class DatabaseSchema {

    public static class Connection {
        //The JDBC connection string start
        private static String databaseDriver = "jdbc:sqlite:";
        private static String getDriver() { return databaseDriver; }

        //Name of the embedded database file
        private static String databaseName = "ComputerSales.DB";
        private static String getName() { return databaseName; }

        //The directory of the database file
        private static String databasePath = System.getProperty("user.dir") + "\\" + getName();
        private static String getPath() { return databasePath; }

        private static String databaseConnection = getDriver() + getPath();
        private static String getDBNConnection() { return databaseConnection; }

        public static Boolean testConnection() {
            System.out.println(getDBNConnection());
            try(java.sql.Connection DBCon = DriverManager.getConnection(getDBNConnection())) {
                DatabaseMetaData DBMeta = ((java.sql.Connection) DBCon).getMetaData();
                System.out.println("Connection to Database successful!");
            }
            catch(SQLException ex) {
                System.out.println(ex.getMessage());
                return false;
            }
            return true;
        }
    }

    protected class Schema {

    }

    protected class DataSources {

    }

    public void SetupDatabase() {

    }

}