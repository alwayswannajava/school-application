package com.spring.schoolApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Repository;

import java.util.Scanner;

@Repository
public class QueryExecutor {

    private JdbcTemplate jdbcTemplate;

    public QueryExecutor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void executor() {
        Scanner scanner = new Scanner(System.in);
        String sql;
        try {
            while (true) {
                System.out.println("SQL executor ready to work. You can type some SQL query that you want");
                System.out.println("If you wanna exit from sql executor, type 'exit' ");
                sql = scanner.nextLine();
                if (sql.equals("exit")) {
                    break;
                }
                if (sql.contains("select")) {
                    SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
                    SqlRowSetMetaData metaData = sqlRowSet.getMetaData();
                    int countColumn = metaData.getColumnCount();
                    while (sqlRowSet.next()) {
                        System.out.println();
                        for (int column = 1; column <= countColumn; column++) {
                            System.out.print(sqlRowSet.getString(column) + "  ");
                            if (column == countColumn) {
                                System.out.println();
                            }
                        }
                    }
                } else if (sql.contains("insert")) {
                    jdbcTemplate.update(sql);
                } else if (sql.contains("drop") || sql.contains("delete")) {
                    System.out.println("You don't have permission to do this");
                }

            }
        } catch (DataAccessException e) {
            System.out.println("You had written the wrong sql request, try another one");
            executor();
        }
    }
}
