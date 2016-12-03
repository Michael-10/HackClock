package projecttadhisto.qtma.com.hackclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AlarmActivity extends AppCompatActivity {

    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> answer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        readQAndA();

        TextView textView = (TextView) findViewById(R.id.textView);
        Random random = new Random();
        int ran = random.nextInt(4) + 1;
        textView.setText(questions.get(ran));

    }

    private void readQAndA() {
        for (int i = 1; i < 6; i++) {
            try {
                questions.add(new Scanner(new File("question" + String.valueOf(i) + ".txt")).useDelimiter("\\Z").next());
                answer.add(new Scanner(new File("answer" + String.valueOf(i) + ".txt")).useDelimiter("\\Z").next());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
