import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CaseDAO {

    public CaseDAO() {

    }

    String sqlQuerySelectAll = "SELECT " +
            AddresseeDB.COLUMN_CASE_ID + ", " +
            AddresseeDB.COLUMN_CASE_NUMBER + ", " +
            AddresseeDB.COLUMN_CASE_SOD_NUMBER + ", " +
            AddresseeDB.COLUMN_ADDRESSEE_ID +
            " FROM " +
            AddresseeDB.TABLE_CASE;


    public List<CaseModel> getFilteredCaseList(String queryString){
        List<CaseModel> caseList = new ArrayList<>();
        try {
            ExecuteSQL  dbExecuteSQL = new ExecuteSQL();
            ResultSet resultSet = dbExecuteSQL.executeSQL(queryString);
            while(resultSet.next()){
                CaseModel caseModel = new CaseModel();
                caseModel.setId(resultSet.getInt(1));
                caseModel.setCaseNo(resultSet.getString(2));
                caseModel.setSODNo(resultSet.getString(3));
                caseModel.setAddressee(resultSet.getString(4));
                caseList.add(caseModel);
            }
            dbExecuteSQL.closeConnection();
            return caseList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CaseModel> getCaseList(){
        List<CaseModel> caseList = new ArrayList<>();
        try {
            ExecuteSQL  dbExecuteSQL = new ExecuteSQL();
            ResultSet resultSet = dbExecuteSQL.executeSQL(sqlQuerySelectAll);
            while(resultSet.next()){
                CaseModel caseModel = new CaseModel();
                caseModel.setId(resultSet.getInt(1));
                caseModel.setCaseNo(resultSet.getString(2));
                caseModel.setSODNo(resultSet.getString(3));
                caseModel.setAddressee(resultSet.getString(4));
                caseList.add(caseModel);
            }
            dbExecuteSQL.closeConnection();
            return caseList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCase(CaseModel caseModel) {
        String sqlQuery = "UPDATE " + AddresseeDB.TABLE_CASE +
                " SET " + AddresseeDB.COLUMN_CASE_NUMBER + " = '" + caseModel.getCaseNo() + "', " +
                AddresseeDB.COLUMN_CASE_SOD_NUMBER + " = '" + caseModel.getSODNo() + "' " +
                " WHERE " + AddresseeDB.COLUMN_CASE_ID + " = " + caseModel.getId();

        ExecuteSQL  dbExecuteSQL = new ExecuteSQL();
        if(dbExecuteSQL.runSQL(sqlQuery)) return true;

        return false;
    }


    public boolean deleteCase(long caseID){
        String sqlDeleteAddressee = "DELETE FROM " +
                AddresseeDB.TABLE_CASE +
                " WHERE " + AddresseeDB.COLUMN_CASE_ID + " = " + caseID;
        ExecuteSQL  dbExecuteSQL = new ExecuteSQL();
        if(dbExecuteSQL.runSQL(sqlDeleteAddressee)) return true;

        return false;
    }

    public boolean insertCase(CaseModel caseModel) {

        String sqlQuery = "INSERT INTO " + AddresseeDB.TABLE_CASE + " (" +
                AddresseeDB.COLUMN_CASE_NUMBER + ", " +
                AddresseeDB.COLUMN_CASE_SOD_NUMBER + ", " +
                AddresseeDB.COLUMN_ADDRESSEE_ID + ") VALUES ('"
                + caseModel.getCaseNo() + "', '"
                + caseModel.getSODNo() + "', "
                + caseModel.getAddresse() + ");";

        ExecuteSQL  dbExecuteSQL = new ExecuteSQL();
        if(dbExecuteSQL.runSQL(sqlQuery)) return true;
        return false;
    }

    public CaseModel getCaseById(long Id)  {
        CaseModel caseModel = new CaseModel();
        String sqlQuery = sqlQuerySelectAll + " WHERE " + AddresseeDB.COLUMN_CASE_ID +  " = " + Id;
        ExecuteSQL  dbExecuteSQL = new ExecuteSQL();

        try {
            ResultSet resultSet = dbExecuteSQL.executeSQL(sqlQuery);

            caseModel.setId(resultSet.getInt(1));
            caseModel.setCaseNo(resultSet.getString(2));
            caseModel.setSODNo(resultSet.getString(3));
            caseModel.setAddressee(resultSet.getString(4));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        dbExecuteSQL.closeConnection();
        return caseModel;
    }





    public long getLastId(){

        String sqlQuery = "SELECT seq FROM sqlite_sequence WHERE NAME = '" + AddresseeDB.TABLE_CASE + "';";

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
}
