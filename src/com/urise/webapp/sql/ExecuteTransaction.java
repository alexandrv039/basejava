package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ExecuteTransaction {
    Object execute(PreparedStatement preparedStatement) throws SQLException;
}
