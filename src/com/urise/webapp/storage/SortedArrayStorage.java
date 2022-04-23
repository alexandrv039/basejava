package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void addResumeInIndex(int index, Resume resume) {
        int key = -index - 1;
        size++;
        System.arraycopy(storage, key, storage, -index, size - 1);
        storage[key] = resume;
    }

    @Override
    protected void deleteResumeFromIndex(int index) {
        size--;
        System.arraycopy(storage, index + 1, storage, index, size);
    }
}
