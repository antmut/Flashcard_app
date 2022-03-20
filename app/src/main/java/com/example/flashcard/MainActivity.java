package com.example.flashcard;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView flashcardQuestion;
    TextView flashcardAnswer1;
    TextView flashcardAnswer2;
    TextView flashcardAnswer3;
    ImageView eyeVisible;
    ImageView eyeInvisible;

    FlashcardDatabase flashcardDatabase;                                            //declaring as global variable flashcard
    List<Flashcard> allFlashcards;                                                  //declaring the whole list of flashcards
    int currentCardIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardQuestion = findViewById(R.id.flashcard_question);
        flashcardAnswer1 = findViewById(R.id.flashcard_answer1);
        flashcardAnswer2 = findViewById(R.id.flashcard_answer2);
        flashcardAnswer3 = findViewById(R.id.flashcard_answer3);

        eyeVisible = findViewById(R.id.ic_eye_visible);
        eyeInvisible = findViewById(R.id.ic_eye_invisible);

        flashcardDatabase = new FlashcardDatabase(this);                     // initializing flashcardDatabase
        allFlashcards = flashcardDatabase.getAllCards();

        //Toast message if clicked the question
        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Question was clicked", Toast.LENGTH_SHORT).show();
                Log.i("Toni", "Entered method onClick");
            }
        });

        //When user tap the eye icon answers become visible
        eyeVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eyeVisible.setVisibility(View.INVISIBLE);
                eyeInvisible.setVisibility(View.VISIBLE);
                flashcardAnswer1.setVisibility(View.VISIBLE);
                flashcardAnswer2.setVisibility(View.VISIBLE);
                flashcardAnswer3.setVisibility(View.VISIBLE);
            }
        });

        // When user tap the eye icon answers become invisible
        eyeInvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eyeVisible.setVisibility(View.VISIBLE);
                eyeInvisible.setVisibility(View.INVISIBLE);
                flashcardAnswer1.setVisibility(View.INVISIBLE);
                flashcardAnswer2.setVisibility(View.INVISIBLE);
                flashcardAnswer3.setVisibility(View.INVISIBLE);
            }
        });

        // When User tap on 1st answer colors change accordingly
        findViewById(R.id.flashcard_answer1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.flashcard_answer1)).setBackgroundColor(getResources().getColor(R.color.red));
                ((TextView) findViewById(R.id.flashcard_answer3)).setBackgroundColor(getResources().getColor(R.color.green));
            }
        });

        // When User tap on 2nd answer colors change accordingly
        findViewById(R.id.flashcard_answer2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.flashcard_answer2)).setBackgroundColor(getResources().getColor(R.color.red));
                ((TextView) findViewById(R.id.flashcard_answer3)).setBackgroundColor(getResources().getColor(R.color.green));
            }
        });

        // When User tap on 3rd answer colors change accordingly
        findViewById(R.id.flashcard_answer3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.flashcard_answer3)).setBackgroundColor(getResources().getColor(R.color.green));
            }
        });

        // User can tap on the background view to reset all views to default settings
        findViewById(R.id.parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.flashcard_answer1)).setBackgroundColor(getResources().getColor(R.color.tan));
                ((TextView) findViewById(R.id.flashcard_answer2)).setBackgroundColor(getResources().getColor(R.color.tan));
                ((TextView) findViewById(R.id.flashcard_answer3)).setBackgroundColor(getResources().getColor(R.color.tan));
            }
        });

        // User can press add button and navigate to a new activity
        findViewById(R.id.ic_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        // User can press edit button and navigate to AddCardActivity
        findViewById(R.id.ic_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                String str = ((TextView) findViewById(R.id.flashcard_question)).getText().toString();
                String str1 = ((TextView) findViewById(R.id.flashcard_answer1)).getText().toString();
                String str2 = ((TextView) findViewById(R.id.flashcard_answer2)).getText().toString();
                String str3 = ((TextView) findViewById(R.id.flashcard_answer3)).getText().toString();
                intent.putExtra("Question", str);
                intent.putExtra("Answer1", str1);
                intent.putExtra("Answer2", str2);
                intent.putExtra("Answer3", str3);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        // User can press trash button to delete current card
        findViewById(R.id.ic_trash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allFlashcards == null || allFlashcards.size() == 0) {       //check if empty
                    Snackbar.make(view,
                            "No more cards left. Please press enter a card!",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    currentCardIndex = 0;
                    return;
                }
                flashcardDatabase.deleteCard(flashcardQuestion.getText().toString());   //what about answers??
                allFlashcards = flashcardDatabase.getAllCards();
                currentCardIndex--;
            }
        });

        // User can press next button to go through the cards
        findViewById(R.id.ic_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allFlashcards == null || allFlashcards.size() == 0) {       //always check edges
                    return;
                }
                    currentCardIndex++;

                if (currentCardIndex >= allFlashcards.size()) {
                    Snackbar.make(view,
                            "End of cards reached. Starting from the beginning",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    currentCardIndex = 0;
                }
                    Flashcard currentCard = allFlashcards.get(currentCardIndex);
                    flashcardQuestion.setText(currentCard.getQuestion());
                    flashcardAnswer1.setText(currentCard.getAnswer());
                    flashcardAnswer2.setText(currentCard.getWrongAnswer1());
                    flashcardAnswer3.setText(currentCard.getWrongAnswer2());
            }
        });

        //Accessing flashcards
        if (allFlashcards != null && allFlashcards.size() > 0) {                     // check if the list is empty
            flashcardQuestion.setText(allFlashcards.get(0).getQuestion());
            flashcardAnswer1.setText(allFlashcards.get(0).getAnswer());
            flashcardAnswer2.setText(allFlashcards.get(0).getWrongAnswer1());
            flashcardAnswer3.setText(allFlashcards.get(0).getWrongAnswer2());
        }
    }

        // get data passed from download button AddCardActivity
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 100) {            // codes must match
                if(data != null) {
                    String question = data.getExtras().getString("Question_key");
                    String answer = data.getExtras().getString("Answer_key");
                    String answer1 = data.getExtras().getString("Answer_key1");
                    String answer2 = data.getExtras().getString("Answer_key2");
                    flashcardQuestion.setText(question);
                    flashcardAnswer1.setText(answer);
                    flashcardAnswer2.setText(answer1);
                    flashcardAnswer3.setText(answer2);

                    flashcardDatabase.insertCard(new Flashcard(question, answer, answer1, answer2));          // save a new flashcard
                    allFlashcards = flashcardDatabase.getAllCards();                                          // update the list
                }
            }
        }
}