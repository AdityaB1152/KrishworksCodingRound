package com.example.krishworkscodinground.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishworkscodinground.Model.DataModel;
import com.example.krishworkscodinground.R;
import com.example.krishworkscodinground.databinding.SampleStudentListBinding;

import java.util.ArrayList;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    private Context context;
    private ArrayList<DataModel> students;

    public DataAdapter(Context context, ArrayList<DataModel> students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_student_list , parent , false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        DataModel student = students.get(position);

        holder.binding.SLstudentId.setText(student.getStudentId());
        holder.binding.SLname.setText(student.getName());
        holder.binding.SLmarks.setText(student.getMarks());



    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{

        SampleStudentListBinding binding;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = SampleStudentListBinding.bind(itemView);
        }
    }
}
