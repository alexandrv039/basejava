package com.urise.webapp.model;

import java.util.EnumMap;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume>{

    // Unique identifier
    private final String uuid;
    private String fullName;
    private EnumMap<ContactType, String> contacts;
    private EnumMap<SectionType, Object> sections;

    public Resume(){
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

    public String getFullName(){
        return fullName;
    }

    public EnumMap<ContactType, String> getContacts() {
        return contacts;
    }

    public EnumMap<SectionType, Object> getSections() {
        return sections;
    }

    public void setContacts(EnumMap<ContactType, String> contacts) {
        this.contacts = contacts;
    }

    public void setSections(EnumMap<SectionType, Object> sections) {
        this.sections = sections;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
