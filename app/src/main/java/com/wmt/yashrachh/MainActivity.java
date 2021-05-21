package com.wmt.yashrachh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wmt.yashrachh.Adapter.UserAdapter;
import com.wmt.yashrachh.Model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView user_recycler;
    RecyclerView.Adapter adapter;
    List<UserModel> userModelList;
    String url = "";
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_recycler = findViewById(R.id.user_list);
        progressBar = findViewById(R.id.progressBar);

        userModelList = new ArrayList<>();
        adapter = new UserAdapter(getApplicationContext(), userModelList);
        user_recycler.setHasFixedSize(true);
        user_recycler.setAdapter(adapter);
        
        getUser();


    }

    private void getUser() {
        progressBar.setVisibility(View.VISIBLE);
        url = url.concat("https://randomuser.me/api/?page=1&results=25");
        RequestQueue volleyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("Response : ",response);
                        if (response != null) {
                            JSONObject json = null;

                            try {
                                json = new JSONObject(String.valueOf(response));
                                JSONArray userssarray = json.getJSONArray("results");

                                for (int i = 0; i < userssarray.length(); i++) {

                                    JSONObject jsonObject = userssarray.getJSONObject(i);
                                    UserModel users = new UserModel();

                                    JSONObject nameobj = jsonObject.getJSONObject("name");

                                    String firstname = nameobj.getString("first");
                                    String lastname = nameobj.getString("last");
                                    String email = jsonObject.getString("email");

                                    JSONObject dateobj = jsonObject.getJSONObject("dob");

                                    String dob = dateobj.getString("date");

                                    JSONObject imgobj = jsonObject.getJSONObject("picture");

                                    String profile = imgobj.getString("thumbnail");
                                    users.setFirst(firstname);
                                    users.setLast(lastname);
                                    users.setEmail(email);
                                    users.setDate(dob);
                                    users.setThumbnail(profile);

                                    userModelList.add(users);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            adapter.notifyDataSetChanged();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.e("Error : ",error.getMessage());
                if (error instanceof ClientError) {
                    try {
                        String responsebody = new String(error.networkResponse.data, "utf-8");
                        JSONObject data = new JSONObject(responsebody);
                        Boolean status = data.getBoolean("status");
                        String stat = status.toString();
                        if (stat.equals("false")) {
                            String msg = data.getString("message");
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }){
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("page", "1");
//                params.put("results", "25");
//                return params;
//            }
        };
        volleyRequestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
    }
}