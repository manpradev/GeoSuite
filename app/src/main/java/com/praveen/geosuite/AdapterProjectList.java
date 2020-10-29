package com.praveen.geosuite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterProjectList extends RecyclerView.Adapter<AdapterProjectList.ViewHolderProject> {

    private String[] data;
    public AdapterProjectList(ArrayList<String> data){
        this.data = data.toArray(new String[0]);
    }

    @NonNull
    @Override
    public ViewHolderProject onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.project_list,parent,false);
        return new ViewHolderProject(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProject holder, int position) {
        String projects = data[position];
        holder.proName.setText(projects);

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolderProject extends RecyclerView.ViewHolder {
        TextView proName;
        ImageButton delProBut;
        public ViewHolderProject(@NonNull View itemView) {
            super(itemView);
            proName =(TextView) itemView.findViewById(R.id.projectsName);
            delProBut = (ImageButton) itemView.findViewById(R.id.trashBut);
        }
    }

    }
