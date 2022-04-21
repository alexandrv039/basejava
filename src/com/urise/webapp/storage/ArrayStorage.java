package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage{
    private static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Индекс за пределами массива");
        } else if (getIndex(r.getUuid()) > -1) {
            System.out.println("Элемент с uuid: \"" + r.getUuid() + "\" уже добавлен");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("Нельзя обновить несуществующий элемент, uuid: \"" + resume.getUuid() + "\"");
        } else {
            storage[index] = resume;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Элемент не найден, uuid: \"" + uuid + "\"");
        }
    }

    @Override

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
