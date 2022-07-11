package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

/**
 * Array based storage for Resumes
 */
public interface Storage {

    int clear();
    void save(Resume r);

    void update(Resume resume);

    Object get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();

}
