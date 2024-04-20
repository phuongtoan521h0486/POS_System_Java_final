package com.thd.pos_system_java_final.payments;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.thd.pos_system_java_final.services.PaypalService;
public class Paypal implements IPayment{
    @Override
    public String processPayment(PaymentParams params) {
        try {
            Payment payment = new PaypalService()
                        .createPayment(params.getTotalAmount());
            for(Links link: payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}