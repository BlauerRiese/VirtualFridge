package bjoernbinzer.virtualfridge;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Alina on 20.11.2015.
 */
public class FridgeItem implements Serializable {
    /*
    Serialization of FridgeItem object: Collection of strings, doubles and a boolean that can be
    returned as a String


    DECLARATION of all FridgeItem components
     */

    public String id;
    public String name;
    public Date durability;
    public double quantity;
    public String uom;
    public double price;
    public String category;
    public boolean selected;



    public FridgeItem (String id, String name, Date durability, double quantity,
                       String uom, double price, String category){
        // initialisation of FridgeItem components with parameters of constructor call
        this.id = id;
        this.name = name;
        this.durability = durability;
        this.quantity = quantity;
        this.uom = uom;
        this.price = price;
        this.category = category;
        this.selected = false;

    };

    public String getText (){
        // return String of quantity, unit of measure and name of product concatenated
        String item;
        if(uom.equals("Stück")){

            // not necessary to return unit of measure if unit of measure = "Stück"

            item = quantity + " " +name;
        }else{
            item = quantity + " " + uom + " " +name;
        }
      return item;
    }

    public void setSelected (boolean selected){
        // set a FridgeItem as selected, using selected boolean
        this.selected = selected;
    }
}
