package com.example.deeppatel.car_rerntal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_fragment_layout, container, false);

        //Set the bottom navigation to home
        ((Home) getActivity()).setBottomNavigationItemChecked(R.id.navigation_home);
        //Set the actionbar title to home
        ((Home) getActivity()).setActionBarTitle(getText(R.string.title_home).toString());

        Log.i("INFO", "HOME CRE VIEW");

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();

        //Set the bottom navigation to home
        ((Home) getActivity()).setBottomNavigationItemChecked(R.id.navigation_home);
        //Set the actionbar title to home
        ((Home) getActivity()).setActionBarTitle(getText(R.string.title_home).toString());

        Log.i("INFO", "HOME RES");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("INFO", "HOME ATTACH");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("INFO", "HOME CREATE");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("INFO", "HOME START");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("INFO", "HOME PAU");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("INFO", "HOME STOP");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("INFO", "HOME DES VIEW");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("INFO", "HOME DES");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("INFO", "HOME DET");
    }
}
