package com.thd.pos_system_java_final.conf;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

import java.util.HashMap;
import java.util.Map;


public class PaypalConfig {
    private static final String clientId = "AYeR6IDFMwW_LnlVFrYwnct52mBkNz0e8L1GHfl5KF7B6QeVK1ysyFRun4RCbQroTpjCK3jrtV3gw8_J";
    private static final String clientSecret = "ECy-WNUtJmYBFWAr8aAEZ7Ry_eLG_5GhW9HW1T22rfiLm0q2Z96OfqgD-MCH_bVHDY_6qhJic4BfrtSK";
    private static final String mode = "sandbox";

    public static Map<String, String> paypalSdkConfig() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", mode);
        return configMap;
    }

    public static OAuthTokenCredential oAuthTokenCredential() {
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
    }

    public static APIContext apiContext() throws PayPalRESTException {
        APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
        context.setConfigurationMap(paypalSdkConfig());
        return context;
    }
}