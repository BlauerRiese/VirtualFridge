package bjoernbinzer.virtualfridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;

/**
 * Created by Mareike on 09.12.2015.
 */
public class ShoppingListItemAdapter extends ArrayAdapter<ShoppingListItem>{
    private ArrayList<ShoppingListItem> itemList;

    public ShoppingListItemAdapter(Context context, int textViewResourceId, ArrayList<ShoppingListItem> itemList) {
        super(context, textViewResourceId, itemList);
        this.itemList = new ArrayList<ShoppingListItem>();
        this.itemList.addAll(itemList);
    }

    ;

    private class ViewHolder {
        CheckBox name;
    }

    ;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShoppingListItem item = getItem(position);
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shoppinglist_layout, parent, false);
            holder = new ViewHolder();
            holder.name = (CheckBox) convertView.findViewById(R.id.checkBox_2);
            holder.name.setTag(item);
            convertView.setTag(holder);

            holder.name.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    ShoppingListItem item = (ShoppingListItem) cb.getTag();
                    item.setSelected(cb.isChecked());
                    cb.setTag(item);
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ;

        holder.name.setText(item.getProduct());
        holder.name.setChecked(false);
        return convertView;
    };

}

