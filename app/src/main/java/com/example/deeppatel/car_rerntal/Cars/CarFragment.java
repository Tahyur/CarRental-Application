package com.example.deeppatel.car_rerntal.Cars;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deeppatel.car_rerntal.Home;
import com.example.deeppatel.car_rerntal.R;
import com.example.deeppatel.car_rerntal.Returning_Process.CarsEngine;

public class CarFragment extends Fragment implements AllCarsAdapter.OnAllCarItemClickedListener{

    RecyclerView allCarsList;
    AllCarsEngine carsEngine;
    AllCarsAdapter carsAdapter;
    private final String CARSTR = "Car";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.car_fragment_layout, container, false);

        //Set the bottom navigation to home
        ((Home) getActivity()).setBottomNavigationItemChecked(R.id.navigation_car);

        //Set the actionbar title to home
        ((Home) getActivity()).setActionBarTitle(getText(R.string.title_car).toString());

        allCarsList = rootView.findViewById(R.id.all_cars_list);
        allCarsList.setLayoutManager(new LinearLayoutManager(getContext()));

        carsEngine = new AllCarsEngine();
        carsEngine.addCars(10);
        carsAdapter = new AllCarsAdapter(carsEngine, this);

        allCarsList.setAdapter(carsAdapter);

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();

        //Set the bottom navigation to home
        ((Home) getActivity()).setBottomNavigationItemChecked(R.id.navigation_car);

        //Set the actionbar title to home
        ((Home) getActivity()).setActionBarTitle(getText(R.string.title_car).toString());

    }

    @Override
    public void onAllCarItemClicked(int position, View view) {

        Intent toEditCar = new Intent(getContext(), EditCar.class);
        toEditCar.putExtra(CARSTR, carsEngine.getCar(position));
        startActivity(toEditCar);

    }
}
