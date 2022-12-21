package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.database.EntityClass;


public class SavedFilesFragment extends Fragment implements SavedFilesAdapter.onItemClick {

    RecyclerView recyclerView;
    SavedFilesAdapter savedFilesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved_files, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        savedFilesAdapter=new SavedFilesAdapter(this);
        recyclerView.setAdapter(savedFilesAdapter);

        savedFilesAdapter.updateList(MainActivity.db.savedFilesDao().getAll());
    }

    @Override
    public void onDeleteClick(int position, EntityClass model) {
        MainActivity.db.savedFilesDao().delete(model);
        savedFilesAdapter.updateList(MainActivity.db.savedFilesDao().getAll());
    }
}