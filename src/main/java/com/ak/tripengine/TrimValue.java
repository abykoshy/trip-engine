package com.ak.tripengine;

import com.opencsv.bean.processor.StringProcessor;

public class TrimValue implements StringProcessor {
    @Override
    public String processString(String value) {
        if (value !=null )
        {
            return value.trim();
        }
        return null;
    }

    @Override
    public void setParameterString(String value) {

    }
}
