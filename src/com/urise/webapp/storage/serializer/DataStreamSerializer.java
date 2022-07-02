package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;

public class DataStreamSerializer implements StreamSerializer{
    @Override
    public void doWrite(Resume r, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());

            writeWithException(entry-> {
                dos.writeUTF(entry);
            }, contacts, dos);

            for (Map.Entry<ContactType,String> entry: contacts.entrySet()
                 ) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType,AbstractSection> entry: sections.entrySet()
            ) {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF((entry.getValue().toString()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeListSection(dos, (ListSection) entry.getValue());
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeOrganizationSection(dos, (OrganizationSection) entry.getValue());
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)){
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(sectionType, readListSection(dis));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        resume.addSection(sectionType, readOrganizationSection(dis));
                        break;
                }
            }
            return resume;
        }
    }

    private void writeListSection(DataOutputStream dos, ListSection listSection) throws IOException {
        List<String> list = listSection.getList();
        dos.writeInt(list.size());
        for (String text : list
        ) {
            dos.writeUTF(text);
        }
    }

    private ListSection readListSection(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(dis.readUTF());
        }
        return new ListSection(list);
    }

    private void writeOrganizationSection(DataOutputStream dos, OrganizationSection organizationSection) throws IOException {
        List<Organization> list = organizationSection.getOrganizations();
        dos.writeInt(list.size());
        for (Organization organization : list
        ) {
            dos.writeUTF(organization.getTitle());
            Link link = organization.getLink();
            dos.writeUTF(link.getName());
            dos.writeUTF(link.getUrl());

            List<Organization.Period> periods = organization.getPeriods();
            dos.writeInt(periods.size());
            for (Organization.Period period : periods
            ) {
                dos.writeUTF(period.getDateFrom().toString());
                dos.writeUTF(period.getDateTo().toString());
                dos.writeUTF(period.getPosition());
                dos.writeUTF(period.getDescription());
            }
        }
    }

    private OrganizationSection readOrganizationSection(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<Organization> organizations = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Organization organization = new Organization();
            organization.setTitle(dis.readUTF());
            organization.setLink(new Link(dis.readUTF(), dis.readUTF()));

            int periodSize = dis.readInt();
            List<Organization.Period> periods = organization.getPeriods();
            for (int j = 0; j < periodSize; j++) {
                periods.add(new Organization.Period(LocalDate.parse(dis.readUTF()),
                        LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
            }
            organizations.add(organization);
        }
        return new OrganizationSection(organizations);
    }

    void writeWithException(Consumer<Object> action, Map collection, DataOutputStream dos) {

        for (Object t : collection.keySet()) {
            action.accept(t);
        }
    }

}
