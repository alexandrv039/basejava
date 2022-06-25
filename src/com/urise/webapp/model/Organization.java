package com.urise.webapp.model;

import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private final List<Period> periods = new ArrayList<>();
    private Link link;

    public Organization() {
    }

    public Organization(String title) {
        this(title, "");
    }

    public Organization(String title, String url) {
        link = new Link(title, url);
        this.title = title;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!title.equals(that.title)) return false;
        if (!periods.equals(that.periods)) return false;
        return link != null ? link.equals(that.link) : that.link == null;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + periods.hashCode();
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "title='" + title + '\'' +
                ", website=" + link.getUrl() +
                ", positions=" + periods +
                '}';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable{
        private static final long serialVersionUID = 1L;

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateFrom;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateTo;
        private String position;
        private String description;

        public Period(){
        }

        public Period(LocalDate dateFrom, LocalDate dateTo, String position) {
            this(dateFrom, dateTo, position, "");
        }

        public Period(LocalDate dateFrom, LocalDate dateTo, String position, String description) {
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
            this.position = position;
            this.description = description;
        }

        public LocalDate getDateFrom() {
            return dateFrom;
        }

        public LocalDate getDateTo() {
            return dateTo;
        }

        public String getPosition() {
            return position;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return dateFrom + "-" + dateTo + "       " + position
                    + (description.length() > 0 ? System.lineSeparator() + "             " + description : "");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Period position1 = (Period) o;

            if (!dateFrom.equals(position1.dateFrom)) return false;
            if (!dateTo.equals(position1.dateTo)) return false;
            if (!position.equals(position1.position)) return false;
            return description != null ? description.equals(position1.description) : position1.description == null;
        }

        @Override
        public int hashCode() {
            int result = dateFrom.hashCode();
            result = 31 * result + dateTo.hashCode();
            result = 31 * result + position.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }
    }
}
