package com.urise.webapp;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.*;

/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private static final Storage ARRAY_STORAGE = new MapStorageNameSearch();

    public static void main(String[] args) {
        final Resume r1 = new Resume("uuid1", "Andrey");
        final Resume r2 = new Resume("uuid2", "John");
        final Resume r3 = new Resume("uuid3", "Leo");
        final Resume r4 = new Resume("uuid4", "Alex");
        final Resume r5 = new Resume("uuid5", "Ilya");
        final Resume r6 = new Resume("uuid6", "Ivan");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r5);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r6);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r4);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));

        System.out.println("Size: " + ARRAY_STORAGE.size());

        try {
            System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        } catch (NotExistStorageException e) {
            System.out.println("получение записи по ключу 'dummy' привело к ошибке: " + e.getMessage());
        }

        Resume r4Update = new Resume("uuid4");
        ARRAY_STORAGE.update(r4Update);

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
