import java.io.Serializable;

public class Goods implements Serializable {

    private String name;
    private double amountOfEnergy;
    private double protein;
    private String typeOfProtein;
    private double carb;
    private double fat;
    private String typeOfFat;
    String key;
    private String typeOfCarb;
    private String amountOfProduct = "100";
    private String oldAmountOfProduct ="1";

    public Goods(String name, double amountOfEnergy, double protein,String typeOfProtein,
                 double carb,String typeOfCarb, double fat, String typeOfFat, String key){
        this.name = name;
        this.amountOfEnergy = amountOfEnergy;
        this.carb = carb;
        this.protein = protein;
        this.typeOfProtein = typeOfProtein;
        this.fat = fat;
        this.typeOfFat = typeOfFat;
        this.key = key;
        this.typeOfCarb = typeOfCarb;
    }

    public Goods(Goods product){
        this.name = product.getName();
        this.amountOfEnergy = product.getAmountOfEnergy();
        this.carb = product.getCarb();
        this.protein = product.getProtein();
        this.typeOfProtein = product.getTypeOfProtein();
        this.fat = product.getFat();
        this.typeOfFat = product.getTypeOfFat();
        this.key = product.getKey();
        this.typeOfCarb = product.getTypeOfCarb();
    }

    public String getName(){
        return this.name;
    }

    public String getKey(){
        return this.key;
    }

    public double getAmountOfEnergy(){
        return this.amountOfEnergy;
    }

    public double getCarb(){
        return this.carb;
    }

    public double getProtein(){
        return this.protein;
    }

    public String getTypeOfProtein(){
        return this.typeOfProtein;
    }

    public double getFat(){
        return this.fat;
    }

    public String getTypeOfFat(){
        return this.typeOfFat;
    }

    public String getTypeOfCarb(){return  this.typeOfCarb;}

    public String getAmountOfProduct() {
        return amountOfProduct;
    }

    public void setAmountOfProduct(String amountOfProduct) {
        this.amountOfProduct = amountOfProduct;
    }

    public void setAmountOfEnergy(double amountOfEnergy) {
        this.amountOfEnergy = amountOfEnergy;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public void setCarb(double carb) {
        this.carb = carb;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public String getOldAmountOfProduct() {
        return oldAmountOfProduct;
    }

    public void setOldAmountOfProduct(String oldAmountOfProduct) {
        this.oldAmountOfProduct = oldAmountOfProduct;
    }
}
