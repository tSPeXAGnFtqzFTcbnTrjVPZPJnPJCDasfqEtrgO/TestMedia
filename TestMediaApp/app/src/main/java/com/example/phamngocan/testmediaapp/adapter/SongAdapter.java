package com.example.phamngocan.testmediaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phamngocan.testmediaapp.Animation.Translate;
import com.example.phamngocan.testmediaapp.R;
import com.example.phamngocan.testmediaapp.constant.Action;
import com.example.phamngocan.testmediaapp.model.Song;
import com.example.phamngocan.testmediaapp.services.ForegroundService;

import java.util.ArrayList;
import java.util.Locale;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.Holder> {


    ArrayList<Song> songs;
    Context context;

    public SongAdapter(ArrayList<Song> songs, Context context) {
        this.songs = songs;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if(Locale.getDefault().getLanguage().equals("vi")){
            holder.name.setText(songs.get(position).getNameVi());
        }else{
            holder.name.setText(songs.get(position).getNameEn());
        }
        Translate.setAnim(holder.itemView);


    }

    @Override
    public int getItemCount() {
        return songs.size();
    }


    class Holder extends RecyclerView.ViewHolder {
        TextView name;
         Holder(View itemView) {
             super(itemView);
             name = itemView.findViewById(R.id.item_name);

             itemView.setOnClickListener(v -> {

                 int pos = getLayoutPosition();

                 Intent intent  = new Intent(context, ForegroundService.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                 intent.putExtra(ForegroundService.posKey,pos);
                 intent.setAction(Action.START_FORE.getName());

                 Log.d("AAA","recycler "+pos );
                 context.startService(intent);
             });

         }
     }


}
