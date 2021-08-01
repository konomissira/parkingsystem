package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

    private static ParkingService parkingService;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;

    @BeforeEach
    private void setUpPerTest(){
            parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    }

    @Test
    public void processIncomingVehicleTest() throws Exception {
        //GIVEN
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingSpotDAO.getNextAvailableSlot(any())).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        when(parkingSpotDAO.updateParking(any())).thenReturn(true);
        when(ticketDAO.saveTicket(any())).thenReturn(true);
        //WHEN
        parkingService.processIncomingVehicle();
        //THEN
        verify(ticketDAO, Mockito.times(1)).saveTicket(any());
    }

    @Test
    public void processIncomingVehicleWithBadVehicleRegNumberTest() throws Exception {
        //GIVEN
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingSpotDAO.getNextAvailableSlot(any())).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenThrow(new Exception());
        //WHEN
        parkingService.processIncomingVehicle();
        //THEN
        verify(ticketDAO, Mockito.times(0)).saveTicket(any());
    }

    @Test
    public void processExitingVehicleTest() throws Exception {
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        Ticket ticket = new Ticket();
        ticket.setInTime(new Date(System.currentTimeMillis() - (60*60*1000)));
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
        when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
        when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);

        parkingService.processExitingVehicle();

        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
    }

    @Test
    public void getNextAvailableSlotWithCarTest() {
        //GIVEN
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingSpotDAO.getNextAvailableSlot(any())).thenReturn(1);
        //WHEN
        ParkingSpot spot = parkingService.getNextParkingNumberIfAvailable();
        //THEN
        assertEquals(1, spot.getId());
        assertEquals(ParkingType.CAR, spot.getParkingType());
        assertTrue(spot.isAvailable());
    }

    @Test
    public void getNextAvailableSlotWithBikeTest() {
        //GIVEN
        when(inputReaderUtil.readSelection()).thenReturn(2);
        when(parkingSpotDAO.getNextAvailableSlot(any())).thenReturn(1);
        //WHEN
        ParkingSpot spot = parkingService.getNextParkingNumberIfAvailable();
        //THEN
        assertEquals(1, spot.getId());
        assertEquals(ParkingType.BIKE, spot.getParkingType());
        assertTrue(spot.isAvailable());
    }

    @Test
    public void getNextAvailableSlotWithUnknownVehicleTest() {
        //GIVEN
        when(inputReaderUtil.readSelection()).thenReturn(3);
        //WHEN
        ParkingSpot spot = parkingService.getNextParkingNumberIfAvailable();
        //THEN
        assertNull(spot);
    }

    @Test
    public void getNextAvailableSlotWithParkingFullTest() {
        //GIVEN
        when(inputReaderUtil.readSelection()).thenReturn(2);
        when(parkingSpotDAO.getNextAvailableSlot(any())).thenReturn(0);
        //WHEN
        ParkingSpot spot = parkingService.getNextParkingNumberIfAvailable();
        //THEN
        assertNull(spot);
    }
}
