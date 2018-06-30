package com.example.android.natifeslots.model;

/**
 * Data class that represents current player
 */
public class Player {
    //Keys for shared preferences
    public static final String COINS = "coins";
    public static final String BET = "bet";
    public static final String JACKPOT = "jackpot";

    private int coins = 1000;
    private int bet = 5;
    private int jackpot = 100000;
    private static Player instance;

    public static Player getInstance(){
        if (instance == null){
            instance = new Player();
        }
        return instance;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getJackpot() {
        return jackpot;
    }

    public void setJackpot(int jackpot) {
        this.jackpot = jackpot;
    }
}
