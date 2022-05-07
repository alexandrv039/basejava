package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> list = new ArrayList<>();

    @Override
    protected void doDelete(Object key) {
        list.remove((int) key);
    }

    @Override
    protected void doSave(Object key, Resume r) {
        list.add(r);
    }

    @Override
    protected Object doGet(Object key) {
        return list.get((Integer)key);
    }

    @Override
    protected void doUpdate(Object key, Resume resume) {
        list.set((Integer)key, resume);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public List<Resume> getAll() {
        return list;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer)key >= 0;
    }

    @Override
    public Integer getKey(String searchString) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(searchString)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected String getSearchString(Resume resume) {
        return resume.getUuid();
    }
}
