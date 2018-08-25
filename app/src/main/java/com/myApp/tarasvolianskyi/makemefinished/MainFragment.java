package com.myApp.tarasvolianskyi.makemefinished;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements View.OnClickListener {

    public static final String USER_NAME = "userName";
    public static final String USER_ID = "userId";
    private View view;
    private EditText editTextTask;
    private Button btnAddTask;
    private Button btnLogin;
    private TextView tvToken;
    private Button btnLogout;
    private Spinner spinnerCategoryOfTasks;
    private String uid;

    DatabaseReference databaseReferenceUsers;
    ListView listViewTasks;
    List<TasksPojo> tasksPojoList;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
        //AdView adView = new AdView(getContext());
        //AdView adView = (AdView)view.findViewById(R.id.adView);
        //adView.setAdSize(AdSize.BANNER);
        //adView.setAdUnitId("ca-app-pub-3623739700338204/6622720117");
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        //adView.loadAd(adRequest);
        //MobileAds.initialize(getContext(),               "ca-app-pub-3623739700338204~3749144762");
        //MobileAds.initialize(getContext(), "ca-app-pub-3940256099942544/6300978111");
        //AdView mAdView = view.findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);

        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        return view;
    }

    private void initView() {
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("tasks")/*.child(uid)*/;
        editTextTask = (EditText) view.findViewById(R.id.et_text_for_entering_main_fragment);
        btnAddTask = (Button) view.findViewById(R.id.btn_add_task_main_fragment);
        btnLogin = (Button) view.findViewById(R.id.btn_login_main_fragment);
        tvToken = (TextView) view.findViewById(R.id.tv_token);
        btnLogout = (Button) view.findViewById(R.id.btn_logout_main_fragment);
        spinnerCategoryOfTasks = (Spinner) view.findViewById(R.id.spinner_categories_main_fragment);
        tasksPojoList = new ArrayList<>();
        listViewTasks = (ListView) view.findViewById(R.id.lv_data_of_tasks_from_firebase_main_fragment);
        btnLogin.setOnClickListener(this);
        btnAddTask.setOnClickListener(this);
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


        if (user != null) {
            // User is signed in
            uid = user.getUid();
            tvToken.setText(uid);
        } else {
            // No user is signed in
            uid = "Please login";
            tvToken.setText(uid);
        }


        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TasksPojo tasksPojo = tasksPojoList.get(position);
                showUpdateDialog(tasksPojo.getUidAuth(), tasksPojo.getUserIdGenereted(), tasksPojo.getUserName());
                Toast.makeText(getContext(), "showUpdateDialog", Toast.LENGTH_SHORT).show();
                return false;
            }
        });



    }


    private void showUpdateDialog(final String uid, final String userId, String userName) {
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
                updateUser(uid, userId, name, category);
                alertDialog.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(uid, userId);
            }
        });

    }

    private void deleteUser(String uid, String userId) {
        DatabaseReference dbrefTask = FirebaseDatabase.getInstance().getReference("tasks").child(uid).child(userId);
        DatabaseReference dbrefOptions = FirebaseDatabase.getInstance().getReference("options").child("option_" + userId);
        dbrefTask.removeValue();
        dbrefOptions.removeValue();
        Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
    }

    private boolean updateUser(String uid, String id, String name, String category) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tasks").child(uid).child(id);
        TasksPojo tasksPojo = new TasksPojo(uid, id, name, category);
        databaseReference.setValue(tasksPojo);
        Toast.makeText(getContext(), "User update successfully", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void addTask() {
        String name = editTextTask.getText().toString().trim();
        String category = spinnerCategoryOfTasks.getSelectedItem().toString();

        if (!TextUtils.isEmpty(name)) {

            String uid = user.getUid();
            String id = databaseReferenceUsers.push().getKey();
            TasksPojo tasksPojo = new TasksPojo(uid, id, name, category);
            databaseReferenceUsers.child(uid).child(id).setValue(tasksPojo);
        } else {
            Toast.makeText(getContext(), "Write the name", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_task_main_fragment:
                Toast.makeText(getContext(), "Click button add task", Toast.LENGTH_SHORT).show();
                addTask();
                break;
            case R.id.btn_login_main_fragment:
                Toast.makeText(getContext(), "Click button login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), GoogleSignInActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_logout_main_fragment:
                Toast.makeText(getContext(), "Click button login", Toast.LENGTH_SHORT).show();
                GoogleSignInActivity googleSignInActivity = new GoogleSignInActivity();
                googleSignInActivity.signOut();
                break;
        }
    }
}


