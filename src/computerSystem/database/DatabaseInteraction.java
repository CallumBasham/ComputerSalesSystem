package computerSystem.database;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

        }

        public static class Tabular {

        }

        public static class NonQuery {

            public static boolean isPostNewUser(String _Username, String _Email, String _PhoneNumber, String _Password, int _AccountType) {
                try(Statement query = getQuery()) {
                    query.execute("INSERT INTO tbAccounts (Username, Email, PhoneNumber, Password, AccountType)" +
                            "VALUES(" +
                            "'" + _Username + "'," +            //Username
                            "'" + _Email + "'," +               //Email
                            "'" + _PhoneNumber + "'," +         //Phone
                            "'" + _Password + "'," +            //Password
                            "" + _AccountType + "" +           //AccountType
                            ")");
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    return false;
                }
                return true;
            }

        }

    }

}
