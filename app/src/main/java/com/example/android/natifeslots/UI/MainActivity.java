package com.example.android.natifeslots.UI;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.natifeslots.model.Player;
import com.example.android.natifeslots.R;
import com.example.android.natifeslots.service.ScrollPerformer;

/**
 * Main activity with spinners
 */
public class MainActivity extends AppCompatActivity {
    private ScrollPerformer firstScroller;
    private ScrollPerformer secondScroller;
    private ScrollPerformer thirdScroller;

    private SharedPreferences sp;

    private Button spinButton;

    private TextView coinsText;
    private TextView betText;
    private TextView jackpotText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Blue button that shows dialog to start new game
        ImageButton blueButton = (ImageButton) findViewById(R.id.blue_button);
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BlueButtonFragment fragment = new BlueButtonFragment();
                fragment.setNewGameListener(new BlueButtonFragment.NewGameListener() {
                    @Override
                    public void newGameClicked() {
                        //Reset player params to default values
                        Player.getInstance().setCoins(1000);
                        coinsText.setText(String.valueOf(Player.getInstance().getCoins()));
                        Player.getInstance().setBet(5);
                        betText.setText(String.valueOf(Player.getInstance().getBet()));
                        Player.getInstance().setJackpot(100000);
                        jackpotText.setText(String.valueOf(Player.getInstance().getJackpot()));
                    }
                });
                fragment.show(getSupportFragmentManager(), "fragment");
            }
        });
        //Reduce bet
        Button minusButton = (Button) findViewById(R.id.button_minus);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Player.getInstance().getBet() >= 10){
                    Player.getInstance().setBet(Player.getInstance().getBet() - 5);
                    betText.setText(String.valueOf(Player.getInstance().getBet()));
                }
            }
        });
        //Increase bet
        Button plusButton = (Button) findViewById(R.id.button_plus);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Player.getInstance().getBet() <= (Player.getInstance().getCoins() - 5)){
                    Player.getInstance().setBet(Player.getInstance().getBet() + 5);
                    betText.setText(String.valueOf(Player.getInstance().getBet()));
                }
            }
        });
        //Button that starts spinning
        spinButton = (Button)findViewById(R.id.button_spin);
        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Player.getInstance().getBet() > 0) {
                    spinButton.setClickable(false);
                    ScrollPerformer.result.clear();
                    updateMyCoins(Player.getInstance().getCoins() - Player.getInstance().getBet());
                    firstScroller.performScroll();
                    secondScroller.performScroll();
                    thirdScroller.performScroll();
                }
            }
        });

        //Set player params from saved data
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        Player.getInstance().setCoins(sp.getInt(Player.COINS, 1000));
        Player.getInstance().setBet(sp.getInt(Player.BET, 5));
        Player.getInstance().setJackpot(sp.getInt(Player.JACKPOT, 100000));

        //Set the values of the player params to the text views
        coinsText = (TextView)findViewById(R.id.my_coins_text);
        updateMyCoins(Player.getInstance().getCoins());
        betText = (TextView)findViewById(R.id.bet_text);
        betText.setText(String.valueOf(Player.getInstance().getBet()));
        jackpotText = (TextView)findViewById(R.id.jackpot_text);
        jackpotText.setText(String.valueOf(Player.getInstance().getJackpot()));

        //Initialize spinners and scrollPerformers
        ImageView firstSpinner = (ImageView) findViewById(R.id.first_spinner);
        firstSpinner.setImageResource(R.drawable.combination_1);
        firstScroller = new ScrollPerformer(this, firstSpinner, coinsText, jackpotText,
                betText, spinButton);

        ImageView secondSpinner = (ImageView) findViewById(R.id.second_spinner);
        secondSpinner.setImageResource(R.drawable.combination_1);
        secondScroller = new ScrollPerformer(this, secondSpinner, coinsText, jackpotText,
                betText, spinButton);

        ImageView thirdpinner = (ImageView) findViewById(R.id.third_spinner);
        thirdpinner.setImageResource(R.drawable.combination_1);
        thirdScroller = new ScrollPerformer(this, thirdpinner, coinsText, jackpotText,
                betText, spinButton);
    }

    //Update player coins
    public void updateMyCoins(int coins){
        Player.getInstance().setCoins(coins);
        coinsText.setText(String.valueOf(Player.getInstance().getCoins()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Save current player state when close the activity
        sp.edit().putInt(Player.COINS, Player.getInstance().getCoins()).apply();
        sp.edit().putInt(Player.BET, Player.getInstance().getBet()).apply();
        sp.edit().putInt(Player.JACKPOT, Player.getInstance().getJackpot()).apply();
    }
}
