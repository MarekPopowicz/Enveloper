import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddresseeDAO {

    String sqlQuerySelectAll = "SELECT " +
            AddresseeDB.COLUMN_ADDRESSEE_ID + ", " +
            AddresseeDB.COLUMN_ADDRESSEE_FIRSTNAME + ", " +
            AddresseeDB.COLUMN_ADDRESSEE_LASTNAME + ", " +
            AddresseeDB.COLUMN_ADDRESSEE_ADDRESS + ", " +
            AddresseeDB.COLUMN_ADDRESSEE_ZIPCODE + ", " +
            AddresseeDB.COLUMN_ADDRESSEE_POST +
            " FROM " +
            AddresseeDB.TABLE_ADDRESSEE + " ORDER BY " + AddresseeDB.COLUMN_ADDRESSEE_FIRSTNAME + " ASC";

    public AddresseeModel getAddresseeById(long Id)  {
        AddresseeModel addressee = new AddresseeModel();
        String sqlQuery =  "SELECT " +
                AddresseeDB.COLUMN_ADDRESSEE_ID + ", " +
                AddresseeDB.COLUMN_ADDRESSEE_FIRSTNAME + ", " +
                AddresseeDB.COLUMN_ADDRESSEE_LASTNAME + ", " +
                AddresseeDB.COLUMN_ADDRESSEE_ADDRESS + ", " +
                AddresseeDB.COLUMN_ADDRESSEE_ZIPCODE + ", " +
                AddresseeDB.COLUMN_ADDRESSEE_POST +
                " FROM " +
                AddresseeDB.TABLE_ADDRESSEE + " WHERE " + AddresseeDB.COLUMN_ADDRESSEE_ID +  " = " + Id;
        ExecuteSQL  dbExecuteSQL = new ExecuteSQL();

        try {
            ResultSet resultSet = dbExecuteSQL.executeSQL(sqlQuery);

            addressee.setId(resultSet.getInt(1));
            addressee.setFirstname(resultSet.getString(2));
            addressee.setLastname(resultSet.getString(3));
            addressee.setAddress(resultSet.getString(4));
            addressee.setZipcode(resultSet.getString(5));
            addressee.setPost(resultSet.getString(6));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        dbExecuteSQL.closeConnection();
        return addressee;
    }

    public List<AddresseeModel> getAddresseeList(){

        List<AddresseeModel> addresseeList = new ArrayList<>();

        try {
            ExecuteSQL  dbExecuteSQL = new ExecuteSQL();
            ResultSet resultSet = dbExecuteSQL.executeSQL(sqlQuerySelectAll);

            while(resultSet.next()){
                AddresseeModel addressee = new AddresseeModel();
                addressee.setId(resultSet.getInt(1));
                addressee.setFirstname(resultSet.getString(2));
                addressee.setLastname(resultSet.getString(3));
                addressee.setAddress(resultSet.getString(4));
                addressee.setZipcode(resultSet.getString(5));
                addressee.setPost(resultSet.getString(6));
                addresseeList.add(addressee);
            }
            dbExecuteSQL.closeConnection();
            return addresseeList;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateAddressee(AddresseeModel addressee) {
        String sqlQuery = "UPDATE " + AddresseeDB.TABLE_ADDRESSEE +
                " SET " + AddresseeDB.COLUMN_ADDRESSEE_FIRSTNAME + " = '" + addressee.getFirstname() + "', " +
                AddresseeDB.COLUMN_ADDRESSEE_LASTNAME + " = '" + addressee.getLastname() + "', " +
                AddresseeDB.COLUMN_ADDRESSEE_ADDRESS + " = '" + addressee.getAddress() + "', " +
                AddresseeDB.COLUMN_ADDRESSEE_ZIPCODE + " = '" + addressee.getZipcode() + "', " +
                AddresseeDB.COLUMN_ADDRESSEE_POST + " = '" + addressee.getPost() + "'" +
                " WHERE " + AddresseeDB.COLUMN_ADDRESSEE_ID + " = " + addressee.getId();

        ExecuteSQL  dbExecuteSQL = new ExecuteSQL();
        if(dbExecuteSQL.runSQL(sqlQuery)) return true;

        return false;
    }

    public boolean deleteAddressee(long Id) {

        String sqlDeleteAddressee = "DELETE FROM " +
                AddresseeDB.TABLE_ADDRESSEE +
                " WHERE " + AddresseeDB.COLUMN_ADDRESSEE_ID + " = " + Id;
        ExecuteSQL  dbExecuteSQL = new ExecuteSQL();
        if(dbExecuteSQL.runSQL(sqlDeleteAddressee)) return true;

        return false;
    }

    public boolean insertAddressee(AddresseeModel addressee) {

        String sqlQuery = "INSERT INTO " + AddresseeDB.TABLE_ADDRESSEE + " (" +
                AddresseeDB.COLUMN_ADDRESSEE_FIRSTNAME + ", " +
                AddresseeDB.COLUMN_ADDRESSEE_LASTNAME + ", " +
                AddresseeDB.COLUMN_ADDRESSEE_ADDRESS + ", " +
                AddresseeDB.COLUMN_ADDRESSEE_ZIPCODE + ", " +
                AddresseeDB.COLUMN_ADDRESSEE_POST + ") VALUES ('"
                + addressee.getFirstname() + "', '"
                + addressee.getLastname() + "', '"
                + addressee.getAddress() + "', '"
                + addressee.getZipcode() + "', '"
                + addressee.getPost() + "');";

        ExecuteSQL  dbExecuteSQL = new ExecuteSQL();
        if(dbExecuteSQL.runSQL(sqlQuery)) return true;
        return false;
    }

    public long getLastId(){

        String sqlQuery = "SELECT seq FROM sqlite_sequence WHERE NAME = '" + AddresseeDB.TABLE_ADDRESSEE + "';";

        long result = 0;

        try {
            ExecuteSQL dbExecuteSQL = new ExecuteSQL();
            ResultSet r = dbExecuteSQL.executeSQL(sqlQuery);
            result =  r.getLong(1);
            dbExecuteSQL.closeConnection();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public AddresseeDAO() {

    }
}
