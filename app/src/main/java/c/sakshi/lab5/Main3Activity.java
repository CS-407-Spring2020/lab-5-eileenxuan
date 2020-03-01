package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {

    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        EditText editText = (EditText) findViewById(R.id.editTextBox);
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid",-1);

        if (noteid != -1){
            Note note = Main2Activity.notes.get(noteid);
            String noteContent = note.getContent();
            editText.setText(noteContent);
        }

    }

    public void saveMethod (View view){
        EditText editText = (EditText)findViewById(R.id.editTextBox);
        String content = editText.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",Context.MODE_PRIVATE,null);

        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");


        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) {
            title = "NOTE_" + (Main2Activity.notes.size()+1);
            dbHelper.saveNotes(username, title, content, date);
            Log.d("x","add note to database");
        } else {
            title = "NOTE_" + (noteid+1);
            dbHelper.updateNote(title, date, content, username);
        }

        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
    }
}
