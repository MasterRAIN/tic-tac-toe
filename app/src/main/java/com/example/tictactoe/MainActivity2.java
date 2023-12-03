package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private TextView player_1_score, player_2_score, player_status;
    private Button [] buttons = new Button[9];
    private Button reset_game;

    private int player_1_score_count;
    private int player_2_score_count;
    private int round_count;
    boolean active_player;

    // p1 => 0
    // p2 => 1
    // empty => 2

    int [] game_state = {2,2,2,2,2,2,2,2,2};

    int [][] winning_positions = {
            {0,1,2},{3,4,5},{6,7,8}, // rows
            {0,3,6},{1,4,7},{2,5,8}, // columns
            {0,4,8},{2,4,6} // cross
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        player_1_score = (TextView) findViewById(R.id.player1Score);
        player_2_score = (TextView) findViewById(R.id.player2Score);
        player_status = (TextView) findViewById(R.id.playerStatus);

        reset_game = (Button) findViewById(R.id.resetBtn);

        for (int i=0; i < buttons.length; i++){
            String button_id = "btn" + i;
            int resource_id = getResources().getIdentifier(button_id,"id", getPackageName());
            buttons[i] = (Button) findViewById(resource_id);
            buttons[i].setOnClickListener(this);

        }

        round_count = 0;
        player_1_score_count = 0;
        player_2_score_count = 0;
        active_player = true;

        if (savedInstanceState != null) {
            player_1_score_count = savedInstanceState.getInt("player1");
            player_1_score.setText(String.valueOf(player_1_score_count));
        }
        if (savedInstanceState != null) {
            player_2_score_count = savedInstanceState.getInt("player2");
            player_2_score.setText(String.valueOf(player_2_score_count));
        }

    }


    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        String button_id = v.getResources().getResourceEntryName(v.getId());
        int game_state_pointer = Integer.parseInt(button_id.substring(button_id.length() - 1, button_id.length()));

        if (active_player) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#64C3EB"));
            game_state[game_state_pointer] = 0;
        } else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#E3B34C"));
            game_state[game_state_pointer] = 1;
        }

        round_count++;

        if(check_winner()){
            if(active_player) {
                player_1_score_count++;
                update_player_score();
                Toast.makeText(this,"Player 1 Won!", Toast.LENGTH_SHORT).show();
                play_again();
            }
            else {
                player_2_score_count++;
                update_player_score();
                Toast.makeText(this,"Player 2 Won!", Toast.LENGTH_SHORT).show();
                play_again();
                }
            }
            else if(round_count == 9){
                play_again();
                Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
            }
        else {
            active_player = !active_player;
        }
            if(player_1_score_count > player_2_score_count){
                player_status.setText("Player 1 is Leading!");
            }
            else if(player_2_score_count > player_1_score_count){
                player_status.setText("Player 2 is Leading!");
            }
            else {
                player_status.setText("Tie");
            }



        reset_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_again();
                player_1_score_count = 0;
                player_2_score_count = 0;
                player_status.setText("");
                update_player_score();
            }
        });

    }

    public boolean check_winner() {
        boolean winner_result = false;

        for (int [] winning_position : winning_positions){
            if(game_state[winning_position[0]] == game_state[winning_position[1]] &&
                    game_state[winning_position[1]] == game_state[winning_position[2]] &&
                    game_state[winning_position[0]] != 2){
                winner_result = true;
            }
        }
        return winner_result;
    }

    public void update_player_score(){
        player_1_score.setText(Integer.toString(player_1_score_count));
        player_2_score.setText(Integer.toString(player_2_score_count));
    }

    public void play_again(){
        round_count = 0;
        active_player = true;

        for (int i = 0; i < buttons.length; i++){
            game_state[i] = 2;
            buttons[i].setText("");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("player1", player_1_score_count);
        outState.putInt("player2", player_2_score_count);
    }


}
