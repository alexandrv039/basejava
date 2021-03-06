package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final StreamSerializer streamSerializer;

    protected FileStorage(File directory, StreamSerializer streamSerializer) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(streamSerializer, "streamSerializer must be not null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.streamSerializer = streamSerializer;
    }

    @Override
    public void clear() {
        File[] files = getDirectoryList();
        for (File file : files) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        File[] files = getDirectoryList();
        return files.length;
    }

    @Override
    protected File getKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(File file, Resume r) {
        try {
            streamSerializer.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("file save error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(File file, Resume r) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("file create error", file.getName(), e);
        }
        doUpdate(file, r);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("error read file", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File " + file.getName() + " not delete");
        }
    }

    @Override
    protected List<Resume> getAll() {
        List<Resume> resumes = new ArrayList<>();
        File[] files = getDirectoryList();
        for (File file : files) {
            if (file.isFile()) {
                resumes.add(doGet(file));
            }
        }
        return resumes;
    }

    private File[] getDirectoryList() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("pathname does not denote a directory, or if an I/O error occurs");
        }
        return files;
    }
}