package computerSystem.database;

import computerSystem.Main;
import computerSystem.models.classes.Address;
import computerSystem.models.classes.Product;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.*;
import java.sql.*;
import javafx.embed.swing.SwingFXUtils;

@SuppressWarnings("Duplicates")
public class DatabaseInteraction {

    protected static java.sql.Statement getQuery() throws SQLException {
        java.sql.Connection SqlCon =  DriverManager.getConnection(DatabaseSchema.Connection.getDBNConnection());
        return SqlCon.createStatement();
    }

    public static class StoredProcedures {

        public static class Scalar{

            public static boolean isUsernameDuplicate(String _Username) {
                try(Statement query = getQuery()) {
                    int DupeCount = query.executeQuery("SELECT COUNT(*) Counted FROM tbAccounts WHERE Username = '" + _Username +"'").getInt("Counted");
                    if(DupeCount > 0) { return true; }
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    return true;
                }
                return false;
            }

            public static int getNextMaxProductID() {
                try(Statement query = getQuery()) {
                    return query.executeQuery("SELECT MAX(ProductID) MaxID FROM tbProducts").getInt("MaxID") + 1;
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    return 0;
                }
            }

            public static boolean isAddressDuplicate(computerSystem.models.classes.Address _Address) {
                try(Statement query = getQuery()) {
                    int DupeCount = query.executeQuery("SELECT COUNT(*) Counted FROM tbAddress WHERE AddressID = " + _Address.getAddressID() + ";").getInt("Counted");
                    if(DupeCount > 0) { return true; }
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    return true;
                }
                return false;
            }

            public static int getNextMaxAddressID() {
                try(Statement query = getQuery()) {
                    int DupeCount = query.executeQuery("SELECT MAX(AddressID) MaxID FROM tbAddress").getInt("MaxID");
                    return DupeCount + 1;
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    return 0;
                }
            }

            public static boolean isLoginCredentialsCorrect(String _Username, String _Password) {
                try(Statement query = getQuery()) {
                    int DupeCount = query.executeQuery("SELECT COUNT(*) Counted FROM tbAccounts WHERE Username = '" + _Username +"' AND Password = '" + _Password + "'").getInt("Counted");
                    if(DupeCount > 0) { return true; }
                }catch(SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                return false;
            }

        }

        public static class Tabular {

            public static void getUserDetails() {
                try(Statement query = getQuery()) {
                    ResultSet userAccountandClientResults = query.executeQuery(
                            "SELECT * " +
                            "FROM tbAccounts as AC, tbClients as CL " +
                            "WHERE AC.UserID = CL.UserID " +
                            "AND AC.Username = '" + Main.localUser.userAccount.getUsername() + "'; "
                    );

                    userAccountandClientResults.next();
                    System.out.println(userAccountandClientResults.getString("Username"));
                    System.out.println(userAccountandClientResults.getString("Email"));
                    System.out.println(userAccountandClientResults.getString("PhoneNumber"));
                    System.out.println(userAccountandClientResults.getString("Password"));
                    System.out.println(userAccountandClientResults.getBoolean("AccountType"));
                    System.out.println(userAccountandClientResults.getBoolean("CanContact"));
                    System.out.println(userAccountandClientResults.getString("Title"));
                    System.out.println(userAccountandClientResults.getString("Forename"));
                    System.out.println(userAccountandClientResults.getString("Surname"));

                    int UserID = userAccountandClientResults.getInt("UserID");

                    Main.localUser.userAccount.setUserID(userAccountandClientResults.getInt("UserID"));
                    Main.localUser.userAccount.setEmail(userAccountandClientResults.getString("Email"));
                    Main.localUser.userAccount.setPhone(userAccountandClientResults.getString("PhoneNumber"));
                    Main.localUser.userAccount.setAccountType(userAccountandClientResults.getBoolean("AccountType"));
                    Main.localUser.userAccount.setCanContact(userAccountandClientResults.getBoolean("CanContact"));

                    Main.localUser.userClient.setTitle(userAccountandClientResults.getString("Title"));
                    Main.localUser.userClient.setForename(userAccountandClientResults.getString("Forename"));
                    Main.localUser.userClient.setSurname(userAccountandClientResults.getString("Surname"));

                    //TODO - ISSUE WITH WHERE STATEMENT
                    ResultSet userAddressResults = query.executeQuery("SELECT * FROM tbAddress WHERE UserID = " + UserID + ";");
                    //ResultSet userAddressResults = query.executeQuery("SELECT * FROM tbAddress ;");

                    System.out.println("Column Count: " + userAddressResults.getMetaData().getColumnCount());

                    while(userAddressResults.next()) {
                        System.out.println(userAddressResults.getInt("AddressID") + userAddressResults.getInt("Postcode"));
                        Address addr = new Address();
                        addr.setAddressID(userAddressResults.getInt("AddressID"));
                        addr.setPostcode(userAddressResults.getString("Postcode"));
                        addr.setCountry(userAddressResults.getString("Country"));
                        addr.setTownCityRegion(userAddressResults.getString("TownCityRegion"));
                        addr.setHouseName(userAddressResults.getString("HouseName"));
                        addr.setBillingAddress(userAddressResults.getBoolean("BillingAddress"));
                        Main.localUser.userAddresses.add(addr);
                    }
                }catch(Exception ex) {
                    System.out.println("Exception Reached");
                    System.out.println(ex.getMessage());
                }
            }

            public static void getShopDetails() {
                try(Statement query = getQuery()) {
                    ResultSet productResults = query.executeQuery("SELECT * FROM tbProducts");
                    while(productResults.next()) {
                        System.out.println(productResults.getInt("ProductID") + productResults.getString("ProductName"));
                        Product product = new Product(
                                productResults.getInt("ProductID"),
                                productResults.getBoolean("ProductActive"),
                                productResults.getString("ProductName"),
                                productResults.getString("ProductDescription"),
                                productResults.getDouble("ProductBasePrice"),
                                productResults.getString("ProductCategory")
                        );

                        //Write to File
                        if(productResults.getBytes("ProductImage") != null){
                            OutputStream outputFile = new FileOutputStream("C:\\Users\\Callum\\Documents\\file.png");
                            outputFile.write(productResults.getBytes("ProductImage"));
                            outputFile.close();

                            File file = new File("C:\\Users\\Callum\\Documents\\file.png");
                            System.out.println(file.toURI().toString());
                            Image img = new Image(file.toURI().toString());
                            product.setProductImageFile(file);
                            product.setProductImage(img);
                        }

                        Main.localShop.localProductsList.add(product);
                    }
                    productResults.close();
                }catch(Exception ex) {
                    System.out.println("Exception Reached");
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }

        public static class NonQuery {

            public static boolean postNewAddress(Address _Address) {
                try(Statement query = getQuery()) {
                    System.out.println("Adding new address with the AddressID of" + _Address.getAddressID());
                    //Create primary key entry in tbAccounts
                    query.execute("INSERT INTO tbAddress (AddressID, UserID, Postcode, Country, TownCityRegion, HouseName, BillingAddress)" +
                            "VALUES(" +
                            "" + _Address.getAddressID() + "," +
                            "" + Main.localUser.userAccount.getUserID() + "," +
                            "'" + _Address.getPostcode() + "'," +
                            "'" + _Address.getCountry() + "'," +
                            "'" + _Address.getTownCityRegion() + "'," +
                            "'" + _Address.getHouseName() + "'," +
                            "" + _Address.getBillingAddress() + " " +
                            ")");
                    System.out.println("Address added!");
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    return false;
                }
                return true;
            }

            public static boolean updateAddress(Address _Address) {
                try(Statement query = getQuery()) {
                    System.out.println("Updating address with the AddressID of" + _Address.getAddressID());
                    //Create primary key entry in tbAccounts
                    query.execute("UPDATE tbAddress " +
                            "SET Postcode = '" + _Address.getPostcode() + "'," +
                            "Country = '" + _Address.getCountry() + "'," +
                            "TownCityRegion = '" + _Address.getTownCityRegion() + "'," +
                            "HouseName = '" + _Address.getHouseName() + "'," +
                            "BillingAddress = " + _Address.getBillingAddress() + " " +
                            "WHERE AddressID = " + _Address.getAddressID() + ";");
                    System.out.println("Address updated!");
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    return false;
                }
                return true;
            }

            public static boolean deleteAddress(int _AddressID) {
                try(Statement query = getQuery()) {
                    System.out.println("Deleting Address with the AddressID of " + _AddressID);
                    //Create primary key entry in tbAccounts
                    query.execute("DELETE FROM tbAddress WHERE AddressID = " + _AddressID + ";");
                    System.out.println("Deleted!");
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    return false;
                }
                return true;
            }

            public static boolean postNewProduct(Product _Product){
                try(Statement query = getQuery()) {
                    System.out.println("Adding new product with the ProductID of" + _Product.getProductID());
                    //Create primary key entry in tbAccounts
                    query.execute("INSERT INTO tbProducts (ProductID, ProductActive, ProductName, ProductDescription, ProductBasePrice, ProductCategory)" +
                            "VALUES(" +
                            "" + _Product.getProductID() + "," +
                            "" + _Product.getProductActive() + "," +
                            "'" + _Product.getProductName() + "'," +
                            "'" + _Product.getProductDescription() + "'," +
                            "" + _Product.getProductBasePrice() + "," +
                            "'" + _Product.getProductCategory() + "'" +
                            ")");
                    System.out.println("product added!");
                }catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    return false;
                }
                return true;
            }

            public static boolean updateProduct(Product _Product) {
                try(Statement query = getQuery()) {
                    System.out.println("Updating product with the ProductID of" + _Product.getProductID());

                    FileInputStream fis = new FileInputStream(_Product.getProductImageFile());
                    int fileLength = (int)_Product.getProductImageFile().length();

                    var conn = DriverManager.getConnection(DatabaseSchema.Connection.getDBNConnection());

                    PreparedStatement prep = conn.prepareStatement("UPDATE tbProducts " +
                            "SET ProductActive = " + _Product.getProductActive() + "," +
                            "ProductName = '" + _Product.getProductName() + "'," +
                            "ProductDescription = '" + _Product.getProductDescription() + "'," +
                            "ProductBasePrice = " + _Product.getProductBasePrice() + "," +
                            "ProductCategory = '" + _Product.getProductCategory() + "', " +
                            "ProductImage = ? " +
                            "WHERE ProductID = " + _Product.getProductID() + ";");
                    prep.setBinaryStream(1, fis, fileLength);

                    prep.executeUpdate();

                    conn.close();
                    System.out.println("Address updated!");
                }catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    return false;
                }
                return true;
            }

            public static boolean deleteProduct(Product _Product) {
                try(Statement query = getQuery()) {
                    System.out.println("Deleting Product with the ProductID of " + _Product.getProductID());
                    //Create primary key entry in tbAccounts
                    query.execute("DELETE FROM tbProducts WHERE ProductID = " + _Product.getProductID() + ";");
                    System.out.println("Deleted Product!");
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    return false;
                }
                return true;
            }

            public static boolean isPostNewUser(String _Username, String _Password, int _AccountType, String _Email, String _PhoneNumber, int _CanContact, String _Title, String _Forename, String _Surname) {
                try(Statement query = getQuery()) {
                    //Create primary key entry in tbAccounts

                    int usernameExists = query.executeQuery("SELECT COUNT(*) Counted FROM tbAccounts WHERE Username = '" + _Username + "'").getInt("Counted");
                    if(usernameExists == 0) {
                        query.execute("INSERT INTO tbAccounts (Username, Password, AccountType, Email, PhoneNumber, CanContact)" +
                                "VALUES(" +
                                "'" + _Username + "'," +            //Username
                                "'" + _Password + "'," +               //Password
                                "" + _AccountType + "," +         //Account Type
                                "'" + _Email + "'," +            //Email
                                "'" + _PhoneNumber + "'," +         //Phone Number
                                "'" + _CanContact + "'" +           //Can Contact
                                ")");

                        int userID = query.executeQuery("SELECT UserID FROM tbAccounts WHERE Username = '" + _Username + "'").getInt("UserID");

                        System.out.println("user Account created and returned the ID: " + userID);

                        //Create foregin key entry in tbClients
                        query.execute("INSERT INTO tbClients (UserID, Title, Forename, Surname)" +
                                "VALUES(" +
                                "'" + userID + "'," +            //Foregin User ID
                                "'" + _Title + "'," +               //Title
                                "'" + _Forename + "'," +            //Forename
                                "'" + _Surname + "'" +         //Surname
                                ")");

                        System.out.println("user Client information created using the ID: " + userID);
                    }
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    return false;
                }
                return true;
            }

        }
    }
}
