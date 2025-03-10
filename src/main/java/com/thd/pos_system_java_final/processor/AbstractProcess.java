package com.thd.pos_system_java_final.processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thd.pos_system_java_final.conf.Environment;
import com.thd.pos_system_java_final.conf.PartnerInfo;
import com.thd.pos_system_java_final.shared.exception.MoMoException;
import com.thd.pos_system_java_final.shared.ultils.Execute;

public abstract class AbstractProcess<T, V> {

    protected PartnerInfo partnerInfo;
    protected Environment environment;
    protected Execute execute = new Execute();

    public AbstractProcess(Environment environment) {
        this.environment = environment;
        this.partnerInfo = environment.getPartnerInfo();
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .create();
    }

    public abstract V execute(T request) throws MoMoException;
}
