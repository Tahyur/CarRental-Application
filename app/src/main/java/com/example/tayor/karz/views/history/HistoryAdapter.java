package com.example.tayor.karz.views.history;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tayor.karz.Model.Reservation;
import com.example.tayor.karz.R;
import com.example.tayor.karz.views.history.HistoryFragment.OnHistoryListFragmentInteractionListener;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<Reservation> mReservationList;
    private OnHistoryListFragmentInteractionListener mListener;
    private Context context;

    public HistoryAdapter(List<Reservation> reservationList, OnHistoryListFragmentInteractionListener listener) {
        mReservationList = reservationList;
        mListener = listener;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public HistoryAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_item, viewGroup, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryAdapter.ViewHolder viewHolder, int i) {
        if (mReservationList != null || !mReservationList.isEmpty()) {
            if(mReservationList.get(i).getCar().getStatus() != null) {
                if (mReservationList.get(i).getCar().getStatus().equalsIgnoreCase("false"))
                    viewHolder.mCardView.setBackgroundColor(getContext().getResources().getColor(R.color.red));

                viewHolder.reservation = mReservationList.get(i);
                viewHolder.mCarInitial.setText(String.valueOf(mReservationList.get(i).getCar().getName().charAt(0)));
                viewHolder.mCarName.setText(mReservationList.get(i).getCar().getName());
                viewHolder.mModel.setText(mReservationList.get(i).getCar().getModel());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mListener) {
                            mListener.onHistoryListFragmentInteraction(viewHolder.reservation);
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mReservationList.size();
    }

    public void setmReservationList(List<Reservation> reservationList) {
        this.mReservationList = reservationList;
    }

    public void setmListener(OnHistoryListFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mCarInitial, mCarName, mModel, mDateTime;
        private final CardView mCardView;
        private final View mView;
        private Reservation reservation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCarInitial = itemView.findViewById(R.id.car_initial);
            mCarName = itemView.findViewById(R.id.car_name);
            mModel = itemView.findViewById(R.id.model);
            mDateTime = itemView.findViewById(R.id.dateTime);
            mCardView = itemView.findViewById(R.id.card);
            mView = itemView;
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mCarInitial=" + mCarInitial +
                    ", mCarname=" + mCarName +
                    ", mModel=" + mModel +
                    ", mDateTime=" + mDateTime +
                    ", reservation=" + reservation +
                    '}';
        }
    }
}
