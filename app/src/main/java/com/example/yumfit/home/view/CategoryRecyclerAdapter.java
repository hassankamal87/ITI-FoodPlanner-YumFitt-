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
import com.example.yumfit.pojo.Category;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder> {
    private ArrayList<Category> myList = new ArrayList<>();
    private Context context;
    private OnClickInterface onClickInterface;

    public CategoryRecyclerAdapter(Context context, OnClickInterface onClickInterface){
        this.context = context;
        this.onClickInterface = onClickInterface;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Glide.with(context).load(myList.get(position).getStrCategoryThumb())
                .apply(new RequestOptions().override(500,500)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.back_register)).into(holder.categoryImage);
        holder.categoryName.setText(myList.get(position).getStrCategory());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.onCategoryItemClicked(myList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public void setList(ArrayList<Category> myList) {
        this.myList = myList;
        notifyDataSetChanged();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryImage;
        TextView categoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.countryImg);
            categoryName = itemView.findViewById(R.id.countryName);

        }
    }
}
