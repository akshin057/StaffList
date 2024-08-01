package com.example.stafflist.utils;

import static android.system.Os.remove;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.stafflist.R;
import com.example.stafflist.models.Worker;
import com.example.stafflist.ui.edit.EditActivity;
import com.example.stafflist.ui.main.MainActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAlertDialog extends DialogFragment {

    private Removable remove = null;

    private Updatable update = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        remove = (Removable) context;
        update = (Updatable) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Worker worker = (Worker) getArguments().getSerializable("worker");
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());
        dialog.setTitle("Данные о работнике");

        LayoutInflater inflater = getLayoutInflater();
        View registerWindow = inflater.inflate(R.layout.details_activity, null);
        dialog.setView(registerWindow);

        CircleImageView personImageCIV = registerWindow.findViewById(R.id.personImageCIV);
        TextView nameTV = registerWindow.findViewById(R.id.nameTV);
        TextView surnameTV = registerWindow.findViewById(R.id.surnameTV);
        TextView ageTV = registerWindow.findViewById(R.id.ageTV);
        TextView postTV = registerWindow.findViewById(R.id.postTV);

        if (worker != null) {
            if (worker.getImage() != null) {
                personImageCIV.setImageURI(Uri.parse(worker.getImage()));
            } else {
                personImageCIV.setImageResource(R.drawable.person);
            }
            nameTV.setText(worker.getName());
            surnameTV.setText(worker.getSurname());
            ageTV.setText(worker.getAge());
            postTV.setText(worker.getPost());
        }

        Button closeBTN = registerWindow.findViewById(R.id.closeBTN);
        closeBTN.setOnClickListener(v -> {
            dismiss();
        });

        Button editBTN = registerWindow.findViewById(R.id.editBTN);
        editBTN.setOnClickListener(v -> {
            if (worker instanceof Worker && update != null) {
                update.update(worker);
            }
        });

        Button deleteBTN = registerWindow.findViewById(R.id.deleteBTN);
        deleteBTN.setOnClickListener(v -> {
            if (worker instanceof Worker && remove != null) {
                remove.remove(worker);
            }
            dismiss();
        });

        return dialog.create();
    }
}
