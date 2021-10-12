package edu.qc.seclass.glm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class HierarchicaListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> categoryName;
    private HashMap<String, List<String>> categoryItem;

    public HierarchicaListAdapter(Context myContext, List<String> categoryGroup, HashMap<String, List<String>> listItems){
        this.context = myContext;
        this.categoryName = categoryGroup;
        this.categoryItem = listItems;
    }

    @Override
    public int getGroupCount() {
        return this.categoryName.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List childList = categoryItem.get(categoryName.get(groupPosition));
        if (childList != null && ! childList.isEmpty()) {
            return childList.size();
        }
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.categoryName.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.categoryItem.get(this.categoryName.get(groupPosition)).get(childPosition);
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
            convertView = layoutInflater.inflate(R.layout.category_group, null);
        }
        TextView category_name = (TextView) convertView.findViewById(R.id.type_name);
        category_name.setText(typeName);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String itemName = (String) getChild(groupPosition, childPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.category_items, null);
        }
        TextView category_item = (TextView) convertView.findViewById(R.id.category_items);

        category_item.setText(itemName);
        return convertView;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
