package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.UUID;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    protected Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        Assert.assertEquals(UUID_1, storage.get(UUID_1).getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(new Resume(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void delete() {
        storage.delete(UUID_3);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist(){
        storage.delete("dummy");
    }

    @Test
    public void getAll() {
        Assert.assertEquals(3, storage.getAll().length);
    }

    @Test
    public void save() {
        storage.save(new Resume("uuid4"));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = StorageException.class)
    public void saveStorageException() throws StorageException{
        storage.clear();
        int i = 0;
        try {
            for (i = 0; i <= 10000; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (Exception exception) {
            if (i < 10000){
                Assert.fail("Переполнение произошло раньше времени");
            } else {
                throw exception;
            }
        }

    }

}