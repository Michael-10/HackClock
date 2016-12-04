package projecttadhisto.qtma.com.hackclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class AlarmActivity extends AppCompatActivity {

    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> answer = new ArrayList<>();
    int ran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        readQAndA();

        EditText textView = (EditText) findViewById(R.id.editText);
        Random random = new Random();
        ran = random.nextInt(3) + 1;
        textView.setText(questions.get(ran));

    }

    private void readQAndA() {
        for (int i = 1; i < 5; i++) {
            questions.add(readFile("question" + String.valueOf(i) + ".txt"));
            answer.add(readFile("answer" + String.valueOf(i) + ".txt"));
        }
    }

    private String readFile(String filename) {
        String text = "";
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
        } catch (IOException e) {
            System.err.println("Error reading file");
        } // end try-catch
        return text;
    } // end readFile method

    public void onTurnOff(View v) {
        EditText userInput = (EditText) findViewById(R.id.editText);
        String userAnswer = userInput.getText().toString();
        if (userAnswer.replaceAll("\\s+", "").equalsIgnoreCase(answer.get(ran).replaceAll("\\s+", ""))) {
            // turn off alarm

            Toast.makeText(this, "Answer Correct", Toast.LENGTH_SHORT).show();

            finish();
            System.exit(0);

        } else {
            Toast.makeText(this, "Answer Incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}