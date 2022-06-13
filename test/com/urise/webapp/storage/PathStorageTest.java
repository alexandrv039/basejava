package com.urise.webapp.storage;

import com.urise.webapp.serializer.ObjectStreamStorage;

public class PathStorageTest extends AbstractStorageTest{
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }
}