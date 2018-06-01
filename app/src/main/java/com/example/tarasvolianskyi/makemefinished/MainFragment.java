package com.example.tarasvolianskyi.makemefinished;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    public static final String USER_NAME = "userName";
    public static final String USER_ID = "userId";
    private View view;
    private EditText editTextTask;
    private Button btnAddTask;
    private Spinner spinnerCategoryOfTasks;


    DatabaseReference databaseReferenceUsers;
    ListView listViewTasks;
    List<TasksPojo> tasksPojoList;

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initView();
        return view;
    }

    private void initView() {
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("tasks");
        editTextTask = (EditText) view.findViewById(R.id.et_text_for_entering_main_fragment);
        btnAddTask = (Button) view.findViewById(R.id.btn_add_task_main_fragment);
        spinnerCategoryOfTasks = (Spinner) view.findViewById(R.id.spinner_categories_main_fragment);
        tasksPojoList = new ArrayList<>();
        listViewTasks = (ListView) view.findViewById(R.id.lv_data_of_tasks_from_firebase_main_fragment);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tasksPojoList.clear();
                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    TasksPojo tasksPojo = taskSnapshot.getValue(TasksPojo.class);
                    tasksPojoList.add(tasksPojo);
                }
                TasksList adapterForTask = new TasksList(getActivity(), tasksPojoList);
                listViewTasks.setAdapter(adapterForTask);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    /*   listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UsersPojo usersPojo = usersPojoList.get(position);
                Intent intent = new Intent(getApplicationContext(), AddOptionActivity.class);
                intent.putExtra(USER_ID, usersPojo.getUserId());
                intent.putExtra(USER_NAME, usersPojo.getUserName());
                startActivity(intent);
            }
        });*/

        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TasksPojo tasksPojo = tasksPojoList.get(position);
               showUpdateDialog(tasksPojo.getUserId(), tasksPojo.getUserName());
                Toast.makeText(getContext(), "showUpdateDialog", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }


    private void showUpdateDialog(final String userId, String userName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_task_dialog, null);
        dialogBuilder.setView(dialogView);
        //final TextView textViewName = (TextView) dialogView.findViewById(R.id.tv_name_of_user_update_dialog);
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.et_new_name_of_task_update_dialog);
        final Button btnUpdate = (Button) dialogView.findViewById(R.id.btn_click_for_update_task_update_dialog);
        final Button btnDelete = (Button) dialogView.findViewById(R.id.btn_click_for_delete_task_update_dialog);
        final Spinner spinnerCategoriesDialUptd = (Spinner) dialogView.findViewById(R.id.spinner_categories_of_task_update_dialog);
        dialogBuilder.setTitle("Updating of user" + userName);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String category = spinnerCategoriesDialUptd.getSelectedItem().toString();
                if (TextUtils.isEmpty(name)) {
                    editTextName.setError("Name required");
                    return;
                }
                updateUser(userId, name, category);
                alertDialog.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(userId);
            }
        });

    }

    private void deleteUser(String userId) {
        DatabaseReference dbrefTask = FirebaseDatabase.getInstance().getReference("tasks").child(userId);
        DatabaseReference dbrefOptions = FirebaseDatabase.getInstance().getReference("options").child("option_" + userId);
        dbrefTask.removeValue();
        dbrefOptions.removeValue();
        Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
    }

    private boolean updateUser(String id, String name, String category) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tasks").child(id);
        TasksPojo tasksPojo = new TasksPojo(id, name, category);
        databaseReference.setValue(tasksPojo);
        Toast.makeText(getContext(), "User update successfully", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void addTask() {
        String name = editTextTask.getText().toString().trim();
        String category = spinnerCategoryOfTasks.getSelectedItem().toString();

        if (!TextUtils.isEmpty(name)) {
            String id = databaseReferenceUsers.push().getKey();
            TasksPojo tasksPojo = new TasksPojo(id, name, category);
            databaseReferenceUsers.child(id).setValue(tasksPojo);
        } else {
            Toast.makeText(getContext(), "Write the name", Toast.LENGTH_SHORT).show();
        }

    }
}

    /*
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

    @Override
    public void onStart() {
        super.onStart();
        btnAddNewTask = (Button) view.findViewById(R.id.btn_add_new_task_fragment_main);
    }

    private void initView() {
        lvTasks = (ListView) view.findViewById(R.id.lv_tasks_fragment_main);
        etForNewTask = (EditText) view.findViewById(R.id.et_add_new_task_fragment_main);
       // btnAddNewTask = (Button) view.findViewById(R.id.btn_add_new_task_fragment_main);
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
}*/
