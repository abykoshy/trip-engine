package com.ak.tripengine.model;

import java.util.Date;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tap {

    //ID, DateTimeUTC, TapType, StopId, CompanyId, BusID, PAN

    @CsvBindByName(column = "ID")
    private String id;

    @CsvBindByName(column = "DateTimeUTC")
    private Date dateTimeUTC;
    
    @CsvBindByName(column = "TapType")
    private String tapType;

    @CsvBindByName(column = "StopId")
    private String stopId;
    
    @CsvBindByName(column = "CompanyId")
    private String companyId;
    
    @CsvBindByName(column = "BusID")
    private String busId;
    
    @CsvBindByName(column = "PAN")
    private String pan;

}
