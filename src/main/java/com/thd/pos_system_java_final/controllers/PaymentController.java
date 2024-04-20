package com.thd.pos_system_java_final.controllers;
import com.thd.pos_system_java_final.payments.IPayment;
import com.thd.pos_system_java_final.payments.PaymentMethod;
import com.thd.pos_system_java_final.payments.PaymentParams;
import com.thd.pos_system_java_final.payments.SimplePaymentFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


    @RestController
    @RequestMapping("/api/payment")
    public class PaymentController {
        private IPayment paymentStrategy;

        public void setPaymentStrategy(IPayment paymentStrategy) {
            this.paymentStrategy = paymentStrategy;
        }

        @GetMapping("/create_payment")
        public String createPayment(
                HttpServletRequest req, Double totalAmount, Double givenMoney, PaymentMethod paymentMethod)
                throws Exception {
            PaymentParams params = new PaymentParams();
            params.setHttpServletRequest(req);
            params.setTotalAmount(totalAmount);
            params.setGivenMoney(givenMoney);

            setPaymentStrategy(new SimplePaymentFactory().createPayment(paymentMethod));
            return paymentStrategy.processPayment(params);
        }
    }
