package com.google.mytravelapp.fragments.trip;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.mytravelapp.R;
import com.google.mytravelapp.activities.DisplayDetailsActivity;
import com.google.mytravelapp.activities.EditTripActivity;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripViewHolder>{

    private List<Trip> tripList;
    private Context context;

    public TripAdapter(Context context){
        this.context = context;
    }

    public void setTripList(List<Trip> trips){
        tripList = new ArrayList<>();
        tripList.clear();
        tripList = trips;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.custom_row,parent,false);
        return new TripViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        if(tripList != null){
            Trip currentTrip = tripList.get(position);
            holder.getImageView2().setImageURI(Uri.parse(currentTrip.getImageUrl()));
            holder.getBookmarkIconCustomRow().setImageResource(
                    currentTrip.isBookmarked() ? R.drawable.ic_gold_baseline_bookmark_24 : R.drawable.ic_baseline_bookmark_24
            );
            holder.getTripNameCustomRow().setText(currentTrip.getTripName());
            holder.getDestinationCustomRow().setText(currentTrip.getDestination());
            holder.getTextView9().setText(String.valueOf(currentTrip.getPrice()));
            holder.getRatingCustomRow().setText("Rating\n" +"\t"+currentTrip.getRating());
            holder.getEmailCustomRow().setText(currentTrip.getEmail());

            holderLongClick(holder,currentTrip,position);
            holderClick(holder,currentTrip);
        }
        else{
            holder.getTripNameCustomRow().setText(R.string.no_trips);
        }
    }

    @Override
    public int getItemCount() {
        if(tripList != null){
            return tripList.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void holderLongClick(@NonNull TripViewHolder holder, Trip currentTrip, int position){
        holder.itemView.setOnLongClickListener(view -> {
            Intent intent = new Intent(context, EditTripActivity.class);
            intent.putExtra("bookmarkItem", currentTrip.isBookmarked());
            intent.putExtra("tripName",currentTrip.getTripName());
            intent.putExtra("destination",currentTrip.getDestination());
            intent.putExtra("price",String.valueOf(currentTrip.getPrice()));
            intent.putExtra("rating",String.valueOf(currentTrip.getRating()));
            intent.putExtra("email",String.valueOf(currentTrip.getEmail()));
            intent.putExtra("position", position);
            intent.putExtra("imageLink",currentTrip.getImageUrl());
            context.startActivity(intent);
            return true;
        });
    }

    private void holderClick(@NonNull TripViewHolder holder, Trip currentTrip){
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DisplayDetailsActivity.class);
            intent.putExtra("tripName",currentTrip.getTripName());
            intent.putExtra("destination",currentTrip.getDestination());
            intent.putExtra("email",currentTrip.getEmail());
            context.startActivity(intent);
        });
    }
}
