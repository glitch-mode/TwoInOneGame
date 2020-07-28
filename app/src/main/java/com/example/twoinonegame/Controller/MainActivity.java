package com.example.twoinonegame.Controller;

import Fragments.FourInARow;
import Fragments.TicTacToe;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.twoinonegame.R;

public class MainActivity extends AppCompatActivity {

    private Button mButtonTicTacToe, mButtonFourInARow;
    private FragmentManager fragmentManager;
    private boolean isFirst = true;
    private Fragment mFragmentCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentById(R.id.container_game) == null && !isFirst)
            makeFragment();

        mButtonFourInARow = findViewById(R.id.button_four_in_a_row);
        mButtonTicTacToe = findViewById(R.id.button_tic_tac_toe);

        mButtonTicTacToe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentCurrent = new TicTacToe();
                makeFragment();
            }
        });

        mButtonFourInARow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentCurrent = new FourInARow();
                makeFragment();
            }
        });

    }

    public void makeFragment() {
        isFirst = false;
        fragmentManager
                .beginTransaction()
                .add(R.id.container_game, mFragmentCurrent)
                .commit();
    }
}