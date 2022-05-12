package com.urise.webapp;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;

import java.util.EnumMap;
import java.util.Map;

public class ResumeTestData {

    public static void main(String[] args) {

        Resume testResume = new Resume("uuid1", "Григорий Кислин");
        testResume.setContacts(getContacts());
        testResume.setSections(getSections());
        printResume(testResume);
    }

    private static EnumMap<ContactType, String> getContacts() {
        EnumMap<ContactType, String> contacts = new EnumMap<ContactType, String>(ContactType.class);

        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "skype:grigory.kislin");
        contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        contacts.put(ContactType.HOMEPAGE, "http://gkislin.ru/");

        return contacts;
    }

    private static EnumMap<SectionType, Object> getSections() {
        EnumMap<SectionType, Object> sections = new EnumMap<SectionType, Object>(SectionType.class);

        sections.put(SectionType.PERSONAL, "");
        sections.put(SectionType.OBJECTIVE, "");
        sections.put(SectionType.ACHIEVEMENT, "");
        sections.put(SectionType.QUALIFICATIONS, "");
        sections.put(SectionType.EXPERIENCE, "");
        sections.put(SectionType.EDUCATION, "");

        return sections;
    }

    private static void printResume(Resume resume) {
        System.out.println(resume.getFullName());
        System.out.println("---------------------------");

        for (Map.Entry<ContactType, String> entry:resume.getContacts().entrySet()
             ) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("---------------------------");

        for (Map.Entry<SectionType, Object> entry:resume.getSections().entrySet()
        ) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("---------------------------");
    }
}
