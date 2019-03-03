package com.example.deeppatel.car_rerntal.Customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.deeppatel.car_rerntal.Home;
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

        ItemTouchHelper swipeToDel = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

                //Delete the customer from the list
                //Here the is not the item i.e. is swiped
                Snackbar.make(getView().findViewById(R.id.customer_fragment_layout), R.string.confirm_cust_del , Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.delete_confirm_snackbar, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //Make sure the user wants to delete the customer after all
                                Toast.makeText(getContext(),
                                        customerEngine.getcustomer(viewHolder.getAdapterPosition()).getCustomerName()+ " is deleted" ,
                                        Toast.LENGTH_SHORT).show();
                                customerEngine.delCustomer(viewHolder.getAdapterPosition());
                                customerAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                                customerAdapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), customerAdapter.getItemCount());
                            }
                        }).show();
            }
        });

        swipeToDel.attachToRecyclerView(customerList);

        return view;
    }

    @Override
    public void onCustomerItemClicked(int position, View view) {

        Intent toEditCar = new Intent(getContext(), EditCustomer.class);
        toEditCar.putExtra(CUSTOMERSTR, customerEngine.getcustomer(position));
        startActivity(toEditCar);

    }
}
