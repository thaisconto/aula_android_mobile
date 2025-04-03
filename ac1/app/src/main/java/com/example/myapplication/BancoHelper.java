package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoHelper extends SQLiteOpenHelper {
    // Configurações do banco de dados
    private static final String DATABASE_NAME = "biblioteca.db";
    private static final int DATABASE_VERSION = 2; // Incrementado para versão 2

    // Nomes da tabela e colunas
    public static final String TABLE_NAME = "livros";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_AUTOR = "autor";
    public static final String COLUMN_CATEGORIA = "categoria";
    public static final String COLUMN_LIDO = "lido";

    // SQL para criar a tabela
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_TITULO + " TEXT NOT NULL," +
                    COLUMN_AUTOR + " TEXT NOT NULL," +
                    COLUMN_CATEGORIA + " TEXT," +
                    COLUMN_LIDO + " INTEGER DEFAULT 0)"; // 0 = não lido, 1 = lido

    public BancoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Migração para versão 2 - adiciona coluna 'lido' se não existir
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " +
                    COLUMN_LIDO + " INTEGER DEFAULT 0");
        }
    }

    // Metodo para inserir um novo livro
    public long inserirLivro(String titulo, String autor, String categoria, int lido) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, titulo);
        values.put(COLUMN_AUTOR, autor);
        values.put(COLUMN_CATEGORIA, categoria);
        values.put(COLUMN_LIDO, lido);

        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    // Metodo para obter todos os livros
    public Cursor listarLivros() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_TITULO, COLUMN_AUTOR, COLUMN_CATEGORIA, COLUMN_LIDO},
                null, null, null, null,
                COLUMN_TITULO + " ASC" // Ordena por título
        );
    }

    // Metodo para atualizar um livro
    public int atualizarLivro(int id, String titulo, String autor, String categoria, int lido) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, titulo);
        values.put(COLUMN_AUTOR, autor);
        values.put(COLUMN_CATEGORIA, categoria);
        values.put(COLUMN_LIDO, lido);

        int rowsAffected = db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return rowsAffected;
    }

    // Método para excluir um livro
    public int excluirLivro(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(
                TABLE_NAME,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
        db.close();
        return rowsAffected;
    }

    // Metodo para buscar um livro específico por ID
    public Cursor getLivroById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_TITULO, COLUMN_AUTOR, COLUMN_CATEGORIA, COLUMN_LIDO},
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null
        );
    }
}