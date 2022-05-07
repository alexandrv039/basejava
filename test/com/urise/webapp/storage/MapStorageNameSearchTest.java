package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapStorageNameSearchTest extends AbstractStorageTest{

    public MapStorageNameSearchTest() {
        super(new MapStorageNameSearch());
    }

    @Override
    @Test
    public void update(){
        storage.update(RESUME_2);
        Assert.assertSame(RESUME_2, storage.get(NAME_2));
    }

    @Override
    protected void assertGet(Resume excepted) {
        Assert.assertEquals(excepted, storage.get(excepted.getFullName()));
    }
}