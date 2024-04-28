package com.thd.pos_system_java_final.payments;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

@Getter
@Setter
public class PaymentParams {
    public HttpServletRequest httpServletRequest;
    public Double givenMoney;
    public Double totalAmount;
}
