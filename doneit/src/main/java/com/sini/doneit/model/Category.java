package com.sini.doneit.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Category {
    @Id
    private Long id;

    private String name;
    private Integer cfuPrice;

    @OneToOne(mappedBy = "category")
    private Todo todo;

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCfuPrice() {
        return cfuPrice;
    }

    public void setCfuPrice(Integer cfuPrice) {
        this.cfuPrice = cfuPrice;
    }

}
