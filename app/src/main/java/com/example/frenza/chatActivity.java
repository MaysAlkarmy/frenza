package com.example.frenza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class chatActivity extends AppCompatActivity {

    RecycleAdapter adapter;
    EditText userMsgEditText;
    ImageButton send;
    ArrayList<Message> chatArray;
    RecyclerView recyclerView;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatArray= new ArrayList<>();
        recyclerView=findViewById(R.id.recycler);
        userMsgEditText= findViewById(R.id.messageEditText);
        send= findViewById(R.id.sendButton);


        adapter= new RecycleAdapter(chatArray);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager= new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);


        send.setOnClickListener(v -> {
            String question= userMsgEditText.getText().toString().trim();
            addToChat(question, Message.sent_by_me);
            userMsgEditText.setText("");
            callAPI(question);

        });





    }

    void addToChat(String message, String sentBy){
        runOnUiThread(() -> {
            chatArray.add(new Message(message, sentBy));
            adapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(adapter.getItemCount());
        });
    }

    void addResponse(String response){
        chatArray.remove(chatArray.size()-1);
        addToChat(response,Message.sent_by_bot);
    }

    void callAPI(String question){
        //okhttp
        chatArray.add(new Message("Typing... ",Message.sent_by_bot));

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model","text-davinci-003");
            jsonBody.put("prompt",question);
            jsonBody.put("max_tokens",4000);
            jsonBody.put("temperature",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                addResponse("Failed to load response due to "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject  jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else{
                    addResponse("Failed to load response due to "+response.body().toString());
                }
            }
        });





    }



// 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
    //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



}