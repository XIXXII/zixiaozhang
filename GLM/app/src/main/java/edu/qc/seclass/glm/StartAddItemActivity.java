package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StartAddItemActivity extends AppCompatActivity {
    int list_id;
    String item_name;
    String item_type;
    private DatabaseHelper DB;
    TextView selectName;
    EditText quantity;
    int current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_add_item);
        Intent intent = getIntent();
        list_id = intent.getIntExtra("ListId", 0);
        item_name = intent.getStringExtra("ItemName");
        DB = new DatabaseHelper(this);
        selectName = (TextView) findViewById(R.id.realName);
        selectName.setText(item_name);
        item_type = DB.whatType(item_name);
        quantity = (EditText) findViewById(R.id.quantity);
        current = Integer.parseInt(quantity.getText().toString());
    }

    public void backNameSearch(View view) {
        Intent backSearch = new Intent(this, AddItemActivity.class);
        backSearch.putExtra("ListId", list_id);
        startActivity(backSearch);
    }

    public void doneWithAdding(View view){
        DB.addItemToTheList(list_id, item_name, item_type, current);
        Toast.makeText(StartAddItemActivity.this, item_name + " added successfully！", Toast.LENGTH_SHORT).show();
        Intent backListDetail = new Intent(this, DetailListActivity.class);
        backListDetail.putExtra("ListId", list_id);
        startActivity(backListDetail);
    }

    public void amountDecrease(View view) {
        current = current - 1;
        if(current == 0){
            current = 1;
            quantity.setText(""+current);
            Toast.makeText(StartAddItemActivity.this, item_name + " quantity cannot be zero!", Toast.LENGTH_SHORT).show();

        }
        quantity.setText(""+current);
    }

    public void amountIncrease(View view) {
        current = current + 1;
        quantity.setText(""+current);
    }
}