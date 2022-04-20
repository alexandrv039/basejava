package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;
    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Индекс за пределами массива");
        } else if (findIndexByUuid(r.getUuid()) > -1) {
            System.out.println("Элемент с uuid: \"" + r.getUuid() + "\" уже добавлен");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void update(Resume resume) {
        int index = findIndexByUuid(resume.getUuid());
        if (index == -1) {
            System.out.println("Нельзя обновить несуществующий элемент, uuid: \"" + resume.getUuid() + "\"");
        } else {
            storage[index] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = findIndexByUuid(uuid);
        if (index == -1) {
            System.out.println("не найден элемент с uuid: \"" + uuid + "\"");
            return null;
        } else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        int index = findIndexByUuid(uuid);
        if (index > -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Элемент не найден, uuid: \"" + uuid + "\"");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findIndexByUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
