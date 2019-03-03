package com.example.deeppatel.car_rerntal.Cars;

import com.example.deeppatel.car_rerntal.Returning_Process.Car;

import java.util.ArrayList;
import java.util.List;

public class AllCarsEngine {

    private List<AllCars> carList = new ArrayList<>();


    public List<AllCars> getCarList() {
        return carList;
    }

    public void setCarList(List<AllCars> carList) {
        this.carList = carList;
    }

    public int getCount(){

        return this.carList.size();

    }

    public AllCars getCar(int position){

        if(carList.size() > 0){

            return carList.get(position);

        }

        return null;

    }

    public void addCars(int count){

        for(int i = 0; i < count; i++){

            carList.add(new AllCars("Car " + i, "Model " + i, "Booked by", "Booked on", "Available on"));

        }

    }

}
