package com.example.demo.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class CommonAbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, name = "ID")
    protected Long id;

    @Version
    @Column(name = "VERSION")
    private int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
