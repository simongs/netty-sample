package com.dasoulte.simons.server.core;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by NHNEnt on 2016-11-08.
 */
public class DaemonProperties {
    public static final String PAYCO_API_HOST = PropertyResourceBundle.getBundle("properties/value").getString("payco.billing.api.host");
}
