package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.util.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;
    public static final String SQL_CODE_EXIST = "23505";

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public int clear() {
        sqlHelper.execute("DELETE FROM resume", (preparedStatement) -> {
                    preparedStatement.execute();
                    return null;
                }
        );
        return 0;
    }

    @Override
    public void save(Resume r) {
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                preparedStatement -> {
                    try {
                        preparedStatement.execute();
                        return null;
                    } catch (SQLException e) {
                        if (e.getSQLState().equals(SQL_CODE_EXIST)) {
                            throw new ExistStorageException(r.getUuid());
                        } else throw e;
                    }
                },
                r.getUuid(), r.getFullName());
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.execute("UPDATE resume SET full_name=? WHERE uuid=?",
                preparedStatement -> {
                    if (preparedStatement.executeUpdate() == 0) {
                        throw new NotExistStorageException(resume.getUuid());
                    }
                    return null;
                },
                resume.getFullName(), resume.getUuid());
    }

    @Override
    public Object get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid =?",
                preparedStatement -> {
                    ResultSet rs = preparedStatement.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                },
                uuid);
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid =?",
                preparedStatement -> {
                    if (preparedStatement.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                },
                uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        return (List<Resume>) sqlHelper.execute("SELECT * FROM resume as r ORDER BY r.uuid",
                preparedStatement -> {
                    ResultSet rs = preparedStatement.executeQuery();
                    List<Resume> resumes = new ArrayList<>();
                    while (rs.next()) {
                        resumes.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
                    }
                    return resumes;
                });
    }

    @Override
    public int size() {
        return (int) sqlHelper.execute("SELECT COUNT(*) as count FROM resume",
                preparedStatement -> {
                    ResultSet rs = preparedStatement.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException("Table resume is not exist");
                    }
                    return rs.getInt("count");
                });
    }
}
