package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sourceforge.tess4j.Tesseract;
import org.example.ocr.model.Person;
import org.example.ocr.model.Result;
import org.example.ocr.service.OcrIDService;


import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static OcrIDService service = new OcrIDService(new Tesseract());
    public static Person person = new Person();

    public static void main(String[] args) {

        String path = "src/main/resources/static/CamScanner 10-25-2024 09.05.jpg";
        String jsonFolderPath = "src/main/resources/static/json";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Pattern datePattern = Pattern.compile("\\b(\\d{1,2}\\.\\d{1,2}\\.\\d{4})\\b");
        Pattern cardNumberIdPattern = Pattern.compile("\\b(\\d{9})\\b");

        String IDReadResult = service.readImage(path);
        String[] lines = IDReadResult.split("\\r?\\n");

        // Map to store each line with an index
        Map<String, String> data = parseResultLines(lines);

        Result result = processResult(data, datePattern, cardNumberIdPattern);

        person = new Person(result.getNameTrimmed(), result.getLastnameTrimmed(), result.getDateOfBirth(), result.getCardNumberId());

        String jsonFile = gson.toJson(person);

        try (FileWriter fileWriter = new FileWriter(jsonFolderPath +
                "/"+result.getNameTrimmed()+"-"+
                result.getLastnameTrimmed()+"-"+
                result.getCardNumberId()+".json")) {
            fileWriter.write(jsonFile);
            System.out.println("JSON written to file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Result processResult(Map<String, String> data, Pattern datePattern, Pattern cardNumberIdPattern) {
        Result result = new Result();

        for (int i = 1; i <= data.size(); i++) {
            String key = String.valueOf(i);
            String value = data.get(key);
            if (value.contains("Име")) {
                // Get the next line for the name
                String name = data.get(String.valueOf(i + 1)).trim();
                result.setNameTrimmed(name.split(" ")[0]);
            } else if (value.contains("Презиме")) {
                // Get the next line for the lastname
                String lastname = data.get(String.valueOf(i + 1)).trim();
                result.setLastnameTrimmed(lastname.split(" ")[0]);
            } else if (value.contains("Датум рођења")) {
                String dobLine = data.get(String.valueOf(i + 1)).trim();
                // Check if it contains a date in the desired format
                Matcher matcher = datePattern.matcher(dobLine);
                if (matcher.find()) {
                    String dateOfBirth = matcher.group(1);
                    result.setDateOfBirth(dateOfBirth);
                }
            }
            else if(value.contains("Per.6p.")){
                String cardNumberId = data.get(String.valueOf(i+1).trim());
                Matcher matcher = cardNumberIdPattern.matcher(cardNumberId);
                if(matcher.find()){
                    String cardIdNumber = matcher.group(1);
                    result.setCardNumberId(cardIdNumber);
                }
            }
        }
        return result;
    }

    private static Map<String, String> parseResultLines(String[] lines) {
        Map<String, String> data = new LinkedHashMap<>();
        int index = 1;
        for (String line : lines) {
            data.put(String.valueOf(index), line.trim()); // Start keys from 1 in order
            index++;
        }
        return data;
    }
}