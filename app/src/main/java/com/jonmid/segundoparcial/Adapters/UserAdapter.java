package com.jonmid.segundoparcial.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jonmid.segundoparcial.Models.User;
import com.jonmid.segundoparcial.R;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    List<User> myUser = new ArrayList<>();
    Context context;

    public UserAdapter(Context context, List<User> myUser) {
        this.myUser = myUser;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.myNameUser.setText(myUser.get(position).getUsername());
        holder.phone.setText(myUser.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return myUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView myLogo;
        TextView myNameUser;
        TextView phone;

        // Constructor
        public ViewHolder(View item)  {
            super(item);
            myLogo = (ImageView) item.findViewById(R.id.logo_user);
            myNameUser = (TextView) item.findViewById(R.id.txt_name_user);
            phone = (TextView) item.findViewById(R.id.phone);
        }
    }
}
