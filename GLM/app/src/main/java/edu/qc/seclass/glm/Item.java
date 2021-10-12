package edu.qc.seclass.glm;

public class Item {
    private int itemID;
    private String itemName;
    private String itemTypeName;

    public Item() {
    }
    public void setItemID(int itemID){
        this.itemID = itemID;
    }
    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    public void setItemTypeName(String itemTypeName){
        this.itemTypeName = itemTypeName;
    }
    public int getItemID(){
        return this.itemID;
    }
    public String getItemName(){
        return this.itemName;
    }
    public String getItemTypeName(){
        return this.itemTypeName;
    }
    @Override
    public String toString() {
        return "Item{ItemID = " + itemID + ", ItemName = " + itemName + ", ItemTypeName = " + itemTypeName + "}";
    }
    public Item(int itemID, String itemName, String itemTypeName){
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemTypeName = itemTypeName;
    }
}

