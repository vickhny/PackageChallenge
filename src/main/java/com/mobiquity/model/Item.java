package com.mobiquity.model;

public final class Item {

    private Integer index;
    private Double weight;
    private Integer cost;

    public Item(Integer index, Double weight, Integer cost) {
        this.index = index;
        this.weight = weight;
        this.cost = cost;
    }

    public Integer getIndex() {
        return index;
    }

    public Double getWeight() {
        return weight;
    }

    public Integer getCost() {
        return cost;
    }


    @Override
    public String toString() {
        return "Item{" +
                "index=" + index +
                ", weight=" + weight +
                ", cost=" + cost +
                '}';
    }
}
