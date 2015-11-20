package bjoernbinzer.virtualfridge;

import java.util.Date;

/**
 * Created by Alina on 20.11.2015.
 */
public class FridgeItem {
    public String id;
    public String name;
    public Date durability;
    public double quantity;
    public String uom;
    public double price;
    public String category;

    public FridgeItem (String id, String name, Date durability, double quantity,
                       String uom, double price, String category){
        this.id = id;
        this.name = name;
        this.durability = durability;
        this.quantity = quantity;
        this.uom = uom;
        this.price = price;
        this.category = category;

    }
}
