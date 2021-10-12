package edu.qc.seclass.glm;

public class GroceryLists {
    private int groceryListsID;
    private int selectListID;
    private String selectItemName;
    private String selectItemTypeName;
    private int itemCheckMark;
    private int quantity;

    public GroceryLists() {
    }
    //setters
    public void setGroceryListsID(int groceryListsID) {
        this.groceryListsID = groceryListsID;
    }
    public void setSelectListID(int selectListID) {
        this.selectListID = selectListID;
    }
    public void setSelectItemName(String selectItemName) {
        this.selectItemName = selectItemName;
    }
    public void setSelectItemTypeName(String selectItemTypeName) {
        this.selectItemTypeName = selectItemTypeName;
    }
    public void setItemCheckMark(int itemCheckMark) {
        this.itemCheckMark = itemCheckMark;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    //getters
    public int getGroceryListsID() {
        return this.groceryListsID;
    }
    public int getSelectListID() {
        return this.selectListID;
    }
    public String getSelectItemName() {
        return this.selectItemName;
    }
    public String getSelectItemTypeName() {
        return this.selectItemTypeName;
    }
    public int getItemCheckMark() {
        return this.itemCheckMark;
    }
    public int getQuantity() {
        return this.quantity;
    }
    @Override
    public String toString() {
        return "GroceryLists{GroceryListsID = " + groceryListsID + ", SelectListID = " + selectListID + ", SelectItemID = " + ", SelectItemName = " + selectItemName + ", SelectItemTypeName = " + selectItemTypeName + ", ItemCheckMark = " + itemCheckMark + ", Quantity = " + quantity + "}";
    }
    public GroceryLists(int groceryListsID, int selectListID, String selectItemName, String selectItemTypeName, int ItemCheckMark, int quantity) {
        this.groceryListsID = groceryListsID;
        this.selectListID = selectListID;
        this.selectItemName = selectItemName;
        this.selectItemTypeName = selectItemTypeName;
        this.itemCheckMark = ItemCheckMark;
        this.quantity = quantity;
    }

}

