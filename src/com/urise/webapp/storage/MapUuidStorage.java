package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {

    private Map<String, Resume> map = new LinkedHashMap<>();

    @Override
    protected void doDelete(String key) {
        map.remove(key);
    }

    @Override
    protected void doSave(String key, Resume r) {
        map.put(key, r);
    }

    @Override
    protected Resume doGet(String key) {
        return map.get(key);
    }

    @Override
    protected void doUpdate(String key, Resume resume) {
        map.put(key, resume);
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
    protected boolean isExist(String key) {
        return map.containsKey(key);
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }
}
