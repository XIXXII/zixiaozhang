package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    Dialog deleteWindow;
    Button cancelDelete, confirmDelete;
    private DatabaseHelper DB;
    UsersAdapter listsAdapter;
    private ListView listDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        deleteWindow = new Dialog(this);

        DB = new DatabaseHelper(HomePageActivity.this);

        listDisplay = (ListView)findViewById(R.id.ListDisplay);
        setListAdapter();
        selectList();
    }
    private void setListAdapter() {
        listsAdapter = new UsersAdapter(HomePageActivity.this, R.layout.display_lists, DB.getAllLists());
        listDisplay.setAdapter(listsAdapter);
    }

    private void selectList(){
        listDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Users selectedList = (Users) listDisplay.getItemAtPosition(position);
                int listID = selectedList.getListID();
                String listName = selectedList.getListName();
                Intent listDetails = new Intent(getApplicationContext(), DetailListActivity.class);
                listDetails.putExtra("ListId", listID);
                listDetails.putExtra("ListName", listName);
                startActivity(listDetails);
            }
        });
    }

    public void createNew(View view) {
        Intent myIntent = new Intent(HomePageActivity.this, CreateListActivity.class);
        startActivity(myIntent);
    }

    public void deleteAll(View view){
        deleteWindow.setContentView(R.layout.delete_all_list);
        deleteWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteWindow.show();
        cancelDelete = (Button) deleteWindow.findViewById(R.id.deleteALlCancel);
        confirmDelete = (Button) deleteWindow.findViewById(R.id.deleteALlConfirm);
        cancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWindow.dismiss();
            }
        });
        confirmDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DB.deleteAllList();
                DB.closeDB();
                Intent myIntent = new Intent(HomePageActivity.this, HomePageActivity.class);
                Toast.makeText(HomePageActivity.this, "All Lists were deleted successfully",  Toast.LENGTH_SHORT).show();
                startActivity(myIntent);
            }
        });

    }


}