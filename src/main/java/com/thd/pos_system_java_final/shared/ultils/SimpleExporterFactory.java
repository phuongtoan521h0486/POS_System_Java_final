package com.thd.pos_system_java_final.shared.ultils;

import com.thd.pos_system_java_final.enums.ExportFormat;

public class SimpleExporterFactory {
    public static Exporter createExporter(ExportFormat format) {
        switch (format) {
            case CSV:
                return new CSVExporter();
            case XLSX:
                return new ExcelAdapter();
            default:
                return null;
        }
    }

}
