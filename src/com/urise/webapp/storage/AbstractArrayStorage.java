package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return list, contains only Resumes in storage (without null)
     */
    public List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public Integer getKey(String uuid) {
        return getIndex(uuid);
    }

    @Override
    protected boolean isExist(Integer key) {
        return key >= 0;
    }

    @Override
    protected void doUpdate(Integer key, Resume resume) {
        storage[key] = resume;
    }

    @Override
    protected void doSave(Integer key, Resume r) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else if (getKey(r.getUuid()) > -1) {
            throw new ExistStorageException(r.getUuid());
        } else {
            insertResume(getKey(r.getUuid()), r);
            size++;
        }
    }

    @Override
    protected Resume doGet(Integer key) {
        return storage[key];
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertResume(Integer index, Resume resume);
}
