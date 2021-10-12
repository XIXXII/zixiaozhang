package edu.qc.seclass.glm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;

public class ListDetailsDisplayAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> groupByType;
    private HashMap<String, List<GroceryLists>> selectItem;
    private DatabaseHelper DB;

    public ListDetailsDisplayAdapter(Context myContext, List<String> categoryGroup, HashMap<String, List<GroceryLists>> listItems){
        this.context = myContext;
        this.groupByType = categoryGroup;
        this.selectItem = listItems;
        notifyDataSetChanged();
    }
    @Override
    public int getGroupCount(){
        return this.groupByType.size();
    }
    @Override
    public int getChildrenCount(int groupPosition){
        List childCount = selectItem.get(groupByType.get(groupPosition));
        if(childCount != null && !childCount.isEmpty()){
            return childCount.size();
        }
        return 1;
    }
    @Override
    public Object getGroup(int groupPosition){
        return this.groupByType.get(groupPosition);
    }
    @Override
    public GroceryLists getChild(int groupPosition, int childPosition) {
        return this.selectItem.get(this.groupByType.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String typeName = (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.group_by_type, null);
        }
        TextView category_name = (TextView) convertView.findViewById(R.id.type_name);
        category_name.setText(typeName);
        ExpandableListView notCollapse = (ExpandableListView) parent;
        notCollapse.expandGroup(groupPosition);
        return convertView;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String itemName = (String) getChild(groupPosition, childPosition).getSelectItemName();
        final int itemQuantity = (int) getChild(groupPosition, childPosition).getQuantity();
        int itemCheck = (int) getChild(groupPosition, childPosition).getItemCheckMark();
        int listID = (int) getChild(groupPosition, childPosition).getSelectListID();
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.display_items, null);
        }
        TextView display_item = (TextView) convertView.findViewById(R.id.itemNameDisplay);
        TextView quantityDisplay = (TextView) convertView.findViewById(R.id.itemQuantityDisplay);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
        ImageButton editIcon = (ImageButton) convertView.findViewById(R.id.editItem);
        display_item.setText(itemName);
        quantityDisplay.setText("( "+itemQuantity+" )");
        if(itemCheck == 0){
            checkBox.setChecked(false);
            display_item.setPaintFlags(display_item.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        else{
            checkBox.setChecked(true);
            display_item.setPaintFlags(display_item.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(context, EditItemActivity.class);
                edit.putExtra("ListId", listID);
                edit.putExtra("ItemName", itemName);
                edit.putExtra("CurrentQuantity", itemQuantity);
                context.startActivity(edit);
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener(){
            @Override
             public void onClick(View v){
                if(checkBox.isChecked()){
                   DB = new DatabaseHelper(context);
                   DB.setCheckBox(listID, itemName);
                   checkBox.setChecked(true);
                   display_item.setPaintFlags(display_item.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else{
                    DB = new DatabaseHelper(context);
                    DB.clearCheckBox(listID, itemName);
                    checkBox.setChecked(false);
                    display_item.setPaintFlags(display_item.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
