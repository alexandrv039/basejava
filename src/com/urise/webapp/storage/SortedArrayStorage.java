package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Индекс за пределами массива");
        } else {
            int index = getIndex(r.getUuid());
            if (index > -1) {
                System.out.println("Элемент с uuid: \"" + r.getUuid() + "\" уже добавлен");
            } else {
                int key = -index - 1;
                size++;
                System.arraycopy(storage, key, storage, -index, size - 1);
                storage[key] = r;
            }
        }
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Нельзя обновить несуществующий элемент, uuid: \"" + resume.getUuid() + "\"");
        } else {
            storage[index] = resume;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            size--;
            System.arraycopy(storage, index + 1, storage, index, size);
        } else {
            System.out.println("Элемент не найден, uuid: \"" + uuid + "\"");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

}
