package com.parkit.parkingsystem.model;

import java.util.Calendar;
import java.util.Date;

public class Ticket {
    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private Date inTime;
    private Date outTime;
    private boolean regular;

    public boolean isRegular() {
        return regular;
    }

    public void setRegular(boolean regular) {
        this.regular = regular;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getInTime() {
        return inTime == null ? null : new Date(inTime.getTime());
    }

    public void setInTime(Date inTime) {
        if (inTime != null)
            this.inTime = new Date(inTime.getTime());
        else
            this.inTime = null;
    }

    public Date getOutTime() {
        return outTime == null ? null : new Date(outTime.getTime());
    }

    public void setOutTime(Date outTime) {
        if (outTime != null)
            this.outTime = new Date(outTime.getTime());
        else
            this.outTime = null;
    }
}
