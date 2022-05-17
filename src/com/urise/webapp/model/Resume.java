package com.urise.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private String fullName;
    private final EnumMap<ContactType, String> contacts = new EnumMap<ContactType, String>(ContactType.class);
    private final EnumMap<SectionType, AbstractSection> sections =
            new EnumMap<SectionType, AbstractSection>(SectionType.class);

    public Resume() {
        this(UUID.randomUUID().toString(), "No name");
    }

    public Resume(String uuid) {
        this(uuid, "No name");
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public EnumMap<ContactType, String> getContacts() {
        return contacts;
    }

    public EnumMap<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void printTitle() {
        System.out.println(getFullName());
        System.out.println("---------------------------" + System.lineSeparator());

        for (Map.Entry<ContactType, String> entry : getContacts().entrySet()
        ) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }
        System.out.println("---------------------------" + System.lineSeparator());

        for (Map.Entry<SectionType, AbstractSection> entry : getSections().entrySet()
        ) {
            System.out.println(entry.getKey().getTitle() + System.lineSeparator()
                    + entry.getValue() + System.lineSeparator());
        }
        System.out.println("---------------------------" + System.lineSeparator());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume o) {
        int compare = fullName.compareTo(o.getFullName());
        if (compare != 0) {
            compare = uuid.compareTo(o.getUuid());
        }
        return compare;
    }
}
