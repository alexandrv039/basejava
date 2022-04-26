package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void doSave(int index, Resume resume) {
        int key = -index - 1;
        System.arraycopy(storage, key, storage, -index, size);
        storage[key] = resume;
    }

    @Override
    protected void doDelete(int index) {
        System.arraycopy(storage, index + 1, storage, index, size);
    }
}
