package com.praveen.geosuite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AddProDialog extends AppCompatDialogFragment {

    private EditText etProName;
    private EditText etProDesc;
    private String TAG = "Testing...";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_project,null);
        builder.setView(view)
                .setTitle("ADD PROJECT")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (etProName.getText().toString().matches("") | etProDesc.getText().toString().matches("")){
                           if (etProDesc.getText().toString().matches("")) {
                               Toast.makeText(getActivity(), "Please enter the Description", Toast.LENGTH_SHORT).show();
                           }else if (etProName.getText().toString().matches("")){
                               Toast.makeText(getActivity(), "Please enter the Project Name", Toast.LENGTH_SHORT).show();
                           }
                           }

                        else {
                        File root = new File(Environment.getExternalStorageDirectory()+"/"+"GeoSuite");
                        if (!root.exists()) {
                            Log.d(TAG, "Folder exists or not: " + root.exists());
                            root.mkdirs();
                        }
                        File project = new File(root + "/" + etProName.getText().toString());
                        if (project.exists()){
                            Toast.makeText(getActivity(),"Project Already Exists",Toast.LENGTH_SHORT).show();
                        }else {
                            try {
                                project.mkdir();
                                String descF = etProName.getText().toString() + "_Description.txt";
                                Log.d(TAG,descF);
                                File descPath = new File(project,descF);
                                FileWriter fw = new FileWriter(descPath);
                                fw.append(etProDesc.getText().toString());
                                fw.flush();
                                fw.close();
                                Toast.makeText(getActivity(), "Project Created", Toast.LENGTH_SHORT).show();
                                Intent intent = getActivity().getIntent();
                                //getActivity().finish();
                                //startActivity(intent);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    }
                });

        etProName = view.findViewById(R.id.etProName);
        etProDesc = view.findViewById(R.id.etDesc);

        return builder.create();

    }
}
