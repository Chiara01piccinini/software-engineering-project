package org.example.app.model;

import javax.xml.stream.Location;

public class Azienda {
    private String name;
    private Location position;
    private String PI;

    public Azienda (String name, Location position, String PI){
        this.name = name;
        this.position = position;
        this.PI = PI;
    }
}
