package com.example.tayor.karz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tayor.karz.Model.License;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DriverLicenseFragment.OnLicenseInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DriverLicenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriverLicenseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText license,name,address,clazz,exp_date,province,zip;
    private FirebaseUser user;
    Button finish;
    ImageView camera;
    DatabaseReference mDatabase;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnLicenseInteractionListener mListener;

    public DriverLicenseFragment() {
        // Required empty public constructor
    }

    public DriverLicenseFragment(FirebaseUser user){
        this.user = user;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DriverLicenseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DriverLicenseFragment newInstance(String param1, String param2) {
        DriverLicenseFragment fragment = new DriverLicenseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_driver_license, container, false);
        license = v.findViewById(R.id.lic_et);
        name = v.findViewById(R.id.name_et);
        address = v.findViewById(R.id.address_et);
        zip = v.findViewById(R.id.zip_et);
        exp_date = v.findViewById(R.id.expire_et);
        province = v.findViewById(R.id.province_et);
        clazz = v.findViewById(R.id.class_et);

        finish = v.findViewById(R.id.finish);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLicenseInfo();
            }
        });
        return v;
    }

    private void getLicenseInfo(){
        String licenseNo = license.getText().toString();
        String name_ = name.getText().toString();
        String address_ = address.getText().toString();
        String zip_ = zip.getText().toString();
        String exp_date_ = exp_date.getText().toString();
        String province_ = province.getText().toString();
        String clazz_ = clazz.getText().toString();

        License license = new License();
        license.setLicense(licenseNo);
        license.setName(name_);
        license.setAddress(address_);
        license.setZip(zip_);
        license.setExp_date(exp_date_);
        license.setProvince(province_);
        license.setClazz(clazz_);

        loadMainActivity(license);
    }

    private void loadMainActivity(License license) {
        if(mListener != null) {
            // Saves the license information to the database
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("license").child(user.getUid()).setValue(license);
            mListener.onLicenseInteraction();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onLicenseInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLicenseInteractionListener) {
            mListener = (OnLicenseInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnLicenseInteractionListener {
        // TODO: Update argument type and name
        void onLicenseInteraction();
    }
}
