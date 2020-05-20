package com.example.parcial2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>implements View.OnClickListener{

    private View.OnClickListener listener;
    public ArrayList<Track> tracks;

    public Adapter(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }
    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        view.setOnClickListener(this);

        Adapter.ViewHolder viewHolder = new Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        holder.title.setText(tracks.get(position).name);
        holder.artist.setText(tracks.get(position).artist);
        holder.duration.setText(tracks.get(position).duration);

    }


    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView artist;
        private TextView duration;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.nombre);
            artist=itemView.findViewById(R.id.artist);
            duration= itemView.findViewById(R.id.duracion);
        }
    }
}
