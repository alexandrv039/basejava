package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage{
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("не найден элемент с uuid: \"" + uuid + "\"");
            return null;
        } else {
            return storage[index];
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Нельзя обновить несуществующий элемент, uuid: \"" + resume.getUuid() + "\"");
        } else {
            storage[index] = resume;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            deleteResumeFromIndex(index);
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

    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Индекс за пределами массива");
        } else if (getIndex(r.getUuid()) > -1) {
            System.out.println("Элемент с uuid: \"" + r.getUuid() + "\" уже добавлен");
        } else {
            addResumeInIndex(getIndex(r.getUuid()), r);
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract void addResumeInIndex(int index, Resume resume);

    protected abstract void deleteResumeFromIndex(int index);

}
