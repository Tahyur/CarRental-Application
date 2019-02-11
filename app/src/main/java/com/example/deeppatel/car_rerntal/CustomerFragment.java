package com.example.deeppatel.car_rerntal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CustomerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.customer_fragment_layout, container, false);

        //Set the bottom navigation to home
        ((Home) getActivity()).setBottomNavigationItemChecked(R.id.navigation_customer);

        //Set the actionbar title to home
        ((Home) getActivity()).setActionBarTitle(getText(R.string.title_customer).toString());

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();

        //Set the bottom navigation to home
        ((Home) getActivity()).setBottomNavigationItemChecked(R.id.navigation_customer);

        //Set the actionbar title to home
        ((Home) getActivity()).setActionBarTitle(getText(R.string.title_customer).toString());

        Log.i("INFO", "CUST RES");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("INFO", "CUST PAU");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("INFO", "CUST STOP");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("INFO", "CUST DES VIEW");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("INFO", "CUST DES");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("INFO", "CUST DET");
    }

}
