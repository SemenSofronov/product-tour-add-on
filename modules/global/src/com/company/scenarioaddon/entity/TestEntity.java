package com.company.scenarioaddon.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "SCENARIOADDON_TEST_ENTITY")
@Entity(name = "scenarioaddon$TestEntity")
public class TestEntity extends StandardEntity {
    private static final long serialVersionUID = 7004455375923754156L;

    @Column(name = "NAME")
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}