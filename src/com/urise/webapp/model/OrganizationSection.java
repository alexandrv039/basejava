package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends AbstractSection{
    List<Organization> organizations = new ArrayList<>();

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public String getSectionContent() {
        String content = "";
        for (Organization organization:organizations
             ) {
            content += System.lineSeparator() + organization.getContent();
        }
        return content;
    }
}
