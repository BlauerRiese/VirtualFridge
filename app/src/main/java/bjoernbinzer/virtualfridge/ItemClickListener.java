package bjoernbinzer.virtualfridge;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Mareike on 20.11.2015.
 */
public abstract class ItemClickListener implements ListView.OnItemClickListener{

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    abstract void onItemLongClick(AdapterView<?> parent, View view, int position, long id);
}
