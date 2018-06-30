package com.example.android.natifeslots.service;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.natifeslots.R;
import com.example.android.natifeslots.UI.YouWonFragment;
import com.example.android.natifeslots.model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Service to perform scroll for each spinner
 */
public class ScrollPerformer {
    private AppCompatActivity context;
    private ImageView image;
    private Random r;
    private int position;
    private List<Integer> images;
    private Animation in;
    private Animation out;
    private TextView myCoins;
    private TextView jackpot;
    private TextView bet;
    private Button spinButton;

    public static List<Integer> result = new ArrayList<>();

    public ScrollPerformer(AppCompatActivity context, ImageView image, TextView myCoins,
                           TextView jackpot, TextView bet, Button spinButton) {
        this.context = context;
        this.image = image;
        this.myCoins = myCoins;
        this.jackpot = jackpot;
        this.bet = bet;
        this.spinButton = spinButton;

        r = new Random();
        position = 0;
        in = AnimationUtils.loadAnimation(context, R.anim.in_animation);
        out = AnimationUtils.loadAnimation(context, R.anim.out_animation);
        images = new ArrayList<>();
        images.add(R.drawable.combination_1);
        images.add(R.drawable.combination_2);
        images.add(R.drawable.combination_3);
        images.add(R.drawable.combination_4);
        images.add(R.drawable.combination_5);
        images.add(R.drawable.combination_6);
        images.add(R.drawable.combination_7);
    }

    //Perform scroll for current spinner
    public void performScroll() {
        //Generate random position to scroll
        int scrollTo = r.nextInt(24) + 1;
        final int endPosition = position + scrollTo;

        //Animation listeners to call each other recursively
        in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //Set image resource when starting animation
                if (position < endPosition) {
                    image.setImageResource(images.get(position % 7));
                }
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if (position < endPosition) {
                    //If spinner not in the end position starts next iteration
                    image.startAnimation(out);
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //Increment position and fill result if in the end
                position++;
                if (position == endPosition) {
                    result.add(position % 7);
                }
                if (result.size() == 3) {
                    performResultProcessing();
                }
                image.startAnimation(in);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        image.startAnimation(in);
    }

    //Spin result processing
    private void performResultProcessing() {
        spinButton.setClickable(true);
        int wonCoins = 0;
        //Set the appropriate amount of coins
        if (result.get(0) == result.get(1) && result.get(1) == result.get(2)) {
            switch (result.get(0)) {
                case 1:
                    wonCoins = 10;
                    break;
                case 2:
                    wonCoins = 15;
                    break;
                case 3:
                    wonCoins = 25;
                    break;
                case 4:
                    wonCoins = 35;
                    break;
                case 5:
                    wonCoins = 50;
                    break;
                case 6:
                    wonCoins = 75;
                    break;
                case 0:
                    wonCoins = Player.getInstance().getJackpot();
                    Player.getInstance().setJackpot(100000);
                    jackpot.setText(String.valueOf(Player.getInstance().getJackpot()));
                    break;
            }
            //Update player coins and show dialog with amount of won coins
            updateMyCoins(Player.getInstance().getCoins() + wonCoins);
            showWonDialog(wonCoins);
        } else if (result.contains(0)) {
            switch (Collections.frequency(result, 0)) {
                case 1:
                    wonCoins = 25;
                    break;
                case 2:
                    wonCoins = 50;
                    break;
            }
            //Update player coins and show dialog with amount of won coins
            updateMyCoins(Player.getInstance().getCoins() + wonCoins);
            showWonDialog(wonCoins);
        } else {
            //If player did not win the coins add the bet to the jackpot
            Player.getInstance().setJackpot(Player.getInstance().getJackpot()
                    + Player.getInstance().getBet());
            jackpot.setText(String.valueOf(Player.getInstance().getJackpot()));
        }
        if (Player.getInstance().getBet() > Player.getInstance().getCoins()){
            //Reset bet if player has not enough coins
            Player.getInstance().setBet(Player.getInstance().getCoins());
            bet.setText(String.valueOf(Player.getInstance().getBet()));
        }
        if (Player.getInstance().getCoins() == 0){
            Toast.makeText(myCoins.getContext(), "Game over", Toast.LENGTH_SHORT).show();
        }
    }

    //Update player coins
    public void updateMyCoins(int coins) {
        Player.getInstance().setCoins(coins);
        myCoins.setText(String.valueOf(Player.getInstance().getCoins()));
    }

    //Show dialog if player wins
    private void showWonDialog(int wonCoins){
        YouWonFragment fragment = new YouWonFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(YouWonFragment.WON_COINS, wonCoins);
        fragment.setArguments(bundle);
        fragment.show(context.getSupportFragmentManager(), "fragment");
    }
}
