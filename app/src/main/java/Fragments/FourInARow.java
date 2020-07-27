package Fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twoinonegame.Model.Player;
import com.example.twoinonegame.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class FourInARow extends Fragment {

    private TextView mBlueScore, mRedScore, mTextViewTurn;
    private Button[][] mRC;
    private LinearLayout[] mColumn;
    private View view;
    private int r, c, mIntBlueScore = 0, mIntRedScore = 0;
    private Player[][] mPlayers;
    private Player mTurn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayers = new Player[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mPlayers[i][j] = Player.EMPTY;
            }
        }
        mRC = new Button[5][5];
        mColumn = new LinearLayout[5];
        mTurn = Player.BLUE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_four_in_a_row, container, false);
        findAllViews();
        setOnClickListeners();
        checkWinner();
        return view;
    }

    public void findAllViews() {
        mBlueScore = view.findViewById(R.id.blue_score);
        mRedScore = view.findViewById(R.id.red_score);
        mTextViewTurn = view.findViewById(R.id.text_view_turn);
        mRC[0][0] = view.findViewById(R.id.r1c1);
        mRC[0][1] = view.findViewById(R.id.r1c2);
        mRC[0][2] = view.findViewById(R.id.r1c3);
        mRC[0][3] = view.findViewById(R.id.r1c4);
        mRC[0][4] = view.findViewById(R.id.r1c5);
        mRC[1][0] = view.findViewById(R.id.r2c1);
        mRC[1][1] = view.findViewById(R.id.r2c2);
        mRC[1][2] = view.findViewById(R.id.r2c3);
        mRC[1][3] = view.findViewById(R.id.r2c4);
        mRC[1][4] = view.findViewById(R.id.r2c5);
        mRC[2][0] = view.findViewById(R.id.r3c1);
        mRC[2][1] = view.findViewById(R.id.r3c2);
        mRC[2][2] = view.findViewById(R.id.r3c3);
        mRC[2][3] = view.findViewById(R.id.r3c4);
        mRC[2][4] = view.findViewById(R.id.r3c5);
        mRC[3][0] = view.findViewById(R.id.r4c1);
        mRC[3][1] = view.findViewById(R.id.r4c2);
        mRC[3][2] = view.findViewById(R.id.r4c3);
        mRC[3][3] = view.findViewById(R.id.r4c4);
        mRC[3][4] = view.findViewById(R.id.r4c5);
        mRC[4][0] = view.findViewById(R.id.r5c1);
        mRC[4][1] = view.findViewById(R.id.r5c2);
        mRC[4][2] = view.findViewById(R.id.r5c3);
        mRC[4][3] = view.findViewById(R.id.r5c4);
        mRC[4][4] = view.findViewById(R.id.r5c5);
        mColumn[0] = view.findViewById(R.id.column_1);
        mColumn[1] = view.findViewById(R.id.column_2);
        mColumn[2] = view.findViewById(R.id.column_3);
        mColumn[3] = view.findViewById(R.id.column_4);
        mColumn[4] = view.findViewById(R.id.column_5);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void checkWinner() {
        int win = 0, win2 = 0;
        boolean isCompleted = true;
        for (int i = 0; i < 5; i++) {
            if (mPlayers[0][i] == Player.EMPTY) isCompleted = false;
            if (mPlayers[r][i] == mTurn) {
                win++;
            } else win = 0;
            if (mPlayers[i][c] == mTurn) {
                win2++;
            } else win2 = 0;

            if (win == 4 || win2 == 4) {
                String s = mTurn.toString() + " has won!";
                Snackbar.make(Objects.requireNonNull(getView()), s, Snackbar.LENGTH_LONG).show();
                if (mTurn == Player.BLUE) mIntBlueScore++;
                else mIntRedScore++;
                for (int k = 0; k < 5; k++) {
                    mColumn[k].setClickable(false);
                }
                new CountDownTimer(5000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        reset();
                    }
                }.start();
            }
        }
        if (isCompleted) {
            Toast.makeText(getActivity(), "Nobody won :( ", Toast.LENGTH_LONG).show();
            reset();
        }
        if (mTurn == Player.BLUE) {
            mTurn = Player.RED;
            mTextViewTurn.setText(R.string.red_s_turn);
        } else {
            mTurn = Player.BLUE;
            mTextViewTurn.setText(R.string.blue_s_turn);
        }
        mBlueScore.setText(String.valueOf(mIntBlueScore));
        mRedScore.setText(String.valueOf(mIntRedScore));
    }

    public void setOnClickListeners() {
        for (int i = 0; i < 5; i++) {
            setColumn(mColumn[i], i);
        }
    }

    public void setColumn(View vi, final int n) {
        vi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                boolean b = false;
                for (int i = 4; i >= 0; i--) {
                    if (mPlayers[i][n] == Player.EMPTY) {
                        if (mTurn == Player.BLUE) {
                            mRC[i][n].setBackgroundResource(R.drawable.rounded_blue);
                        } else {
                            mRC[i][n].setBackgroundResource(R.drawable.rounded_red);
                        }
                        r = i;
                        c = n;
                        mPlayers[i][n] = mTurn;
                        b = true;
                        break;
                    }
                }
                if (!b) {
                    Snackbar.make(Objects.requireNonNull(getView()), "This column is full!", Snackbar.LENGTH_SHORT).show();
                } else checkWinner();
            }
        });
    }

    public void reset() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mPlayers[i][j] = Player.EMPTY;
                mRC[i][j].setBackgroundResource(R.drawable.rounded_button);
            }
            mColumn[i].setClickable(true);
        }
    }
}