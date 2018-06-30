package com.example.android.natifeslots.UI;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.android.natifeslots.R;

/**
 * Fragment that appears when player wins coins and show the amount of won coins
 */
public class YouWonFragment extends DialogFragment {
    public static final String WON_COINS = "won coins";

    public YouWonFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = View.inflate(getContext(), R.layout.fragment_you_won, null);
        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
        adb.setView(v);

        ((TextView)v.findViewById(R.id.won_coins)).setText(
                String.valueOf(getArguments().getInt(WON_COINS)) + "\ncoins");

        Dialog dialog = adb.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }
}
