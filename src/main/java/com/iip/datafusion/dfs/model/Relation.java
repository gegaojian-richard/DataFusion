package com.iip.datafusion.dfs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by GeGaojian on 2017/12/22.
 */

public class Relation {
    @JsonProperty("left")
    String left;
    @JsonProperty("right")
    String right;

    public Relation() {
        super();
    }

    @JsonCreator
    public Relation(@JsonProperty("left") String left, @JsonProperty("right") String right) {
        this.left = left;
        this.right = right;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "left='" + left + '\'' +
                ", right='" + right + '\'' +
                '}';
    }
}
