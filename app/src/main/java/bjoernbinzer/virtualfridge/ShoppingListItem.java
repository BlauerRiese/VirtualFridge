package bjoernbinzer.virtualfridge;

import java.io.Serializable;

/**
 * Created by Mareike on 10.12.2015.
 */
public class ShoppingListItem implements Serializable{
    private String product;
    public boolean selected;

    public ShoppingListItem(String product){
        this.product = product;
    }
    public void setSelected(boolean selected){
        this.selected = selected;
    }
    public String getProduct(){
        return product;
    }
}
