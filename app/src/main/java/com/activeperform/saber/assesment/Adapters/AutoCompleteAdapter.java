package com.activeperform.saber.assesment.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.activeperform.saber.assesment.Models.BikeStation;
import com.activeperform.saber.assesment.R;
import com.activeperform.saber.assesment.Utils.Util;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteAdapter extends ArrayAdapter<BikeStation> {


    private ArrayList<BikeStation> suggestions;
    private List<BikeStation> items;
    private List<BikeStation> itemsAll;
    private int viewResourceId;

    public AutoCompleteAdapter(@NonNull Context context, int resource, @NonNull List<BikeStation> objects) {
        super(context, resource, objects);

        this.suggestions = new ArrayList<BikeStation>();
        this.items = objects;
        this.itemsAll = Util.cloneList(objects);
        this.viewResourceId = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        final BikeStation customer = items.get(position);
        if (customer != null) {
            TextView customerNameLabel = (TextView) v.findViewById(android.R.id.text1);
            if (customerNameLabel != null) {
//              Log.i(MY_DEBUG_TAG, "getView Customer Name:"+customer.getName());
                customerNameLabel.setText(customer.getFeaturename());
                customerNameLabel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("aaa", "  ");
                        Intent in = new Intent();
                        in.putExtra("lat",customer.getCoordinates().getLatitude());
                        in.putExtra("lng",customer.getCoordinates().getLongitude());
                        in.putExtra("name",customer.getFeaturename());
                        in.setAction("NOW");
//sendBroadcast(in);
                        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(in);
                    }
                });
            }
        }
        return v;
    }


    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((BikeStation)(resultValue)).getFeaturename();
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (BikeStation station : itemsAll) {
                    if(station.getFeaturename().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(station);
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
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<BikeStation> filteredList = (ArrayList<BikeStation>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (BikeStation c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };
}
