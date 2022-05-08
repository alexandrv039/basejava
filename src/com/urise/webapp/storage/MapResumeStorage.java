package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {

    private Map<String, Resume> map = new LinkedHashMap<>();

    @Override
    protected void doDelete(Object resume) {
        map.remove(((Resume) resume).getUuid());
    }

    @Override
    protected void doSave(Object key, Resume r) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void doUpdate(Object key, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    public Resume getKey(String searchString) {
        return map.get(searchString);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
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
