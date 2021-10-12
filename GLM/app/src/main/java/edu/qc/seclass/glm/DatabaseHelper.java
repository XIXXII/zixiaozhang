package edu.qc.seclass.glm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GroceryList.db";

//table and column name for table User
    private static final String USERS_TABLE = "Users";
    private static final String LISTID_COL = "ListID";
    private static final String LISTNAME_COL = "ListName";

//table and column name for table GroceryLists
    private static final String GROCERYLISTS_TABLE = "GroceryLists";
    private static final String GROCERYLISTSID_COL = "GroceryListsID";
    private static final String SELECTLISTID_COL = "SelectListID";
    private static final String SELECTITEMNAME_COL = "SelectItemName";
    private static final String SELECTITEMTYPE_COL = "SelectItemType";
    private static final String CHECKMARK_COL = "ItemCheckMark";
    private static final String ITEMQUANTITY_COL = "Quantity";

//table and column name for table Item
    private static final String ITEM_TABLE = "Item";
    private static final String ITEMID_COL = "ItemID";
    private static final String ITEMNAME_COL = "ItemName";
    private static final String ITEMTYPENAME_COL = "ItemTypeName";

//table and column name for table ItemType
    private static final String ITEMTYPE_TABLE = "ItemType";
    private static final String TYPEID_COL = "TypeID";
    private static final String TYPENAME_COL = "TypeName";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
//create table for users
        String createListTable = "CREATE TABLE " + USERS_TABLE + " (" + LISTID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LISTNAME_COL + " TEXT) ";;
        DB.execSQL(createListTable);

//create table for item types
        String createTypeTable = "CREATE TABLE " + ITEMTYPE_TABLE + " (TYPEID_COL INTEGER PRIMARY KEY AUTOINCREMENT, TYPENAME_COL TEXT)";
        DB.execSQL(createTypeTable);

//create table for items
        String createItemTable = "CREATE TABLE " + ITEM_TABLE + " (ITEMID_COL INTEGER PRIMARY KEY AUTOINCREMENT, ITEMNAME_COL TEXT, ITEMTYPENAME_COL TEXT)";
        DB.execSQL(createItemTable);

//create table for grocery list
        String createGroceryListTable = "CREATE TABLE " + GROCERYLISTS_TABLE + " (GROCERYLISTSID_COL INTEGER PRIMARY KEY AUTOINCREMENT, SELECTLISTID_COL INTEGER, SELECTITEMNAME_COL TEXT, SELECTITEMTYPE_COL TEXT, CHECKMARK_COL INTEGER DEFAULT 0, ITEMQUANTITY_COL INTEGER)";
        DB.execSQL(createGroceryListTable);

//insert item type to table itemType
        DB.execSQL("INSERT INTO " + ITEMTYPE_TABLE + "(TYPENAME_COL) VALUES('Bakery')");
        DB.execSQL("INSERT INTO " + ITEMTYPE_TABLE + "(TYPENAME_COL) VALUES('Beverages')");
        DB.execSQL("INSERT INTO " + ITEMTYPE_TABLE + "(TYPENAME_COL) VALUES('Dairy&Eggs')");
        DB.execSQL("INSERT INTO " + ITEMTYPE_TABLE + "(TYPENAME_COL) VALUES('Household')");
        DB.execSQL("INSERT INTO " + ITEMTYPE_TABLE + "(TYPENAME_COL) VALUES('Meat&Seafood')");
        DB.execSQL("INSERT INTO " + ITEMTYPE_TABLE + "(TYPENAME_COL) VALUES('Other')");
        DB.execSQL("INSERT INTO " + ITEMTYPE_TABLE + "(TYPENAME_COL) VALUES('Pantry')");
        DB.execSQL("INSERT INTO " + ITEMTYPE_TABLE + "(TYPENAME_COL) VALUES('Produce')");
//insert item to table item
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Bagels', 'Bakery')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Bread', 'Bakery')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Buns', 'Bakery')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Cake', 'Bakery')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Flour', 'Bakery')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Yeast', 'Bakery')");

        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Coffee', 'Beverages')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Soda', 'Beverages')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Sparking Water', 'Beverages')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Tea', 'Beverages')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Water', 'Beverages')");

        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Butter', 'Dairy&Eggs')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Cheese', 'Dairy&Eggs')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Cream', 'Dairy&Eggs')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Eggs', 'Dairy&Eggs')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Yogurt', 'Dairy&Eggs')");

        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Batteries', 'Household')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Paper Towels', 'Household')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Soap', 'Household')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Trash Bags', 'Household')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Toilet Paper', 'Household')");

        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Beef', 'Meat&Seafood')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Chicken', 'Meat&Seafood')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Ham', 'Meat&Seafood')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Lobster', 'Meat&Seafood')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Pork', 'Meat&Seafood')");

        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Alarm clock', 'Other')");

        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Chili Powder', 'Pantry')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Cumin', 'Pantry')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Pepper', 'Pantry')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Olive Oil', 'Pantry')");

        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Avocado', 'Produce')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Garlic', 'Produce')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Mango', 'Produce')");
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('Onion', 'Produce')");
    }

    @Override
//We will not upgrade our database to another version
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        DB.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE);
        DB.execSQL("DROP TABLE IF EXISTS " + ITEMTYPE_TABLE);
        DB.execSQL("DROP TABLE IF EXISTS " + GROCERYLISTS_TABLE);
        onCreate(DB);
    }
//Create list
    public long creatNewList(String newListName){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LISTNAME_COL, newListName);
        long result = DB.insert(USERS_TABLE, null, contentValues);
        return result;
    }
//Get all lists
    public List<Users> getAllLists(){
        SQLiteDatabase DB = this.getReadableDatabase();
        List<Users> user = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + USERS_TABLE;
        Cursor cursor = DB.rawQuery(selectQuery, null);
        while(cursor.moveToNext()){
            Users lists = new Users();
            lists.setListID((cursor.getInt(cursor.getColumnIndex(LISTID_COL))));
            lists.setName((cursor.getString(cursor.getColumnIndex(LISTNAME_COL))));
            user.add(lists);
        }
        cursor.close();
        return user;
    }
//check for existing
    public boolean searchForListName(String newListName){
        SQLiteDatabase DB = this.getReadableDatabase();
        String selectQuery = "SELECT COUNT(*) FROM " + USERS_TABLE + " WHERE " + LISTNAME_COL   + " = '" + newListName + "' COLLATE NOCASE";
        Cursor cursor = DB.rawQuery(selectQuery, null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }
//Rename a list
    public boolean renameList(int ListID, String newListName){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LISTNAME_COL, newListName);
        return DB.update(USERS_TABLE, contentValues, LISTID_COL + " = " + ListID, null) > 0;
    }
//Delete a selecting list
    public boolean deleteList(int ListID){
        SQLiteDatabase DB = this.getWritableDatabase();
        if(DB.delete(USERS_TABLE, LISTID_COL + "=" + ListID, null)>0){
            if(checkForList(ListID)){
                DB.execSQL("DELETE FROM " + GROCERYLISTS_TABLE + " WHERE SELECTLISTID_COL " + " = " + ListID);
            }
            return true;
        }
        return false;
    }
//Delete all lists
    public boolean deleteAllList(){
        SQLiteDatabase DB = this.getWritableDatabase();
        if(DB.delete(USERS_TABLE, null, null)>0){
            if(checkForAllList()){
                return DB.delete(GROCERYLISTS_TABLE, null, null)>0;
            }
            return true;
        }
        return false;
    }
//Check if there has item in selecting list
    public boolean checkForList(int listID){
        SQLiteDatabase DB = this.getReadableDatabase();
        String selectQuery = "SELECT COUNT(*) FROM " + GROCERYLISTS_TABLE + " WHERE SELECTLISTID_COL"  + " = " + listID;
        Cursor cursor = DB.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        return count > 0;
    }
//Check if there has item in all lists
    public boolean checkForAllList(){
        SQLiteDatabase DB = this.getReadableDatabase();
        String selectQuery = "SELECT COUNT(*) FROM " + GROCERYLISTS_TABLE;
        Cursor cursor = DB.rawQuery(selectQuery, null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }
//Get the name for selected list
    public String selectListName(int list_id) {
        SQLiteDatabase DB = this.getReadableDatabase();
        String selectQuery = "SELECT " + LISTNAME_COL + " FROM " + USERS_TABLE + " WHERE " + LISTID_COL + " = '" + list_id + "'";
        Cursor cursor = DB.rawQuery(selectQuery, null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            return "NON FOUND";
        }
        String listName = cursor.getString(0);
        cursor.close();
        return listName;
    }
//Get type
    public List<ItemType> typeIDcollect(){
        SQLiteDatabase DB = this.getReadableDatabase();
        List<ItemType> type = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + ITEMTYPE_TABLE;
        Cursor cursor = DB.rawQuery(selectQuery, null);
        while(cursor.moveToNext()){
            ItemType typeCollect = new ItemType();
            typeCollect.setTypeID((cursor.getInt(0)));
            typeCollect.setTypeName((cursor.getString(1)));
            type.add(typeCollect);
        }
        cursor.close();
        return type;
    }
//get types from a selected list
    public ArrayList<String> currentType(int list_id) {
        SQLiteDatabase DB = this.getReadableDatabase();
        ArrayList<String> type = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT SELECTITEMTYPE_COL FROM " + GROCERYLISTS_TABLE + " WHERE SELECTLISTID_COL"  + " = '" + list_id + "' ORDER BY SELECTITEMTYPE_COL";
        Cursor cursor = DB.rawQuery(selectQuery, null);
        while(cursor.moveToNext()){
            type.add((cursor.getString(0)));
        }
        cursor.close();
        return type;
    }
//get all item under one category
    public ArrayList<String> underCategory(String categoryName){
        SQLiteDatabase DB = this.getReadableDatabase();
        ArrayList<String> items = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + ITEM_TABLE + " WHERE ITEMTYPENAME_COL"  + " = '" + categoryName + "'";
        Cursor cursor = DB.rawQuery(selectQuery, null);
        while(cursor.moveToNext()){
            items.add((cursor.getString(1)));
        }
        cursor.close();
        return items;
    }
//get all items from database
    public List<Item> allItemsInDB(){
        SQLiteDatabase DB = this.getReadableDatabase();
        List<Item> items = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + ITEM_TABLE;
        Cursor cursor = DB.rawQuery(selectQuery, null);
        while(cursor.moveToNext()){
            Item itemCollect = new Item();
            itemCollect.setItemID((cursor.getInt(0)));
            itemCollect.setItemName((cursor.getString(1)));
            itemCollect.setItemTypeName((cursor.getString(2)));
            items.add(itemCollect);
        }
        cursor.close();
        return items;
    }
//get all items from a selected list
    public List<GroceryLists> underExistingCategory(String typeName, int listID) {
        SQLiteDatabase DB = this.getReadableDatabase();
        List<GroceryLists> items = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + GROCERYLISTS_TABLE + " WHERE SELECTITEMTYPE_COL"  + " = '" + typeName + "' AND SELECTLISTID_COL" + " = " + listID;
        Cursor cursor = DB.rawQuery(selectQuery, null);
        while(cursor.moveToNext()){
            GroceryLists itemUnder = new GroceryLists();
            itemUnder.setGroceryListsID((cursor.getInt(0)));
            itemUnder.setSelectListID((cursor.getInt(1)));
            itemUnder.setSelectItemName((cursor.getString(2)));
            itemUnder.setSelectItemTypeName((cursor.getString(3)));
            itemUnder.setItemCheckMark((cursor.getInt(4)));
            itemUnder.setQuantity((cursor.getInt(5)));
            items.add(itemUnder);
        }
        cursor.close();
        return items;
    }
//Get item type for item that are add to the grocery list
    public String whatType(String itemName){
        SQLiteDatabase DB = this.getReadableDatabase();
        String selectQuery = "SELECT ITEMTYPENAME_COL FROM " + ITEM_TABLE + " WHERE ITEMNAME_COL"  + " = '" + itemName + "'";
        Cursor cursor = DB.rawQuery(selectQuery, null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            return "NON FOUND";
        }
        String listName = cursor.getString(0);
        cursor.close();
        return listName;
    }
//Create item
    public void createNewItem(String newItemName, String typeName){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("INSERT INTO " + ITEM_TABLE + "(ITEMNAME_COL, ITEMTYPENAME_COL) VALUES('" + newItemName+ "', '"+ typeName +"')");
    }
 //Check new item
    public boolean newItem(String itemName){
        SQLiteDatabase DB = this.getReadableDatabase();
        String selectQuery = "SELECT COUNT(*) FROM " + ITEM_TABLE + " WHERE ITEMNAME_COL"  + " = '" + itemName + "' COLLATE NOCASE";
        Cursor cursor = DB.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        return count > 0;
    }
//add item to the list
    public void addItemToTheList(int selectList, String selectItem, String selectType, int quantity){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("INSERT INTO " + GROCERYLISTS_TABLE + "(SELECTLISTID_COL, SELECTITEMNAME_COL, SELECTITEMTYPE_COL, CHECKMARK_COL, ITEMQUANTITY_COL) VALUES(" + selectList+ ", '"+ selectItem + "', '"+selectType+"'," + 0 + ", "+ quantity+")");
    }
//Check whether item exist in seleted list
    public boolean deosExist(int listID, String itemName){
        SQLiteDatabase DB = this.getReadableDatabase();
        String selectQuery = "SELECT COUNT(*) FROM " + GROCERYLISTS_TABLE + " WHERE SELECTLISTID_COL"  + " = " + listID + " AND SELECTITEMNAME_COL" + " = '" + itemName + "'";
        Cursor cursor = DB.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        return count > 0;
    }

//check off all item in a selected list
    public void checkAllItem(int listID) {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("UPDATE " + GROCERYLISTS_TABLE +" SET CHECKMARK_COL"+ " = " + 1 + " WHERE SELECTLISTID_COL" + " = " + listID);

    }
//check off one item in a selected list
    public void setCheckBox(int listID, String itemName) {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("UPDATE " + GROCERYLISTS_TABLE +" SET CHECKMARK_COL"+ " = " + 1 + " WHERE SELECTLISTID_COL" + " = " + listID + " AND SELECTITEMNAME_COL" + " = '" + itemName + "'");
    }

//clear check off of all item in a selected list
    public void clearCheckAll(int list_id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("UPDATE " + GROCERYLISTS_TABLE +" SET CHECKMARK_COL"+ " = " + 0 + " WHERE SELECTLISTID_COL" + " = " + list_id);
    }
//clear check off of one item in a selected list
    public void clearCheckBox(int listID, String itemName) {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("UPDATE " + GROCERYLISTS_TABLE +" SET CHECKMARK_COL"+ " = " + 0 + " WHERE SELECTLISTID_COL" + " = " + listID + " AND SELECTITEMNAME_COL" + " = '" + itemName + "'");
    }
//Update quantity of an item in a seleted list
    public void changeQuantity(int listid, String itemforEdit, int currentAmount) {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("UPDATE " + GROCERYLISTS_TABLE +" SET ITEMQUANTITY_COL"+ " = " + currentAmount + " WHERE SELECTLISTID_COL" + " = " + listid + " AND SELECTITEMNAME_COL" + " = '" + itemforEdit + "'");
    }

//Delete one item from a select list
    public void deleteItemFromList(int listID, String itemName){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("DELETE FROM " + GROCERYLISTS_TABLE + " WHERE SELECTLISTID_COL" + " = " + listID + " AND SELECTITEMNAME_COL" + " = '" + itemName + "'");

    }
//Delete all items from a select list
public void deleteAllItemFromList(int listID){
    SQLiteDatabase DB = this.getWritableDatabase();
    DB.execSQL("DELETE FROM " + GROCERYLISTS_TABLE + " WHERE SELECTLISTID_COL" + " = " + listID);

}
    public void closeDB() {
        SQLiteDatabase DB = this.getReadableDatabase();
        if (DB != null && DB.isOpen())
            DB.close();
    }


}

