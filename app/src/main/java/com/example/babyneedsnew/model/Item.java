package com.example.babyneedsnew.model;

public class Item {


    int id;
    String itemName;
    int quantity;
    String Color;
    int item_size;
    String date_added;

    public Item() {
    }

    public Item(int id, String itemName, int quantity, String color, int item_size, String date_added) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        Color = color;
        this.item_size = item_size;
        this.date_added = date_added;
    }

    public Item(String itemName, int quantity, String color, int item_size, String date_added) {
        this.itemName = itemName;
        this.quantity = quantity;
        Color = color;
        this.item_size = item_size;
        this.date_added = date_added;
    }

    public Item(String itemName, int quantity, String color, int item_size) {
        this.itemName = itemName;
        this.quantity = quantity;
        Color = color;
        this.item_size = item_size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getItem_size() {
        return item_size;
    }

    public void setItem_size(int item_size) {
        this.item_size = item_size;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", Color='" + Color + '\'' +
                ", item_size=" + item_size +
                ", date_added='" + date_added + '\'' +
                '}';
    }
}
