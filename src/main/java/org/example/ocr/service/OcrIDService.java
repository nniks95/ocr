package org.example.ocr.service;
import com.google.cloud.vision.v1.*;
import com.google.cloud.vision.v1.Image;
import com.google.common.io.Files;
import com.google.protobuf.ByteString;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class OcrIDService {

    private final ITesseract tesseract;

    public OcrIDService(ITesseract tesseract) {
        this.tesseract = tesseract;
    }

    public String readImage(String path){
        setDataPath(tesseract);
        File image = new File(path);
        try {
            String result = tesseract.doOCR(image);
            return result;
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
    }


    private void setDataPath(ITesseract tesseract){
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
        tesseract.setLanguage("srp");
    }

}
