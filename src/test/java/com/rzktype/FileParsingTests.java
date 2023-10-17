package com.rzktype;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipFile;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;

public class FileParsingTests {
    ClassLoader cl = FileParsingTests.class.getClassLoader();
    private static final String ARCHIVE_NAME = "filesArchive.zip";
    private static final String XLSX_FILE_NAME = "filesArchive/KFO.xlsx";
    private static final String CSV_FILE_NAME = "filesArchive/csvFile.csv";
    private static final String PDF_FILE_NAME = "filesArchive/pdfSber.pdf";
    private static final String JSON_FILE_NAME = "filesArchive/generated.json";

    @Test
    @Description("Проверка pdf файла из архива")
    void pdfTest() throws Exception {
        InputStream pdfFileStream = getFile(ARCHIVE_NAME, PDF_FILE_NAME);
        PDF pdf = new PDF(pdfFileStream);
        assertThat(pdf.text).contains("«СБЕРБАНК РОССИИ»");
        closeInputStream(pdfFileStream);
    }

    @Test
    @Description("Проверка csv файла из архива")
    void csvTest() throws Exception {
        InputStream csvFileStream = getFile(ARCHIVE_NAME, CSV_FILE_NAME);
        CSVReader csvReader = new CSVReader(new InputStreamReader(csvFileStream, UTF_8));
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
        closeInputStream(csvFileStream);
    }

    @Test
    @Description("Проверка xlsx файла из архива")
    void xlsxTest() throws Exception {
        InputStream xlsxFileStream = getFile(ARCHIVE_NAME, XLSX_FILE_NAME);
        XLS xls = new XLS(xlsxFileStream);
        Assertions.assertEquals(
                "Томашук Никита (Томас)",
                xls.excel
                        .getSheetAt(0)
                        .getRow(4)
                        .getCell(0).getStringCellValue());
        closeInputStream(xlsxFileStream);
    }
    @Test
    @Description("Проверка json файла из архива")
    void jsonTest() throws  Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream jsonFileStream = getFile(ARCHIVE_NAME, JSON_FILE_NAME);

        Employee jsonObject = mapper.readValue(jsonFileStream, Employee.class);

        assertThat(jsonObject.getId()).isEqualTo("652d511c040023a6b742dcfc");
        assertThat(jsonObject.getIndex()).isEqualTo(0);
        assertThat(jsonObject.getActive()).isEqualTo(false);
        assertThatList(jsonObject.getFriends()).contains("Hays Cole", "Welch Harper");
        assertThat(jsonObject.getGreeting()).contains("unread messages");
        assertThat(jsonObject.getFavoriteFruit()).isEqualTo("apple");

    }
    private InputStream getFile(String ARCHIVE_NAME, String fileName) throws Exception {
        URL url = cl.getResource(ARCHIVE_NAME);
        File file = new File(url.toURI());
        ZipFile zipFile = new ZipFile(file);
        return zipFile.getInputStream(zipFile.getEntry(fileName));
    }

    private void closeInputStream(InputStream inputStream) throws IOException {
        if (inputStream != null)
            inputStream.close();
    }


}
