package com.example.project.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {EntityClass.class}, version = 1)
public abstract class SavedFilesDataBase extends RoomDatabase {
    public abstract SavedFilesDao savedFilesDao();

}
