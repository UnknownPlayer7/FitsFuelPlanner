import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class WriterPdf {
    private static Color borderColor;
    private static Color textColorInFrame;
    private static Color textColorBeyondFrame;
    private static Color cellColorAnimalElement;
    private static Color cellColorPlantElement;
    private static Color cellColorComplexCarb;
    private static Color cellColorSimpleCarb;

    private final String name;
    private final Client client;
    private final float[] columnDefaultSize = {90,30,30,30,30,30};
    private final ArrayList<String> titles;
    private final ArrayList<String> commonNutrition;

    private Font rusFont12_BOLD;
    private Font rusFont12_LIGHT;
    private Font rusFont14_BOLD;
    private Font rusFont16_BOLD;
    private Font rusFont_RED;
    private Font rusFont_GREEN;
    private Font rusFont_YELLOW;
    private Font rusFont_BLUE;

    static{
        initializeColors();
        registerFonts();
    }

    WriterPdf(String name,Client client,ArrayList<String> titles,ArrayList<String> commonNutrition){
        initializeFonts();
        this.name = name;
        this.client = client;
        this.titles = titles;
        this.commonNutrition = commonNutrition;
    }

    private static void initializeColors() {
        Properties properties = ResourceSupplier.getProperties("PDF.properties","/config/");
        borderColor = Color.decode(properties.get("borderColor").toString());
        textColorInFrame = Color.decode(properties.get("textColorInFrame").toString());
        textColorBeyondFrame = Color.decode(properties.get("textColorBeyondFrame").toString());
        cellColorAnimalElement = Color.decode(properties.get("cellColorAnimalElement").toString());
        cellColorPlantElement = Color.decode(properties.get("cellColorPlantElement").toString());
        cellColorComplexCarb = Color.decode(properties.get("cellColorComplexCarb").toString());
        cellColorSimpleCarb = Color.decode(properties.get("cellColorSimpleCarb").toString());
    }

    private void initializeFonts() {
        rusFont12_BOLD = FontFactory.getFont("rusFont_BOLD", BaseFont.IDENTITY_H, true, 12,0, textColorInFrame);
        rusFont12_LIGHT = FontFactory.getFont("rusFont_LIGHT", BaseFont.IDENTITY_H, true, 12,0, textColorBeyondFrame);
        rusFont14_BOLD = FontFactory.getFont("rusFont_BOLD", BaseFont.IDENTITY_H, true, 14,0, textColorInFrame);
        rusFont16_BOLD = FontFactory.getFont("rusFont_BOLD", BaseFont.IDENTITY_H, true, 16,0, textColorInFrame);
        rusFont_RED = FontFactory.getFont("rusFont_LIGHT", BaseFont.IDENTITY_H, true, 12,0, cellColorAnimalElement);
        rusFont_GREEN = FontFactory.getFont("rusFont_LIGHT", BaseFont.IDENTITY_H, true, 12,0, cellColorPlantElement);
        rusFont_YELLOW = FontFactory.getFont("rusFont_LIGHT", BaseFont.IDENTITY_H, true, 12,0, cellColorSimpleCarb);
        rusFont_BLUE = FontFactory.getFont("rusFont_LIGHT", BaseFont.IDENTITY_H, true, 12,0, cellColorComplexCarb);
    }

    private static void registerFonts() {
        FontFactory.register( String.valueOf(WriterPdf.class.getResource("fonts/Noto_Sans_Rus/static/NotoSans-Light.ttf")), "rusFont_LIGHT");
        FontFactory.register( String.valueOf(WriterPdf.class.getResource("fonts/Noto_Sans_Rus/static/NotoSans-Bold.ttf")), "rusFont_BOLD");

    }

    public boolean createPdf(){
        Document document = new Document(PageSize.A4.rotate(),35,65,50,50);
        float pageWidth = document.getPageSize().getWidth();
        try{
            PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(name+".pdf"));
            writer.setPageEvent(new GradientBackground());
            document.open();
            PdfPTable table;
            PdfPCell cell;
            HashMap<Integer, ArrayList<Goods>> productMap = client.getMap();
            HashMap<Integer, ArrayList<String>> nutritionMap = getNutrition(client);
            writeIntroduction(document);
            for(Integer i: productMap.keySet()){
                if(productMap.get(i).isEmpty()) break;
                table = new PdfPTable(columnDefaultSize);
                setTableFactory(table,pageWidth);
                if(titles != null){
                    cell = new PdfPCell(new Phrase(titles.get(i-1), rusFont16_BOLD));
                    cell.setColspan(columnDefaultSize.length);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                }
                fillColumnNames(table);
                for(Goods product: productMap.get(i)){
                    cell = new PdfPCell(new Phrase(product.getName(), rusFont12_BOLD));
                    table.addCell(cell);
                    if(product.getTypeOfCarb().equals("Simple")){
                        cell.setBackgroundColor(cellColorSimpleCarb);
                    }else cell.setBackgroundColor(cellColorComplexCarb);
                    cell.setPhrase(new Phrase(String.valueOf(product.getCarb()), rusFont12_BOLD));
                    table.addCell(cell);
                    if(product.getTypeOfProtein().equals("Animal")){
                        cell.setBackgroundColor(cellColorAnimalElement);
                    }else cell.setBackgroundColor(cellColorPlantElement);
                    cell.setPhrase(new Phrase(String.valueOf(product.getProtein()), rusFont12_BOLD));
                    table.addCell(cell);
                    if(product.getTypeOfFat().equals("Animal")){
                        cell.setBackgroundColor(cellColorAnimalElement);
                    }else cell.setBackgroundColor(cellColorPlantElement);
                    cell.setPhrase(new Phrase(String.valueOf(product.getFat()), rusFont12_BOLD));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(String.valueOf(product.getAmountOfEnergy()), rusFont12_BOLD));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(String.valueOf(product.getAmountOfProduct()), rusFont12_BOLD));

                    table.addCell(cell);
                }
                cell = new PdfPCell(new Phrase("Итого:", rusFont14_BOLD));

                cell.setVerticalAlignment(5);
                cell.setHorizontalAlignment(1);
                table.addCell(cell);
                for(String string: nutritionMap.get(i)){
                    cell = new PdfPCell(new Phrase(string, rusFont12_BOLD));
                    table.addCell(cell);
                }
                cell = new PdfPCell(new Phrase("-", rusFont12_BOLD));
                cell.setVerticalAlignment(5);
                cell.setHorizontalAlignment(1);
                table.addCell(cell);
                setBorderWidthAndColor(table);
                document.add(table);
            }
            writeCommonNutrition(document);

        }
        catch(DocumentException | IOException exc){
            System.out.println(exc.getMessage());
        }
        document.close();


        return true;
    }

    private void setBorderWidthAndColor(PdfPTable table) {

        try{
            ArrayList<PdfPRow> rows = table.getRows();
            PdfPCell[] cells;
            PdfPCell cell;
            float borderWidth = 2f;
            for (int rowNumber = 0; rowNumber < rows.size(); rowNumber++) {
                cells = rows.get(rowNumber).getCells();
                PdfPCell rightCell = null;
                for (int cellNumber = 0; cellNumber < cells.length; cellNumber++) {
                    cell = cells[cellNumber];
                    if(cell != null) {
                        cell.setBorderColor(borderColor);
                        rightCell = cell;
                        if(rowNumber == 0) cells[cellNumber].setBorderWidthTop(borderWidth);
                        if(rowNumber == rows.size() - 1) cells[cellNumber].setBorderWidthBottom(borderWidth);
                        if(cellNumber == 0) cells[cellNumber].setBorderWidthLeft(borderWidth);
                    } else if (rowNumber == rows.size() - 1) {
                        for (int i = rows.size() - 1; i >= 0 ; i--) {
                            if(rows.get(i).getCells()[cellNumber] != null) {
                                rows.get(i).getCells()[cellNumber].setBorderWidthBottom(borderWidth);
                                break;
                            }
                        }
                    }
                    if(cellNumber == cells.length - 1 && rightCell != null) rightCell.setBorderWidthRight(borderWidth);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void setTableFactory(PdfPTable table,float width){
        table.getDefaultCell().setBorder(0);
        table.setHorizontalAlignment(0);
        table.setTotalWidth(width-72);
        table.setLockedWidth(true);
        table.setSpacingAfter(10f);
        table.setSpacingBefore(10f);
    }

    private void fillColumnNames(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase("Продукты", rusFont14_BOLD));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Углеводы", rusFont14_BOLD));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Белки", rusFont14_BOLD));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Жиры", rusFont14_BOLD));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Ккал", rusFont14_BOLD));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Кол-во в сухом виде(г)", rusFont14_BOLD));
        table.addCell(cell);
    }

    private void writeIntroduction(Document document){
        document.add(buildPhrase("1) ","Элементы "," животного происхождения;",rusFont_RED));
        document.add(buildPhrase("2) ","Элементы "," растительного происхождения;",rusFont_GREEN));
        document.add(buildPhrase("3) ","Элементы"," относящийся к полисахаридам, инимы словами сложные углеводы;",rusFont_BLUE));
        document.add(buildPhrase("4) ","Элементы"," относящийся к моносахаридам, т.е. простые углеводы;", rusFont_YELLOW));
    }

    private Paragraph buildPhrase(String first,String second,String third, Font painter){
        Paragraph paragraph = new Paragraph(new Chunk(first, rusFont12_LIGHT));
        paragraph.add(new Chunk(second,painter));
        paragraph.add(new Chunk(third, rusFont12_LIGHT));
        return paragraph;
    }

    private void writeCommonNutrition(Document document){
        PdfPTable table = new PdfPTable(new float[]{72,30,30,30,30});
        setTableFactory(table,document.getPageSize().getWidth());
        PdfPCell cell = new PdfPCell(new Phrase("Итого: ", rusFont14_BOLD));
        cell.setRowspan(2);
        cell.setVerticalAlignment(5);
        cell.setHorizontalAlignment(1);
        table.addCell(cell);

        for(String string: commonNutrition){
            cell = new PdfPCell(new Phrase(Finder.deleteNumber(string), rusFont14_BOLD));
            table.addCell(cell);

        }
        for(String string: commonNutrition){
            cell = new PdfPCell(new Phrase(Finder.findNumber(string), rusFont12_BOLD));
            cell.setFixedHeight(20f);
            table.addCell(cell);
        }
        setBorderWidthAndColor(table);
        document.add(table);

    }

    private HashMap<Integer, ArrayList<String>> getNutrition(Client client){
        HashMap<Integer, ArrayList<String>> nutrition = new HashMap<>();
        for(Integer i:client.getMap().keySet()){
            double carb=0;
            double fat=0;
            double protein=0;
            double amountOfEnergy=0;
            for(Goods product:client.getMap().get(i)){
                carb += product.getCarb();
                fat += product.getFat();
                protein += product.getProtein();
                amountOfEnergy += product.getAmountOfEnergy();
            }
            ArrayList<String> strings = new ArrayList<>();
            strings.add(String.format("%.1f",carb));
            strings.add(String.format("%.1f",protein));
            strings.add(String.format("%.1f",fat));
            strings.add(String.format("%.1f",amountOfEnergy));
            nutrition.put(i,strings);
        }
        return nutrition;
    }

    static class GradientBackground extends PdfPageEventHelper {

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte canvas = writer.getDirectContentUnder();
            ResourceSupplier resourceSupplier = new ResourceSupplier();
            Image image = null;
            try {
                image = Image.getInstance(resourceSupplier.getImageByteArray("WallpaperPDF.jpg"));
            } catch (IOException e) {
                System.out.println("Exception! Can't set PDF image.");
            }
            image.setAbsolutePosition(0f,0f);
            image.scaleAbsolute(document.getPageSize().getWidth(),document.getPageSize().getHeight());
            canvas.addImage(image);
        }
    }

    public static void setBorderColor(String hexCodeColor) {
        try {
            WriterPdf.borderColor = Color.decode(hexCodeColor);
            ResourceSupplier.setConfigFile("PDF.properties","/config/","borderColor", hexCodeColor);
            NotificationManager.showSuccessfulInfo(InfoType.SUCCESSFUL_SETTING);
        } catch (NumberFormatException e) {
            NotificationManager.showError(InfoType.ERROR_SETTING);
        }
    }

    public static void setTextColorInFrame(String hexCodeColor) {
        try {
            WriterPdf.textColorInFrame = Color.decode(hexCodeColor);
            ResourceSupplier.setConfigFile("PDF.properties","/config/","textColorInFrame", hexCodeColor);
            NotificationManager.showSuccessfulInfo(InfoType.SUCCESSFUL_SETTING);
        } catch (NumberFormatException e) {
            NotificationManager.showError(InfoType.ERROR_SETTING);
        }
    }

    public static void setTextColorBeyondFrame(String hexCodeColor) {
        try {
            WriterPdf.textColorBeyondFrame = Color.decode(hexCodeColor);
            ResourceSupplier.setConfigFile("PDF.properties","/config/","textColorBeyondFrame", hexCodeColor);
            NotificationManager.showSuccessfulInfo(InfoType.SUCCESSFUL_SETTING);
        } catch (NumberFormatException e) {
            NotificationManager.showError(InfoType.ERROR_SETTING);
        }
    }

    public static void setCellColorAnimalElement(String hexCodeColor) {
        try {
            WriterPdf.cellColorAnimalElement = Color.decode(hexCodeColor);
            ResourceSupplier.setConfigFile("PDF.properties","/config/","cellColorAnimalElement", hexCodeColor);
            NotificationManager.showSuccessfulInfo(InfoType.SUCCESSFUL_SETTING);
        } catch (NumberFormatException e) {
            NotificationManager.showError(InfoType.ERROR_SETTING);
        }
    }

    public static void setCellColorPlantElement(String hexCodeColor) {
        try {
            WriterPdf.cellColorPlantElement = Color.decode(hexCodeColor);
            ResourceSupplier.setConfigFile("PDF.properties","/config/","cellColorPlantElement", hexCodeColor);
            NotificationManager.showSuccessfulInfo(InfoType.SUCCESSFUL_SETTING);
        } catch (NumberFormatException e) {
            NotificationManager.showError(InfoType.ERROR_SETTING);
        }
    }

    public static void setCellColorComplexCarb(String hexCodeColor) {
        try {
            WriterPdf.cellColorComplexCarb = Color.decode(hexCodeColor);
            ResourceSupplier.setConfigFile("PDF.properties","/config/","cellColorComplexCarb", hexCodeColor);
            NotificationManager.showSuccessfulInfo(InfoType.SUCCESSFUL_SETTING);
        } catch (NumberFormatException e) {
            NotificationManager.showError(InfoType.ERROR_SETTING);
        }
    }

    public static void setCellColorSimpleCarb(String hexCodeColor) {
        try {
            WriterPdf.cellColorSimpleCarb = Color.decode(hexCodeColor);
            ResourceSupplier.setConfigFile("PDF.properties","/config/","cellColorSimpleCarb", hexCodeColor);
            NotificationManager.showSuccessfulInfo(InfoType.SUCCESSFUL_SETTING);
        } catch (NumberFormatException e) {
            NotificationManager.showError(InfoType.ERROR_SETTING);
        }
    }


}
