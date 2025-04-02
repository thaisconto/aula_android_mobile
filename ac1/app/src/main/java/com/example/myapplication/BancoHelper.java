package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoHelper extends SQLiteOpenHelper {

    //configuracoes banco
    private static final String DATABASE_NAME="meubanco.db";
    private static final int DATABASE_VERSION=1;

    //nome da tabela e colunas
    private static final String TABLE_NAME="livro";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITULO="titulo";
    private static final String COLUMN_AUTOR="autor";
    private static final String COLUMN_CATEGORIA="categoria";

    private static final String COLUMN_LIDO="lido";

    public BancoHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // criar banco
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITULO + "TEXT,"
                + COLUMN_AUTOR + "TEXT,"
                + COLUMN_CATEGORIA +"TEXT"
                + COLUMN_LIDO + "INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // CRUD
    public  long inserirLivro(String titulo, String autor, String categoria){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, titulo);
        values.put(COLUMN_AUTOR, autor);
        values.put(COLUMN_CATEGORIA, categoria);
        values.put(COLUMN_LIDO, 0);
        return db.insert(TABLE_NAME, null, values);
    }

    public Cursor listarLivro(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public int atualizarLivro(int id, String titulo, String autor, String categoria){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, titulo);
        values.put(COLUMN_AUTOR, autor);
        values.put(COLUMN_CATEGORIA, categoria);
        return db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int excluirLivro(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID+ "=?", new String[]{String.valueOf(id)});
    }
}
