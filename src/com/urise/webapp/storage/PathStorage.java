package com.urise.webapp.storage;

import com.urise.webapp.StreamSerializer;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StreamSerializer streamSerializer;

    protected PathStorage(String dir, StreamSerializer streamSerializer) {
        Objects.requireNonNull(dir, "directory must not be null");
        Objects.requireNonNull(streamSerializer, "streamSerializer must be not null");

        directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory");
        }
        this.streamSerializer = streamSerializer;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("pathname does not denote a directory, or if an I/O error occurs");
        }
    }

    @Override
    protected Path getKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected void doUpdate(Path path, Resume r) {
        doSave(path, r);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Path path, Resume r) {
        try {
            Files.createFile(path);
            doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("file save error", path.toString(), e);
        }
    }

    protected void doWrite(Resume r, OutputStream outputStream) throws IOException {
        streamSerializer.doWrite(r, outputStream);
    }

    protected Resume doRead(InputStream inputStream) throws IOException {
        return streamSerializer.doRead(inputStream);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("error read file", path.toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File " + path.toString() + " not delete");
        }
    }

    @Override
    protected List<Resume> getAll() {
        List<Resume> resumes = new ArrayList<>();
        try {
            Files.list(directory).filter(path -> !Files.isDirectory(path)).forEach(path -> resumes.add(doGet(path)));
        } catch (IOException e) {
            throw new StorageException("pathname does not denote a directory, or if an I/O error occurs");
        }
        return resumes;
    }
}
