package com.ak.tripengine.utility;

import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(Config.class.getClassLoader().getResourceAsStream("engine.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the property value as a float. Will be used to get the rate of the trip
     * @param key property key
     * @return float value
     */
    public static float getPropertyAsFloat(String key)
    {
        String value = properties.getProperty(key);
        if (value != null)
        {
            return Float.parseFloat(value);
        }
        return 0f;
    }
}
