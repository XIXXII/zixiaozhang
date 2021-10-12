package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateNewItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    int list_id;
    private DatabaseHelper DB;
    EditText userInput;
    String typeName;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_item);
        DB = new DatabaseHelper(this);
        userInput = (EditText) findViewById(R.id.realName);
        Intent intent = getIntent();
        list_id = intent.getIntExtra("ListId", 0);
        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> categories = new ArrayList<String>();
        categories.add("Bakery");
        categories.add("Beverages");
        categories.add("Dairy&Eggs");
        categories.add("Household");
        categories.add("Other");
        categories.add("Pantry");
        categories.add("Produce");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void backtoSearch(View v){
        Intent backSearch = new Intent(this, SearchNameActivity.class);
        backSearch.putExtra("ListId", list_id);
        startActivity(backSearch);
    }
//function that will be call when user click on finish to adding the item to the list
    public void finishCreate(View view) {
        String input = userInput.getText().toString();
        typeName = (String) spinner.getSelectedItem().toString();
        if(input.replaceAll(" ", "").length()>0){
            if(DB.newItem(input)){
                Toast.makeText(CreateNewItemActivity.this, "This item already exists!", Toast.LENGTH_SHORT).show();
            }
            else {
                userInput.setText("");
                DB.createNewItem(input, typeName);
                DB.close();
                Intent myIntent = new Intent(CreateNewItemActivity.this, SearchNameActivity.class);
                myIntent.putExtra("ListId", list_id);
                Toast.makeText(CreateNewItemActivity.this, input + " added", Toast.LENGTH_SHORT).show();
                startActivity(myIntent);
            }
        }
        else{
            Toast.makeText(CreateNewItemActivity.this, "Item Name Cannot Be Empty", Toast.LENGTH_SHORT).show();
        }


    }
}