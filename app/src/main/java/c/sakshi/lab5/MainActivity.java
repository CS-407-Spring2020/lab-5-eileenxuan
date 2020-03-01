package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void clickFunction(View view ){

        EditText nameTextField = (EditText) findViewById(R.id.username);
        String str = nameTextField.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username",str).apply();


        gotoActivity2(str);

        //Toast.makeText(MainActivity.this,nameTextField.getText().toString(),Toast.LENGTH_LONG).show();
    }

    public void gotoActivity2(String s){
        Intent intent = new Intent(this,Main2Activity.class);
        intent.putExtra("message",s);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString(usernameKey,"").equals("")){
            String name = sharedPreferences.getString("username","");
            Intent intent = new Intent(this,Main2Activity.class);
            intent.putExtra("message",name);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);
        }

    }
}
