package Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.twoinonegame.Model.Player;
import com.example.twoinonegame.R;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.Objects;


public class TicTacToe extends Fragment {
    private View mView;
    private Button[][] mButtons;
    private Player[][] mPlayers;
    private Player mTurn;
    private int mIntBlueScore = 0, mIntRedScore = 0, r, c;
    private TextView mTextViewTurn, mTextViewBlueScore, mTextViewRedScore;
    private static final String PLAYERS = "mPlayers";
    private static final String TURN = "mTurn";
    private static final String BLUE_SCORE = "mIntBlueScore";
    private static final String RED_SCORE = "mIntRedScore";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayers = new Player[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mPlayers[i][j] = Player.EMPTY;
            }
        }
        mButtons = new Button[3][3];
        mTurn = Player.BLUE;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PLAYERS, mPlayers);
        outState.putSerializable(TURN, mTurn);
        outState.putInt(BLUE_SCORE, mIntBlueScore);
        outState.putInt(RED_SCORE, mIntRedScore);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_tic_tac_toe, container, false);
        findAllViews();
        setAllListeners();
        if (savedInstanceState != null) {
            mPlayers = (Player[][]) savedInstanceState.getSerializable(PLAYERS);
            mTurn = (Player) savedInstanceState.getSerializable(TURN);
            mIntRedScore = savedInstanceState.getInt(RED_SCORE);
            mIntBlueScore = savedInstanceState.getInt(BLUE_SCORE);
            setButtonColors();
        }
        checkWin();
        return mView;
    }

    private void setButtonColors() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mPlayers[i][j] == Player.BLUE) {
                    mButtons[i][j].setBackgroundResource(R.drawable.ttt_blue_button);
                } else if (mPlayers[i][j] == Player.RED) {
                    mButtons[i][j].setBackgroundResource(R.drawable.ttt_red_button);
                } else mButtons[i][j].setBackgroundResource(R.drawable.ttt_button);
            }
        }
    }

    public void findAllViews() {
        mButtons[0][0] = mView.findViewById(R.id.r1c1t);
        mButtons[0][1] = mView.findViewById(R.id.r1c2t);
        mButtons[0][2] = mView.findViewById(R.id.r1c3t);
        mButtons[1][0] = mView.findViewById(R.id.r2c1t);
        mButtons[1][1] = mView.findViewById(R.id.r2c2t);
        mButtons[1][2] = mView.findViewById(R.id.r2c3t);
        mButtons[2][0] = mView.findViewById(R.id.r3c1t);
        mButtons[2][1] = mView.findViewById(R.id.r3c2t);
        mButtons[2][2] = mView.findViewById(R.id.r3c3t);
        mTextViewTurn = mView.findViewById(R.id.turn);
        mTextViewBlueScore = mView.findViewById(R.id.blue_score);
        mTextViewRedScore = mView.findViewById(R.id.red_score);
    }

    public void setAllListeners() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int finalI = i;
                final int finalJ = j;
                mButtons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPlayers[finalI][finalJ] == Player.EMPTY) {
                            if (mTurn == Player.BLUE) {
                                mButtons[finalI][finalJ].setBackgroundResource(R.drawable.ttt_blue_button);
                            } else {
                                mButtons[finalI][finalJ].setBackgroundResource(R.drawable.ttt_red_button);
                            }
                            mPlayers[finalI][finalJ] = mTurn;
                            r = finalI;
                            c = finalJ;
                            checkWin();
                        }
                    }
                });
            }
        }
    }

    public void setTurn() {
        if (mTurn == Player.BLUE) {
            mTurn = Player.RED;
            mTextViewTurn.setText(R.string.red_s_turn);
        } else {
            mTurn = Player.BLUE;
            mTextViewTurn.setText(R.string.blue_s_turn);
        }
    }

    public void checkWin() {
        boolean isComplete = true;
        int w1 = 0, w2 = 0, w3 = 0, w4 = 0;
        for (int i = 0, j = 2; i < 3; i++, j--) {
            if (mPlayers[r][i] == mTurn) w1++;
            if (mPlayers[i][c] == mTurn) w2++;
            if (mPlayers[i][i] == mTurn) w3++;
            if (mPlayers[j][i] == mTurn) w4++;
            for (int k = 0; k < 3; k++) {
                if (mPlayers[i][k] == Player.EMPTY) {
                    isComplete = false;
                    break;
                }
            }
        }
        if (isComplete) {
            Snackbar.make(Objects.requireNonNull(getView()), "Nobody won :(", Snackbar.LENGTH_LONG).show();
            makeTimer();
        }
        if (w1 == 3 || w2 == 3 || w3 == 3 || w4 == 3) afterWin();
        mTextViewRedScore.setText(String.valueOf(mIntRedScore));
        mTextViewBlueScore.setText(String.valueOf(mIntBlueScore));
        setTurn();
    }

    public void afterWin() {
        if (mTurn == Player.BLUE) mIntBlueScore++;
        else mIntRedScore++;
        setClickables(false);
        String s = mTurn.toString() + " has won!";
        Snackbar.make(Objects.requireNonNull(getView()), s, Snackbar.LENGTH_LONG).show();
        makeTimer();
    }

    public void setClickables(boolean b) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mButtons[i][j].setClickable(b);
            }
        }
    }

    public void makeTimer() {
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                reset();
            }
        }.start();
    }

    public void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mPlayers[i][j] = Player.EMPTY;
                mButtons[i][j].setBackgroundResource(R.drawable.ttt_button);
            }
        }
        setClickables(true);
    }
}