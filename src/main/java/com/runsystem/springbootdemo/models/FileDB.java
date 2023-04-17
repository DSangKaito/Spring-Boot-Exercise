package com.runsystem.springbootdemo.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "fileDB")
public class FileDB {

    /** id of FileDB */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    /** name of FileDB */
    private String name;

    /** type of FileDB */
    private String type;

    /** data of FileDB */
    @Lob
    private byte[] data;

    /**
     * Constructor
     * @param name name of FileDB
     * @param type type of FileDB
     * @param data data of FileDB
     */
    public FileDB(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public FileDB() {

    }
}
