package com.example.stafflist.ui.edit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stafflist.R;
import com.example.stafflist.databinding.ActivityEditBinding;
import com.example.stafflist.models.Worker;
import com.example.stafflist.ui.main.MainActivity;
import com.example.stafflist.viewModels.WorkerViewModel;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EditActivity extends AppCompatActivity {

    ActivityEditBinding binding;
    private WorkerViewModel workerViewModel = WorkerViewModel.getInstance();
    private static final int GALLERY_REQUEST_CODE = 302;
    private Integer position;
    private String item;
    private Uri imageURI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initElements();

        Worker worker = (Worker) Objects.requireNonNull(getIntent().getExtras()).getSerializable("worker");
        position = getIntent().getExtras().getInt("position");

        if (worker != null) {
            settingCurrent(worker);
        }

        binding.editCIV.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_REQUEST_CODE);
        });

        binding.editBTN.setOnClickListener(v -> {
            Worker worker1 = new Worker(
                    (imageURI != null) ? String.valueOf(imageURI) : worker.getImage(),
                    binding.nameET.getText().toString(),
                    binding.surnameET.getText().toString(),
                    binding.ageET.getText().toString(),
                    item
            );

            workerViewModel.updateWorkerLiveDataList(worker1, position);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        binding.cancelBTN.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void initElements() {
        List<String> postList = Arrays.asList("Разработчик", "Дизайнер", "Маркетолог", "Бухгалтер", "Менеджер проекта",
                "Директор", "Заместитель директора", "Облсуживающий персонал");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, postList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.postCTV.setAdapter(adapter);
        binding.postCTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void settingCurrent(Worker worker) {
        if (worker.getImage() == null){
            binding.editCIV.setImageResource(R.drawable.person);
        } else {
            binding.editCIV.setImageURI(Uri.parse(worker.getImage()));
        }
        binding.nameET.setText(worker.getName());
        binding.surnameET.setText(worker.getSurname());
        binding.ageET.setText(worker.getAge());
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) binding.postCTV.getAdapter();
        int position = adapter.getPosition(worker.getPost());
        binding.postCTV.setSelection(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            imageURI = data.getData();
            binding.editCIV.setImageURI(imageURI);
        }
    }


}