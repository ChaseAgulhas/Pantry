package com.chase.apps.pantry.repository.customer.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chase.apps.pantry.conf.databases.DBConstants;
import com.chase.apps.pantry.domain.customer.Customer;
import com.chase.apps.pantry.repository.customer.CustomerRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chase on 2016-09-02.
 */
public class CustomerRepositoryImpl extends SQLiteOpenHelper implements CustomerRepository {

    public static final String TABLE_CUSTOMER = "customer";
    private SQLiteDatabase database;

    public static final String COLUMN_EMAILADDRESS = "emailaddress";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_DATEOFBIRTH = "dateofbirth";

    //Database table creation
    private static final String DATABASE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_CUSTOMER + "("
            + COLUMN_EMAILADDRESS + " TEXT PRIMARY KEY,"
            + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_SURNAME + " TEXT NOT NULL,"
            + COLUMN_DATEOFBIRTH + " TEXT NOT NULL);";

    public CustomerRepositoryImpl(Context context)
    {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public CustomerRepositoryImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void open()
    {
        database = this.getWritableDatabase();
    }

    public void close()
    {
        this.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(this.getClass().getName(),
                "Currently upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all the old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        onCreate(db);
    }

    @Override
    public Customer findById(String emailAddress, String name) {

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(
                TABLE_CUSTOMER,
                new String[]{
                        COLUMN_EMAILADDRESS,
                        COLUMN_NAME,
                        COLUMN_SURNAME,
                        COLUMN_DATEOFBIRTH},
                COLUMN_EMAILADDRESS + " =? ",
                new String[]{String.valueOf(emailAddress)},
                null,
                null,
                null,
                null);
        if(cursor.moveToFirst())
        {
            final Customer customer = new Customer.Builder()
                    .emailAddress(cursor.getString(0))
                    .name(cursor.getString(1))
                    .surname(cursor.getString(2))
                    .dateOfBirth(cursor.getString(3))
                    .build();
            return customer;
        }
        else
        {
            return null;
        }
    }

    @Override
    public Customer save(Customer customer) {
        open();
        onCreate(database);
        ContentValues values = new ContentValues();

        values.put(COLUMN_EMAILADDRESS, customer.getEmailAddress());
        values.put(COLUMN_NAME, customer.getName());
        values.put(COLUMN_SURNAME, customer.getSurname());
        values.put(COLUMN_DATEOFBIRTH, customer.getDateOfBirth());

        Long employeeID = database.insertOrThrow(TABLE_CUSTOMER, null, values);

        final Customer insertedEntity = new Customer.Builder()
                .copy(customer)
                .emailAddress(employeeID.toString())
                .build();

        return insertedEntity;
    }

    @Override
    public Customer update(Customer customer) {

        open();
        ContentValues values = new ContentValues();

        values.put(COLUMN_EMAILADDRESS, customer.getEmailAddress());
        values.put(COLUMN_NAME, customer.getName());
        values.put(COLUMN_SURNAME, customer.getSurname());
        values.put(COLUMN_DATEOFBIRTH, customer.getDateOfBirth());

        database.update(
                TABLE_CUSTOMER,
                values,
                COLUMN_EMAILADDRESS + " =? ",
                new String[]{String.valueOf(customer.getEmailAddress())}
        );
        return customer;
    }

    @Override
    public Customer delete(Customer customer) {

        open();
        database.delete(
                TABLE_CUSTOMER,
                COLUMN_EMAILADDRESS + " =? ",
                new String[]{String.valueOf(customer.getEmailAddress())}
        );

        return customer;
    }

    @Override
    public int deleteAll() {

        open();
        int rowsDeleted = database.delete(TABLE_CUSTOMER, null, null);
        return rowsDeleted;
    }

    @Override
    public Set<Customer> findAll() {

        open();
        String selectAll = " SELECT * FROM " + TABLE_CUSTOMER;
        Set<Customer> customers = new HashSet<>();
        Cursor cursor = database.rawQuery(selectAll, null);

        if(cursor.moveToFirst())
        {
            do {

                final Customer customer = new Customer.Builder()
                        .emailAddress(cursor.getString(0))
                        .name(cursor.getString(1))
                        .surname(cursor.getString(2))
                        .dateOfBirth(cursor.getString(3))
                        .build();

                customers.add(customer);

            }while(cursor.moveToNext());
        }

        return customers;
    }
}

