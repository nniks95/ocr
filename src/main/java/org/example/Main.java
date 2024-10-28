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

    public static void main(String[] args) {
        String path = "src/main/resources/static/CamScanner 10-25-2024 09.05.jpg";
        service.writeImageToJsonFile(path);
    }
}