package com.example.deeppatel.car_rerntal.Cars;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deeppatel.car_rerntal.R;

public class AllCarsAdapter extends RecyclerView.Adapter<AllCarsAdapter.ViewHolder> {

    private AllCarsEngine carsEngine;
    private OnAllCarItemClickedListener onAllCarItemClickedListener;

    public AllCarsAdapter(AllCarsEngine carsEngine, OnAllCarItemClickedListener onAllCarItemClickedListener) {
        this.carsEngine = carsEngine;
        this.onAllCarItemClickedListener = onAllCarItemClickedListener;
    }

    public AllCarsAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View listItem = LayoutInflater
                                .from(viewGroup.getContext())
                                .inflate(R.layout.all_car_item_layout, viewGroup, false);

                return new ViewHolder(listItem, onAllCarItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final AllCars car = carsEngine.getCar(i);
        viewHolder.car_name.setText(car.getCar_name());

        GradientDrawable bgShape = (GradientDrawable) viewHolder.circle_ele.getBackground();
        bgShape.setColor(car.getColor());

    }

    @Override
    public int getItemCount() {
        return carsEngine.getCount();
    }

    public interface OnAllCarItemClickedListener{

        void onAllCarItemClicked(int position, View view);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView car_name, model_name, booked_by, booked_on, available_on;
        View circle_ele;
        //Gotta declare image for the car once available
        OnAllCarItemClickedListener onAllCarItemClickedListener;


        public ViewHolder(@NonNull View itemView, OnAllCarItemClickedListener onAllCarItemClickedListener) {
            super(itemView);

            car_name = itemView.findViewById(R.id.car_name_all);
            circle_ele = itemView.findViewById(R.id.circle_ele_all);

            this.onAllCarItemClickedListener = onAllCarItemClickedListener;

            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {

            onAllCarItemClickedListener.onAllCarItemClicked(getAdapterPosition(), v);

        }
    }

}
