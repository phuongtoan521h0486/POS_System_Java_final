package com.thd.pos_system_java_final.processor;


import com.thd.pos_system_java_final.conf.Environment;
import com.thd.pos_system_java_final.enums.Language;
import com.thd.pos_system_java_final.enums.RequestType;
import com.thd.pos_system_java_final.models.MoMoModels.HttpResponse;
import com.thd.pos_system_java_final.models.MoMoModels.PaymentRequest;
import com.thd.pos_system_java_final.models.MoMoModels.PaymentResponse;
import com.thd.pos_system_java_final.shared.constants.Parameter;
import com.thd.pos_system_java_final.shared.exception.MoMoException;
import com.thd.pos_system_java_final.shared.ultils.Encoder;
import com.thd.pos_system_java_final.shared.ultils.LogUtils;

public class CreateOrderMoMo extends AbstractProcess<PaymentRequest, PaymentResponse> {

    public CreateOrderMoMo(Environment environment) {
        super(environment);
    }

    public static PaymentResponse process(Environment env, String orderId, String requestId, String amount, String orderInfo, String returnURL, String notifyURL, String extraData, RequestType requestType, Boolean autoCapture) throws Exception {
        try {
            CreateOrderMoMo m2Processor = new CreateOrderMoMo(env);

            PaymentRequest request = m2Processor.createPaymentCreationRequest(orderId, requestId, amount, orderInfo, returnURL, notifyURL, extraData, requestType, autoCapture);
            PaymentResponse captureMoMoResponse = m2Processor.execute(request);

            return captureMoMoResponse;
        } catch (Exception exception) {
            LogUtils.error("[CreateOrderMoMoProcess] " + exception);
        }
        return null;
    }

    @Override
    public PaymentResponse execute(PaymentRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, PaymentRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getCreateUrl(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[PaymentResponse] [" + request.getOrderId() + "] -> Error API");
            }

            System.out.println("uweryei7rye8wyreow8: " + response.getData());

            PaymentResponse captureMoMoResponse = getGson().fromJson(response.getData(), PaymentResponse.class);
            String responserawData = Parameter.REQUEST_ID + "=" + captureMoMoResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + captureMoMoResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + captureMoMoResponse.getMessage() +
                    "&" + Parameter.PAY_URL + "=" + captureMoMoResponse.getPayUrl() +
                    "&" + Parameter.RESULT_CODE + "=" + captureMoMoResponse.getResultCode();

            LogUtils.info("[PaymentMoMoResponse] rawData: " + responserawData);

            return captureMoMoResponse;

        } catch (Exception exception) {
            LogUtils.error("[PaymentMoMoResponse] " + exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public PaymentRequest createPaymentCreationRequest(String orderId, String requestId, String amount, String orderInfo,
                                                       String returnUrl, String notifyUrl, String extraData, RequestType requestType, Boolean autoCapture) {

        try {
            String requestRawData = Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() + "&" +
                    Parameter.AMOUNT + "=" + amount + "&" +
                    Parameter.EXTRA_DATA + "=" + extraData + "&" +
                    Parameter.IPN_URL + "=" + notifyUrl + "&" +
                    Parameter.ORDER_ID + "=" + orderId + "&" +
                    Parameter.ORDER_INFO + "=" + orderInfo + "&" +
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&" +
                    Parameter.REDIRECT_URL + "=" + returnUrl + "&" +
                    Parameter.REQUEST_ID + "=" + requestId + "&" +
                    Parameter.REQUEST_TYPE + "=" + requestType.getRequestType();

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            LogUtils.debug("[PaymentRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new PaymentRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, orderInfo, Long.valueOf(amount), "test MoMo", null, requestType,
                    returnUrl, notifyUrl, "test store ID", extraData, null, autoCapture, null, signRequest);
        } catch (Exception e) {
            LogUtils.error("[PaymentRequest] " + e);
        }

        return null;
    }

}
