package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateListActivity extends AppCompatActivity {
    private Button addListBtn;
    private EditText newList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        addListBtn = findViewById(R.id.createConfirm);
        newList = findViewById(R.id.NewList);
    }
    public void createNewList(View view){
        DatabaseHelper DB  = new DatabaseHelper(this);
        String name = newList.getText().toString();

//check the user input whether is empty or not
        if(name.replaceAll(" ", "").length()>0){
//check for the list name in the database, if there exist a list with same name, user need to customize a new list name
//after user create list successfully, user will redirect to the home page
            if(DB.searchForListName(name)){
                Toast.makeText(CreateListActivity.this, "The name already exists!", Toast.LENGTH_SHORT).show();
            }
            else {
                newList.setText("");
                DB.creatNewList(name);
                DB.close();
                Intent myIntent = new Intent(CreateListActivity.this, HomePageActivity.class);
                Toast.makeText(CreateListActivity.this, name + " added", Toast.LENGTH_SHORT).show();
                startActivity(myIntent);
            }
        }
        else{
            Toast.makeText(CreateListActivity.this, "List Name Cannot Be Empty", Toast.LENGTH_SHORT).show();
        }
    }
//function that will be call when user decide not to create the new list and click on the cancel button
    public void cancelCreateList(View view){
        Intent backHome = new Intent(CreateListActivity.this, HomePageActivity.class);
        startActivity(backHome);
    }
}