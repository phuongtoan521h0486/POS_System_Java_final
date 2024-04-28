package com.thd.pos_system_java_final.models.MoMoModels;

public class Response {
    protected long responseTime;
    protected String message;
    protected Integer resultCode;
    private String partnerCode;
    private String orderId;
    public Response() {
        this.responseTime = System.currentTimeMillis();
    }

    public long getResponseTime() {
        return System.currentTimeMillis();
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }
}
