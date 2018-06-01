package com.example.tarasvolianskyi.makemefinished;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private View view;
    private ListView lvTasks;
    private EditText etForNewTask;
    private Button btnAddNewTask;
    private TextView tvText;
    private List<String> arrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initView();
        return view;
    }

    private void initView() {
        lvTasks = (ListView) view.findViewById(R.id.lv_tasks_fragment_main);
        etForNewTask = (EditText) view.findViewById(R.id.et_add_new_task_fragment_main);
        btnAddNewTask = (Button) view.findViewById(R.id.btn_add_new_task_fragment_main);
        tvText = (TextView) view.findViewById(R.id.tvTestTekst);
        arrayList = new ArrayList<>();

        btnAddNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvText.setText(etForNewTask.getText().toString());
                //  arrayList.add(etForNewTask.getText().toString());
                lvMethod(etForNewTask.getText().toString());
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                arrayList);

        lvTasks.setAdapter(arrayAdapter);


    }

    private void lvMethod(String task) {

        arrayList.add(task);
        arrayList.add("stepan");



    }
}
