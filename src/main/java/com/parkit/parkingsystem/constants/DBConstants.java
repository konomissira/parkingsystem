package com.parkit.parkingsystem.constants;

public class DBConstants {

    private DBConstants() {}

    /**
     * CREATE A VARIABLE GET_NEXT_PARKING_SPOT IN THE DB.
     */
    public static final String GET_NEXT_PARKING_SPOT =
            "select min(PARKING_NUMBER) from parking "
                    +
            "where AVAILABLE = true and TYPE = ?";
    /**
     * CREATE A VARIABLE UPDATE_PARKING_SPOT IN THE DB
     */
    public static final String UPDATE_PARKING_SPOT =
            "update parking set available = ? "
                    +
            "where PARKING_NUMBER = ?";
    /**
     * CREATE A VARIABLE SAVE_TICKET IN THE DB
     */
    public static final String SAVE_TICKET =
            "insert into ticket(PARKING_NUMBER, "
                    +
            "VEHICLE_REG_NUMBER, "
                    +
            "PRICE, IN_TIME, OUT_TIME) values(?,?,?,?,?)";
    /**
     * CREATE A VARIABLE UPDATE_TICKET IN THE DB
     */
    public static final String UPDATE_TICKET =
            "update ticket set PRICE=?, "
                    +
            "OUT_TIME=? where ID=?";
    /**
     * CREATE A VARIABLE GET_TICKET IN THE DB
     */
    public static final String GET_TICKET =
            "select t.PARKING_NUMBER, t.ID, "
                    +
            "t.PRICE, t.IN_TIME, t.OUT_TIME, "
                    +
            "p.TYPE from ticket t,"
                    +
            "parking p where p.parking_number = t.parking_number "
                    +
            "and t.VEHICLE_REG_NUMBER=? order by t.IN_TIME  limit 1";
    /**
     * CREATE A VARIABLE GET_TICKET_WITH_OUTTIME IN THE DB
     */
    public static final String GET_TICKET_WITH_OUTTIME =
            "select t.ID from ticket t "
                    +
            "where t.VEHICLE_REG_NUMBER=? "
                    +
            "and t.OUT_TIME IS NOT NULL";
}
