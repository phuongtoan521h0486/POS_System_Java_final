package com.thd.pos_system_java_final.processor;

import com.thd.pos_system_java_final.conf.Environment;
import com.thd.pos_system_java_final.enums.Language;
import com.thd.pos_system_java_final.models.MoMoModels.BindingTokenRequest;
import com.thd.pos_system_java_final.models.MoMoModels.BindingTokenResponse;
import com.thd.pos_system_java_final.models.MoMoModels.HttpResponse;
import com.thd.pos_system_java_final.shared.constants.Parameter;
import com.thd.pos_system_java_final.shared.exception.MoMoException;
import com.thd.pos_system_java_final.shared.ultils.Encoder;
import com.thd.pos_system_java_final.shared.ultils.LogUtils;

public class BindingToken extends AbstractProcess<BindingTokenRequest, BindingTokenResponse> {
    public BindingToken(Environment environment) {
        super(environment);
    }

    public static BindingTokenResponse process(Environment env, String orderId, String requestId, String partnerClientId, String callbackToken) {
        try {
            BindingToken m2Processor = new BindingToken(env);

            BindingTokenRequest request = m2Processor.createBindingTokenRequest(orderId, requestId, partnerClientId, callbackToken);
            BindingTokenResponse bindingTokenResponse = m2Processor.execute(request);

            return bindingTokenResponse;
        } catch (Exception exception) {
            LogUtils.error("[BindingTokenProcess] " + exception);
        }
        return null;
    }

    @Override
    public BindingTokenResponse execute(BindingTokenRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, BindingTokenRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getTokenBindUrl(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[BindingTokenResponse] [" + request.getOrderId() + "] -> Error API");
            }

            System.out.println("uweryei7rye8wyreow8: " + response.getData());

            BindingTokenResponse BindingTokenResponse = getGson().fromJson(response.getData(), BindingTokenResponse.class);
            String responserawData = Parameter.REQUEST_ID + "=" + BindingTokenResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + BindingTokenResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + BindingTokenResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + BindingTokenResponse.getResultCode();

            LogUtils.info("[BindingTokenResponse] rawData: " + responserawData);

            return BindingTokenResponse;

        } catch (Exception exception) {
            LogUtils.error("[BindingTokenResponse] " + exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public BindingTokenRequest createBindingTokenRequest(String orderId, String requestId, String partnerClientId, String callbackToken) {

        try {
            String requestRawData = Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() + "&" +
                    Parameter.CALLBACK_TOKEN + "=" + callbackToken + "&" +
                    Parameter.ORDER_ID + "=" + orderId + "&" +
                    Parameter.PARTNER_CLIENT_ID + "=" + partnerClientId + "&" +
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&" +
                    Parameter.REQUEST_ID + "=" + requestId;

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            LogUtils.debug("[BindingTokenRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new BindingTokenRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, partnerClientId, callbackToken, signRequest);
        } catch (Exception e) {
            LogUtils.error("[BindingTokenResponse] " + e);
        }

        return null;
    }

}