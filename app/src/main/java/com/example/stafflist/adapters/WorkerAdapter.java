package com.example.stafflist.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.stafflist.R;
import com.example.stafflist.models.Worker;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class WorkerAdapter extends ArrayAdapter<Worker> {

    public WorkerAdapter(Context context, List<Worker> workerList) {
        super(context, R.layout.list_item_activity, workerList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Worker worker = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_activity, parent, false);
        }
        if (worker != null) {
            CircleImageView circleImageView = view.findViewById(R.id.personImageCIV);
            TextView nameTV = view.findViewById(R.id.nameTV);
            TextView surnameTV = view.findViewById(R.id.surnameTV);
            if (worker.getImage() != null) {
                circleImageView.setImageURI(Uri.parse(worker.getImage()));
            } else {
                circleImageView.setImageResource(R.drawable.person);
            }
            nameTV.setText(worker.getName());
            surnameTV.setText(worker.getSurname());
        }

        Button editBTN = view.findViewById(R.id.editBTN);
        editBTN.setTag(position);

        return view;
    }
}
