package com.google.mytravelapp.fragments.trip;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.mytravelapp.R;

import lombok.Getter;

@Getter
public class TripViewHolder extends RecyclerView.ViewHolder {
    private final ImageView imageView2, bookmarkIconCustomRow;
    private final TextView tripNameCustomRow, destinationCustomRow, textView9, ratingCustomRow, emailCustomRow;

    public TripViewHolder(@NonNull View itemView) {
        super(itemView);
        this.imageView2 = itemView.findViewById(R.id.imageView2);
        this.bookmarkIconCustomRow = itemView.findViewById(R.id.bookmarkIconCustomRow);
        this.tripNameCustomRow = itemView.findViewById(R.id.tripNameCustomRow);
        this.destinationCustomRow = itemView.findViewById(R.id.destinationCustomRow);
        this.textView9 = itemView.findViewById(R.id.textView9);
        this.ratingCustomRow = itemView.findViewById(R.id.ratingCustomRow);
        this.emailCustomRow = itemView.findViewById(R.id.emailCustomRow);
    }
}
