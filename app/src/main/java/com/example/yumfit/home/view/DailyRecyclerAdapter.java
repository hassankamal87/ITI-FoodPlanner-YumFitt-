package com.example.yumfit.home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yumfit.R;
import com.example.yumfit.pojo.Meal;

import java.util.ArrayList;

public class DailyRecyclerAdapter extends RecyclerView.Adapter<DailyRecyclerAdapter.DailyViewHolder> {
    private ArrayList<Meal> myList = new ArrayList<>();
    private Context context;

    private OnClickInterface onClickInterface;

    public DailyRecyclerAdapter(Context context, OnClickInterface onClickInterface){
        this.context = context;
        this.onClickInterface = onClickInterface;
    }


    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DailyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DailyViewHolder holder, int position) {

        holder.mealName.setText(myList.get(position).getStrMeal());
        holder.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.onSaveBtnClick(myList.get(holder.getAdapterPosition()));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.onDailyInspirationItemClicked(myList.get(holder.getAdapterPosition()).getIdMeal());
            }
        });

        Glide.with(context).load(myList.get(position).getStrMealThumb())
                .apply(new RequestOptions().override(500,500)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.back_register)).into(holder.mealImg);

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public void setList(ArrayList<Meal> myList) {
        this.myList = myList;
        notifyDataSetChanged();
    }

    public class DailyViewHolder extends RecyclerView.ViewHolder {

        ImageView mealImg, saveBtn;
        TextView mealName;


        public DailyViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImg = itemView.findViewById(R.id.mealImg);
            saveBtn = itemView.findViewById(R.id.addToFavouriteBtn);
            mealName = itemView.findViewById(R.id.mealName);
        }
    }
}
