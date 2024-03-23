package com.thd.pos_system_java_final.payments;

public class SimplePaymentFactory { // Apply SimpleFactory

    public IPayment createPayment(PaymentMethod method) {
        switch (method) {
            case Cash:
                return new Cash();
            case VNPay:
                return new VNPay();
            case Paypal:
                return new Paypal();
            case MoMo:
                return new MoMo();
            default:
                return null;
        }
    }
}
