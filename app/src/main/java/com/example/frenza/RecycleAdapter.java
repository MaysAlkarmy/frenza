package com.example.frenza;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.viewItem> {

   // Context c;
    ArrayList<Message> chatArray;


    public RecycleAdapter(ArrayList<Message> chatArray) {
        this.chatArray = chatArray;

    }

    class viewItem extends RecyclerView.ViewHolder {
        LinearLayout userView, botView;
        TextView userMessage, botMessage;

        public viewItem(View itemView) {
            super(itemView);
            userView=itemView.findViewById(R.id.userView);
            botView=itemView.findViewById(R.id.botView);
            userMessage=itemView.findViewById(R.id.userMessage);
            botMessage=itemView.findViewById(R.id.botMessage);


        }
    }


    @Override
    public viewItem onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        viewItem viewItem= new viewItem(itemView);
        return viewItem;
    }

    @Override
    public void onBindViewHolder(viewItem holder, final int position) {
        Message message= chatArray.get(position);
        if(message.getSentBy().equals((Message.sent_by_me))){
            holder.botView.setVisibility(View.GONE);
            holder.userView.setVisibility(View.VISIBLE);
            holder.userMessage.setText(message.getMessage());
        } else{
            holder.userView.setVisibility(View.GONE);
            holder.botView.setVisibility(View.VISIBLE);
            holder.botMessage.setText(message.getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return chatArray.size();
    }


}
