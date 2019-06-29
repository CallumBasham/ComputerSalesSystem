package computerSystem.database;

import computerSystem.Main;

import java.sql.*;

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
                    System.out.println(userAccountandClientResults.getString("Title"));
                    System.out.println(userAccountandClientResults.getString("Forename"));
                    System.out.println(userAccountandClientResults.getString("Surname"));

                    Main.localUser.userAccount.setEmail(userAccountandClientResults.getString("Email"));
                    Main.localUser.userAccount.setPhone(userAccountandClientResults.getString("PhoneNumber"));
                    Main.localUser.userAccount.setAccountType(userAccountandClientResults.getBoolean("AccountType"));
                }catch(SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        public static class NonQuery {

            public static boolean isPostNewUser(String _Username, String _Password, boolean _AccountType, String _Email, String _PhoneNumber, boolean _CanContact, String _Title, String _Forename, String _Surname) {
                try(Statement query = getQuery()) {
                    //Create primary key entry in tbAccounts
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

                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    return false;
                }
                return true;
            }

        }

    }

}
