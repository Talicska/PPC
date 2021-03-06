import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class PdfExporter {
    private static String fileName;
    private static String title = "";
    private static Material material;
    private static double width;
    private static double height;
    private static int colorNum;
    private static int lakkNum;
    private static double cliche;
    private static double stanc;
    private static int amount;
    private static double profitOnPiece;

    static BaseFont bf;

    private static Font catFont;
    private static Font subFont;
    private static Font smallNorm;
    private static Font smallBold;

    private static DecimalFormat df = new DecimalFormat("#.##");

    private enum choices {UPM, Herma, Budaval, Intercoat, Avery, Lintec};

    public PdfExporter(String fileName, String title, Material material, double width, double height, int colorNum, int lakkNum,
                       double cliche, double stancCost, int amount, double profitOnPiece) {

        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);

        for (choices c : choices.values()) {
            if (material.getName().contains(c.toString())) {
                material.setName(material.getName().replace(c.toString(), ""));
            }
        }

        PdfExporter.fileName = fileName;
        if (!title.equals("")) PdfExporter.title = " - " + title;
        System.out.println(fileName);
        PdfExporter.material = material;
        PdfExporter.width = width;
        PdfExporter.height = height;
        PdfExporter.colorNum = colorNum;
        PdfExporter.lakkNum = lakkNum;
        PdfExporter.cliche = cliche;
        PdfExporter.stanc = stancCost;
        PdfExporter.amount = amount;
        PdfExporter.profitOnPiece = profitOnPiece;

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            addMetaData(document);
            addTitlePage(document);
            addContent(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("Imprenta Nyomdaipari Kft. árajánlata");
        document.addSubject("Árajánlat");
        document.addKeywords("árajánlat, PPC");
        document.addAuthor("Imprenta Kft.");
        document.addCreator("PPC");
    }

    private static void addTitlePage(Document document) throws DocumentException {

        Paragraph preface = new Paragraph();

        try {
            bf = BaseFont.createFont("c:/windows/fonts/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            catFont = new Font(bf, 18, Font.BOLD);
            subFont = new Font(bf, 16, Font.BOLD);
            smallBold = new Font(bf, 12, Font.BOLD);
            smallNorm = new Font(bf, 12, Font.NORMAL);

            addEmptyLine(preface, 1);

            Paragraph titlePara = new Paragraph("Árajánlat" + title, catFont);
            titlePara.setAlignment(Element.ALIGN_CENTER);
            preface.add(titlePara);
            addEmptyLine(preface, 2);

            Paragraph listPara = new Paragraph();
            listPara.add(new Phrase("Alapanyag: ", smallBold));
            listPara.add(new Phrase(material.getName(), smallNorm));
            addEmptyLine(listPara, 1);

            listPara.add(new Phrase("Címke mérete: ", smallBold));
            listPara.add(new Phrase(width + " x " + height + " mm", smallNorm));
            addEmptyLine(listPara, 1);

            listPara.add(new Phrase("Színek száma: ", smallBold));
            if (lakkNum == 0) listPara.add(new Phrase(colorNum + " C", smallNorm));
            else listPara.add(new Phrase(colorNum + " C + lakk", smallNorm));
            addEmptyLine(listPara, 1);

            listPara.add(new Phrase("Nyomdai előkészítés: ", smallBold));
            listPara.add(new Phrase(cliche + " Ft / szín", smallNorm));
            addEmptyLine(listPara, 1);

            listPara.add(new Phrase("Stanc: ", smallBold));
            if (stanc == 0) listPara.add(new Phrase("van ", smallNorm));
            else listPara.add(new Phrase(stanc + " Ft", smallNorm));
            addEmptyLine(listPara, 1);

            listPara.add(new Phrase("Példányszám: ", smallBold));
            listPara.add(new Phrase(df.format(amount) + " db", smallNorm));
            addEmptyLine(listPara, 1);

            listPara.add(new Phrase("Darabár: ", smallBold));
            listPara.add(new Phrase(df.format(profitOnPiece) + " Ft", smallNorm));
            addEmptyLine(listPara, 1);

            listPara.add(new Phrase("Az árak áfa nélkül értendők.", smallNorm));

            preface.add(listPara);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*addEmptyLine(preface, 1);
        preface.add(new Paragraph("Címke mérete: " + width + " mm x " + height + " mm", smallNorm));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Színek száma: " + colorNum + " C", smallNorm));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Nyomdai előkészítés: " + preCost + " Ft", smallNorm));
        addEmptyLine(preface, 1);
        if (stanc) preface.add(new Paragraph("Stanc: van", smallNorm));
        else preface.add(new Paragraph("Stanc: nincs", smallNorm));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Mennyiség: " + df.format(amount) + " db", smallNorm));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Darabár: " + df.format(profitOnPiece) + " Ft", smallNorm));
        addEmptyLine(preface, 1);*/

        // Will create: Report generated by: _name, _date
        /*preface.add(new Paragraph("Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallNorm));
        addEmptyLine(preface, 3);
        preface.add(new Paragraph("This document describes something which is very important ",
                smallNorm));

        addEmptyLine(preface, 8);

        preface.add(new Paragraph("This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
                redFont));*/

        document.add(preface);

        document.newPage();
    }

    private static void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("First Chapter", catFont);
        anchor.setName("First Chapter");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory 2", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Paragraph 1"));
        subCatPart.add(new Paragraph("Paragraph 2"));
        subCatPart.add(new Paragraph("Paragraph 3"));

        // add a list
        createList(subCatPart);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 5);
        subCatPart.add(paragraph);

        // add a table
        createTable(subCatPart);

        // now add all this to the document
        document.add(catPart);

        /*// Next section
        anchor = new Anchor("Second Chapter", catFont);
        anchor.setName("Second Chapter");

        // Second parameter is the number of the chapter
        catPart = new Chapter(new Paragraph(anchor), 1);

        subPara = new Paragraph("Subcategory", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("This is a very important message"));

        // now add all this to the document
        document.add(catPart);*/

    }

    private static void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        subCatPart.add(table);

    }

    private static void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
