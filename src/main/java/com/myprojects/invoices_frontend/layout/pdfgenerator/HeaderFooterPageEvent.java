package com.myprojects.invoices_frontend.layout.pdfgenerator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class HeaderFooterPageEvent extends PdfPageEventHelper {

    String topLeft;
    String topRight;
    String bottomLeft;
    String bottomRight;
    BaseFont baseFont = BaseFont.createFont(FontFactory.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
    Font font = new Font(baseFont, 9);

    public HeaderFooterPageEvent(String topLeft, String topRight, String bottomLeft, String bottomRight)
            throws DocumentException, IOException {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }

    public void onStartPage(@NotNull PdfWriter writer, Document document) {
        Rectangle rect = writer.getBoxSize("art");
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(topLeft, font),
                rect.getLeft(), rect.getTop(), 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase(topRight, font),
                rect.getRight(), rect.getTop(), 0);
    }
    public void onEndPage(@NotNull PdfWriter writer, @NotNull Document document) {
        Rectangle rect = writer.getBoxSize("art");
        rect.setBorder(Rectangle.BOX);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(bottomLeft, font),
                rect.getLeft(), rect.getBottom(), 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase(bottomRight, font),
                rect.getRight(), rect.getBottom(), 0);
    }
}