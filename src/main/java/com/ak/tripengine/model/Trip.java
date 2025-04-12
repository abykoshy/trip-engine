package com.ak.tripengine.model;

import com.ak.tripengine.TripEngineException;
import com.ak.tripengine.utility.Config;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 *
 * VO to hold the output Trip
 *
 * @author Aby Koshy
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trip {

    public static String header = "Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status";

    @CsvBindByPosition(position = 0)
    @CsvDate("dd-MM-yyyy HH:mm:ss")
    private Date started;

    @CsvBindByPosition(position = 1)
    @CsvDate("dd-MM-yyyy HH:mm:ss")
    private Date finished;

    @CsvBindByPosition(position = 2)
    private long durationSecs;

    @CsvBindByPosition(position = 3)
    private String fromStopId;

    @CsvBindByPosition(position = 4)
    private String toStopId;

    @CsvBindByPosition(position = 5)
    private float chargeAmount;

    @CsvBindByPosition(position = 6)
    private String companyId;

    @CsvBindByPosition(position = 7)
    private String busId;

    @CsvBindByPosition(position = 8)
    private String pan;

    @CsvBindByPosition(position = 9)
    private Status status;

    public void calculateTripDetails() throws TripEngineException {
        if ((getStarted() != null) && (getFinished() != null)) {
            setDurationSecs((getFinished().getTime() - getStarted().getTime()) / 1000);
        }
        float charge = Config.getPropertyAsFloat(getFromStopId()+"-"+getToStopId());
        if (charge <= 0)
        {
            charge = Config.getPropertyAsFloat(getToStopId()+"-"+getFromStopId());
        }
        if ((charge <= 0) &&
                (fromStopId != null) && (toStopId != null) &&
                (!StringUtils.equals(fromStopId,toStopId))) {
            throw new TripEngineException(String.format("Charge not configured for the route from : %s, to : %s", fromStopId, toStopId));
        }
        setChargeAmount(charge);
    }

    public void addTapDetails(Tap tap) throws TripEngineException {
        setCompanyId(tap.getCompanyId());
        setBusId(tap.getBusId());
        setPan(tap.getPan());
        if (tap.getTapType() == TapType.ON) {
            setStarted(tap.getDateTimeUTC());
            setFromStopId(tap.getStopId());
        } else if (tap.getTapType() == TapType.OFF) {
            setFinished(tap.getDateTimeUTC());
            setToStopId(tap.getStopId());
            calculateTripDetails();
        }

    }

}
