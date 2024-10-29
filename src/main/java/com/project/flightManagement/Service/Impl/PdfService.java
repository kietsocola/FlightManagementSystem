package com.project.flightManagement.Service.Impl;
import com.google.zxing.WriterException;
import com.lowagie.text.DocumentException;
import com.project.flightManagement.Model.Ve;
import com.project.flightManagement.Service.BarcodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class PdfService {

    @Autowired
    public SpringTemplateEngine templateEngine;

    public byte[] generatePdfFromHtml(String htmlContent) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (IOException | DocumentException e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    public Context createContext(Ve ticket) throws IOException, WriterException {
        Context context = new Context();
        context.setVariable("ticket", ticket);

        byte[] imageBytes = BarcodeGenerator.generateBarcodeImage(ticket.getMaVe(), 180, 40);
        String barcodeBase64 = Base64.getEncoder().encodeToString(imageBytes);

        context.setVariable("logoUrl", "https://airhex.com/images/airline-logos/alt/bamboo-airways.png");
        context.setVariable("arrowIconUrl", "https://cdn-icons-png.freepik.com/512/724/724816.png");
        context.setVariable("barcodeUrl", "data:image/png;base64," + barcodeBase64);

        return context;
    }
}