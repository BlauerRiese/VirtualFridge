package bjoernbinzer.virtualfridge;

import java.util.Date;

/**
 * Created by Alina on 20.11.2015.
 */
public class FridgeItem {
    public String name;
    public Date durability;
    public double quantity;
    public String uom;
    public double price;
    public String category;

    public FridgeItem (String name, Date durability, double quantity,
                       String uom, double price, String category){
        this.name = name;
        this.durability = durability;
        this.quantity = quantity;
        this.uom = uom;
        this.price = price;
        this.category = category;

    }
}
