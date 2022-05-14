package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends AbstractSection{
    private List<String> list = new ArrayList<>();

    public ListSection(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public String getSectionContent() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s:list
        ) {
            stringBuilder.append("*").append(s).append("\n");
        }
        return stringBuilder.toString();
    }
}
