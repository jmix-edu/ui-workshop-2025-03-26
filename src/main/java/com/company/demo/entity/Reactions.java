package com.company.demo.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.JmixId;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;

import java.util.UUID;

@JmixEntity
public class Reactions {

    @JmixGeneratedValue
    @JmixId
    private UUID id;

    private Long likes;

    private Long dislikes;

    public Long getDislikes() {
        return dislikes;
    }

    public void setDislikes(Long dislikes) {
        this.dislikes = dislikes;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @InstanceName
    @DependsOnProperties({"likes", "dislikes"})
    public String getInstanceName(DatatypeFormatter datatypeFormatter) {
        return String.format("Likes: %s; Dislikes: %s",
                datatypeFormatter.formatLong(likes),
                datatypeFormatter.formatLong(dislikes));
    }
}