public class Invoice implements Payable {
    
    //----- Instance Variables -----//
    private String partNumber;
    private String partDescription;
    private int quantity;
    private double pricePerItem;


    //----- PartNumber Methods -----//
    public String getPartNumber(){
        return this.partNumber;
    }

    public void setPartNumber(String part){
        this.partNumber = part;
    }


    //----- PartDescription Methods -----//
    public String getPartDescription(){
        return this.partDescription;
    }

    public void setPartDescription(String description){
        this.partDescription = description;
    }


    //----- Quantity Methods -----//
    public int getQuantity(){
        return this.quantity;
    }

    public void setQuantity(int count){
        this.quantity = count;
    }


    //----- PricePerItem Methods -----//
    public double getPrice(){
        return this.pricePerItem;
    }

    public void setPrice(double price){
        this.pricePerItem = price;
    }


    //----- Constructors -----//
    public Invoice(){

    }

    public Invoice(String part, String description, int count, double price) {
        this.setPartNumber(part);
        this.setPartDescription(description);
        this.setQuantity(count);
        this.setPrice(price);
    }

    
    //----- Overloads, Overrides, and Abstract implementations -----//
    public String toString(){

        return  "Part Number: " + this.getPartNumber() + "/n" +
                "Part Description: " + this.getPartDescription() + "/n" +
                "Quantity: " + this.getQuantity() + "/n" +
                "Price Per Item: " + this.getPrice() + "/n";
    }

    public double getPaymentAmount() {

        return this.getPrice() * this.getQuantity();
    }

}
