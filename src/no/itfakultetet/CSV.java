package no.itfakultetet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class CSV {

    public static void lagreTilCsv(List<String> firmaListe, String navn) {

        try {
            BufferedWriter utfil = Files.newBufferedWriter(Paths.get(navn.trim()+".csv"), StandardCharsets.UTF_8, StandardOpenOption.CREATE);

            utfil.append("Orgnummer\tFirmanavn\tAnsatte\n");

            firmaListe.forEach( firma -> {
                try {
                    utfil.append(firma+"\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            utfil.flush();
            System.out.println("Filen firma.csv er lagret.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
