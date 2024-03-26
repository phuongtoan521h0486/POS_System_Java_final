package com.thd.pos_system_java_final.payments;

import com.thd.pos_system_java_final.conf.Environment;
import com.thd.pos_system_java_final.enums.RequestType;
import com.thd.pos_system_java_final.models.MoMoModels.PaymentResponse;
import com.thd.pos_system_java_final.processor.CreateOrderMoMo;
import com.thd.pos_system_java_final.shared.ultils.LogUtils;

public class MoMo implements IPayment{
    @Override
    public String processPayment(PaymentParams params){
        LogUtils.init();
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderId = String.valueOf(System.currentTimeMillis());
        long amount = (long) (params.getTotalAmount() * 23000);

        String orderInfo = "Pay With MoMo";
        String returnURL = "http://localhost:8888/cart/complete";
        String notifyURL = "http://localhost:8888/cart/step-2";
        Environment environment = Environment.selectEnv("dev");

        PaymentResponse captureWalletMoMoResponse = null;
        try {
            captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_METHOD, Boolean.TRUE);
            return captureWalletMoMoResponse.getPayUrl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
