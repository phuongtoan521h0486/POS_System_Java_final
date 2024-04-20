package com.thd.pos_system_java_final.shared.ultils;


import com.thd.pos_system_java_final.models.Account.Account;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
public class CompressDecorator extends Decorator{
    public CompressDecorator(Exporter wrapObj) {
        super(wrapObj);
    }

    @Override
    public String getExtensionPart() {
        return ".zip";
    }

    @Override
    public byte[] generate(List<Account> accounts) {
        byte[] data = super.generate(accounts);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zipOut = new ZipOutputStream(baos)) {

            zipOut.putNextEntry(new ZipEntry("staff" + wrapObj.getExtensionPart()));

            zipOut.write(data);
            zipOut.closeEntry();
            zipOut.finish();

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return data;
        }
    }
}
