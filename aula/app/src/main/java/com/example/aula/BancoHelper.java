package com.example.aula;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BancoHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="meubanco.db";
    private static final int DATABASE_VERSION=1;

    //nome da tabela e colunas
    private static final String TABLE_NAME="usuario";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOME="nome";
    private static final String COLUMN_EMAIL="email";

    public BancoHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOME + "TEXT,"
                + COLUMN_EMAIL +"TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public  long inserirUsuario(String nome, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, nome);
        values.put(COLUMN_EMAIL, email);
        return db.insert(TABLE_NAME, null, values);
    }

    public Cursor listarUsuarios(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public int atualizarUsuario(int id, String nome, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, nome);
        values.put(COLUMN_EMAIL, email);
        return db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int excluirUsuario(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID+ "=?", new String[]{String.valueOf(id)});
    }



}
