import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class WriterPdf {
    private final String name;
    private final Client client;
    private final float[] columnDefaultSize = {90,30,30,30,30,30};
    private final ArrayList<String> titles;
    private final ArrayList<String> commonNutrition;
    private final Font font12 = FontFactory.getFont(FontFactory.TIMES_ITALIC,12);
    private final Font rusFont12 = FontFactory.getFont("rusFont", BaseFont.IDENTITY_H, true, 12);
    private final Font rusFont14 = FontFactory.getFont("rusFont", BaseFont.IDENTITY_H, true, 14);
    private final Font rusFont16 = FontFactory.getFont("rusFont", BaseFont.IDENTITY_H, true, 16);
    private final Font rusFont_RED = FontFactory.getFont("rusFont", BaseFont.IDENTITY_H, true, 12,0,Color.RED.darker());
    private final Font rusFont_GREEN = FontFactory.getFont("rusFont", BaseFont.IDENTITY_H, true, 12,0,Color.GREEN.darker());
    private final Font rusFont_CYAN = FontFactory.getFont("rusFont", BaseFont.IDENTITY_H, true, 12,0,Color.CYAN.darker());
    private final Font rusFont_BLUE = FontFactory.getFont("rusFont", BaseFont.IDENTITY_H, true, 12,0,Color.BLUE.darker());

    //Добавляем русский шрифт в регистр класса FontFactory
    static{
        FontFactory.register( String.valueOf(WriterPdf.class.getResource("/resources/fonts/Noto_Sans_Rus/Slavik.otf")), "rusFont");
    }

    WriterPdf(String name,Client client,ArrayList<String> titles,ArrayList<String> commonNutrition){
        this.name = name;
        this.client = client;
        this.titles = titles;
        this.commonNutrition = commonNutrition;
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
                    cell = new PdfPCell(new Phrase(titles.get(i-1),rusFont16));
                    cell.setColspan(columnDefaultSize.length);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                }
                fillColumnNames(table);
                for(Goods product: productMap.get(i)){
                    cell = new PdfPCell(new Phrase(product.getName(), rusFont12));
                    table.addCell(cell);
                    if(product.getTypeOfCarb().equals("Simple")){
                        cell.setBackgroundColor(Color.CYAN.darker());
                    }else cell.setBackgroundColor(Color.BLUE.darker());
                    cell.setPhrase(new Phrase(String.valueOf(product.getCarb()),font12));
                    table.addCell(cell);
                    if(product.getTypeOfProtein().equals("Animal")){
                        cell.setBackgroundColor(Color.RED.darker());
                    }else cell.setBackgroundColor(Color.GREEN.darker());
                    cell.setPhrase(new Phrase(String.valueOf(product.getProtein()),font12));
                    table.addCell(cell);
                    if(product.getTypeOfFat().equals("Animal")){
                        cell.setBackgroundColor(Color.RED.darker());
                    }else cell.setBackgroundColor(Color.GREEN.darker());
                    cell.setPhrase(new Phrase(String.valueOf(product.getFat()),font12));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(String.valueOf(product.getAmountOfEnergy()),font12));
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(String.valueOf(product.getAmountOfProduct()),font12));

                    table.addCell(cell);
                }
                cell = new PdfPCell(new Phrase("Итого:", rusFont14));

                cell.setVerticalAlignment(5);
                cell.setHorizontalAlignment(1);
                table.addCell(cell);
                for(String string: nutritionMap.get(i)){
                    cell = new PdfPCell(new Phrase(string,font12));
                    table.addCell(cell);
                }
                cell = new PdfPCell(new Phrase("-", rusFont12));
                cell.setVerticalAlignment(5);
                cell.setHorizontalAlignment(1);
                table.addCell(cell);
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
        cell.setPhrase(new Phrase("Продукты",rusFont14));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Углеводы",rusFont14));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Белки",rusFont14));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Жиры",rusFont14));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Ккал",rusFont14));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Кол-во в сухом виде(г)",rusFont14));
        table.addCell(cell);
    }

    private void writeIntroduction(Document document){
        document.add(new Paragraph("Для справки:", rusFont12));
        document.add(buildPhrase("1) ","Бордовый цвет"," - элементы животного происхождения;",rusFont_RED));
        document.add(buildPhrase("2) ","Зеленый цвет"," - элементы растительного происхождения;",rusFont_GREEN));
        document.add(buildPhrase("3) ","Синий цвет"," - элементы относящийся к полисахаридам, инимы словами сложные углеводы;",rusFont_BLUE));
        document.add(buildPhrase("4) ","Васильковый цвет"," - элементы относящийся к моносахаридам, т.е. простые углеводы;",rusFont_CYAN));
    }

    private void writeCommonNutrition(Document document){
        PdfPTable table = new PdfPTable(new float[]{72,30,30,30,30});
        setTableFactory(table,document.getPageSize().getWidth());
        PdfPCell cell = new PdfPCell(new Phrase("Итого: ",rusFont14));
        cell.setRowspan(2);
        cell.setVerticalAlignment(5);
        cell.setHorizontalAlignment(1);
        table.addCell(cell);

        for(String string: commonNutrition){
            cell = new PdfPCell(new Phrase(Finder.deleteNumber(string), rusFont14));
            table.addCell(cell);

        }
        for(String string: commonNutrition){
            cell = new PdfPCell(new Phrase(Finder.findNumber(string),font12));
            table.addCell(cell);
        }
        document.add(table);

    }

    private Paragraph buildPhrase(String first,String second,String third, Font painter){
        Paragraph paragraph = new Paragraph(new Chunk(first, rusFont12));
        paragraph.add(new Chunk(second,painter));
        paragraph.add(new Chunk(third, rusFont12));
        return paragraph;
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
            Image image = null;

            try {
                image = Image.getInstance(String.valueOf(WriterPdf.class.getResource("/images/Var1New.jpg")));
                image.setAbsolutePosition(0f,0f);
                image.scaleAbsolute(document.getPageSize().getWidth(),document.getPageSize().getHeight());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            canvas.addImage(image);
        }
    }
}
