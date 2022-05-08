package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected static final Comparator<Resume> RESUME_COMPARATOR = (r1, r2) ->
        Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid).compare(r1, r2);

    public Object get(String searchString) {
        Object key = getNotExistKey(searchString);
        return doGet(key);
    }

    public void update(Resume resume) {
        Object key = getNotExistKey(resume.getUuid());
        doUpdate(key, resume);
    }

    public void save(Resume r) {
        Object key = getExistKey(r.getUuid());
        doSave(key, r);
    }

    public void delete(String searchString) {
        Object key = getNotExistKey(searchString);
        doDelete(key);
    }

    private Object getExistKey(String searchString) {
        Object key = getKey(searchString);
        if (isExist(key)) {
            throw new ExistStorageException(searchString);
        }
        return key;
    }

    private Object getNotExistKey(String searchString) {
        Object key = getKey(searchString);
        if (!isExist(key)) {
            throw new NotExistStorageException(searchString);
        }
        return key;
    }

    public List<Resume> getAllSorted(){
        List<Resume> resumes = getAll();
        resumes.sort(RESUME_COMPARATOR);
        return resumes;
    }

    protected abstract List<Resume> getAll();

    protected abstract void doDelete(Object key);

    protected abstract void doSave(Object key, Resume r);

    protected abstract Object doGet(Object key);

    protected abstract boolean isExist(Object key);

    protected abstract void doUpdate(Object key, Resume resume);

    public abstract Object getKey(String searchString);
}
