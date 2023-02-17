package com.kmm.vegancheckerapp.features.Searching;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kmm.vegancheckerapp.R;

import java.util.ArrayList;
/* Code below is based on:
Medium Article: Autocomplete TextView in Android (2018),
Droid By Me,
https://droidbyme.medium.com/autocomplete-textview-in-android-a1bf5fc112f6
 */
public class SearchAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final int resourceId;
    private final ArrayList<String> items;
    private final ArrayList<String> tempItems;
    private final ArrayList<String> suggestions;

    public SearchAdapter(@NonNull Context context, int resource, ArrayList<String> items) {

        super(context, resource, items);
        this.items =items;
        this.context =context;
        this.resourceId = resource;

        this.tempItems = new ArrayList<>(items);

        suggestions = new ArrayList<>();

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }
           String productType = getItem(position);
            TextView name = view.findViewById(R.id.textView);

            name.setText(productType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return ProductFilter;
    }
    private final Filter ProductFilter = new Filter() {
         @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (String type: tempItems) {
                    if (type.toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(type);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<String> tempValues;
            tempValues = (ArrayList<String>) filterResults.values;
            if (filterResults.count > 0) {
                clear();
                for (String type : tempValues) {
                    add(type);
                }
                notifyDataSetChanged();
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    }; //END
}
