package com.thd.pos_system_java_final.shared.ultils;

import com.opencsv.CSVWriter;
import com.thd.pos_system_java_final.models.Account.Account;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class CSVExporter implements Exporter{
    public byte[] generate(List<Account> accounts) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             Writer writer = new OutputStreamWriter(baos)) {
            CSVWriter csvWriter = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] header = {"Full Name", "Email", "Status", "Role", "Phone"};
            csvWriter.writeNext(header);

            // Write CSV data
            for (Account account : accounts) {
                String[] data = {
                        account.getFullName(),
                        account.getEmail(),
                        String.valueOf(account.isStatus()),
                        account.getRole().toString(),
                        account.getPhone()
                };
                csvWriter.writeNext(data);
            }
            csvWriter.flush();
            csvWriter.close();

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
