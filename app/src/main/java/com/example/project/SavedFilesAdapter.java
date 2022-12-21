package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.database.EntityClass;

import java.util.ArrayList;
import java.util.List;

public class SavedFilesAdapter extends RecyclerView.Adapter<SavedFilesAdapter.MHolder> {

    public List<EntityClass> list = new ArrayList<>();
    onItemClick onItemClick;

    public SavedFilesAdapter(SavedFilesAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public MHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_files_adapter_item, parent, false);
        return new MHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MHolder holder, int position) {
        EntityClass model = list.get(position);
        holder.tvUrl.setText(model.url);
        holder.tvDate.setText(model.date);


        holder.btnDeleteAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onDeleteClick(position,model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvUrl;
        ImageView btnDeleteAdapter;

        public MHolder(@NonNull View itemView) {
            super(itemView);

            tvUrl = itemView.findViewById(R.id.tvUrl);
            btnDeleteAdapter=itemView.findViewById(R.id.btnDeleteAdapter);
            tvDate=itemView.findViewById(R.id.tvDate);
        }
    }

    void updateList(List<EntityClass> updatedList) {
        this.list = updatedList;
        notifyDataSetChanged();
    }

    interface onItemClick {
        void onDeleteClick(int position, EntityClass model);
    }
}