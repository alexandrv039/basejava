package com.urise.webapp;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Interactive test for ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    private final static ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | size | save uuid fullName | delete uuid | get uuid | " +
                    "update uuid fullName | clear | testsave [massive size] | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 3) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            String fullName = "";
            if (params.length == 2) {
                uuid = params[1].intern();
            } else if (params.length == 3) {
                uuid = params[1].intern();
                fullName = params[2].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    if (commandFormatCorrect("save", fullName)) {
                        r = new Resume(uuid, fullName);
                        ARRAY_STORAGE.save(r);
                        printAll();
                    }
                    break;
                case "update":
                    if (commandFormatCorrect("update", fullName)) {
                        r = new Resume(uuid, fullName);
                        ARRAY_STORAGE.update(r);
                        printAll();
                    }
                case "delete":
                    ARRAY_STORAGE.delete(uuid);
                    printAll();
                    break;
                case "get":
                    System.out.println(ARRAY_STORAGE.get(uuid));
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "testsave":
                    testSave(uuid);
                    break;
                case "getallsorted":
                    System.out.println(ARRAY_STORAGE.getAllSorted());
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    private static boolean commandFormatCorrect(String comandName, String fullName) {
        if (fullName.length() == 0) {
            System.out.println("Неверный формат команды " + comandName +
                    "\n Формат: " + comandName + " [uuid] [fullName]");
        }
        return fullName.length() != 0;
    }

    static void printAll() {
        List<Resume> all = ARRAY_STORAGE.getAll();
        System.out.println("----------------------------");
        if (all.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }

    static void testSave(String parameter) {

        int massiveSize = 0;
        try {
            massiveSize = Integer.parseInt(parameter);
        } catch (NumberFormatException exception) {
            System.out.println("В качестве второго параметра необходимо ввести длину массива");
            return;
        }

        ARRAY_STORAGE.clear();
        int i = 0;

        try {
            for (i = 0; i < massiveSize; i++) {
                ARRAY_STORAGE.save(new Resume("uuid" + i, "Name" + i));
            }
        } catch (StorageException exception) {
            System.out.println(exception.getMessage() + " --- массив переполнен. Всего добавлено элементов: " + i);
            return;
        }

        System.out.println("Массив заполнен, всего добавлено элементов: " + i);

    }
}
