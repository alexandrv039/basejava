package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public Object get(String uuid) {
        Object key = notExistStorage(uuid);
        return doGet(key);
    }

    public void update(Resume resume) {
        Object key = notExistStorage(resume.getUuid());
        doUpdate(key, resume);
    }

    public void save(Resume r) {
        Object key = existStorage(r.getUuid());
        doSave(key, r);
    }

    public void delete(String uuid) {
        Object key = notExistStorage(uuid);
        doDelete(key);
    }

    private Object existStorage(String uuid) {
        Object key = getKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private Object notExistStorage(String uuid) {
        Object key = getKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract void doDelete(Object key);

    protected abstract void doSave(Object key, Resume r);

    protected abstract Object doGet(Object key);

    protected abstract boolean isExist(Object key);

    protected abstract void doUpdate(Object key, Resume resume);

    public abstract Object getKey(String searchString);
}
