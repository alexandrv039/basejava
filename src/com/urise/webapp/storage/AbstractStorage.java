package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage{

    public void update(Resume resume){
        Object key = getKey(resume.getUuid());
        if (keyIsExist(key)) {
            doUpdate(key, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public void save(Resume r) {
        Object key = getKey(r.getUuid());
        if (keyIsExist(key)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            doSave(key, r);
        }
    }

    public void delete(String uuid) {
        Object key = getKey(uuid);
        if (keyIsExist(key)) {
            doDelete(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void doDelete(Object key);

    protected abstract void doSave(Object key, Resume r);

    protected abstract boolean keyIsExist(Object key);

    protected abstract void doUpdate(Object key, Resume resume);

    public abstract Object getKey(String searchString);
}
