package com.example.deeppatel.car_rerntal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReturnACarFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.return_a_car_fragment_layout, container, false);


        //Set the bottom navigation to home
        ((Home) getActivity()).setBottomNavigationItemChecked(R.id.navigation_return_car);

        //Set the actionbar title to home
        ((Home) getActivity()).setActionBarTitle(getText(R.string.return_car).toString());

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();

        //Set the bottom navigation to home
        ((Home) getActivity()).setBottomNavigationItemChecked(R.id.navigation_return_car);

        //Set the actionbar title to home
        ((Home) getActivity()).setActionBarTitle(getText(R.string.return_car).toString());

    }
}
