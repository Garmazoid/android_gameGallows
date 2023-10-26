package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final static String BUNDLE_KEY_WORD = "keyWord";

    private final static String BUNDLE_KEY_CURWORD = "keyCurWord";

    private final static String BUNDLE_KEY_COUNT = "keyCount";

    private final static int MAX_TRY_COUNT = 10;

    private final static int SYMBOLS_IN_WORD = 5;

    private String[] words = {
            "Apple", "Lemon", "Hello", "World", "Board",
            "House", "April", "Horse", "Pause", "Rover",
            "River", "Dream", "Space", "Woman", "Width",
            "Table", "Stone", "Witch", "Drive", "Water",
    };


    private String word;
    private String curWord;
    private int count;

    private TextView tvTryCount;

    private TextView[] tvSymbols = new TextView[MainActivity.SYMBOLS_IN_WORD];

    private int[] tvSymbolsId = {
            R.id.tv1, R.id.tv2,R.id.tv3,R.id.tv4,R.id.tv5
    };

    private EditText editMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.tvTryCount = (TextView) this.findViewById(R.id.tvTryCount);
        this.editMain = (EditText) this.findViewById(R.id.editMain);
        for (int i = 0; i < this.tvSymbols.length; i++){
            this.tvSymbols[i] = (TextView) this.findViewById(this.tvSymbolsId[i]);
        }
        this.initGame();


        if (savedInstanceState != null) {
            this.word = savedInstanceState.getString((MainActivity.BUNDLE_KEY_WORD));
            this.curWord = savedInstanceState.getString((MainActivity.BUNDLE_KEY_CURWORD));
            this.count = savedInstanceState.getInt((MainActivity.BUNDLE_KEY_COUNT));
            this.showGame();
        } else {
            this.initGame();
        }
    }

    private void initGame(){
        Toast.makeText(this, "Новая игра", Toast.LENGTH_LONG).show();
        this.word = this.words[(int) (Math.random() * this.words.length)];
        this.curWord = "*****";
        this.count = MainActivity.MAX_TRY_COUNT;
        this.editMain.setText("");
        this.showGame();
    }

    private void showGame(){
        this.tvTryCount.setText(String.valueOf(this.count));
        for (int i = 0; i < this.curWord.length(); i++){
            this.tvSymbols[i].setText(this.curWord.substring(i, i+1));
        }
    }

    public void btnClick(View v) {
        if(this.count == 0) {
            this.initGame();
            return;
        }

        String S = this.editMain.getText().toString().toLowerCase();

        if (S.isEmpty()){
            Toast.makeText(this, "Вы ничего не ввели", Toast.LENGTH_LONG).show();
            return;
        }

        this.count--;
        if (v.getId() == R.id.btnSymbol) {
            S = S.substring(0, 1);
            if (this.word.toLowerCase().contains(S)) {
                Toast.makeText(this, "Есть такая буква!", Toast.LENGTH_LONG).show();
                for (int i = 0; i < this.tvSymbols.length; i++) {
                    String symbol = this.word.substring(i, i+1);
                    if(symbol.toLowerCase().equals(S)){
                        this.curWord = this.curWord.substring(0, i) + symbol + this.curWord.substring(i+1);
                    }
                }
                if (this.curWord.equals(this.word)) {
                    this.count = 0;
                    Toast.makeText(this, "Вы угадали!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Нет такой буквы!", Toast.LENGTH_SHORT).show();
                }
            } else if (v.getId() == R.id.btnWord) {
                if (S.equals(this.word.toLowerCase())) {
                    this.count = 0;
                    Toast.makeText(this, "Вы угадали!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "слово не угадало!", Toast.LENGTH_SHORT).show();
                }
            }
            this.editMain.setText("");
            this.showGame();

            if (this.count == 0) {
                Toast.makeText(this, "Игра завершена. \n Нажмите любую кнопку для начала игры!", Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(MainActivity.BUNDLE_KEY_WORD, this.word);
        savedInstanceState.putString(MainActivity.BUNDLE_KEY_CURWORD, this.curWord);
        savedInstanceState.putInt(MainActivity.BUNDLE_KEY_COUNT, this.count);
    }
}