package com.example.yumfit.daydetail.view;

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

public class DayMealAdapter extends RecyclerView.Adapter<DayMealAdapter.DayViewHolder> {
    private ArrayList<Meal> myList = new ArrayList<>();
    private Context context;
    private OnDayClickInterface onDayClickInterface;
    public DayMealAdapter(Context context, OnDayClickInterface onDayClickInterface){
        this.context = context;
        this.onDayClickInterface = onDayClickInterface;
    }
    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DayViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {

        holder.deleteBtn.setImageResource(R.drawable.delete_icon);
        holder.mealName.setText(myList.get(position).getStrMeal());
        Glide.with(context).load(myList.get(position).getStrMealThumb())
                .apply(new RequestOptions().override(500,500)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.back_register)).into(holder.mealImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDayClickInterface.onFavItemClicked(myList.get(holder.getAdapterPosition()).getIdMeal());
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDayClickInterface.onDeleteBtnClicked(myList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public void setList(ArrayList<Meal> myList) {
        this.myList = myList;
        notifyDataSetChanged();
    }

    public class DayViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImg, deleteBtn;
        TextView mealName;
        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImg = itemView.findViewById(R.id.mealImg);
            mealName = itemView.findViewById(R.id.mealName);
            deleteBtn = itemView.findViewById(R.id.addToFavouriteBtn);
        }
    }
}
