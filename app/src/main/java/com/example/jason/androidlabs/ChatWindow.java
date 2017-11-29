package com.example.jason.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    private EditText chatText;
    private Button sendBtn;
    private ListView chatList;
    private ArrayList<String> chatMessages = new ArrayList<>();
    SQLiteDatabase db;
    ChatDatabaseHelper dbHelper;
    Cursor queryWork;

    // logging tag
    protected static final String ACTIVITY_NAME = "ChatWindow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        // create temporary chatDatabaseHelper object
        dbHelper = new ChatDatabaseHelper(this);
        // open the database as both readable and writeable
        // The writeable function returns a db object
        // needs to be declared final because it's used in an inner class
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        queryWork = db.query(false, ChatDatabaseHelper.TABLE_NAME,
                new String[] {ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE},
                null, null , null, null, null, null);

        //resets the iteration of the cursor
        queryWork.moveToFirst();

        //Number of rows in the query
        int numRows = queryWork.getCount();

        int messageIndex = queryWork.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);

        //add messages from the database to the arraylist
        for (int i= 0; i < numRows; i++){
            chatMessages.add(queryWork.getString(messageIndex));
            queryWork.moveToNext();
        }

        //resets the iteration of results, move back to the first row
        queryWork.moveToFirst();
        //log the message retrieved and column count
        while(!queryWork.isAfterLast()){
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: "
                    + queryWork.getString(queryWork.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "Cursor's column count = " + queryWork.getColumnCount());
            queryWork.moveToNext();
        }

        chatText = findViewById(R.id.chat_Text);
        sendBtn = findViewById(R.id.send_button);
        chatList = findViewById(R.id.chat_List);

        // Create new inner class object using the ChatWindow as a context (this)
      final ChatAdapter messageAdapter = new ChatAdapter(this);
      chatList.setAdapter(messageAdapter);


        // add on click listener for send button
        sendBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                //Create new row data
                ContentValues cValues = new ContentValues();
                cValues.put(ChatDatabaseHelper.KEY_MESSAGE, chatText.getText().toString());

                //Then insert
                db.insert(ChatDatabaseHelper.TABLE_NAME, "" , cValues);

                chatMessages.add(chatText.getText().toString());
                messageAdapter.notifyDataSetChanged();
                chatText.setText("");
            }
        });
    }

    public void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy");
        super.onDestroy();
        // close cursor
        if (queryWork != null) {
            queryWork.close();
            Log.i(ACTIVITY_NAME, "cursor closed");
        }
        // close database
        if (dbHelper != null) {
            dbHelper.close();
            Log.i(ACTIVITY_NAME, "database closed");
        }
    }

    // Inner class ChatAdapter object which holds all the chat messages
    private class ChatAdapter extends ArrayAdapter<String>{

        public ChatAdapter(Context context){
            super(context, 0);
        }

        // return rows in list view
        public int getCount(){
            return chatMessages.size();
        }

        // return item in list at specified position
        public String getItem(int position){
            return chatMessages.get(position);
        }

        // return layout positioned at specified row
        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;

            if(position%2==0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);}
            else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
            // set TextView message to be list.get(position)
            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }

    }
}
