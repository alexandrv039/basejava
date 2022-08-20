package com.urise.webapp.model;

import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private List<Position> positions = new ArrayList<>();
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

    public String getTitle() {
        return title;
    }

    public Link getLink() {
        return link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public List<Position> getPeriods() {
        return positions;
    }

    public void setPeriods(List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!title.equals(that.title)) return false;
        if (!positions.equals(that.positions)) return false;
        return link != null ? link.equals(that.link) : that.link == null;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + positions.hashCode();
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "title='" + title + '\'' +
                ", website=" + link.getUrl() +
                ", positions=" + positions +
                '}';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateFrom;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateTo;
        private String position;
        private String description;

        public Position() {
        }

        public Position(LocalDate dateFrom, LocalDate dateTo, String position) {
            this(dateFrom, dateTo, position, "");
        }

        public Position(LocalDate dateFrom, LocalDate dateTo, String position, String description) {
            Objects.requireNonNull(description, "description must not be NULL");
            Objects.requireNonNull(position, "position must not be NULL");
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
            this.position = position;
            this.description = description == null ? "" : description;
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

            Position position1 = (Position) o;

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
