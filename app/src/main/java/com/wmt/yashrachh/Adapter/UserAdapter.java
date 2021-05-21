package com.wmt.yashrachh.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wmt.yashrachh.Model.UserModel;
import com.wmt.yashrachh.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<UserModel> list;

    public UserAdapter(Context context, List<UserModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_user,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {

        UserModel user = list.get(position);
        Log.e("Name : ",user.getFirst());
        holder.name.setText(user.getFirst()+" "+ user.getLast());
        holder.email.setText(user.getEmail());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

        try{
            Date date = simpleDateFormat.parse(user.getDate());
            String date2 = sdf.format(date);
            holder.dob.setText(""+ date2);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }



        Picasso.get().load(user.getThumbnail()).fit().into(holder.profile_image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView profile_image;
        TextView name,email,dob;

        public ViewHolder(View itemView)
        {
            super(itemView);

            profile_image = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.user_name);
            email = itemView.findViewById(R.id.user_email);
            dob = itemView.findViewById(R.id.user_dob);
        }
    }
}
