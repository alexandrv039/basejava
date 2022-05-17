package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Organization {

    private String name;
    private final List<Position> positions = new ArrayList<>();
    private Website website;

    public Organization(String name) {
        this(name, "");
    }

    public Organization(String name, String url) {
        website = new Website(name, url);
        this.name = name;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public String getContent() {
        String content = name;
        for (Position position : positions
        ) {
            content += System.lineSeparator() + position;
        }
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!name.equals(that.name)) return false;
        if (!positions.equals(that.positions)) return false;
        return website != null ? website.equals(that.website) : that.website == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + positions.hashCode();
        result = 31 * result + (website != null ? website.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", website=" + website.getUrl() +
                ", positions=" + positions +
                '}';
    }

    public static class Position {
        private LocalDate dateFrom;
        private LocalDate dateTo;
        private String position;
        private String description;

        public Position(LocalDate dateFrom, LocalDate dateTo, String position) {
            this(dateFrom, dateTo, position, "");
        }

        public Position(LocalDate dateFrom, LocalDate dateTo, String position, String description) {
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
