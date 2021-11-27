package com.example.krishworkscodinground.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishworkscodinground.Model.DataModel;
import com.example.krishworkscodinground.R;
import com.example.krishworkscodinground.databinding.SampleResultItemBinding;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {
    private  Context context;

    public ResultAdapter(Context context, ArrayList<DataModel> resultData) {
        this.context = context;
        this.resultData = resultData;
    }

    private ArrayList<DataModel>resultData;


    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_result_item , parent ,false);

        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        DataModel student = resultData.get(position);

        holder.binding.id.setText(student.getStudentId());
        holder.binding.name.setText(student.getName());
        holder.binding.marks.setText(student.getMarks());
        int result = Integer.parseInt(student.getMarks());

        if(result>=40){
            holder.binding.fail.setVisibility(View.GONE);
            holder.binding.pass.setVisibility(View.VISIBLE);
        }
        else {
            holder.binding.pass.setVisibility(View.GONE);
            holder.binding.fail.setVisibility(View.VISIBLE);
        }




    }

    @Override
    public int getItemCount() {
        return resultData.size();
    }

    public  class ResultViewHolder extends RecyclerView.ViewHolder{

        SampleResultItemBinding binding;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = SampleResultItemBinding.bind(itemView);
        }
    }
}
