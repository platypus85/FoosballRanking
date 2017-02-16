package com.albertocamillo.foosballranking.model;

/**
 * Created by Alberto Camillo on 14/02/2017.
 */

public class Match {

    private int id;
    private Player playerOne;
    private Player playerTwo;
    private Player playerThree;
    private Player playerFour;
    private int scoreTeam1;
    private int scoreTeam2;
    private long creationDate;

    public Match() {

    }

    public Match(int id, Player playerOne, Player playerTwo, Player playerThree, Player playerFour, int scoreTeam1, int scoreTeam2, long creationDate) {
        this.id = id;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.playerThree = playerThree;
        this.playerFour = playerFour;
        this.scoreTeam1 = scoreTeam1;
        this.scoreTeam2 = scoreTeam2;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public Player getPlayerThree() {
        return playerThree;
    }

    public void setPlayerThree(Player playerThree) {
        this.playerThree = playerThree;
    }

    public Player getPlayerFour() {
        return playerFour;
    }

    public void setPlayerFour(Player playerFour) {
        this.playerFour = playerFour;
    }

    public int getScoreTeam1() {
        return scoreTeam1;
    }

    public void setScoreTeam1(int scoreTeam1) {
        this.scoreTeam1 = scoreTeam1;
    }

    public int getScoreTeam2() {
        return scoreTeam2;
    }

    public void setScoreTeam2(int scoreTeam2) {
        this.scoreTeam2 = scoreTeam2;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }
}
