package com.rzktype;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ArchiveParsingTest {
    ClassLoader cl = ArchiveParsingTest.class.getClassLoader();
    String archiveName = "filesArchive.zip";
    String xlsxFileName = "KFO.xlsx";
    String csvFileName = "csvFile.csv";
    String pdfFileName = "pdfSber.pdf";
    String jsonFileName = "generated.json";

    @Test
    void ArchivedFilesTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream(archiveName);
             ZipInputStream zis = new ZipInputStream(stream, Charset.forName("windows-1251"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(pdfFileName)) {
                    PDF pdf = new PDF(zis);
                    Assertions.assertTrue(pdf.text.contains("«СБЕРБАНК РОССИИ»"));
                } else if (entry.getName().contains(xlsxFileName)) {
                    XLS xls = new XLS(zis);
                    Assertions.assertEquals(
                            "Томашук Никита (Томас)",
                            xls.excel
                                    .getSheetAt(0)
                                    .getRow(4)
                                    .getCell(0).getStringCellValue());
                } else if (entry.getName().contains(csvFileName)) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = csvReader.readAll();
                    Assertions.assertEquals(5, content.size());

                    final String[] firstRow = content.get(0);
                    final String[] secondRow = content.get(1);
                    final String[] thirdRow = content.get(2);
                    final String[] fourthRow = content.get(3);
                    final String[] fifthRow = content.get(4);

                    Assertions.assertArrayEquals(new String[]{"100", "информационные"}, firstRow);
                    Assertions.assertArrayEquals(new String[]{"200", "код выполнен успешно"}, secondRow);
                    Assertions.assertArrayEquals(new String[]{"300", "информирует о редиректе"}, thirdRow);
                    Assertions.assertArrayEquals(new String[]{"400", "ошибка на стороне клиента"}, fourthRow);
                    Assertions.assertArrayEquals(new String[]{"500", "ошибка на стороне сервера"}, fifthRow);

                }


//                else {
//                    System.out.println("All files checked");
//
//                }
            }
@Test
        @DisplayName("")

        }
    }

}

