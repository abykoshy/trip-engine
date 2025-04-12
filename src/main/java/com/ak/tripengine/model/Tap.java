package com.ak.tripengine.model;

import java.util.Date;

import com.ak.tripengine.TrimValue;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.processor.PreAssignmentProcessor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tap {

    //ID, DateTimeUTC, TapType, StopId, CompanyId, BusID, PAN

    @CsvBindByName(column = "ID")
    private Integer id;

    @CsvBindByName(column = "DateTimeUTC")
    @CsvDate("dd-MM-yyyy HH:mm:ss")
    private Date dateTimeUTC;
    
    @CsvBindByName(column = "TapType")
    @PreAssignmentProcessor(processor = TrimValue.class)
    private TapType tapType;

    @CsvBindByName(column = "StopId")
    @PreAssignmentProcessor(processor = TrimValue.class)
    private String stopId;
    
    @CsvBindByName(column = "CompanyId")
    private String companyId;
    
    @CsvBindByName(column = "BusID")
    private String busId;
    
    @CsvBindByName(column = "PAN")
    private String pan;

    public String getKey() {
        return pan + companyId + busId;
    }
}
