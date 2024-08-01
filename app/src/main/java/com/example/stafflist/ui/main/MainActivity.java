package com.example.stafflist.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stafflist.R;
import com.example.stafflist.adapters.WorkerAdapter;
import com.example.stafflist.databinding.ActivityMainBinding;
import com.example.stafflist.models.Worker;
import com.example.stafflist.ui.edit.EditActivity;
import com.example.stafflist.utils.MyAlertDialog;
import com.example.stafflist.utils.Removable;
import com.example.stafflist.utils.Updatable;
import com.example.stafflist.viewModels.WorkerViewModel;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements Removable, Updatable {

    private ActivityMainBinding binding;
    private static final int GALLERY_REQUEST_CODE = 302;

    private WorkerViewModel workerViewModel;
    private Worker worker = null;

    private WorkerAdapter workerAdapter = null;

    private Uri imageURI = null;
    private String item;
    private Integer selectedItemPosition = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initElements();

        binding.personImageCIV.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_REQUEST_CODE);
        });

        binding.saveBTN.setOnClickListener(v -> {
            worker = new Worker(
                    imageURI != null ? imageURI.toString() : null,
                    binding.nameET.getText().toString(),
                    binding.surnameET.getText().toString(),
                    binding.ageET.getText().toString(),
                    item
            );
            workerViewModel.addWorkerLiveDataList(worker);
            workerAdapter.notifyDataSetChanged();
            clearFields();
        });

        binding.workersLV.setOnItemClickListener((parent, view, position, id) -> {
            Worker worker1 = workerAdapter.getItem(position);
            selectedItemPosition = position;

            MyAlertDialog dialog = new MyAlertDialog();
            Bundle args = new Bundle();
            args.putSerializable("worker", worker1);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "custom");
        });

    }

    private void initElements() {
        setSupportActionBar(binding.mainTB);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Персонал компании");
        getSupportActionBar().setSubtitle("Версия 1.0");
        getSupportActionBar().setLogo(R.drawable.logo);

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

    @Override
    protected void onResume() {
        super.onResume();
        workerViewModel = WorkerViewModel.getInstance();

        workerViewModel.getWorkerLiveDataList().observe(this, workers -> {
            workerAdapter = new WorkerAdapter(MainActivity.this, workers);
            binding.workersLV.setAdapter(workerAdapter);
            workerAdapter.notifyDataSetChanged();
        });
    }

    private void clearFields() {
        binding.nameET.getText().clear();
        binding.personImageCIV.setImageResource(R.drawable.person);
        binding.surnameET.getText().clear();
        binding.ageET.getText().clear();
        imageURI = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            imageURI = data.getData();
            binding.personImageCIV.setImageURI(imageURI);
        }
    }

    @Override
    public void remove(Worker worker) {
        workerViewModel.removeWorkerLiveDataList(worker);
    }

    @Override
    public void update(Worker worker) {
        Intent intent = new Intent(this, EditActivity.class);

        intent.putExtra("worker", worker);
        intent.putExtra("position", selectedItemPosition);

        startActivity(intent);
    }

    public void onEditClickListener(View view) {
        Integer position = (Integer) view.getTag();

        if (position != null) {
            Worker worker1 = workerAdapter.getItem(position);
            update(worker1);
        }
    }

}