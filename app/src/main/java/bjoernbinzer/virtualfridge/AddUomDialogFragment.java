package bjoernbinzer.virtualfridge;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

/**
 * Created by BJOERN on 06.11.2015.
 */
public class AddUomDialogFragment extends DialogFragment implements View.OnClickListener {

    private NumberPicker uomPicker;
    public Button neutral;
    public String uom_value;
    Communicator communicator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.uom_picker_dialog, container);

        getDialog().setTitle(R.string.title_uom_dialog);

        //NumberPicker for unit of measure
        final String[] uom = {"mg", "g", "kg", "ml", "l", "Packung", "Dose", "Flasche", "Stück"};
        uomPicker = (NumberPicker) view.findViewById(R.id.numberPicker_uom);
        uomPicker.setMinValue(0);
        uomPicker.setMaxValue(uom.length - 1);
        uomPicker.setDisplayedValues(uom);

        NumberPicker.OnValueChangeListener listener = new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                uom_value = String.valueOf(newVal);
            }
        };

        neutral = (Button) view.findViewById(R.id.button_neutral);
        neutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uom_value = uom[uomPicker.getValue()];
                communicator.onDialogMessage(uom_value);
                dismiss();
            }
        });

        setCancelable(true);
        return view;
    }

    @Override
    public void onClick(View view) {

    }

    interface Communicator {
        void onDialogMessage(String message);
    }
}
