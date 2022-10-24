package no.itfakultetet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Excel {
    public static void lagreTilExcel(List<String> firmaListe, String navn) {


        Workbook wb = new HSSFWorkbook();
        // Ark med Firmadetaljer
        Sheet ark1 = wb.createSheet(navn);
        CreationHelper createHelper = wb.getCreationHelper();
        Row head = ark1.createRow(0);
        head.createCell(0).setCellValue(createHelper.createRichTextString("Orgnummer"));
        head.createCell(1).setCellValue(createHelper.createRichTextString("Firmanavn"));
        head.createCell(2).setCellValue(createHelper.createRichTextString("Antall ansatte"));

        AtomicInteger i = new AtomicInteger(1);
        firmaListe.forEach(firma -> {
            Row row = ark1.createRow(i.get());
            String[] ord = firma.split("\t");
            for (int j = 0; j < ord.length; j++) {
                row.createCell(j).setCellValue(createHelper.createRichTextString(ord[j]));
            }
            i.getAndIncrement();
        });


        try (OutputStream fileOut = new FileOutputStream(navn.trim()+".xls")) {
            wb.write(fileOut);
            System.out.println(navn+".xsl er lagret.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
