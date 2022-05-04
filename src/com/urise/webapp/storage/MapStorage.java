package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> map = new LinkedHashMap<>();

    @Override
    protected void doDelete(Object key) {
        map.remove(key);
    }

    @Override
    protected void doSave(Object key, Resume r) {
        map.put((String) key, r);
    }

    @Override
    protected Resume doGet(Object key) {
        return map.get(key);
    }

    @Override
    protected void doUpdate(Object key, Resume resume) {
        map.put((String) key, resume);
    }

    @Override
    public String getKey(String searchString) {
        return searchString;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected boolean isExist(Object key) {
        return map.containsKey(key);
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return map.size();
    }
}
