package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    HierarchicaListAdapter hierarchicalAdapter;
    List<String> categoryGroup;
    HashMap<String, List<String>> listItems;
    List<ItemType> allTypes;
    private DatabaseHelper DB;
    Dialog addNewItemToList;
    Button confirmADD, cancelAdd;
    TextView askUserToAdd;
    int list_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Intent intent = getIntent();
        list_id = intent.getIntExtra("ListId", 0);
        DB = new DatabaseHelper(this);
        expandableListView = (ExpandableListView) findViewById(R.id.hierarchical_list);
//to establish the expandable list with all categories and items in the item database
        establishList();
        hierarchicalAdapter = new HierarchicaListAdapter(this, categoryGroup, listItems);
        expandableListView.setAdapter(hierarchicalAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int parentPosition, int childPosition, long l) {
//when the user click on the item under each category, user will be ask to for adding this item to the list
                addNewItemToList = new Dialog(AddItemActivity.this);
                addNewItemToList.setContentView(R.layout.askfor_usertoadd);
                addNewItemToList.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//show the modal window to ask user whether that is the item they intended to add
                addNewItemToList.show();
                String itemName = listItems.get(categoryGroup.get(parentPosition)).get(childPosition);
                confirmADD = (Button) addNewItemToList.findViewById(R.id.confirmAddItem);
                cancelAdd = (Button)  addNewItemToList.findViewById(R.id.cancelAddItem);
                askUserToAdd = (TextView) addNewItemToList.findViewById(R.id.askFor);
                askUserToAdd.setText("Do you want to add <" + itemName + "> to your list?");
                cancelAdd.setOnClickListener(v -> addNewItemToList.dismiss());
                confirmADD.setOnClickListener(v -> {
                    if(DB.deosExist(list_id, itemName)){
                        Toast.makeText(AddItemActivity.this, itemName + " already in the list!", Toast.LENGTH_SHORT).show();
                        addNewItemToList.dismiss();
                    }
                    else {
                        Intent myIntent = new Intent(AddItemActivity.this, StartAddItemActivity.class);
                        myIntent.putExtra("ListId", list_id);
                        myIntent.putExtra("ItemName", itemName);
                        startActivity(myIntent);
                    }
                });
                return false;
            }
        });
    }
//function that will wi call when user click on the back arrow, go to the previous page
    public void backDetailPage(View view){
        Intent backList = new Intent(this, DetailListActivity.class);
        backList.putExtra("ListId", list_id);
        startActivity(backList);
    }
//function that will wi call when user click on the search bar, go to the search by name page
    public void toSearchPage(View view){
        Intent toSearch = new Intent(this, SearchNameActivity.class);
        toSearch.putExtra("ListId", list_id);
        startActivity(toSearch);
    }
    private void establishList() {
        categoryGroup = new ArrayList<>();
        allTypes = new ArrayList<>();
        listItems = new HashMap<String, List<String>>();
        List<String> typeOne = new ArrayList<String>();
        List<String> typeTwo = new ArrayList<String>();
        List<String> typeThree = new ArrayList<String>();
        List<String> typeFour = new ArrayList<String>();
        List<String> typeFive = new ArrayList<String>();
        List<String> typeSix = new ArrayList<String>();
        List<String> typeSeven = new ArrayList<String>();
        List<String> typeEight = new ArrayList<String>();

        allTypes = DB.typeIDcollect();
        for(int i = 0; i < allTypes.size(); i++){
            categoryGroup.add(allTypes.get(i).getTypeName());
        }
//to find item under each specific type
        typeOne.addAll(DB.underCategory("Bakery"));
        typeTwo.addAll(DB.underCategory("Beverages"));
        typeThree.addAll(DB.underCategory("Dairy&Eggs"));
        typeFour.addAll(DB.underCategory("Household"));
        typeFive.addAll(DB.underCategory("Meat&Seafood"));
        typeSix.addAll(DB.underCategory("Other"));
        typeSeven.addAll(DB.underCategory("Pantry"));
        typeEight.addAll(DB.underCategory("Produce"));

//put data on the expandable list (group position and children position)
        listItems.put(categoryGroup.get(0), typeOne);
        listItems.put(categoryGroup.get(1), typeTwo);
        listItems.put(categoryGroup.get(2), typeThree);
        listItems.put(categoryGroup.get(3), typeFour);
        listItems.put(categoryGroup.get(4), typeFive);
        listItems.put(categoryGroup.get(5), typeSix);
        listItems.put(categoryGroup.get(6), typeSeven);
        listItems.put(categoryGroup.get(7), typeEight);
    }
}