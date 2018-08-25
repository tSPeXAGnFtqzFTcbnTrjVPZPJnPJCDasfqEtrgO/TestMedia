package com.example.phamngocan.testmediaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.phamngocan.testmediaapp.R;
import com.example.phamngocan.testmediaapp.constant.Action;
import com.example.phamngocan.testmediaapp.function.Kmp;
import com.example.phamngocan.testmediaapp.model.Song;
import com.example.phamngocan.testmediaapp.services.ForegroundService;

import java.util.ArrayList;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Holder> implements Filterable{

    ArrayList<Song> songs;
    ArrayList<Song> baseSongs;
    Context context;
    ItemFilter itemFilter = new ItemFilter();

    public SearchAdapter(ArrayList<Song> songs, Context context) {
        this.songs = songs;
        this.baseSongs = songs;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_search,parent,false );

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if(Locale.getDefault().getLanguage().equals("vi")){
            holder.txtvName.setText(songs.get(position).getNameVi());
        }else{
            holder.txtvName.setText(songs.get(position).getNameEn());
        }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView txtvName;
        public Holder(View itemView) {
            super(itemView);
            txtvName = itemView.findViewById(R.id.item_name);
            txtvName.setSelected(true);

            itemView.setOnClickListener(v -> {

                int pos = getLayoutPosition();
                int index = songs.get(pos).getPosition();

                Intent intent  = new Intent(context, ForegroundService.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(ForegroundService.POS_KEY,index);
                intent.setAction(Action.START_FORE.getName());

                Log.d("AAA","recycler "+index );
                context.startService(intent);
            });

        }
    }

    private class ItemFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if(charSequence.toString().trim().equals("")){
                return null;
            }
            FilterResults results = new FilterResults();
            ArrayList<Song> nlist = new ArrayList<>();

            for(int i = 0; i< baseSongs.size(); i++){
                if(Kmp.isMatch(baseSongs.get(i).getNameSearch(),charSequence.toString() )){
                    nlist.add(baseSongs.get(i));
                }
            }
            results.values = nlist;
            results.count = nlist.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if(filterResults == null){
                songs = baseSongs;
            }else{
                songs = (ArrayList<Song>) filterResults.values;
            }
            notifyDataSetChanged();
        }
    }
}
