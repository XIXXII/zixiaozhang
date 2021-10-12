package edu.qc.seclass.glm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchFilterAdapter extends RecyclerView.Adapter<SearchFilterAdapter.SearchViewHolder> implements Filterable {
    private List<Item> itemCollect;
    private List<Item> itemListFull;
    private OnItemClickListener unItemListener;
    public SearchFilterAdapter(List<Item> itemCollection, OnItemClickListener myItenmClick){
        this.itemCollect = itemCollection;
        itemListFull = new ArrayList<>(itemCollect);
        this.unItemListener = myItenmClick;
    }


    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView itemName;
        OnItemClickListener myItemListener;
        public SearchViewHolder(@NonNull View itemView, OnItemClickListener theItemListener) {
            super(itemView);
            itemName = itemView.findViewById(R.id.searchResults);
            myItemListener = theItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            unItemListener.onItemClick(v, getAdapterPosition());
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_items, parent, false);
        return new SearchViewHolder(view, unItemListener);
    }
    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position){
        Item items = itemCollect.get(position);
        holder.itemName.setText(items.getItemName());

    }
    @Override
    public int getItemCount(){
        return itemCollect.size();
    }
    @Override
    public Filter getFilter(){
        return itemFilter;
    }

    private Filter itemFilter = new Filter(){
        @Override
        protected  FilterResults performFiltering(CharSequence constraint){
            List<Item> filteredlist = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredlist.addAll(itemListFull);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Item item: itemListFull){
                    if(item.getItemName().toLowerCase().contains(filterPattern)){
                        filteredlist.add(item);
                    }
                }
            }
            if(filteredlist.isEmpty()) {
                filteredlist.add(new Item(99999, "CREATE NEW ITEM", "NON-CATEGORY"));
            }
            FilterResults results = new FilterResults();
            results.values = filteredlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemCollect.clear();
            itemCollect.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
