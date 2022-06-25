package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer{
    private XmlParser xmlParser;

    public XmlStreamSerializer(){
        xmlParser = new XmlParser(Resume.class, Organization.class, Link.class, OrganizationSection.class,
                TextSection.class, ListSection.class, Organization.Period.class);
    }

    @Override
    public void doWrite(Resume r, OutputStream outputStream) throws IOException {
        try (Writer w = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)){
            xmlParser.marshall(r, w);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (Reader r = new InputStreamReader(inputStream, StandardCharsets.UTF_8)){
            return xmlParser.unmarshall(r);
        }
    }
}
