package com.albertocamillo.foosballranking.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.albertocamillo.foosballranking.model.Match;
import com.albertocamillo.foosballranking.model.Player;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alberto Camillo on 14/02/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "foosballRanking";

    // PLAYERS
    private static final String TABLE_PLAYERS = "players";
    private static final String KEY_ID = "id";
    private static final String KEY_FULL_NAME = "fullName";
    private static final String KEY_EMAIL = "emailAddress";
    private static final String CREATE_PLAYERS_TABLE = "CREATE TABLE " + TABLE_PLAYERS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FULL_NAME + " TEXT," + KEY_EMAIL + " TEXT" + ")";

    // MATCHES
    private static final String TABLE_MATCHES = "matches";
    private static final String KEY_PLAYER_ONE = "playerOne";
    private static final String KEY_PLAYER_TWO = "playerTwo";
    private static final String KEY_PLAYER_THREE = "playerThree";
    private static final String KEY_PLAYER_FOUR = "playerFour";
    private static final String KEY_SCORE_TEAM_ONE = "scoreTeamOne";
    private static final String KEY_SCORE_TEAM_TWO = "scoreTeam2";
    private static final String KEY_DATE = "creationDate";
    private static final String CREATE_MATCHES_TABLE = "CREATE TABLE "
            + TABLE_MATCHES
            + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PLAYER_ONE + " TEXT,"
            + KEY_PLAYER_TWO + " TEXT,"
            + KEY_PLAYER_THREE + " TEXT,"
            + KEY_PLAYER_FOUR + " TEXT,"
            + KEY_SCORE_TEAM_ONE + " INTEGER,"
            + KEY_SCORE_TEAM_TWO + " INTEGER,"
            + KEY_DATE + " DATE"
            + ")";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PLAYERS_TABLE);
        db.execSQL(CREATE_MATCHES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCHES);
        // Create tables again
        onCreate(db);
    }

    //###########################################
    //
    //                  PLAYERS
    //
    //###########################################

    public Player getPlayerByEmailAddress(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Player player = null;
        Cursor cursor = db.query(TABLE_PLAYERS, new String[]{KEY_ID, KEY_FULL_NAME, KEY_EMAIL}, KEY_EMAIL + " = ?", new String[]{email}, null, null, null);
        if (cursor != null)
            if (cursor.getCount() <= 0) {
                cursor.close();
                return null;
            } else {
                cursor.moveToFirst();
                player = new Player(cursor.getString(1), cursor.getString(2));
                cursor.close();
            }

        return player;
    }

    public Player getPlayerById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Player player = null;
        Cursor cursor = db.query(TABLE_PLAYERS, new String[]{KEY_ID, KEY_FULL_NAME, KEY_EMAIL}, KEY_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null)
            if (cursor.getCount() <= 0) {
                cursor.close();
                return null;
            } else {
                cursor.moveToFirst();
                player = new Player(cursor.getString(1), cursor.getString(2));
                cursor.close();
            }

        return player;
    }

    public void addPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FULL_NAME, player.getFullName());
        values.put(KEY_EMAIL, player.getEmailAddress());

        db.insert(TABLE_PLAYERS, null, values);
        db.close();
    }

    public List<Player> getAllPlayers() {
        List<Player> playersList = new ArrayList<Player>();
        String selectQuery = "SELECT  * FROM " + TABLE_PLAYERS + " ORDER BY " + KEY_FULL_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Player player = new Player();
                player.setId(Integer.parseInt(cursor.getString(0)));
                player.setFullName(cursor.getString(1));
                player.setEmailAddress(cursor.getString(2));
                playersList.add(player);
            } while (cursor.moveToNext());
        }

        return playersList;
    }

    //###########################################
    //
    //                  MATCHES
    //
    //###########################################

    public void addMatch(Match match) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_PLAYER_ONE, null != match.getPlayerOne() ? match.getPlayerOne().getId() : null);
        values.put(KEY_PLAYER_TWO, null != match.getPlayerTwo() ? match.getPlayerTwo().getId() : null);
        values.put(KEY_PLAYER_THREE, null != match.getPlayerThree() ? match.getPlayerThree().getId() : null);
        values.put(KEY_PLAYER_FOUR, null != match.getPlayerFour() ? match.getPlayerFour().getId() : null);
        values.put(KEY_SCORE_TEAM_ONE, match.getScoreTeam1());
        values.put(KEY_SCORE_TEAM_TWO, match.getScoreTeam2());
        values.put(KEY_DATE, new Date().getTime());

        db.insert(TABLE_MATCHES, null, values);
        db.close();
    }

    public List<Match> getAllmatches() {
        List<Match> matchesList = new ArrayList<Match>();
        String selectQuery = "SELECT  * FROM " + TABLE_MATCHES + " ORDER BY " + KEY_DATE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Match match = new Match();
                match.setId(Integer.parseInt(cursor.getString(0)));
                match.setPlayerOne(StringUtils.isNotBlank(cursor.getString(1)) ? getPlayerById(cursor.getInt(1)) : null);
                match.setPlayerTwo(StringUtils.isNotBlank(cursor.getString(2)) ? getPlayerById(cursor.getInt(2)) : null);
                match.setPlayerThree(StringUtils.isNotBlank(cursor.getString(3)) ? getPlayerById(cursor.getInt(3)) : null);
                match.setPlayerFour(StringUtils.isNotBlank(cursor.getString(4)) ? getPlayerById(cursor.getInt(4)) : null);
                match.setScoreTeam1(Integer.parseInt(cursor.getString(5)));
                match.setScoreTeam2(Integer.parseInt(cursor.getString(6)));
                match.setCreationDate(cursor.getLong(7));
                // Adding contact to list
                matchesList.add(match);
            } while (cursor.moveToNext());
        }

        return matchesList;
    }

    //###########################################
    //
    //                  STATS
    //
    //###########################################

    public int getWonMatchesByPlayer(int ID) {
        List<Player> playersList = new ArrayList<Player>();
        String selectQuery = "SELECT COUNT(*) FROM( " +
                "SELECT distinct id FROM " + TABLE_MATCHES + " " +
                "WHERE (playerOne = " + ID + " OR playerTwo = " + ID + ") " +
                "AND (scoreTeamOne > scoreTeam2) " +
                "UNION ALL " +
                "SELECT distinct id FROM " + TABLE_MATCHES + " " +
                "WHERE (playerThree = " + ID + " OR playerFour = " + ID + ") " +
                "AND (scoreTeam2 > scoreTeamOne)" +
                ")";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }

        return 0;
    }

    public int getLostMatchesByPlayer(int ID) {
        List<Player> playersList = new ArrayList<Player>();
        String selectQuery = "SELECT COUNT(*) FROM( " +
                "SELECT distinct id FROM " + TABLE_MATCHES + " " +
                "WHERE (playerOne = " + ID + " OR playerTwo = " + ID + ") " +
                "AND (scoreTeamOne < scoreTeam2) " +
                "UNION ALL " +
                "SELECT distinct id FROM " + TABLE_MATCHES + " " +
                "WHERE (playerThree = " + ID + " OR playerFour = " + ID + ") " +
                "AND (scoreTeam2 < scoreTeamOne)" +
                ")";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }

        return 0;
    }

    public int getMatchesPlayer1WinsVsPlayer2(int p1id, int p2id) {
        List<Player> playersList = new ArrayList<Player>();
        String selectQuery = "SELECT COUNT(*) FROM( " +
                "SELECT distinct id FROM " + TABLE_MATCHES + " " +
                "WHERE (playerOne = " + p1id + " OR playerTwo = " + p1id + ") " +
                "AND (playerThree = " + p2id + " OR playerFour = " + p2id + ") " +
                "AND (scoreTeamOne > scoreTeam2) " +
                "UNION ALL " +
                "SELECT distinct id FROM " + TABLE_MATCHES + " " +
                "WHERE (playerThree = " + p1id + " OR playerFour = " + p1id + ") " +
                "AND (playerOne = " + p2id + " OR playerTwo = " + p2id + ") " +
                "AND (scoreTeam2 > scoreTeamOne)" +
                ")";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }

        return 0;
    }

    public int getMatchesPlayer1LosesVsPlayer2(int p1id, int p2id) {
        List<Player> playersList = new ArrayList<Player>();
        String selectQuery = "SELECT COUNT(*) FROM( " +
                "SELECT distinct id FROM " + TABLE_MATCHES + " " +
                "WHERE (playerOne = " + p1id + " OR playerTwo = " + p1id + ") " +
                "AND (playerThree = " + p2id + " OR playerFour = " + p2id + ") " +
                "AND (scoreTeamOne < scoreTeam2) " +
                "UNION ALL " +
                "SELECT distinct id FROM " + TABLE_MATCHES + " " +
                "WHERE (playerThree = " + p1id + " OR playerFour = " + p1id + ") " +
                "AND (playerOne = " + p2id + " OR playerTwo = " + p2id + ") " +
                "AND (scoreTeam2 < scoreTeamOne)" +
                ")";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }

        return 0;
    }

}
