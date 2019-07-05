package computerSystem.database;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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
        public static String getDBNConnection() { return databaseConnection; }

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

    protected static class Schema {

        static class tbAccounts {
            static String tbSchema = "CREATE TABLE IF NOT EXISTS tbAccounts" +
                    "(" +
                    "UserID INTEGER PRIMARY KEY," +
                    "Username VARCHAR(50) UNIQUE," +
                    "Password VARCHAR(30)," +
                    "AccountType BIT," +
                    "Email VARCHAR(50)," +
                    "PhoneNumber VARCHAR(25)," +
                    "Picture VARCHAR(200)," +
                    "CanContact BIT" +
                    ");";
            protected static String getSchema() { return tbSchema; }
        }

        static class tbClients{
            static String tbSchema = "CREATE TABLE IF NOT EXISTS tbClients" +
                    "(" +
                    //Foreign Key
                    "UserID INTEGER REFERENCES tbAccounts(UserID)," +
                    "Title VARCHAR(20)," +
                    "Forename VARCHAR(50)," +
                    "Surname VARCHAR(50)" +
                    ");";
            protected static String getSchema() { return tbSchema; }
        }

        static class tbAddress{
            static String tbSchema = "CREATE TABLE IF NOT EXISTS tbAddress" +
                    "(" +
                    //Primary Key
                    "AddressID INTEGER PRIMARY KEY," +
                    //Foreign Key
                    "UserID INTEGER REFERENCES tbAccounts(UserID)," +
                    //"CountryID INTEGER REFERENCES lkCountry(CountryID)," +

                    // Personal Address
                    "Postcode VARCHAR(12)," +
                    "Country VARCHAR(50)," +
                    "TownCityRegion STRING," +
                    "HouseName VARCHAR(50)," +
                    "BillingAddress BIT" +
                    ");";
            protected static String getSchema() { return tbSchema; }
        }

        static class tbCards{
            static String tbSchema = "CREATE TABLE IF NOT EXISTS tbCards" +
                    "(" +
                    //Foreign Key
                    "UserID INTEGER REFERENCES tbAccounts(UserID)," +
                    //Personal Address
                    "CardNumber VARCHAR(20)," + //Encrypted
                    "ExpiryDate DATE" +

                    ");";
            protected static String getSchema() { return tbSchema; }
        }

        //RETIRED DUE TO TIME REMAINING
        /*static class lkCountry{
            static String tbSchema = "CREATE TABLE IF NOT EXISTS lkCountry" +
                    "(" +
                    //Primary Key
                    "CountryID INTEGER PRIMARY KEY," +
                    //Values
                    "CountryName VARCHAR(100)" +
                    ");";
            protected static String getSchema() { return tbSchema; }
        }*/

        static class tbProducts {

            static String tbSchema = "CREATE TABLE IF NOT EXISTS tbProducts" +
                    "(" +
                    //Primary Key
                    "ProductID INTEGER PRIMARY KEY," +
                    "ProductActive BIT," +
                    "ProductName VARCHAR(50)," + //Cosshair, Samsung                                                    Custom PC
                    "ProductImage BLOB," +
                    "ProductDescription VARCHAR(50)," +//Ram used to store memory
                    "ProductBasePrice DOUBLE," + //£120.50
                    "ProductCategory VARCHAR(50)" + //e.g. RAM                                                           CustomPC
                    ");";
            protected static String getSchema() { return tbSchema; }
        }

        /*static class tbProductExtentions {
            static String tbSchema = "CREATE TABLE IF NOT EXISTS tbProductExtension" +
                    "(" +
                    //Primary Key
                    "ExtensionID INTEGER PRIMARY KEY," +
                    "ExtensionActive BIT," +
                    "ExtensionName VARCHAR(50)," + //4 GB, 8GB                  1 Year full cover
                    "ExtensionDescription VARCHAR(50)," +                       //this covers this that and the other
                    "ExtensionPrice DOUBLE," +//£24.50                          £10
                    "ProductCategory VARCHAR(50) REFERENCES tbProducts(ProductCategory)," +//e.g. as above, RAM        RAM                                                     Custom PC
                    "ExtensionCategory VARCHAR(50)" +//Memory Size,            Warrenty
                    ");";
            protected static String getSchema() { return tbSchema; }
        }*/

        static class tbOrders {
            static String tbSchema = "CREATE TABLE IF NOT EXISTS tbOrders" +
                    "(" +
                    //Primary Key
                    "OrderID INTEGER PRIMARY KEY," +
                    //Foreign Key
                    "ProductID INTEGER REFERENCES tbProducts(ProductID)," +
                    "UserID INTEGER REFERENCES tbAccounts(UserID)," +

                    "OrderQuantity INTEGER," +
                    "OrderTotalPrice DOUBLE," +
                    "OrderExtras VARCHAR(4000)" +
                    ");";
            protected static String getSchema() { return tbSchema; }
        }

        /*static class tbOrderExtensions {
            static String tbSchema = "CREATE TABLE IF NOT EXISTS tbOrderExtensions" +
                    "(" +
                    //Primary Key
                    "OrderExtensionID INTEGER PRIMARY KEY," +
                    //Foreign Key
                    "ExtensionID INTEGER REFERENCES tbProductExtension(ExtensionID)" +
                    ");";
            protected static String getSchema() { return tbSchema; }
        }*/

    }

    public static void setupDatabase() {
        //So we can correctly setup the database, monitoring for errors
        boolean sucess;
        //            //Create the databasse file itself
        sucess = initializeDatabase();
        //Create the default tables we require within the database
        if(sucess) { sucess = initializeTables(); }
        //Create the default values required in the tables above for the application to run
        if(sucess) { sucess = initializeDefaultRecords(); }
        //On Success of all of the above, report to user ELSE try and resolve...
        if(sucess) {
            //TODO - Inform user of the default admin account!!
        } else {
            //TODO - Inform users of failure! suggest corrective measures?
        }
    }

    private static boolean initializeDatabase() {
        System.out.println("-[ Creating Database ]--");
        try(java.sql.Connection conn = DriverManager.getConnection(Connection.getDBNConnection())) {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("\t>>> The driver name is " + meta.getDriverName());
            System.out.println("\t>>> Database initialized: " + meta.getConnection().toString());
            System.out.println("\t>>> Database initialized: " + meta.getURL());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    private static boolean initializeTables() {
        System.out.println("-[ Creating Tables ]--");
        try(java.sql.Connection conn = DriverManager.getConnection(Connection.getDBNConnection()); Statement query = conn.createStatement()) {

            //Create Country Lookup Table if not Exists
            //uery.execute(Schema.lkCountry.getSchema());
            //System.out.println("\t>>> lkCountry has been created!");

            //Create Accounts Table if not Exists
            query.execute(Schema.tbAccounts.getSchema());
            System.out.println("\t>>> tbAccounts has been created!");

            //Create Client Table if not Exists
            query.execute(Schema.tbClients.getSchema());
            System.out.println("\t>>> tbClients has been created!");

            //Create Address Table if not Exists
            query.execute(Schema.tbAddress.getSchema());
            System.out.println("\t>>> tbAddress has been created!");

            //Create Cards Table if not Exists
            query.execute(Schema.tbCards.getSchema());
            System.out.println("\t>>> tbCards has been created!");

            //Create Cards Table if not Exists
            query.execute(Schema.tbProducts.getSchema());
            System.out.println("\t>>> tbProducts has been created!");

            //Create Cards Table if not Exists
            //query.execute(Schema.tbProductExtentions.getSchema());
            //System.out.println("\t>>> tbProductExtentions has been created!");

            //Create Cards Table if not Exists
            query.execute(Schema.tbOrders.getSchema());
            System.out.println("\t>>> tbOrders has been created!");

            //Create Cards Table if not Exists
            //query.execute(Schema.tbOrderExtensions.getSchema());
            //System.out.println("\t>>> tbOrderExtentions has been created!");


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    private static boolean initializeDefaultRecords() {
        System.out.println("-[ Creating Default Dataset ]--");
        try(Statement query = DatabaseInteraction.getQuery()) {
            //Create the default admin account if one does not already exist!!
            if (query.executeQuery("SELECT COUNT(*) RowCount FROM tbAccounts WHERE AccountType = 0").getInt("RowCount") < 1) {
                System.out.println("\t>>> tbAccounts Admin Account does not yet exist, creating now....");
                query.close();
                DatabaseInteraction.StoredProcedures.NonQuery.isPostNewUser("Admin", "Password", 1, "Admin@ComputerSales.com", "07557676680", 1, "Mr", "Admin", "ComputerSales");
                System.out.println("\t>>> tbAccounts Admin Account has been created!");
            }
        }catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }

        /*try(Statement query = DatabaseInteraction.getQuery()) {
            //Populate the lkCountries lookup table
            if(query.executeQuery("SELECT COUNT(*) RowCount FROM lkCountry").getInt("RowCount") < 236) {
                System.out.println("\t>>> lkCountries requires the list of countries imported, creating now....");

                NodeList xmlNodes = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().parse(DatabaseSchema.class.getResourceAsStream("/computerSystem/database/dataSources/Countries.xml")).getDocumentElement().getChildNodes();
                for(int i = 0; i < xmlNodes.getLength(); i++) {
                    if(xmlNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {

                        System.out.println(String.format("\t\t>>> XML Node (%1$s): Country Name: {%2$s}\tCountry Code: {%3$s}\tLocal Country Name:{%4$s}\tISO: {%5$s}",
                                i,
                                xmlNodes.item(i).getTextContent(),
                                xmlNodes.item(i).getAttributes().item(0).getTextContent(),
                                xmlNodes.item(i).getAttributes().item(2).getTextContent(),
                                xmlNodes.item(i).getAttributes().item(3).getTextContent()
                        ));

                        query.execute("INSERT INTO lkCountry (CountryName)" +
                                "VALUES(" +
                                "'" + xmlNodes.item(i).getTextContent().replace("'", "''") + "'" + //CountryName
                                ");");
                    }

                }

                System.out.println("\t>>> lkCountries has now been populated!");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (SAXException e) {
            e.printStackTrace();
            return false;
        }*/
        return true;
    }

}