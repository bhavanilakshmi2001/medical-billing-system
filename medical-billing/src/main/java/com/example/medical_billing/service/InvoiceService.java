package com.example.medical_billing.service;

import com.example.medical_billing.model.CustomerOrder;
import com.example.medical_billing.model.OrderItem;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class InvoiceService {

        public byte[] generateInvoicePdf(CustomerOrder order) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                PdfWriter writer = new PdfWriter(out);
                PdfDocument pdf = new PdfDocument(writer);
                Document doc = new Document(pdf);

                // === HEADER ===
                Paragraph header = new Paragraph("Kani Medicals")
                                .setBold()
                                .setFontSize(18)
                                .setTextAlignment(TextAlignment.CENTER)
                                .setFontColor(ColorConstants.WHITE)
                                .setBackgroundColor(ColorConstants.BLUE)
                                .setMarginBottom(10)
                                .setPadding(5);
                doc.add(header);

                // === BILL FROM ===
                doc.add(new Paragraph(order.getBillFromName()).setBold().setFontSize(11));
                doc.add(new Paragraph(order.getBillFromAddress()).setFontSize(10));
                doc.add(new Paragraph("Phone: " + order.getBillFromPhone()).setFontSize(10));
                doc.add(new Paragraph("\n"));

                // === BILL TO + INVOICE DETAILS ===
                Table infoTable = new Table(2);
                infoTable.setWidth(UnitValue.createPercentValue(100));

                infoTable.addCell(new Cell().add(new Paragraph(
                                "Invoice To:\n" + order.getBillToName() + "\n" +
                                                order.getBillToAddress() + "\nPhone: " + order.getBillToPhone()))
                                .setBorder(Border.NO_BORDER)
                                .setFontSize(10));

                infoTable.addCell(new Cell().add(new Paragraph(
                                "Invoice No: " + order.getInvoiceNumber() + "\nDate: " +
                                                order.getInvoiceDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                                +
                                                "\nDL No: " + order.getDlNo()))
                                .setTextAlignment(TextAlignment.RIGHT)
                                .setBorder(Border.NO_BORDER)
                                .setFontSize(10));

                doc.add(infoTable);
                doc.add(new Paragraph("\n"));

                // === ITEM TABLE ===
                float[] colWidths = { 20, 120, 80, 60, 50, 40, 60 };
                Table table = new Table(colWidths);
                table.setWidth(UnitValue.createPercentValue(100));

                String[] headers = { "S.No", "Item Summary", "Batch No","Price", "Qty", "Total" };
                for (String h : headers) {
                        table.addHeaderCell(new Cell().add(new Paragraph(h).setBold().setFontSize(9))
                                        .setBackgroundColor(ColorConstants.BLUE)
                                        .setFontColor(ColorConstants.WHITE)
                                        .setTextAlignment(TextAlignment.CENTER));
                }

                int index = 1;
                for (OrderItem item : order.getItems()) {
                        table.addCell(String.valueOf(index++));
                        table.addCell(item.getProductName());
                        table.addCell(item.getBatchNo());
                        table.addCell("N/A"); // Add expiry if available in future
                        table.addCell(String.format("%.2f", item.getPrice()));
                        table.addCell(String.valueOf(item.getQuantity()));
                        table.addCell(String.format("%.2f", item.getTotal()));
                }

                doc.add(table);
                doc.add(new Paragraph("\n"));

                // === TOTAL & PAYMENT ===
                Paragraph discount = new Paragraph("Discount: 15%")
                                .setTextAlignment(TextAlignment.RIGHT)
                                .setFontSize(10);
                doc.add(discount);

                double discounted = order.getTotalAmount() * 0.85;

                Paragraph totalP = new Paragraph("Total: ₹" + String.format("%.2f", discounted))
                                .setBold()
                                .setFontSize(12)
                                .setTextAlignment(TextAlignment.RIGHT);
                doc.add(totalP);

                doc.add(new Paragraph("Payment Mode: " + order.getPaymentMode())
                                .setTextAlignment(TextAlignment.RIGHT)
                                .setFontSize(10));

                doc.add(new Paragraph("\n"));

                // === LINE SEPARATOR ===
                SolidLine solidLine = new SolidLine(1f);
                solidLine.setColor(ColorConstants.BLACK);
                LineSeparator separator = new LineSeparator(solidLine);
                doc.add(separator);

                // === FOOTER ===
                doc.add(new Paragraph("All Kind of English and Veterinary Medicines Available At 24/7")
                                .setTextAlignment(TextAlignment.CENTER)
                                .setFontSize(9));

                doc.close();
                return out.toByteArray();
        }
}
