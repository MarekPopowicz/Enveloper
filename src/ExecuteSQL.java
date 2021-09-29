import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExecuteSQL extends DBConnect{
    private Connection conn;
    private Statement statement;

    public ExecuteSQL()  {
        createConnection();
    }

    private void createConnection(){
        try {
            this.conn = connect();
            this.statement = conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ResultSet executeSQL(String sql) throws SQLException {
        try {
            if(conn.isClosed() && statement.isClosed()){
                createConnection();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    public boolean runSQL(String sql)  {
        try {
            if(conn.isClosed() && statement.isClosed()){
                createConnection();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        boolean succeed = false;
        try {
            statement.execute(sql);
            succeed = true;

        } catch (SQLException e) {

            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeConnection();
            return succeed;
        }

    }

    public void closeConnection() {
        try {
            conn.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int tableRowCount(String tableName) {
        String sqlQuery = "SELECT COUNT(*) FROM " + tableName;
        int recordCount = 0;
        try {
            ResultSet r = executeSQL(sqlQuery);
            recordCount = r.getInt(1);
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recordCount;
    }

}
