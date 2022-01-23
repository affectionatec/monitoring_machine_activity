package uk.ac.cardiff.mma.application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.mma.application.DTO.EquipmentDetailDTO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import uk.ac.cardiff.mma.application.equipment.entity.Equipment;

// Adapted from: https://ozenero.com/springboot-itext-pdf-mysql
public class PDFGenerator {

    private static Logger logger = LoggerFactory.getLogger(PDFGenerator.class);

    public static ByteArrayInputStream equipmentDetailPDFReport(List<Equipment> equipmentS) {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Add Text to PDF file ->
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph( "Equipment Details Table", font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);

            // Segment code to create PDF table having 4 columns.
            PdfPTable table = new PdfPTable(4);
            // Add table’s headers to PDF file.
            Stream.of("Equipment ID", "Name", "Inventory", "Charge rate")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        // Set border.
                        header.setBorderWidth(2);
                        header.setPadding(8);
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        table.addCell(header);
                    });

            // Add table’s rows.
            for (Equipment equipment : equipmentS) {
                PdfPCell IDCell = new PdfPCell(new Phrase(String.valueOf(equipment.getId())));
                IDCell.setPadding(8);
                IDCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                IDCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(IDCell);

                PdfPCell NameCell = new PdfPCell(new Phrase(equipment.getName()));
                NameCell.setPadding(8);
                NameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                NameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(NameCell);

                PdfPCell InventoryCell = new PdfPCell(new Phrase(String.valueOf(equipment.getInventory())));
                InventoryCell.setPadding(8);
                InventoryCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                InventoryCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(InventoryCell);

                PdfPCell ChargeRateCell = new PdfPCell(new Phrase(Float.toString(equipment.getCharge_rate())));
                ChargeRateCell.setPadding(8);
                ChargeRateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                ChargeRateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(ChargeRateCell);
            }
            document.add(table);

            document.close();
        } catch(DocumentException e) {
            logger.error(e.toString());
        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

}

