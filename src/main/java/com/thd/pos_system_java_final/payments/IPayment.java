package com.thd.pos_system_java_final.payments;
public interface IPayment {
    public String processPayment(PaymentParams params) throws Exception;
}
