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

        //TODO: Some tests are failing here. Need to check if this logic is correct
        // (45*1,5/60)

        double differenceInMilliseconds = outTime - inTime;
        double durationInHour = ((differenceInMilliseconds / 1000) / 60) / 60; // temps passé entre l'arrivée de la voiture et son départ en heure

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(durationInHour * Fare.CAR_RATE_PER_HOUR); // calcul du tarif à payer pour une voiture - pour 45 minutes, ça coute 1.125
                break;
            }
            case BIKE: {
                ticket.setPrice(durationInHour * Fare.BIKE_RATE_PER_HOUR); // calcul du tarif à payer pour une moto
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}