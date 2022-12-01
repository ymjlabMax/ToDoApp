package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static NoteDatabase noteDatabase = null;

    Fragment mainFragment;
    EditText inputToDo;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new MainFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();


        openDatabase();


        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToDo();

                Toast.makeText(getApplicationContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();

            }
        });

    }



    private void saveToDo(){
        inputToDo = findViewById(R.id.inputToDo);
        String todo = inputToDo.getText().toString();

        String sqlSave = "insert into " + NoteDatabase.TABLE_NOTE + " (TODO) values (" +
                "'" + todo + "')";

        NoteDatabase database = NoteDatabase.getInstance(context);
        database.execSQL(sqlSave);
        inputToDo.setText("");
    }

    public void openDatabase(){
        if(noteDatabase != null){
            noteDatabase.close();
            noteDatabase = null;
        }

        noteDatabase = NoteDatabase.getInstance(this);
        boolean isOpen = noteDatabase.open();
         if(isOpen){
             Log.d(TAG, "Note database is open.");
         } else {
             Log.d(TAG, "Note database is not open");
         }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(noteDatabase != null){
            noteDatabase.close();
            noteDatabase = null;
        }
    }







}