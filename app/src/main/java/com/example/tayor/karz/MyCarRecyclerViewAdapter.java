package com.example.tayor.karz;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tayor.karz.CarFragment.OnListFragmentInteractionListener;
import com.example.tayor.karz.Model.Car;
import com.example.tayor.karz.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCarRecyclerViewAdapter extends RecyclerView.Adapter<MyCarRecyclerViewAdapter.ViewHolder> {

    private final List<Car> mCarList;
    private final OnListFragmentInteractionListener mListener;

    public MyCarRecyclerViewAdapter(List<Car> carList, OnListFragmentInteractionListener listener) {
        mCarList = carList;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_car, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mCar = mCarList.get(position);
        holder.mCarNameView.setText(mCarList.get(position).getName());
        holder.mModelView.setText(mCarList.get(position).getModel());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mCar);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mCarNameView;
        public final TextView mModelView;
        public Car mCar;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCarNameView = (TextView) view.findViewById(R.id.car_name);
            mModelView = (TextView) view.findViewById(R.id.model);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mModelView.getText() + "'";
        }
    }
}
