package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Индекс за пределами массива");
            return;
        }
        if (findResume(r) > -1) {
            System.out.println("Элемент уже добавлен");
            return;
        }
        storage[size] = r;
        size++;
    }

    public void update(Resume resume) {
        int index = findResume(resume);
        if (index == -1) {
            System.out.println("Нельзя обновить несуществующий элемент");
            return;
        }
        storage[index] = resume;
    }

    public Resume get(String uuid) {
        int index = findResumeByUuid(uuid);
        return index > -1 ? storage[index] : null;
    }

    public void delete(String uuid) {
        int index = findResumeByUuid(uuid);

        if (index > -1) {
            for (int i = index; i < size -1; i++) {
                storage[i] = storage[i + 1];
            }
            storage[size - 1] = null;
            size--;
        } else System.out.println("Элемент не найден");

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    public int size() {
        return size;
    }


    private int findResumeByUuid(String uuid) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private int findResume(Resume resume) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i] == resume) {
                index = i;
                break;
            }
        }
        return index;
    }

}
