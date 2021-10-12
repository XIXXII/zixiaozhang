package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;

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

public class EditItemActivity extends AppCompatActivity {
    int listid;
    String itemforEdit;
    int currentQuantity;

    Button finishEdit, yesDelete, noDelete, selectToDelete, back_select_list;
    EditText amountforChange;
    TextView nameforEdit, deleteSelectItem ;
    int currentAmount;
    private DatabaseHelper DB;
    Dialog askForDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Intent intent = getIntent();
        listid = intent.getIntExtra("ListId", 0);
        itemforEdit = intent.getStringExtra("ItemName");
        currentQuantity = intent.getIntExtra("CurrentQuantity", 0);
        amountforChange = (EditText) findViewById(R.id.amountForChange);
        amountforChange.setText(""+currentQuantity);
        DB = new DatabaseHelper(this);
        nameforEdit = (TextView) findViewById(R.id.nameforEdit);
        nameforEdit.setText(itemforEdit);
        currentAmount = Integer.parseInt(amountforChange.getText().toString());
        back_select_list = (Button) findViewById(R.id.backSelectList);
        back_select_list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(EditItemActivity.this, DetailListActivity.class);
                edit.putExtra("ListId", listid);
                startActivity(edit);
            }
        });
        finishEdit = (Button) findViewById(R.id.doneEditing);
        finishEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.changeQuantity(listid, itemforEdit, currentAmount);
                Intent edit = new Intent(EditItemActivity.this, DetailListActivity.class);
                edit.putExtra("ListId", listid);
                startActivity(edit);
            }
        });
        selectToDelete = (Button) findViewById(R.id.deleteSelectItem);
        selectToDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForDelete = new Dialog(EditItemActivity.this);
                askForDelete.setContentView(R.layout.askfor_delete_item);
                askForDelete.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                askForDelete.show();
                deleteSelectItem = (TextView) askForDelete.findViewById(R.id.askDelete);
                deleteSelectItem.setText("Are you sure you want to delete <" + itemforEdit + "> from the list?");
                yesDelete = (Button) askForDelete.findViewById(R.id.confirmDeleteItem);
                noDelete = (Button) askForDelete.findViewById(R.id.cancelDeleteItem);
                noDelete.setOnClickListener(v -> askForDelete.dismiss());
                yesDelete.setOnClickListener(v -> {
                    DB.deleteItemFromList(listid, itemforEdit);
                    DB.closeDB();
                    Intent edit = new Intent(EditItemActivity.this, DetailListActivity.class);
                    edit.putExtra("ListId", listid);
                    Toast.makeText(EditItemActivity.this, itemforEdit+ " removed from the list", Toast.LENGTH_SHORT).show();
                    startActivity(edit);

                });
            }
        });

    }

    public void amountDec(View view) {
        currentAmount = currentAmount - 1;
        if(currentAmount == 0){
            currentAmount = 1;
            amountforChange.setText(""+currentAmount);
            Toast.makeText(EditItemActivity.this, "Quantity cannot be zero!", Toast.LENGTH_SHORT).show();

        }
        amountforChange.setText(""+currentAmount);
    }

    public void amountInc(View view) {
        currentAmount = currentAmount + 1;
        amountforChange.setText(""+currentAmount);
    }
}