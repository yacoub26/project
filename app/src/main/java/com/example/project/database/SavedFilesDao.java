package com.example.project.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavedFilesDao {


    @Query("SELECT * FROM EntityClass")
    List<EntityClass> getAll();

    @Insert
    void insertAll(EntityClass... users);

    @Delete
    void delete(EntityClass user);

    @Query("Select * from entityclass where uid =:id")
    List<EntityClass> getWithTitle(int id);

}