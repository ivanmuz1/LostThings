package com.example.project;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.ItemChatClickListener;
import com.example.project.R;

public class ViewChat extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView messageTxt;
    public ItemChatClickListener listener;

    public ViewChat(@NonNull View itemView) {
        super(itemView);

        messageTxt = itemView.findViewById(R.id.Message);
    }
    public void setItemChatClickListener(ItemChatClickListener listener) { this.listener = listener;  }

    @Override
    public void onClick(View view) {
        listener.onClick(view,getAdapterPosition(),false);
    }
}
