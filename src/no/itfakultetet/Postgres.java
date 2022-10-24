package no.itfakultetet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Postgres {

    private Connection conn;

    public Postgres() {
        String url = "jdbc:postgresql://itfakultetet.no/brreg";
        Properties props = new Properties();
        props.setProperty("user", "kurs");
        props.setProperty("password", "kurs123");

        try {
            conn = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            System.out.println("Forbindelse ikke akseptert fordi " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void getData(String navn) {

        List<String> firmaListe = new ArrayList<>();

        System.out.println("Orgnummer\tFirmanavn\tAnsatte");
        System.out.println("-".repeat(60));
        int antallFirma = 0;

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT orgnr, navn, ansatte_antall FROM enheter WHERE navn ilike '" + navn + "%'");
            while (rs.next()) {
                String firmaStreng = rs.getString("orgnr") + "\t" + rs.getString("navn") + "\t" + rs.getString("ansatte_antall");
                System.out.println(firmaStreng);
                firmaListe.add(firmaStreng);
                antallFirma++;
            }
            System.out.println("Antall bedrifter funnet: " + antallFirma);
            rs.close();
            st.close();
            lagreData(firmaListe,navn);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void lagreData(List<String> firmaListe, String navn) {

        Scanner lagre = new Scanner(System.in);
        System.out.println("1. Lagre som CSV-fil  2. Lagre som Excel-fil  3. Lagre som CSV og Excel");
        String lagreSom = lagre.nextLine();
        if(lagreSom.equals("1")) {
            CSV.lagreTilCsv(firmaListe,navn);
        } else if (lagreSom.equals("2")) {
            Excel.lagreTilExcel(firmaListe,navn);
        } else if (lagreSom.equals("3")) {
            CSV.lagreTilCsv(firmaListe,navn);
            Excel.lagreTilExcel(firmaListe,navn);
        } else {
            System.out.println("Valg ikke gjenkjent. Tast 1, 2 eller 3. ");
            lagreData(firmaListe, navn);
        }

    }
}
