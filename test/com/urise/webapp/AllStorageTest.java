package com.urise.webapp;

import com.urise.webapp.storage.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        ListStorageTest.class,
        SortedArrayStorageTest.class,
        MapStorageTest.class,
        MapStorageNameSearchTest.class
})
public class AllStorageTest {
}