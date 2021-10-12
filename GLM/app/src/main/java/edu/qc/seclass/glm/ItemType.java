package edu.qc.seclass.glm;

public class ItemType {
    private int typeID;
    private String typeName;

    public ItemType() {
    }
    public void setTypeID(int typeID){
        this.typeID = typeID;
    }
    public void setTypeName(String typeName){
        this.typeName = typeName;
    }
    public int getTypeID(){
        return this.typeID;
    }
    public String getTypeName(){
        return this.typeName;
    }
    @Override
    public String toString() {
        return "Users{TypeID = " + typeID + ", TypeName = " + typeName + "}";
    }
    public ItemType(int typeID, String typeName){
        this.typeID = typeID;
        this.typeName = typeName;
    }
}
