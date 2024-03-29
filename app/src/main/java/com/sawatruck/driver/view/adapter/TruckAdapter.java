package com.sawatruck.driver.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;
import com.sawatruck.driver.BaseApplication;
import com.sawatruck.driver.R;
import com.sawatruck.driver.entities.Truck;
import com.sawatruck.driver.utils.Logger;
import com.sawatruck.driver.utils.Serializer;
import com.sawatruck.driver.view.activity.ActivityTruckDetail;
import com.sawatruck.driver.view.design.CustomTextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by royal on 8/22/2017.
 */


public class TruckAdapter extends RecyclerView.Adapter<TruckAdapter.MyViewHolder> {
    private Context context;

    private ArrayList<Truck> truckList = new ArrayList<>();
    @Override
    public TruckAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.driver_truck_list_item, parent, false);
        return new TruckAdapter.MyViewHolder(v);
    }

    public TruckAdapter() {

    }

    public TruckAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(final TruckAdapter.MyViewHolder holder, int position) {
        final Truck truck = truckList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityTruckDetail.class);
                String strTruck =  Serializer.getInstance().serializeTruck(truck);
                intent.putExtra("truck_info", strTruck);
                context.startActivity(intent);
            }
        });


        holder.txtProductionYear.setText(String.valueOf(truck.getProductionYear()));
        holder.txtBodyWorkType.setText(truck.getTruckBodyworkType());
        holder.txtTruckName.setText(truck.getName());
        holder.txtVehicleBrand.setText(truck.getVehicleBrand());
        holder.txtVehicleClass.setText(truck.getVehicleClassName());
        try {
            BaseApplication.getPicasso().load(truck.getPhoto()).placeholder(R.drawable.ico_truck).into(holder.imgTruckPhoto);
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.layoutContent.setBackgroundColor(Color.WHITE);
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Logger.error("onTouch");
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    holder.layoutContent.setBackgroundColor(Color.parseColor("#88DDDDDD"));
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    holder.layoutContent.setBackgroundColor(Color.WHITE);

                return  false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return truckList.size();
    }

    public ArrayList<Truck> getTruckList() {
        return truckList;
    }

    public void setTruckList(ArrayList<Truck> truckList) {
        this.truckList = truckList;
    }

    public void initializeAdapter() {
        this.truckList = new ArrayList<>();
        notifyDataSetChanged();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_production_year) CustomTextView txtProductionYear;
        @Bind(R.id.txt_truck_name) CustomTextView txtTruckName;
        @Bind(R.id.txt_vehicle_class) CustomTextView txtVehicleClass;
        @Bind(R.id.txt_bodywork_type) CustomTextView txtBodyWorkType;
        @Bind(R.id.txt_vehicle_brand) CustomTextView txtVehicleBrand;
        @Bind(R.id.img_truckphoto) RoundedImageView imgTruckPhoto;
        @Bind(R.id.layout_content) View layoutContent;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}