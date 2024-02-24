package com.spring.schoolApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class DatabaseChecker {
    private static final String COUNT_TABLES_IN_PUBLIC_SCHEMA_QUERY = "select count (*) from information_schema.tables " +
            "where table_schema = 'public'";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public boolean checkDatabaseOnEmpty() {
        Integer countTables = jdbcTemplate.queryForObject(COUNT_TABLES_IN_PUBLIC_SCHEMA_QUERY, Integer.class);
        return countTables != null && countTables > 0;
    }


}
