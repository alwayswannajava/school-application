package mainClasses;

import java.sql.*;
import java.util.Scanner;

public class QueryExecutor {
    private Statement queryExecutorStatement;

    public void executeQuery(Connection connection){
        Scanner scanner = new Scanner(System.in);
        String query;
        ResultSet resultSet;
        while(true) {
            System.out.println("SQL executor ready to work. You can type some SQL query that you want");
            System.out.println("If you wanna exit from sql executor, type 'exit' ");
            try {
                query = scanner.nextLine();
                if(query.equals("exit")){
                    break;
                }
                queryExecutorStatement =  connection.createStatement();
                if(query.contains("select")){
                    resultSet = queryExecutorStatement.executeQuery(query);
                }
                else if(query.contains("insert")){
                    queryExecutorStatement.executeUpdate(query);
                    resultSet = queryExecutorStatement.getGeneratedKeys();
                }
                else {
                    queryExecutorStatement.executeUpdate(query);
                    resultSet = queryExecutorStatement.getGeneratedKeys();
                }
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();
                while (resultSet.next()){
                    for (int column = 1; column <= columnCount; column++) {
                        System.out.print(resultSet.getString(column) + "  ");
                        if(column == columnCount){
                            System.out.println();
                        }
                    }
                }
                resultSet.close();
            } catch (SQLException throwables) {
                System.out.println("You wrote wrong SQL request, try another one");
            }
        }
        scanner.close();
    }

}
