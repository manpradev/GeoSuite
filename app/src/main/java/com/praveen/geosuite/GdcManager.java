package com.praveen.geosuite;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

public class GdcManager extends AppCompatActivity {
    ArrayList<String> projectData = new ArrayList<String>();


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdc_manager);
        try {
            String path = Environment.getExternalStorageDirectory().toString() + "/GeoSuite";
            Log.d("Files", "Path: " + path);
            File directory = new File(path);
            File[] files = directory.listFiles();
            Log.d("Files", "Size: " + files.length);
            for (int i = 0; i < files.length; i++) {
                projectData.add(files[i].getName());
                Log.d("Files", "FileName:" + files[i].getName());
            }

        RecyclerView proList = findViewById(R.id.projectRV);
        proList.setLayoutManager(new LinearLayoutManager(this));
        proList.setAdapter(new AdapterProjectList(projectData));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"Can't Reead",Toast.LENGTH_SHORT).show();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final AddProDialog addPro = new AddProDialog();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPro.show(getSupportFragmentManager(),"Add Project Dialog");
            }
        });
    }

    public void delPro(View view) {
        String path = Environment.getExternalStorageDirectory().toString() + "/GeoSuite";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();

    }
}
