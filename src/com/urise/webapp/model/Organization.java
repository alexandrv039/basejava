package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {

    private String name;
    private List<Position> positions = new ArrayList<>();

    public Organization(String name) {
        this.name = name;
    }

    public void addPosition(String period, String position, String title) {
        positions.add(new Position(period, position, title));
    }

    public void addPosition(String period, String position) {
        positions.add(new Position(period, position));
    }

    public String getContent() {
        String content = name;
        for (Position position:positions
             ) {
            content += System.lineSeparator() + position.getPerformance();
        }
        return content;
    }

    private class Position {
        private String period;
        private String position;
        private String title;

        public Position(String period, String position) {
            this(period, position, "");
        }

        public Position(String period, String position, String title) {
            this.period = period;
            this.position = position;
            this.title = title;
        }

        public String getPerformance() {
            return period + "       " + position +
                    (title.length() > 0 ? System.lineSeparator() + "             " + title : "");
        }
    }
}
