package nl.sense_os.app.dialogs;

import nl.sense_os.app.R;
import nl.sense_os.service.constants.SensePrefs;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class SyncRateDialog extends DialogFragment {

    public interface Listener {
        public abstract void onSyncRateChanged(String rate);
    }

    protected static final String TAG = "SyncRateDialog";

    // Use this instance of the interface to deliver action events
    private Listener mListener;
    private String mRate;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (Listener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement SampleRateDialog.Listener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // read the current preference
        SharedPreferences prefs = getActivity().getSharedPreferences(SensePrefs.MAIN_PREFS,
                Context.MODE_PRIVATE);
        mRate = prefs.getString(SensePrefs.Main.SYNC_RATE, "0");
        // sync rate value ranges from -2 to 1
        int rateIndex = Integer.parseInt(mRate) + 2;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_sync_title);
        builder.setSingleChoiceItems(R.array.sync_rate_items, rateIndex,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mRate = Integer.toString(which - 2);
                    }
                });
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onSyncRateChanged(mRate);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        return builder.create();
    }
}
