package com.spring.schoolApplication;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseChecker {
    private static final String CHECK_DATABASE_ON_EMPTY = "select count (*) from information_schema.tables " +
            "where table_schema = 'public'";
    private JdbcTemplate jdbcTemplate;

    public DatabaseChecker(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int checkDatabaseOnEmpty() {
        return jdbcTemplate.queryForObject(CHECK_DATABASE_ON_EMPTY, Integer.class);
    }


}
