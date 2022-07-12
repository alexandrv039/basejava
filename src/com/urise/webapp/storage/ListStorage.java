package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected void doDelete(Integer key) {
        storage.remove((int) key);
    }

    @Override
    protected void doSave(Integer key, Resume r) {
        storage.add(r);
    }

    @Override
    protected Resume doGet(Integer key) {
        return storage.get(key);
    }

    @Override
    protected void doUpdate(Integer key, Resume resume) {
        storage.set(key, resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAll() {
        return storage;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist(Integer key) {
        return key >= 0;
    }

    @Override
    public Integer getKey(String searchString) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(searchString)) {
                return i;
            }
        }
        return -1;
    }
}
