package Fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twoinonegame.Model.Player;
import com.example.twoinonegame.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class FourInARow extends Fragment {

    private TextView mBlueScore, mRedScore, mTextViewTurn;
    private Button[][] mRC;
    private LinearLayout[] mColumn;
    private View view;
    private int r, c, mIntBlueScore = 0, mIntRedScore = 0;
    private Player[][] mPlayers;
    private Player mTurn;
    public static final String PLAYERS = "mPlayers";
    public static final String TURN = "mTurn";
    public static final String BLUE_SCORE = "mIntBlueScore";
    public static final String RED_SCORE = "mIntRedScore";


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

        view = inflater.inflate(R.layout.fragment_four_in_a_row, container, false);
        findAllViews();
        setOnClickListeners();
        if (savedInstanceState != null) {
            mPlayers = (Player[][]) savedInstanceState.getSerializable(PLAYERS);
            mTurn = (Player) savedInstanceState.getSerializable(TURN);
            mIntRedScore = savedInstanceState.getInt(RED_SCORE);
            mIntBlueScore = savedInstanceState.getInt(BLUE_SCORE);
            setButtonColors();
        }
        checkWinner();
        return view;
    }

    public void setButtonColors() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (mPlayers[i][j] == Player.BLUE) {
                    mRC[i][j].setBackgroundResource(R.drawable.rounded_blue);
                } else if (mPlayers[i][j] == Player.RED) {
                    mRC[i][j].setBackgroundResource(R.drawable.rounded_red);
                } else mRC[i][j].setBackgroundResource(R.drawable.rounded_button);
            }
        }
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
        checkDiagonalWin();
        checkVerticalHorizontalWin();
        setTurn();
        setScore();
    }

    public void checkDiagonalWin() {
        int i = r, j = c, w = 1;
        try {
            while (true) {
                i++;
                j++;
                if (i > 4 || j > 4) throw new ArrayIndexOutOfBoundsException();
                if (mPlayers[i][j] == mTurn) w++;
            }
        } catch (ArrayIndexOutOfBoundsException a) {
            if (w == 4) {
                afterWin();
            }
            i = r;
            j = c;
        }
        try {
            while (true) {
                i--;
                j--;
                if (i < 0 || j < 0) throw new ArrayIndexOutOfBoundsException();
                if (mPlayers[i][j] == mTurn) w++;
            }
        } catch (ArrayIndexOutOfBoundsException a) {
            if (w == 4) {
                afterWin();
            }
            i = r;
            j = c;
            w = 1;
        }
        try {
            while (true) {
                i++;
                j--;
                if (i > 4 || j < 0) throw new ArrayIndexOutOfBoundsException();
                if (mPlayers[i][j] == mTurn) w++;
            }
        } catch (ArrayIndexOutOfBoundsException a) {
            if (w == 4) {
                afterWin();
            }
            i = r;
            j = c;
        }
        try {
            while (true) {
                i--;
                j++;
                if (i < 0 || j > 4) throw new ArrayIndexOutOfBoundsException();
                if (mPlayers[i][j] == mTurn) w++;
            }
        } catch (ArrayIndexOutOfBoundsException a) {
            if (w == 4) {
                afterWin();
            }
        }
    }

    public void setScore() {
        mBlueScore.setText(String.valueOf(mIntBlueScore));
        mRedScore.setText(String.valueOf(mIntRedScore));
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

    public void checkVerticalHorizontalWin() {
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
                afterWin();
            }
        }
        if (isCompleted) {
            Toast.makeText(getActivity(), "Nobody won :( ", Toast.LENGTH_LONG).show();
            makeTimer();
        }
    }

    public void afterWin() {
        String s = mTurn.toString() + " has won!";
        Snackbar.make(Objects.requireNonNull(getView()), s, Snackbar.LENGTH_LONG).show();
        if (mTurn == Player.BLUE) mIntBlueScore++;
        else mIntRedScore++;
        makeUnclickable();
        makeTimer();
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

    public void makeUnclickable() {
        for (int k = 0; k < 5; k++) {
            mColumn[k].setClickable(false);
        }
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