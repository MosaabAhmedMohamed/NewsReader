package com.example.mosaab.newsreader.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.example.mosaab.newsreader.Interface.ItemClickListner;
import com.example.mosaab.newsreader.Model.LocalSavedAPIS;
import com.example.mosaab.newsreader.Model.Providers;
import com.example.mosaab.newsreader.R;

import java.util.ArrayList;

public class Providers_Adapter extends RecyclerView.Adapter<Providers_Adapter.ViewHolder> {

    private static final String TAG = "Providers_Adapter";
    private ArrayList<Providers> providers_list;
    private ItemClickListner itemClickListner;

    public Providers_Adapter(ArrayList<Providers> providers_list, ItemClickListner itemClickListner) {
        this.providers_list = providers_list;
        this.itemClickListner = itemClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View single_provider = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_provider,viewGroup,false);

        return new ViewHolder(single_provider);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.provider_name_tv.setText(providers_list.get(i).getProvider_name());
        viewHolder.check_news_providr.setChecked(providers_list.get(i).isActive_provider());

    }

    @Override
    public int getItemCount()
    {
        return providers_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        protected Switch check_news_providr;
        protected TextView provider_name_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            check_news_providr = itemView.findViewById(R.id.check_provider);
            provider_name_tv = itemView.findViewById(R.id.providerNameTV);


           check_news_providr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // do something, the isChecked will be
                    // true if the switch is in the On position


                        if (itemClickListner != null)
                        {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION)
                            {
                                if (isChecked)
                                {
                                    itemClickListner.onItemClick(buttonView,getAdapterPosition(),true);

                                }
                                else
                                {
                                    itemClickListner.onItemClick(buttonView,getAdapterPosition(),false);
                                }
                            }

                        }
                    }



            });
        }


    }
}
