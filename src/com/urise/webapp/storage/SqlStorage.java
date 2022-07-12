package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.util.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    public SqlStorage(Config config) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(config.getDbUrl(), config.getDbUser(),
                config.getDbPassword()));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", (preparedStatement) -> {
                    preparedStatement.execute();
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                preparedStatement -> {
                        preparedStatement.setString(1, r.getUuid());
                        preparedStatement.setString(2, r.getFullName());
                        preparedStatement.execute();
                        return null;
                });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.execute("UPDATE resume SET full_name=? WHERE uuid=?",
                preparedStatement -> {
                    preparedStatement.setString(1, resume.getFullName());
                    preparedStatement.setString(2, resume.getUuid());
                    if (preparedStatement.executeUpdate() == 0) {
                        throw new NotExistStorageException(resume.getUuid());
                    }
                    return null;
                });
    }

    @Override
    public Object get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid =?",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid =?",
                preparedStatement -> {
                preparedStatement.setString(1, uuid);
                    if (preparedStatement.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
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
