package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AbstractStorageTest {
    protected Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String NAME_1 = "Andrey";
    protected static final Resume RESUME_1 = new Resume(UUID_1, NAME_1);
    protected static final String UUID_2 = "uuid2";
    protected static final String NAME_2 = "John";
    protected static final Resume RESUME_2 = new Resume(UUID_2, NAME_2);
    protected static final String UUID_3 = "uuid3";
    protected static final String NAME_3 = "Leo";
    protected static final Resume RESUME_3 = new Resume(UUID_3, NAME_3);
    protected static final String UUID_4 = "uuid4";
    protected static final String NAME_4 = "Alex";
    protected static final Resume RESUME_4 = new Resume(UUID_4, NAME_4);
    protected static final String UUID_NOT_EXIST = "dummy";

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIST);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        storage.update(RESUME_2);
        Assert.assertSame(RESUME_2, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_3);
        assertSize(2);
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_NOT_EXIST);
    }

    @Test
    public void getAllSorted() {
        List<Resume> resumes = storage.getAllSorted();
        Assert.assertEquals(3, resumes.size());
        Assert.assertEquals(resumes, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void existSave() {
        storage.save(RESUME_2);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    protected void assertSize(int excepted) {
        Assert.assertEquals(excepted, storage.size());
    }

    protected void assertGet(Resume excepted) {
        Assert.assertEquals(excepted, storage.get(excepted.getUuid()));
    }
}