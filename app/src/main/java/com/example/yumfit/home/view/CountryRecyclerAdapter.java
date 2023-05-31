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
import com.example.yumfit.pojo.Country;

import java.util.ArrayList;

public class CountryRecyclerAdapter extends RecyclerView.Adapter<CountryRecyclerAdapter.CountryViewHolder> {
    private ArrayList<Country> myList = new ArrayList<>();
    private Context context;
    private String [] flags;
    private OnClickInterface onClickInterface;

    public CountryRecyclerAdapter(Context context, OnClickInterface onClickInterface){
        this.context = context;
        this.onClickInterface = onClickInterface;
        flags = context.getResources().getStringArray(R.array.flags);
    }


    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CountryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {

        if(position!=26) {

            Glide.with(context).load(flags[position])
                    .apply(new RequestOptions().override(500, 500)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.back_register)).into(holder.countryImg);
            holder.countryName.setText(myList.get(position).getStrArea());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickInterface.onCountryItemClicked(myList.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public void setList(ArrayList<Country> myList) {
        this.myList = myList;
        notifyDataSetChanged();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {

        ImageView countryImg;
        TextView countryName;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            countryImg = itemView.findViewById(R.id.countryImg);
            countryName = itemView.findViewById(R.id.countryName);
        }
    }
}
