package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;

public class MainSql {

    public static void main(String[] args) {
        SqlStorage storage = new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword());
        Resume resume = ResumeTestData.getNewResume("uuid2", "Alexandr5");
//        storage.update(new Resume("dummy"));
        storage.save(resume);
//        storage.clear();
//        System.out.println(storage.getAllSorted());
    }

}
