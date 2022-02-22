package com.myprojects.invoices_frontend.layout.pdfgenerator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.myprojects.invoices_frontend.domain.Invoices;
import com.myprojects.invoices_frontend.domain.Products;
import com.myprojects.invoices_frontend.domain.VatSummary;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class PdfGenerator extends NumberSpeaker {

    private static final Logger logger = LoggerFactory.getLogger(PdfGenerator.class);
    private final Invoices invoice;

    public PdfGenerator(Invoices invoice) {
        this.invoice = invoice;
    }

    public File generatePdf() throws IOException, DocumentException {

        BaseFont baseFont = BaseFont.createFont(FontFactory.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        BaseFont baseBoldFont = BaseFont.createFont(FontFactory.HELVETICA_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED);
        BaseFont oblBoldFont = BaseFont.createFont(FontFactory.HELVETICA_OBLIQUE, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(baseFont, 12);
        Font boldFont = new Font(baseBoldFont, 12);
        Font boldSmallFont = new Font(baseBoldFont, 9);
        Font oblFont = new Font(oblBoldFont, 10);
        Font toPayFont = new Font(baseBoldFont, 16);

        String invoiceDate = invoice.getDate().toString();
        Document document = new Document();

        try {
            String invoiceNumber = invoice.getNumber().replace("/", "_");
            String path = invoice.getUser().getPdfPath();
            String fileName = path + "/Faktura nr " + invoiceNumber + " z dnia "
                    +  invoiceDate + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            Rectangle rect = new Rectangle(30, 30, 550, 800);
            rect.setBorder(Rectangle.TOP);
            writer.setBoxSize("art", rect);
            HeaderFooterPageEvent event = new HeaderFooterPageEvent("","",
                    "Wydrukowano z programu INVOICES",
                    "Faktura nr " + invoice.getNumber() + " " + "Strona " + writer.getCurrentPageNumber());
            writer.setPageEvent(event);

            document.open();

            document.add(createHeaderUserParagraph());
            document.add(createTownAndDateParagraph());
            document.add(createInvoiceNumberParagraph());
            document.add(createHeaderTable());
            document.add(new Chunk("Sposób zapłaty: ", boldFont));
            document.add(new Chunk(invoice.getPaymentMethod(), font));
            document.add(Chunk.NEWLINE);
            if(getPeriod() > 0) {
                document.add(new Chunk("                             Termin: "
                        + getPeriod() + " dni upływa " + invoice.getPaymentDate().toString(), font));
                document.add(Chunk.NEWLINE);
            } else {
                document.add(new Chunk("                             Termin: "
                        + invoice.getPaymentDate().toString(), font));
                document.add(Chunk.NEWLINE);
            }
            if(Objects.equals(invoice.getPaymentMethod(), "przelew")) {
                document.add(new Chunk("Bank: ", boldFont));
                document.add(new Chunk(invoice.getUser().getBank(), font));
                document.add(Chunk.NEWLINE);
                document.add(new Chunk("Nr konta: ", boldFont));
                document.add(new Chunk("PL " + invoice.getUser().getAccountNumber(), font));
            }
            document.add(createProductsTable());
            if(getPeriod() == 0 && Objects.equals(invoice.getPaymentMethod(), "gotówka")) {
                Paragraph par = new Paragraph();
                par.setAlignment(Element.ALIGN_RIGHT);
                par.add(new Chunk("zapłacono gotówką           ", boldSmallFont));
                document.add(par);
            }
            document.add(new Chunk("DO ZAPŁATY: " + invoice.getGrossSum().toString()
                    .replace(".",",") + " zł", toPayFont));
            document.add(Chunk.NEWLINE);
            document.add(new Chunk(generateValueInWords(invoice.getGrossSum()), oblFont));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(signatures());

            document.close();

            File pdfInvoice = new File(fileName);
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pdfInvoice);

            logger.info("A pdf file has been generated for invoice " + invoice.getNumber());
            return pdfInvoice;
        } catch (DocumentException ex) {
            logger.error("Error occurred: {0}", ex);
            return null;
        }
    }

    private @NotNull Paragraph createHeaderUserParagraph() throws DocumentException, IOException {

        BaseFont baseFont = BaseFont.createFont(FontFactory.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(baseFont, 12);

        Paragraph paraHeaderUser = new Paragraph();
        paraHeaderUser.setAlignment(Element.ALIGN_LEFT);
        paraHeaderUser.setFont(font);
        Chunk userFullName = new Chunk(invoice.getUser().getFullName(), font);
        Chunk userStreet = new Chunk(invoice.getUser().getStreet(), font);
        Chunk userPostCodeAndTown = new Chunk(invoice.getUser().getPostCode() + " "
                + invoice.getUser().getTown(), font);
        Chunk userNIP = new Chunk("NIP: " + invoice.getUser().getNip(), font);

        paraHeaderUser.add(userFullName);
        paraHeaderUser.add(Chunk.NEWLINE);
        paraHeaderUser.add(userStreet);
        paraHeaderUser.add(Chunk.NEWLINE);
        paraHeaderUser.add(userPostCodeAndTown);
        paraHeaderUser.add(Chunk.NEWLINE);
        paraHeaderUser.add(userNIP);
        paraHeaderUser.add(Chunk.NEWLINE);
        paraHeaderUser.add(Chunk.NEWLINE);

        return  paraHeaderUser;
    }

    private @NotNull Paragraph createTownAndDateParagraph() throws DocumentException, IOException {

        BaseFont baseFont = BaseFont.createFont(FontFactory.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font headerFont = new Font(baseFont, 10);
        String invoiceDate = invoice.getDate().toString();

        Paragraph paraTownAndDate = new Paragraph();
        paraTownAndDate.setAlignment(Element.ALIGN_RIGHT);
        paraTownAndDate.setFont(headerFont);

        Chunk saleTown = new Chunk("Miejsce wystawienia: " + invoice.getUser().getTown());
        Chunk saleDate = new Chunk("Data wystawienia: " + invoiceDate);

        paraTownAndDate.add(saleTown);
        paraTownAndDate.add(Chunk.NEWLINE);
        paraTownAndDate.add(saleDate);
        paraTownAndDate.add(Chunk.NEWLINE);
        paraTownAndDate.add(Chunk.NEWLINE);

        return paraTownAndDate;
    }

    private @NotNull Paragraph createInvoiceNumberParagraph() throws DocumentException, IOException {

        BaseFont baseFont = BaseFont.createFont(FontFactory.HELVETICA_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(baseFont, 16);

        Paragraph paraInvoiceNumber = new Paragraph();
        paraInvoiceNumber.setAlignment(Element.ALIGN_MIDDLE);
        paraInvoiceNumber.setFont(font);

        paraInvoiceNumber.add("Faktura VAT nr " + invoice.getNumber());
        paraInvoiceNumber.add(Chunk.NEWLINE);
        paraInvoiceNumber.add(Chunk.NEWLINE);

        return paraInvoiceNumber;
    }

    private @NotNull PdfPTable createHeaderTable() throws DocumentException, IOException {

        BaseFont boldBaseFont = BaseFont.createFont(FontFactory.HELVETICA_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED);
        BaseFont baseFont = BaseFont.createFont(FontFactory.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font boldFont = new Font(boldBaseFont, 11);
        Font font = new Font(baseFont, 11);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(new float[] {295, 5, 295}, PageSize.A4);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.getDefaultCell().setBorder(PdfPCell.LEFT + PdfPCell.RIGHT);

       PdfPCell c1 = new PdfPCell(new Phrase("Sprzedawca/podatnik", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        c1.setBorder(0);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(" ", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        c1.setBorder(0);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Nabywca/płatnik", boldFont));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        c1.setBorder(0);
        table.addCell(c1);

        table.addCell(new PdfPCell(new Phrase(invoice.getUser().getFullName(), boldFont)))
                .setBorder(PdfPCell.TOP + PdfPCell.LEFT + PdfPCell.RIGHT);
        table.addCell(new PdfPCell(new Phrase(" ", boldFont))).setBorder(0);
        table.addCell(new PdfPCell(new Phrase(invoice.getCustomer().getFullName(), boldFont)))
                .setBorder(PdfPCell.TOP + PdfPCell.LEFT + PdfPCell.RIGHT);
        table.addCell(new PdfPCell(new Phrase(" ", font)))
                .setBorder(PdfPCell.LEFT + PdfPCell.RIGHT);
        table.addCell(new PdfPCell(new Phrase(" ", font))).setBorder(0);
        table.addCell(new PdfPCell(new Phrase(" ", font)))
                .setBorder(PdfPCell.LEFT + PdfPCell.RIGHT);
        table.addCell(new PdfPCell(new Phrase(invoice.getUser().getStreet(), font)))
                .setBorder(PdfPCell.LEFT + PdfPCell.RIGHT);
        table.addCell(new PdfPCell(new Phrase(" ", font))).setBorder(0);
        table.addCell(new PdfPCell(new Phrase(invoice.getCustomer().getStreet(), font)))
                .setBorder(PdfPCell.LEFT + PdfPCell.RIGHT);
        table.addCell(new PdfPCell(new Phrase(invoice.getUser().getPostCode()
                + " " + invoice.getUser().getTown(), font))).setBorder(PdfPCell.LEFT + PdfPCell.RIGHT);
        table.addCell(new PdfPCell(new Phrase(" ", font))).setBorder(0);
        table.addCell(new PdfPCell(new Phrase(invoice.getCustomer().getPostCode()
                + " " + invoice.getCustomer().getTown(), font))).setBorder(PdfPCell.LEFT + PdfPCell.RIGHT);
        table.addCell(new PdfPCell(new Phrase("NIP: " + invoice.getUser().getNip(), font)))
                .setBorder(PdfPCell.LEFT + PdfPCell.RIGHT + PdfPCell.BOTTOM);
        table.addCell(new PdfPCell(new Phrase(" ", font))).setBorder(0);
        table.addCell(new PdfPCell(new Phrase("NIP: " + invoice.getCustomer().getNip(), font)))
                .setBorder(PdfPCell.LEFT + PdfPCell.RIGHT + PdfPCell.BOTTOM);

        return table;
    }

    private int getPeriod() {
        Period period = Period.between(invoice.getPaymentDate(), invoice.getDate());
        return Math.abs(period.getDays());
    }

    private @NotNull PdfPTable createProductsTable() throws DocumentException, IOException {

        BaseFont baseHeaderFont = BaseFont.createFont(FontFactory.HELVETICA_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED);
        BaseFont baseInsideFont = BaseFont.createFont(FontFactory.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);

        Font headerFont = new Font(baseHeaderFont, 9);
        Font insideFont = new Font(baseInsideFont, 9);
        Font pkwiuFont = new Font(baseInsideFont, 6);

        PdfPTable table = new PdfPTable(10);

        table.setWidthPercentage(new float[] {22, 204, 47, 42, 30, 57, 57, 22, 57, 57}, PageSize.A4);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell c1 = new PdfPCell(new Phrase("L p", headerFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.BOX);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Nazwa towaru lub usługi", headerFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.BOX);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Symbol\nPKWiU", headerFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.BOX);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Ilość", headerFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.BOX);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("J.m.", headerFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.BOX);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Cena jedn.\nbrutto\nzł", headerFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.BOX);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Wartość\nnetto\nzł", headerFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.BOX);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("St\n%", headerFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.BOX);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Kwota\npodatku\nzł", headerFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.BOX);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Wartość\nbrutto\nzł", headerFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.BOX);
        table.addCell(c1);

        AtomicInteger counter = new AtomicInteger();
        for(Products product : invoice.getProductsList()) {
            counter.getAndIncrement();
            c1 = new PdfPCell(new Phrase(String.valueOf(counter.get()), insideFont));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(product.getName()), insideFont));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(product.getPkwiu(), pkwiuFont));
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(1), insideFont));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(product.getMeasureUnit(), insideFont));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(product.getGrossPrice().toString().replace(".",
                    ","), insideFont));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(product.getNetPrice()).replace(".",
                    ","), insideFont));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(product.getVatRate()).replace(".",
                    ","), insideFont));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(product.getVatValue()).replace(".",
                    ","), insideFont));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(product.getGrossPrice()).replace(".",
                    ","), insideFont));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c1);
        }
        c1 = new PdfPCell(new Phrase(" ", insideFont));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        table.addCell(c1);
        table.addCell(c1);
        table.addCell(c1);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("RAZEM", headerFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(" ", insideFont));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        table.addCell(c1);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(String.valueOf(invoice.getGrossSum()).replace(".",","),
                headerFont));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c1.setBorder(Rectangle.BOX);
        table.addCell(c1);

        List<VatSummary> vatSummaryList = generateVatSumList();

        for (VatSummary entry : vatSummaryList) {
            c1 = new PdfPCell(new Phrase(" ", insideFont));
            c1.setBorder(Rectangle.NO_BORDER);
            table.addCell(c1);
            table.addCell(c1);
            table.addCell(c1);
            table.addCell(c1);
            table.addCell(c1);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(entry.getNetSum().toString().replace(".",","),
                    headerFont));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1.setBorder(Rectangle.BOX);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(entry.getVatRate()).replace(".",","),
                    headerFont));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(Rectangle.BOX);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(entry.getVatSum().toString().replace(".",","),
                    headerFont));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1.setBorder(Rectangle.BOX);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(entry.getGrossSum().toString().replace(".",","), headerFont));
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            c1.setBorder(Rectangle.BOX);
            table.addCell(c1);
        }

        c1 = new PdfPCell(new Phrase(" ", insideFont));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        table.addCell(c1);
        table.addCell(c1);
        table.addCell(c1);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("OGÓŁEM", headerFont));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(invoice.getNetSum().toString().replace(".",","), headerFont));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c1.setBorder(Rectangle.BOX);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("XX", insideFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.BOX);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(invoice.getVatSum().toString().replace(".",","), headerFont));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c1.setBorder(Rectangle.BOX);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(invoice.getGrossSum().toString().replace(".",","), headerFont));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c1.setBorder(Rectangle.BOX);
        table.addCell(c1);

        return table;
    }

    private @NotNull List<VatSummary> generateVatSumList() {
        List<VatSummary> vatSummaryList = new ArrayList<>();
        invoice.getProductsList().stream()
                .map(Products::getVatRate)
                .distinct()
                .forEach(v -> {
                    int vatRate = v;
                    BigDecimal netSum  = invoice.getProductsList().stream()
                            .filter(p -> p.getVatRate() == v)
                            .map(Products::getNetPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal vatSum = invoice.getProductsList().stream()
                            .filter(p -> p.getVatRate() == v)
                            .map(Products::getVatValue)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal grossSum = invoice.getProductsList().stream()
                            .filter(p -> p.getVatRate() == v)
                            .map(Products::getGrossPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    vatSummaryList.add(new VatSummary(vatRate, netSum, vatSum, grossSum));
                });
        return vatSummaryList;
    }

    private @NotNull String generateValueInWords(@NotNull BigDecimal value) {
        String val = value.toString();
        String zloty = speakNumber(val.substring(0, val.length() - 3));
        String grosze = speakNumber(val.substring(val.length() - 2));
        return "słownie: " + zloty + "zł " + grosze + "gr";
    }

    private @NotNull PdfPTable signatures() throws DocumentException, IOException {
        BaseFont baseInsideFont = BaseFont.createFont(FontFactory.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);

        Font font = new Font(baseInsideFont, 9);

        PdfPTable table = new PdfPTable(7);

        table.setWidthPercentage(new float[] {30, 140, 60, 110, 110, 140, 30}, PageSize.A4);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell c1 = new PdfPCell(new Phrase("  ", font));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("podpis osoby upoważnionej do odbioru faktury", font));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(PdfPCell.TOP);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("  ", font));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("data odbioru", font));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(PdfPCell.TOP);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("  ", font));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("fakturę wystawił", font));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(PdfPCell.TOP);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("  ", font));
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);

        return table;
    }
}