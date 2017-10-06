package com.example.jason.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        chatText = findViewById(R.id.chat_Text);
        sendBtn = findViewById(R.id.send_button);
        chatList = findViewById(R.id.chat_List);

        // Create new inner class object using the ChatWindow as a context (this)
      final ChatAdapter messageAdapter = new ChatAdapter(this);
        // Errors here
      chatList.setAdapter(messageAdapter);


        // add on click listener for send button
        sendBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                chatMessages.add(chatText.getText().toString());
                messageAdapter.notifyDataSetChanged();
                chatText.setText("");
            }
        });
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
