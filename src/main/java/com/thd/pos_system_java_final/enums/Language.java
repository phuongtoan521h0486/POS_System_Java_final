package com.thd.pos_system_java_final.enums;

import com.google.gson.annotations.SerializedName;
import com.thd.pos_system_java_final.shared.constants.Constants;


public enum Language {

    @SerializedName("vi")
    VI(Constants.LANGUAGE_VI),

    @SerializedName("en")
    EN(Constants.LANGUAGE_EN);

    private final String value;

    Language(String value) {
        this.value = value;
    }

    public static Language findByName(String name) {
        for (Language type : values()) {
            if (type.getLanguage().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public String getLanguage() {
        return value;
    }

}
