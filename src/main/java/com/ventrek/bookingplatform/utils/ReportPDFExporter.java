package com.ventrek.bookingplatform.utils;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfTable;
import com.ventrek.bookingplatform.domain.Allocation;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import javax.servlet.http.HttpServletResponse;

public class ReportPDFExporter {
    private List<Allocation> allocations;
    private double total;

    public ReportPDFExporter(List<Allocation> allocations) {
        this.allocations = allocations;
    }

    public ReportPDFExporter(List<Allocation> allocations, double total) {
        this.allocations = allocations;
        this.total = total;
    }

    private void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Delivery Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Delivery ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Driver Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Vehicle", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Hours", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Wage (/Hr)", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Sub Total", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        for (Allocation allocation : allocations) {
            table.addCell(allocation.getDeliveryDate().toString());
            table.addCell(allocation.getPackageDeliveryRequest().getId().toString());
            table.addCell(allocation.getDriver().getName());
            table.addCell(allocation.getVehicle().getRegistrationNumber()+"-"+allocation.getVehicle().getVehicleDescription());
            table.addCell(String.valueOf(allocation.getDeliveryDuration()));
            table.addCell(String.valueOf(allocation.getDriver().getHourlyWage()));
            table.addCell(String.valueOf(allocation.getDriver().getHourlyWage()*allocation.getDeliveryDuration()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Report", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
