package bjoernbinzer.virtualfridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;

/** Class specifying the design of the list on the fridge item deletion screen
 * Created on 09.12.2015.
 */
public class DeleteItemAdapter extends ArrayAdapter<FridgeItem> {

    private ArrayList<FridgeItem> itemList;

    public DeleteItemAdapter(Context context, int textViewResourceId, ArrayList<FridgeItem> itemList) {
        super(context, textViewResourceId, itemList);
        this.itemList = new ArrayList<FridgeItem>();
        this.itemList.addAll(itemList);
    };

    private class ViewHolder {
        CheckBox name;
    };

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FridgeItem item = getItem(position);
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.fridge_item_info_delete, parent, false);
            holder = new ViewHolder();
            holder.name = (CheckBox) convertView.findViewById(R.id.checkBox);
            holder.name.setTag(item);
            convertView.setTag(holder);

            holder.name.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    FridgeItem item = (FridgeItem) cb.getTag();
                    item.setSelected(cb.isChecked());
                    cb.setTag(item);
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag();
        };

        holder.name.setText(item.getText());
        holder.name.setChecked(false);
        return convertView;
    };

}