package bjoernbinzer.virtualfridge;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/** Class that specifies the dialog that appears if an item of the Shopping List shall be deleted and
 send the events back to the calling activity
 * Created on 09.12.2015.
 */

public class DeleteDialog extends DialogFragment {
    Integer item;

    public void setItem(int item){
        this.item = item;
    };
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.drawer_item_delete)
                // Sets the message of positive Button (User confirms the action)
                .setPositiveButton(R.string.delete_confirmation, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Sends the positive button event back to the host activity
                        mListener.onDialogPositiveClick(DeleteDialog.this, item);
                    }
                })
                // Sets the message of the negative Button (User cancels the action)
                .setNegativeButton(R.string.delete_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Sends the negative button event back to the host activity
                        mListener.onDialogNegativeClick(DeleteDialog.this);
                    }
                });
        // Creates the Dialog object and returns it
        return builder.create();
    }
    /* The activity that creates an instance of this dialog fragment must
         * implement this interface in order to receive event callbacks.
         * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, int item);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use of the instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Overrides the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verifies that the host activity implements the callback interface
        try {
            // Instantiates the NoticeDialogListener so that events are send back to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // If the activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
