package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailListActivity extends AppCompatActivity {
    Button cancelDeleteAll, confirmDeleteAll;
    Button cancelDelete, confirmDelete;
    Button cancelRename, confirmRename;
    EditText newName;
    TextView header;
    ExpandableListView expandableListView;
    ListDetailsDisplayAdapter itemDisplayAdpater;
    List<String> typeGroup;
    List<String> existingTypes;
    HashMap<String, List<GroceryLists>> typeItems;
    private DatabaseHelper DB;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    int list_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        DB = new DatabaseHelper(this);
        Intent intent = getIntent();
        list_id = intent.getIntExtra("ListId", 0);
        String list_name = DB.selectListName(list_id);
        header = (TextView)findViewById(R.id.detailListName);
        header.setText(list_name);

        expandableListView = (ExpandableListView) findViewById(R.id.type_item);
        establishData();

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int parentPosition, int childPosition, long l) {

                return false;
            }
        });
    }

    private void establishData() {
        typeGroup = new ArrayList<>();
        existingTypes = new ArrayList<>();
        typeItems = new HashMap<String, List<GroceryLists>>();

        existingTypes = DB.currentType(list_id);
        List<GroceryLists> typeOne = new ArrayList<GroceryLists>();
        List<GroceryLists> typeTwo = new ArrayList<GroceryLists>();
        List<GroceryLists> typeThree = new ArrayList<GroceryLists>();
        List<GroceryLists> typeFour = new ArrayList<GroceryLists>();
        List<GroceryLists> typeFive = new ArrayList<GroceryLists>();
        List<GroceryLists> typeSix = new ArrayList<GroceryLists>();
        List<GroceryLists> typeSeven = new ArrayList<GroceryLists>();
        List<GroceryLists> typeEight = new ArrayList<GroceryLists>();
        for(int i = 0; i < existingTypes.size(); i++){
            typeGroup.add(existingTypes.get(i));
            switch(i) {
                case 0:
                    typeOne.addAll(DB.underExistingCategory(existingTypes.get(i), list_id));
                    break;
                case 1:
                    typeTwo.addAll(DB.underExistingCategory(existingTypes.get(i), list_id));
                    break;
                case 2:
                    typeThree.addAll(DB.underExistingCategory(existingTypes.get(i), list_id));
                    break;
                case 3:
                    typeFour.addAll(DB.underExistingCategory(existingTypes.get(i), list_id));
                    break;
                case 4:
                    typeFive.addAll(DB.underExistingCategory(existingTypes.get(i), list_id));
                    break;
                case 5:
                    typeSix.addAll(DB.underExistingCategory(existingTypes.get(i), list_id));
                    break;
                case 6:
                    typeSeven.addAll(DB.underExistingCategory(existingTypes.get(i), list_id));
                    break;
                case 7:
                    typeEight.addAll(DB.underExistingCategory(existingTypes.get(i), list_id));
                    break;
            }
        }
        for(int j = 0; j <  typeGroup.size(); j++){
            switch(j) {
                case 0:
                    typeItems.put(typeGroup.get(j), typeOne);
                    break;
                case 1:
                    typeItems.put(typeGroup.get(j), typeTwo);
                    break;
                case 2:
                    typeItems.put(typeGroup.get(j), typeThree);
                    break;
                case 3:
                    typeItems.put(typeGroup.get(j), typeFour);
                    break;
                case 4:
                    typeItems.put(typeGroup.get(j), typeFive);
                    break;
                case 5:
                    typeItems.put(typeGroup.get(j),typeSix);
                    break;
                case 6:
                    typeItems.put(typeGroup.get(j), typeSeven);
                    break;
                case 7:
                    typeItems.put(typeGroup.get(j),typeEight);
                    break;
            }
        }
        itemDisplayAdpater = new ListDetailsDisplayAdapter(this, typeGroup, typeItems);
        expandableListView.setAdapter(itemDisplayAdpater);

    }

    public void cancelManagerList(View view){
        Intent backHome = new Intent(DetailListActivity.this, HomePageActivity.class);
        startActivity(backHome);
    }
    public void showManageMenu(View v){
        PopupMenu popup = new PopupMenu(this, v);
        v.setOnTouchListener(popup.getDragToOpenListener());
        popup.inflate(R.menu.navigation_menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popup.setForceShowIcon(true);
        }
        popup.show();

    }
    public void selectionOnPopupMenu(MenuItem selection){
        switch(selection.getItemId()){
            case R.id.renameBtn:
                showRenamePopUp();
                break;
            case R.id.checkAllBtn:
                checkAllButton();
                break;
            case R.id.uncheckBtn:
                unCheckAllButton();
                break;
            case R.id.deleteBtn:
                showDeletePopUp();
                break;
            case R.id.deleteAllItemBtn:
                deleteAllItemFromList();
                break;
            default:
                break;
        }
    }
    private void deleteAllItemFromList() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View deleteAllItemPop = getLayoutInflater().inflate(R.layout.delete_all_item, null);
        cancelDeleteAll = (Button) deleteAllItemPop.findViewById(R.id.removeAllCancel);
        confirmDeleteAll = (Button) deleteAllItemPop.findViewById(R.id.removeAllConfirm);
        dialogBuilder.setView(deleteAllItemPop);
        dialog = dialogBuilder.create();
        dialog.show();
        cancelDeleteAll.setOnClickListener(v -> dialog.dismiss());
        confirmDeleteAll.setOnClickListener(v -> {
            DB.deleteAllItemFromList(list_id);
            DB.closeDB();
            establishData();
            itemDisplayAdpater.notifyDataSetChanged();
            Toast.makeText(DetailListActivity.this, "All items are deleted！", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

    }

    private void unCheckAllButton() {
        DB.clearCheckAll(list_id);
        DB.closeDB();
        establishData();
        itemDisplayAdpater.notifyDataSetChanged();
        Toast.makeText(DetailListActivity.this, "ALL CHECKOFFS ARE CLEAR", Toast.LENGTH_SHORT).show();
    }

    private void checkAllButton() {
        DB.checkAllItem(list_id);
        DB.closeDB();
        establishData();
        itemDisplayAdpater.notifyDataSetChanged();
        Toast.makeText(DetailListActivity.this, "ALL CHECKOFFS ARE CHECK", Toast.LENGTH_SHORT).show();
    }

    private void showRenamePopUp() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View renameAlertPopup = getLayoutInflater().inflate(R.layout.rename_single_list, null);
        cancelRename = (Button) renameAlertPopup.findViewById(R.id.renameOneCancel);
        confirmRename = (Button) renameAlertPopup.findViewById(R.id.renameOneConfirm);
        newName = (EditText) renameAlertPopup.findViewById(R.id.renameCurrentList);

        dialogBuilder.setView(renameAlertPopup);
        dialog = dialogBuilder.create();
        dialog.show();
        cancelRename.setOnClickListener(v -> dialog.dismiss());

        confirmRename.setOnClickListener(v -> {
            String newListName = newName.getText().toString();
            if(newListName.replaceAll(" ", "").length()>0){
                if(DB.searchForListName(newListName)) {
                    Toast.makeText(DetailListActivity.this, "The name already exists, please rename it ", Toast.LENGTH_SHORT).show();
                }
                else{
                    newName.setText("");
                    DB.renameList(list_id, newListName);
                    DB.closeDB();
                    header.setText(newListName);
                    dialog.dismiss();
                    Toast.makeText(DetailListActivity.this, "List name change to <" + newListName + " >", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "List Name Cannot Be Empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDeletePopUp() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View deleteAlertPopup = getLayoutInflater().inflate(R.layout.delete_single_list, null);
        cancelDelete = (Button) deleteAlertPopup.findViewById(R.id.deleteOneCancel);
        confirmDelete = (Button) deleteAlertPopup.findViewById(R.id.deleteOneConfirm);
        dialogBuilder.setView(deleteAlertPopup);
        dialog = dialogBuilder.create();
        dialog.show();
        cancelDelete.setOnClickListener(v -> dialog.dismiss());
        confirmDelete.setOnClickListener(v -> {
            DB.deleteList(list_id);
            DB.closeDB();
            Intent myIntent = new Intent(DetailListActivity.this, HomePageActivity.class);
            Toast.makeText(DetailListActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
            startActivity(myIntent);
        });
    }

    public void addItemToList(View view) {
        Intent addItemToList = new Intent(DetailListActivity.this, AddItemActivity.class);
        addItemToList.putExtra("ListId", list_id);
        startActivity(addItemToList);
    }

}