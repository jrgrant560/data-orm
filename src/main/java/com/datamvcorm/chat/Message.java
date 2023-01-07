package com.datamvcorm.chat;

import jakarta.persistence.*;

@Entity // this Model can be mapped to a table in the db
@Table(name="message") // the name of the table that this Model is mapped to
public class Message {

    @Id // this id the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // this property is generated via auto-increment
    private Integer id;

    private String name;
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
