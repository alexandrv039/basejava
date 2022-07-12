package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ExecuteTransaction<T> {
    T execute(PreparedStatement preparedStatement) throws SQLException;
}
