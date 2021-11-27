package com.example.krishworkscodinground.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.krishworkscodinground.databinding.FragmentAddDataBinding;

import java.util.HashMap;
import java.util.Map;

public class AddData extends Fragment {
    FragmentAddDataBinding binding;
    private ProgressDialog dialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddDataBinding.inflate(inflater  , container , false);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please Wait");
        dialog.setCancelable(false);
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
                String studentId = binding.newID.getText().toString();
                String name = binding.newName.getText().toString();
                String marks = binding.newMarks.getText().toString();

                if (studentId.isEmpty()) {
                    binding.newID.setError("Please Enter an Id");
                }
                if (name.isEmpty()) {
                    binding.newName.setError("Please Enter Student Name");
                }
                if (marks.isEmpty()) {
                    binding.newMarks.setError("Please Enter Marks");
                }
                else{
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbwovHIR_BJr41ecWc3_sPpwF3W-zqrTFWqpDnOds0vJsTrmUH1H2En_QRKe34tYOB3p/exec",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                                binding.newID.setText(null);
                                binding.newName.setText(null);
                                binding.newMarks.setText(null);


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("action", "addItem");
                        params.put("studentId", studentId);
                        params.put("name", name);
                        params.put("marks", marks);

                        return params;
                    }
                };
                int socketTimeOut = 5000;

                RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(retryPolicy);


                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);

            }
            }


        });

        return binding.getRoot();
        }

        //Sheet Url = https://docs.google.com/spreadsheets/d/e/2PACX-1vTfy76y9c1Z90P9JYoXf6TtNX4_ak3qKYUT0KlZUc-UgfjBMLTCbiSw-lnOWfvavL9bUeLzNj1alMDd/pubhtml




    }