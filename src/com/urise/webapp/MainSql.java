package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;

public class MainSql {

    public static void main(String[] args) {
        SqlStorage storage = Config.get().getStorage();
        Resume resume = ResumeTestData.getNewResume("uuid3", "Ivan");
//        storage.update(new Resume("dummy"));
//        storage.save(resume);
//        storage.clear();
//        Resume resume1 = (Resume) storage.get("uuid2");
//        System.out.println(resume);
//        System.out.println(storage.getAllSorted());
    }

}
