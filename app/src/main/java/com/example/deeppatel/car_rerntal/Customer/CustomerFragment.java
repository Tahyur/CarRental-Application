package com.example.deeppatel.car_rerntal.Customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deeppatel.car_rerntal.R;

public class CustomerFragment extends Fragment implements CustomerAdapter.OnCustomerItemClickedListener{

    RecyclerView customerList;
    CustomerEngine customerEngine;
    CustomerAdapter customerAdapter;
    private final String CUSTOMERSTR = "Customer";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_fragment_layout, container, false);

        customerList = view.findViewById(R.id.customer_list);
        customerList.setLayoutManager(new LinearLayoutManager(getContext()));

        customerEngine = new CustomerEngine();
        customerEngine.addCustomers(10);
        customerAdapter = new CustomerAdapter(customerEngine, this);

        customerList.setAdapter(customerAdapter);

        return view;
    }

    @Override
    public void onCustomerItemClicked(int position, View view) {

        Intent toEditCar = new Intent(getContext(), EditCustomer.class);
        toEditCar.putExtra(CUSTOMERSTR, customerEngine.getcustomer(position));
        startActivity(toEditCar);

    }
}
