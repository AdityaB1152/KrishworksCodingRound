package com.example.krishworkscodinground.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.krishworkscodinground.Adapters.DataAdapter;
import com.example.krishworkscodinground.Adapters.ResultAdapter;
import com.example.krishworkscodinground.Model.DataModel;
import com.example.krishworkscodinground.R;
import com.example.krishworkscodinground.databinding.FragmentReadDataBinding;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Spreadsheet ID
1wshLaQabfI5I46oGeHb52qrhqw6h0CNulJIwG7vQLjk
 */
public class ReadData extends Fragment {
    private boolean created;
    private ArrayList<DataModel> students;
    private DataAdapter dataAdapter;
    private ResultAdapter resultAdapter;
    private String appScriptUrl = "https://script.google.com/macros/s/AKfycbzfFQbi1Lk4CxPicUeMx_CiokKS3oZrAc4b2eQdw_dsDkt7Yy76xyvasNe-FDM78BB6/exec";
    private ProgressDialog dialog;
        FragmentReadDataBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentReadDataBinding.inflate(inflater , container , false);

        students = new ArrayList<>();
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please Wait");
        dialog.setCancelable(false);

        created = false;

        binding.list.setVisibility(View.VISIBLE);
        binding.list2.setVisibility(View.GONE);

        dataAdapter = new DataAdapter(getActivity() , students);
        resultAdapter = new ResultAdapter(getActivity() , students);

        getItems();

        binding.showResultList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(!created){
                   dialog.show();

                   StringRequest stringRequest = new StringRequest(Request.Method.POST, appScriptUrl, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {

                           Toast.makeText(getActivity(), "Spreadsheet Has Been Updated", Toast.LENGTH_SHORT).show();

                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {

                       }
                   }){
                       @Nullable
                       @Override
                       protected Map<String, String> getParams() throws AuthFailureError {
                           Map<String , String>map = new HashMap<>();
                           map.put("action" , "trigger");
                           return map;
                       }
                   };

                   int socketTimeOut = 5000;

                   RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                   stringRequest.setRetryPolicy(retryPolicy);


                   RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                   requestQueue.add(stringRequest);
                   binding.list.setVisibility(View.GONE);
                   binding.list2.setVisibility(View.VISIBLE);
                   dialog.dismiss();
                   created = true;
               }
               else{
                   binding.list.setVisibility(View.GONE);
                   binding.list2.setVisibility(View.VISIBLE);
               }
            }
        });

        binding.showStudentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                binding.list2.setVisibility(View.GONE);
                binding.list.setVisibility(View.VISIBLE);

            }
        });

        return binding.getRoot();
    }

        private void getItems() {
        dialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, appScriptUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                   parseItems(response);
                    Toast.makeText(getActivity(), "Students List is Ready", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });


            int socketTimeOut = 5000;

            RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut , 0 , DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(retryPolicy);


            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }

        private void parseItems(String response) {

        ArrayList<HashMap<String , String>> list = new ArrayList<>();

            try {
                JSONObject object = new JSONObject(response);

                JSONArray jsonArray = object.getJSONArray("items");

                for(int i =0 ;i<jsonArray.length();i++){
                    JSONObject object1 = jsonArray.getJSONObject(i);

                    String studentId = object1.getString("studentId");
                    String name = object1.getString("name");
                    String marks = object1.getString("marks");

                    HashMap<String , String>map = new HashMap<>();
                    map.put("studentId" , studentId);
                    map.put("name" , name);
                    map.put("marks" , marks);

                    list.add(map);
                }
                binding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.list.setAdapter(dataAdapter);

                binding.list2.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.list2.setAdapter(resultAdapter);
                for(HashMap<String, String> map:list){
                    String studentId = map.get("studentId");
                    String name =map.get("name");
                    String marks = map.get("marks");

                    students.add(new DataModel(studentId , name , marks));
                }
                dataAdapter.notifyDataSetChanged();
                resultAdapter.notifyDataSetChanged();



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }