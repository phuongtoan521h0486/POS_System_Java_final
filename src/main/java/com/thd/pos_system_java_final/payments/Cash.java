package com.thd.pos_system_java_final.payments;

public class Cash implements IPayment{
    @Override
    public String processPayment(PaymentParams params) {
        return "POS/step3";
    }
}
