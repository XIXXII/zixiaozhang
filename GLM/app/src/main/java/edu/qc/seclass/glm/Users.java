package edu.qc.seclass.glm;

import java.util.ArrayList;

public class Users {
    private int listID;
    private String listName;
//    public static ArrayList<Users> userArrayList = new ArrayList<>();

    public Users() {
    }
    public void setListID(int listID){
        this.listID = listID;
    }
    public void setName(String listName){
        this.listName = listName;
    }
    public int getListID(){
        return this.listID;
    }
    public String getListName(){
        return this.listName;
    }
    @Override
    public String toString() {
        return "Users{ListID = " + listID + ", ListName = " + listName + "}";
    }
    public Users(int listID, String listName){
        this.listID = listID;
        this.listName = listName;
    }
}
