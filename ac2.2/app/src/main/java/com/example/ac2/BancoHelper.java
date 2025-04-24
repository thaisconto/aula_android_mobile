package com.example.ac2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "remedio.db";
    private static final int DATABASE_VERSION = 3; // Versão incrementada para 3

    // Nomes da tabela e colunas
    public static final String TABLE_NAME = "remedios";
    public static final String COLUMN_ID = "_id"; // Padrão Android
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_HORARIO = "horario";
    public static final String COLUMN_DESCRICAO = "descricao";
    public static final String COLUMN_TOMADO = "tomado";

    // SQL para criar a tabela
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NOME + " TEXT NOT NULL," +
                    COLUMN_HORARIO + " TEXT NOT NULL," +
                    COLUMN_DESCRICAO + " TEXT NOT NULL," +
                    COLUMN_TOMADO + " INTEGER DEFAULT 0)"; // 0 = não tomado, 1 = tomado

    public BancoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Migracao para nova versao - recria a tabela
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Metodo para inserir um novo remedio
    public long inserirRemedio(String nome, String horario, String descricao, int tomado) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, nome);
        values.put(COLUMN_HORARIO, horario);
        values.put(COLUMN_DESCRICAO, descricao);
        values.put(COLUMN_TOMADO, tomado);

        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    // Metodo para obter todos os remédios
    public Cursor listarRemedios() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_NOME,
                        COLUMN_HORARIO,
                        COLUMN_DESCRICAO,
                        COLUMN_TOMADO
                },
                null, null, null, null,
                COLUMN_HORARIO + " ASC" // Ordena por horário
        );
    }

    // Metodo para atualizar um remédio
    public int atualizarRemedio(long id, String nome, String horario, String descricao, int tomado) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, nome);
        values.put(COLUMN_HORARIO, horario);
        values.put(COLUMN_DESCRICAO, descricao);
        values.put(COLUMN_TOMADO, tomado);

        int rowsAffected = db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return rowsAffected;
    }

    // Metodo para excluir um remédio
    public int excluirRemedio(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(
                TABLE_NAME,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
        db.close();
        return rowsAffected;
    }

    // Metodo para buscar um remédio por ID
    public Cursor getRemedioById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_NOME,
                        COLUMN_HORARIO,
                        COLUMN_DESCRICAO,
                        COLUMN_TOMADO
                },
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null
        );
    }

    // Metodo para marcar um remédio como tomado/não tomado
    public int marcarComoTomado(long id, int tomado) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TOMADO, tomado);

        int rowsAffected = db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return rowsAffected;
    }
}