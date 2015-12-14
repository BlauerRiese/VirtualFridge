package bjoernbinzer.virtualfridge;

import java.io.Serializable;

/** Class specifying the design of a ShoppingListItem
 * Created on 10.12.2015.
 */

public class ShoppingListItem implements Serializable{
    private String product;
    public boolean selected;

    public ShoppingListItem(String product){
        this.product = product;
    }

    // Sets the state of a ShoppingListItem to selected
    public void setSelected(boolean selected){
        this.selected = selected;
    }

    // Returns the name of the ShoppingListItem
    public String getProduct(){
        return product;
    }
}
