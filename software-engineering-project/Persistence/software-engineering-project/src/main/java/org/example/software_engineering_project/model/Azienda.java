package org.example.software_engineering_project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Azienda {
    private String name;
    private Position position;
    private String PI;

    @JsonCreator
    public Azienda (@JsonProperty("nome") String name,@JsonProperty("posizione") Position position,@JsonProperty("PI") String PI){
        this.name = name;
        this.position = position;
        this.PI = PI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getPI() {
        return PI;
    }

    public void setPI(String PI) {
        this.PI = PI;
    }
}