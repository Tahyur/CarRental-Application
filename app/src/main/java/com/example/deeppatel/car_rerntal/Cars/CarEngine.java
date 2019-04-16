package com.example.deeppatel.car_rerntal.Cars;
import com.example.deeppatel.car_rerntal.Cars.database.CarsFetcher;
import com.example.deeppatel.car_rerntal.Cars.models.Car;
import java.util.ArrayList;
import java.util.List;

public class CarEngine {

    public static  List<Car> carList = new ArrayList<>();
    public List<Car> getCarList() {
        return carList;
    }
    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    public int getCount(){
        return this.carList.size();
    }

    //Get Car From Local List
    public Car getCar(int position){
        if(carList.size() > 0){
            return carList.get(position);
        }
        return null;
    }

    //Delete
    public Car deleteCar(int position){

        if(carList.size() > 0)
        {
            return carList.remove(position);
        }
        return null;
    }
    //Get All Cars From Database
    public void addCars(final CarAdapter carsAdapter){
        CarsFetcher getCarsFromDB= new CarsFetcher();
        carList=getCarsFromDB.getAllCars(carsAdapter);
    }

}
