package com.mobiquity.model;

import java.util.List;

public class ProcessedPackage {

    public List<Item> items;
    public int totalCost;

    public ProcessedPackage(List<Item> items, int totalCost) {
        this.items = items;
        this.totalCost = totalCost;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "ProcessedPackage{" +
                "items=" + items +
                ", totalCost=" + totalCost +
                '}';
    }
}
