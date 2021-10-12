package edu.qc.seclass.glm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends ArrayAdapter<Users> {
    private Context viewContext;
    private int viewResource;
    private List<Users> userList = new ArrayList<>();
    public UsersAdapter(@NonNull Context context, int resource, List<Users> lists){
        super(context, resource, lists);
        viewContext = context;
        viewResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Users lists = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(viewContext);
        convertView = inflater.inflate(viewResource, parent, false);
        TextView listName = (TextView) convertView.findViewById(R.id.itemNameDisplay);
        listName.setText(lists.getListName());
        return convertView;
    }

}
