package com.thd.pos_system_java_final.payments;

public interface IPayment {
    String processPayment(PaymentParams params) throws Exception;
}
