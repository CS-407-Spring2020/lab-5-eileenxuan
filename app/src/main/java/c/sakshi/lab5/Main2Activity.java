package c.sakshi.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    TextView welcomeMessage;

    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Welcome message
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);

        String usernameKey = "username";
        String str = "";

        welcomeMessage = (TextView) findViewById((R.id.welcomeMessage));

        if (!sharedPreferences.getString(usernameKey,"").equals("")){
            str = sharedPreferences.getString("username","");
        } else {
            Intent intent = getIntent();
            str = intent.getStringExtra("message");
        }

        welcomeMessage.setText("Welcome " + str +"!");

        //Get SQLiteDatabase instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",Context.MODE_PRIVATE,null);

        //Initiate the "notes" class variable
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes = dbHelper.readNotes(str);

        //Create an ArrayList<String> object by iterating over notes object
        ArrayList<String> displayNotes= new ArrayList<>();
        for (Note note: notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(),note.getDate()));
        }

        //Use ListView view to display notes on screen
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView)findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        //Add onItemClickListener for ListView item, a note in our case
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
                Log.d("yxx",String.valueOf(position));
                intent.putExtra("noteid",position);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.addNote:
                Intent intent1 = new Intent(this,Main3Activity.class);
                startActivity(intent1);
                return true;
            case R.id.logOut:
                Intent intent2 = new Intent(this,MainActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5",Context.MODE_PRIVATE);
                sharedPreferences.edit().remove("username").apply();
                startActivity(intent2);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}
