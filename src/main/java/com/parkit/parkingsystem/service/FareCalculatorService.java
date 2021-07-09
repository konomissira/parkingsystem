package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        long inTime = ticket.getInTime().getTime();
        long outTime = ticket.getOutTime().getTime();

        double differenceInMilliseconds = outTime - inTime;
        double durationInHour = ((differenceInMilliseconds / 1000) / 60) / 60;

        double reduction = ticket.isRegular() ? 0.95 : 1;

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                double price = (durationInHour < 0.5) ? 0 : durationInHour  * Fare.CAR_RATE_PER_HOUR * reduction;
                ticket.setPrice(price);
                break;
            }
            case BIKE: {
                double price = (durationInHour < (0.5)) ? 0 : durationInHour  * Fare.BIKE_RATE_PER_HOUR * reduction;
                ticket.setPrice(price);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}