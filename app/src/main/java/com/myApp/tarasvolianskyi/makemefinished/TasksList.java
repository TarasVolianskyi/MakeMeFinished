package com.myApp.tarasvolianskyi.makemefinished;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TasksList extends ArrayAdapter<TasksPojo> {

    private Activity context;
    private List<TasksPojo> tasksPojoList;

    public TasksList(Activity context, List<TasksPojo> tasksPojoList) {
        super(context, R.layout.view_of_one_item_task_for_list_view, tasksPojoList);
        this.context = context;
        this.tasksPojoList = tasksPojoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.view_of_one_item_task_for_list_view, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.tv_name_of_task_view_of_one_item);
        TextView textViewCategory = (TextView) listViewItem.findViewById(R.id.tv_category_of_task_view_of_one_item);

        TasksPojo tasksPojo = tasksPojoList.get(position);

        textViewName.setText(tasksPojo.getUserName());
        textViewCategory.setText(tasksPojo.getUserCategory());

        return listViewItem;

    }
}
