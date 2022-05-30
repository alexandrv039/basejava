package com.urise.webapp;

import com.urise.webapp.exception.StorageException;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        File file = new File(".");
        System.out.println("Tree of directory " + file.getCanonicalPath() + ":");
        printFilePath(file, 0);
    }

    private static void printFilePath(File file, int indent) {
        System.out.println(getIndent(indent) + file.getName());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                throw new StorageException("pathname does not denote a directory, or if an I/O error occurs");
            }
            for (File f: files) {
                printFilePath(f, indent + 1);
            }
        }
    }

    private static String getIndent(int indent) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < indent; i++) {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}
