package bjoernbinzer.virtualfridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Felix on 12.12.15.
 */
public class ItemListAdapter extends ArrayAdapter<String> {

    Context c;
    LayoutInflater inflater;
    String[] products = {};
    String [] quantities = {};
    String [] uom = {} ;



    public ItemListAdapter (Context context, String []  products, String[] quantities, String [] uom) {
        super (context, R.layout.category_item_list_item, products);

        c = context;
        this.products = products;
        this.quantities = quantities;
        this.uom = uom;
    }


    public class ViewHolder {
        TextView productTextView;
        TextView quantityTextView;
        TextView uomTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_item_list_item, null);
        }

        final ViewHolder holder = new ViewHolder();
        holder.productTextView = (TextView) convertView.findViewById(R.id.productTextView);
        holder.quantityTextView = (TextView) convertView.findViewById(R.id.quantityTextView);
        holder.uomTextView = (TextView) convertView.findViewById(R.id.uomTextView);

        holder.productTextView.setText(products[position]);
        holder.uomTextView.setText(uom[position]);
        holder.quantityTextView.setText(quantities[position]);


        return convertView ;
    }
}
