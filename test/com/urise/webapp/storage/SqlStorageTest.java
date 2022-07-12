package com.urise.webapp.storage;

import com.urise.webapp.Config;

import static org.junit.Assert.*;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(new SqlStorage(Config.get()));
    }
}