

public class DBCreate  {

    private final ExecuteSQL executeSQL;

    public DBCreate()  {
        this.executeSQL = new ExecuteSQL();
        executeSQL.runSQL(AddresseeDB.SQL_CREATE_TABLE_ADDRESSEE);
        executeSQL.runSQL(AddresseeDB.SQL_CREATE_TABLE_CASE);

        if(isTableEmpty(AddresseeDB.TABLE_ADDRESSEE ))
            executeSQL.runSQL(AddresseeDB.SQL_INSERT_ADDRESSEE);
    }

    private boolean isTableEmpty(String tableName ) {
        return executeSQL.tableRowCount(tableName) <= 0;
    }
}



