package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchNameActivity extends AppCompatActivity implements SearchFilterAdapter.OnItemClickListener {
    private SearchFilterAdapter searchFilterAdapter;
    private DatabaseHelper DB;
    private List<Item> itemCollection;
    SearchView searchView;
    Dialog addNewItemToList, createNewItemToDB;
    Button confirmADD, cancelAdd, confirmCreate, cancelCreate;
    TextView askUserToAdd;
    int list_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_name);
        DB = new DatabaseHelper(this);
        Intent intent = getIntent();
        list_id = intent.getIntExtra("ListId", 0);
        buildDataInRV();
        setUpView();
        searchView = findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchFilterAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    private void setUpView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        searchFilterAdapter = new SearchFilterAdapter(itemCollection, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(searchFilterAdapter);

    }

    public void buildDataInRV() {
        itemCollection = new ArrayList<>();
        List<Item> items = new ArrayList<>();

        items.addAll(DB.allItemsInDB());
        for(int i = 0; i < items.size(); i++){
            itemCollection.add((Item) items.get(i));
        }
    }
    public void backPrevious(View view){
        Intent backList = new Intent(this, AddItemActivity.class);
        backList.putExtra("ListId", list_id);
        startActivity(backList);
    }
    @Override
    public void onItemClick(View view, int position) {
        if(itemCollection.get(position).getItemTypeName().equals("NON-CATEGORY")){
            createNewItem(view);
        }
        else{
            addtoList(view, itemCollection.get(position));
        }
    }

    public void addtoList(View view, Item item){
        addNewItemToList = new Dialog(this);
        addNewItemToList.setContentView(R.layout.askfor_usertoadd);
        addNewItemToList.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addNewItemToList.show();
        String itemName = item.getItemName();
        confirmADD = (Button) addNewItemToList.findViewById(R.id.confirmAddItem);
        cancelAdd = (Button)  addNewItemToList.findViewById(R.id.cancelAddItem);
        askUserToAdd = (TextView) addNewItemToList.findViewById(R.id.askFor);
        askUserToAdd.setText("Do you want to add <" + itemName + "> to your list?");
        cancelAdd.setOnClickListener(v -> addNewItemToList.dismiss());
        confirmADD.setOnClickListener(v -> {
            if(DB.deosExist(list_id, itemName)){
                Toast.makeText(SearchNameActivity.this, itemName + " already in the list!", Toast.LENGTH_SHORT).show();
                addNewItemToList.dismiss();
            }
            else {
                Intent myIntent = new Intent(SearchNameActivity.this, StartAddItemActivity.class);
                myIntent.putExtra("ListId", list_id);
                myIntent.putExtra("ItemName", itemName);
                startActivity(myIntent);
            }
        });
    }
    public void createNewItem(View view){
        createNewItemToDB = new Dialog(this);
        createNewItemToDB.setContentView(R.layout.askfor_usertocreate);
        createNewItemToDB.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        createNewItemToDB.show();
        confirmCreate = (Button) createNewItemToDB.findViewById(R.id.confirmCreateItem);
        cancelCreate = (Button) createNewItemToDB.findViewById(R.id.cancelCreateItem);
        cancelCreate.setOnClickListener(v -> createNewItemToDB.dismiss());
        confirmCreate.setOnClickListener(v -> {
            Intent myIntent = new Intent(SearchNameActivity.this, CreateNewItemActivity.class);
            myIntent.putExtra("ListId", list_id);
            startActivity(myIntent);
        });
    }
}