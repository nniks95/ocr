package org.example;

import net.sourceforge.tess4j.Tesseract;
import org.example.ocr.service.OcrIDService;

public class Main {

    public static OcrIDService service = new OcrIDService(new Tesseract());

    public static void main(String[] args) {
        String path = "src/main/resources/static/CamScanner 10-25-2024 09.05.jpg";
        service.writeImageToJsonFile(path);
    }
}