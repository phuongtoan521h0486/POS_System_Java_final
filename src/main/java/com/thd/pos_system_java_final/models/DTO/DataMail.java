package com.thd.pos_system_java_final.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataMail {
    private String to;
    private String subject;
    private String content;
    private Map<String, Object> props;
}