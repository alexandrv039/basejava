package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;

public class MainSql {

    public static void main(String[] args) {
        SqlStorage storage = Config.get().getStorage();
        Resume resume = ResumeTestData.getNewResume("uuid3", "Ivan");


//        storage.update(new Resume("dummy"));
        storage.clear();
        storage.save(resume);
//        Resume resume1 = (Resume) storage.get("uuid3");
//        System.out.println(resume);
//        System.out.println(storage.getAllSorted());

//        String s = JsonParser.write(resume, Resume.class);
//                "{\"uuid\":\"uuid3\","
//                + "\"fullName\":\"Ivan\","
//                + "\"contacts\":{\"PHONE\":\"+7(921) 855-0482\",\"MOBILE\":\"\",\"HOMEPAGE\":\"http://gkislin.ru/\",\"SKYPE\":\"skype:grigory.kislin\",\"EMAIL\":\"gkislin@yandex.ru\",\"LINKEDIN\":\"https://www.linkedin.com/in/gkislin\",\"GITHUB\":\"https://github.com/gkislin\",\"STACKOVERFLOW\":\"https://stackoverflow.com/users/548473/grigory-kislin\",\"HOME_PAGE\":\"\"},"
////                + "\"sections\":{\"PERSONAL\":\"Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.}"
//                + "{\"CLASSNAME\":\"com.urise.webapp.model.TextSection\",\"INSTANCE\":{\"text\":\"                        Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.\\r\\n        \\r\\n        \"}}"
//                ;

//        Resume r = JsonParser.read(s, Resume.class);

    }

}
