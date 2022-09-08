package com.bitc.testapp.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitc.testapp.R;

public class commentviewholder extends RecyclerView.ViewHolder {

    public ImageView cuimage;
    public TextView cuname,cumessage,cudt;

    public commentviewholder(@NonNull View itView){
        super(itView);

        cuimage=itemView.findViewById(R.id.cuimage);
        cuname=itemView.findViewById(R.id.cuname);
        cumessage=itemView.findViewById(R.id.cumessage);
        cudt=itemView.findViewById(R.id.cudt);

    }
}
