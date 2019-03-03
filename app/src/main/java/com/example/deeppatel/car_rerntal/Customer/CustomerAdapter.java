package com.example.deeppatel.car_rerntal.Customer;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deeppatel.car_rerntal.R;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    private CustomerEngine customerEngine;
    private OnCustomerItemClickedListener onCustomerItemClickedListener;

    public CustomerAdapter(CustomerEngine customerEngine, OnCustomerItemClickedListener onCustomerItemClickedListener) {
        this.customerEngine = customerEngine;
        this.onCustomerItemClickedListener = onCustomerItemClickedListener;
    }

    public CustomerAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItem = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.list_item_customer, viewGroup, false);

        return new ViewHolder(listItem, onCustomerItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Customer car = customerEngine.getcustomer(i);
        viewHolder.customer_name.setText(car.getCustomerName());

        GradientDrawable bgShape = (GradientDrawable) viewHolder.circle_ele.getBackground();
        bgShape.setColor(car.getColor());

    }

    @Override
    public int getItemCount() {
        return customerEngine.getCount();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView customer_name, license_no;
        View circle_ele;
        OnCustomerItemClickedListener onCustomerItemClickedListener;
        //Gotta declare image for the car once available


        public ViewHolder(@NonNull View itemView, OnCustomerItemClickedListener onCustomerItemClickedListener) {
            super(itemView);

            customer_name = itemView.findViewById(R.id.customer_name);
            circle_ele = itemView.findViewById(R.id.circle_ele_customer);

            this.onCustomerItemClickedListener = onCustomerItemClickedListener;

            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {

            onCustomerItemClickedListener.onCustomerItemClicked(getAdapterPosition(), v);

        }

    }

    public interface OnCustomerItemClickedListener{

        void onCustomerItemClicked(int position, View view);

    }

}
