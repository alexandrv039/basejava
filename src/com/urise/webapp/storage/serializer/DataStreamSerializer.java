package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer{
    @Override
    public void doWrite(Resume r, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            writeWithException(dos, r.getContacts().entrySet(),
                    writer-> {
                        dos.writeUTF(writer.getKey().name());
                        dos.writeUTF(writer.getValue());
                    });

            writeWithException(dos, r.getSections().entrySet(), entry->{
                SectionType type = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF((entry.getValue().toString()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeWithException(dos, ((ListSection) section).getList(), dos::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeWithException(dos, ((OrganizationSection) section).getOrganizations(),
                                writer -> {
                                    Link link = writer.getLink();
                                    dos.writeUTF(writer.getTitle());
                                    dos.writeUTF(link.getName());
                                    dos.writeUTF(link.getUrl());

                                    writeWithException(dos, writer.getPeriods(),
                                            position -> {
                                                dos.writeUTF(position.getDateFrom().toString());
                                                dos.writeUTF(position.getDateTo().toString());
                                                dos.writeUTF(position.getPosition());
                                                dos.writeUTF(position.getDescription());
                                            });
                                });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)){
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readItem(dis, ()-> {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            });

            readItem(dis, ()->{
                SectionType type = SectionType.valueOf(dis.readUTF());
                resume.addSection(type, readSection(dis, type));
            });
            return resume;
        }
    }

    private AbstractSection readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readList(dis, dis::readUTF));
            case EDUCATION:
            case EXPERIENCE:
                return new OrganizationSection(readList(dis, () -> {
                    Organization organization = new Organization(dis.readUTF());
                    organization.setLink(new Link(dis.readUTF(), dis.readUTF()));

                    organization.setPeriods(readList(dis, () ->
                            new Organization.Position(LocalDate.parse(dis.readUTF()),
                                    LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF())
                    ));
                    return organization;
                }));
            default:
                throw new IllegalStateException();
        }
    }

    private interface ElementProcessor{
        void process() throws IOException;
    }

    private interface WriteCollection<T> {
        void write(T t) throws IOException;
    }

    private interface ElementReader<T>{
        T read() throws IOException;
    }

    private <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private void readItem(DataInputStream dis, ElementProcessor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, WriteCollection<T> write) throws IOException {

        dos.writeInt(collection.size());
        for (T t : collection) {
            write.write(t);
        }
    }

}
