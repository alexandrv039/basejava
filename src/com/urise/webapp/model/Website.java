package com.urise.webapp.model;

import java.util.Objects;

public class Website {
    private String name;
    private String url;

    public Website(String name, String url) {
        Objects.requireNonNull(name);
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Website website = (Website) o;

        if (!name.equals(website.name)) return false;
        return url != null ? url.equals(website.url) : website.url == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "website{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
