package com.urise.webapp.util;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.ExecuteTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;
    public static final String SQL_CODE_EXIST = "23505";

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String sql, ExecuteTransaction executeTransaction) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return (T) executeTransaction.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals(SQL_CODE_EXIST)) {
                throw new ExistStorageException(e.getMessage());
            } else throw new StorageException(e);
        }
    }
}

