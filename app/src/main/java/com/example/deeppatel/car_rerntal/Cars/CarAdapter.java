package com.example.deeppatel.car_rerntal.Cars;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.Customer.models.Customer;
import com.example.deeppatel.car_rerntal.R;

import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> implements Filterable {

    private CarEngine carsEngine = new CarEngine();
    private OnAllCarItemClickedListener onAllCarItemClickedListener;
    static List<Car> carListFull;

    public CarAdapter(CarEngine carsEngine, OnAllCarItemClickedListener onAllCarItemClickedListener) {
        this.carsEngine = carsEngine;
        carListFull = CarEngine.carList;
        this.onAllCarItemClickedListener = onAllCarItemClickedListener;
    }

    public CarAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItem = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.list_item_all_car, viewGroup, false);

        return new ViewHolder(listItem, onAllCarItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Car car = carsEngine.getCar(i);
        viewHolder.car_name.setText(car.getName().toUpperCase() + " - " + car.getModel().toUpperCase());
        if (car.getStatus() == true) {
            viewHolder.status.setTextColor(Color.parseColor("#006400"));
            viewHolder.status.setText("Available");
        } else {
            viewHolder.status.setTextColor(Color.RED);
            viewHolder.status.setText("Booked");
        }

        GradientDrawable bgShape = (GradientDrawable) viewHolder.circle_ele.getBackground();
        bgShape.setColor(car.getColor());

    }

    @Override
    public int getItemCount() {
        return carsEngine.getCount();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Car> carsListNew = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {

                //No query in the search box
                carsListNew.addAll(carListFull);
            } else {

                String pattern = constraint.toString().toLowerCase().trim();

                for (Car car : carListFull) {

                    if (car.getName().toLowerCase().contains(pattern)) {

                        carsListNew.add(car);

                    }

                }

            }

            FilterResults results = new FilterResults();

            results.values = carsListNew;

            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }

    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView car_name, status;
        View circle_ele;
        //Gotta declare image for the car once available

        OnAllCarItemClickedListener onAllCarItemClickedListener;

        public ViewHolder(@NonNull View itemView, OnAllCarItemClickedListener onAllCarItemClickedListener) {
            super(itemView);

            car_name = itemView.findViewById(R.id.car_name_all_cars);
            status = itemView.findViewById(R.id.status_all_cars);
            circle_ele = itemView.findViewById(R.id.circle_ele_all);
            this.onAllCarItemClickedListener = onAllCarItemClickedListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onAllCarItemClickedListener.onAllCarItemClicked(getAdapterPosition(), v);
        }

    }

    public interface OnAllCarItemClickedListener {
        void onAllCarItemClicked(int position, View view);
    }
}
