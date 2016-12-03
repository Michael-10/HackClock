package projecttadhisto.qtma.com.hackclock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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


        EditText textView = (EditText) findViewById(R.id.editText);
        Random random = new Random();
        ran = random.nextInt(4) + 1;
        textView.setText(questions.get(ran));

    }

    private void readQAndA() {
        for (int i = 1; i < 6; i++) {
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
            //a.replaceAll("\\s+","").equalsIgnoreCase(b.replaceAll("\\s+",""))
            Toast.makeText(this, "Answer Correct", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Answer Incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
