package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private List<Resume> list = new ArrayList<>();

    @Override
    protected void doDelete(Integer key) {
        list.remove((int) key);
    }

    @Override
    protected void doSave(Integer key, Resume r) {
        list.add(r);
    }

    @Override
    protected Resume doGet(Integer key) {
        return list.get(key);
    }

    @Override
    protected void doUpdate(Integer key, Resume resume) {
        list.set(key, resume);
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
    protected boolean isExist(Integer key) {
        return key >= 0;
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
}
