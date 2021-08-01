package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ParkingSpotDAO {
    /**
     * CREATE A VARIABLE A NEW INSTANCE OF Logger
     */
    private static final Logger logger = LogManager.getLogger("ParkingSpotDAO");
    /**
     * CREATE A NEW INSTANCE OF DataBaseConfig
     */
    public DataBaseConfig dataBaseConfig = new DataBaseConfig();

    /**
     * GET THE NEXT PARKING SPOT AVALAIBLE
     * @param parkingType
     * @return
     */
    public int getNextAvailableSlot(ParkingType parkingType) {
        Connection con = null;
        int result = -1;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps =
            con.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT);
            ps.setString(1, parkingType.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeConnection(con);
        }
        return result;
    }

    /**
     * UPDATE PARKING SPOT
     * @param parkingSpot
     * @return
     */
    public boolean updateParking(ParkingSpot parkingSpot) {
        //update the availability for that parking slot
        Connection con = null;
        PreparedStatement ps = null;
        boolean retValue = false;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.UPDATE_PARKING_SPOT);
            ps.setBoolean(1, parkingSpot.isAvailable());
            ps.setInt(2, parkingSpot.getId());
            int updateRowCount = ps.executeUpdate();
            retValue = (updateRowCount == 1);
        } catch (Exception ex) {
            logger.error("Error updating parking info", ex);
        } finally {
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
        return retValue;
    }

}
