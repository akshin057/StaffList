package com.example.stafflist.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.stafflist.models.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorkerViewModel extends ViewModel {

    private static WorkerViewModel workerViewModel;
    private MutableLiveData<List<Worker>> workerLiveDataList = new MutableLiveData<>(new ArrayList<>());

    private WorkerViewModel() {

    }

    public static WorkerViewModel getInstance() {
        if (workerViewModel == null) {
            workerViewModel = new WorkerViewModel();
        }

        return workerViewModel;
    }

    public MutableLiveData<List<Worker>> getWorkerLiveDataList() {
        return workerLiveDataList;
    }


    public void addWorkerLiveDataList(Worker worker) {
        List<Worker> list = workerLiveDataList.getValue();

        if (list != null) {
            list.add(worker);

            workerLiveDataList.setValue(list);
        }
    }

    public void removeWorkerLiveDataList(Worker worker) {

        List<Worker> list = workerLiveDataList.getValue();

        if (list != null) {
            list.remove(worker);
            workerLiveDataList.setValue(list);
        }

    }

    public void updateWorkerLiveDataList(Worker worker, Integer index) {
        if (worker != null && index != null) {
            Objects.requireNonNull(workerLiveDataList.getValue()).add(index + 1, worker);
            workerLiveDataList.getValue().remove(index.intValue());
        }

        workerLiveDataList.setValue(workerLiveDataList.getValue());

    }


}
