package bjoernbinzer.virtualfridge;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alina on 09.12.2015.
 */
public class DeleteItemAdapter extends ArrayAdapter<FridgeItem> {

    private ArrayList<FridgeItem> itemList;

    public DeleteItemAdapter(Context context, int textViewResourceId, ArrayList<FridgeItem> itemList) {
        super(context, textViewResourceId, itemList);
        this.itemList = new ArrayList<FridgeItem>();
        this.itemList.addAll(itemList);
    }

    ;

    private class ViewHolder {
        TextView code;
        CheckBox name;
    }

    ;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FridgeItem item = getItem(position);
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fridge_item_info_delete, parent, false);
            holder = new ViewHolder();
            holder.code = (TextView) convertView.findViewById(R.id.code);
            holder.name = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        };

        holder.code.setText(item.getText());
        holder.name.setChecked(false);
        return convertView;
    };

}