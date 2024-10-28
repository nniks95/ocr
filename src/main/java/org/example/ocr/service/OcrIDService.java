package org.example.ocr.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import org.example.ocr.model.Person;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OcrIDService {

    private final ITesseract tesseract;

    public OcrIDService(ITesseract tesseract) {
        this.tesseract = tesseract;
    }

    public void writeImageToJsonFile(String path) {

        String jsonFolderPath = "src/main/resources/static/json";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Pattern datePattern = Pattern.compile("\\b(\\d{1,2}\\.\\d{1,2}\\.\\d{4})\\b");
        Pattern cardNumberIdPattern = Pattern.compile("\\b(\\d{9})\\b");

        String IDReadResult = readImage(path);
        String[] lines = IDReadResult.split("\\r?\\n");

        // Map to store each line with an index
        Map<String, String> data = parseResultLines(lines);

        Person person = processResult(data, datePattern, cardNumberIdPattern);

        String jsonFile = gson.toJson(person);

        try (FileWriter fileWriter = new FileWriter(jsonFolderPath + "/"
                + person.getFirstName()
                + "-" + person.getLastName()
                + "-" + person.getCardNumberId() + "-"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                + ".json")) {
            fileWriter.write(jsonFile);
            System.out.println("JSON written to file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Person processResult(Map<String, String> data, Pattern datePattern, Pattern cardNumberIdPattern) {
        Person person = new Person();

        for (int i = 1; i <= data.size(); i++) {
            String key = String.valueOf(i);
            String value = data.get(key);
            if (value.contains("Име")) {
                // Get the next line for the name
                String name = data.get(String.valueOf(i + 1)).trim();
                person.setFirstName(name.split(" ")[0]);
            } else if (value.contains("Презиме")) {
                // Get the next line for the lastname
                String lastname = data.get(String.valueOf(i + 1)).trim();
                person.setLastName(lastname.split(" ")[0]);
            } else if (value.contains("Датум рођења")) {
                String dobLine = data.get(String.valueOf(i + 1)).trim();
                // Check if it contains a date in the desired format
                Matcher matcher = datePattern.matcher(dobLine);
                if (matcher.find()) {
                    String dateOfBirth = matcher.group(1);
                    person.setDateOfBirth(dateOfBirth);
                }
            } else if (value.contains("Per.6p.")) {
                String cardNumberId = data.get(String.valueOf(i + 1).trim());
                Matcher matcher = cardNumberIdPattern.matcher(cardNumberId);
                if (matcher.find()) {
                    String cardIdNumber = matcher.group(1);
                    person.setCardNumberId(cardIdNumber);
                }
            }
        }
        return person;
    }

    private Map<String, String> parseResultLines(String[] lines) {
        Map<String, String> data = new LinkedHashMap<>();
        int index = 1;
        for (String line : lines) {
            data.put(String.valueOf(index), line.trim()); // Start keys from 1 in order
            index++;
        }
        return data;
    }

    private String readImage(String path) {
        setDataPath(tesseract);
        File image = new File(path);
        try {
            String result = tesseract.doOCR(image);
            return result;
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDataPath(ITesseract tesseract) {
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
        tesseract.setLanguage("srp");
    }
}
