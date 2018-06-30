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

import com.example.android.natifeslots.R;

/**
 * Dialog to start new game
 */
public class BlueButtonFragment extends DialogFragment {
    private NewGameListener newGameListener;

    public BlueButtonFragment() {
        // Required empty public constructor
    }

    interface NewGameListener{
        void newGameClicked();
    }

    public void setNewGameListener(NewGameListener newGameListener) {
        this.newGameListener = newGameListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = View.inflate(getContext(), R.layout.fragment_blue_button, null);
        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
        adb.setView(v);

        v.findViewById(R.id.button_new_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGameListener.newGameClicked();
                dismiss();
            }
        });

        Dialog dialog = adb.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }
}
