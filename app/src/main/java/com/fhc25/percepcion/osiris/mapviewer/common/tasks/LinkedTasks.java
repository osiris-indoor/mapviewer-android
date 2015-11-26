package com.fhc25.percepcion.osiris.mapviewer.common.tasks;

import java.util.LinkedList;
import java.util.Queue;

public class LinkedTasks implements ITask {

    private Queue<Task> tasks = new LinkedList<>();

    public void runTasks() {

        if (!tasks.isEmpty()) {
            tasks.poll().runTask();
        }
    }

    public LinkedTasks then(final Task task) {
        tasks.add(task);

        task.addTaskObserver(new ITaskObserver() {
            @Override
            public void onTaskFinishSuccess() {

                if (!tasks.isEmpty()) {
                    tasks.poll().runTask();
                }
            }

            @Override
            public void onTaskFinishFailure() {
                task.removeTaskObserver(this);
            }
        });

        return this;
    }

    @Override
    public void runTask() {
        runTasks();
    }
}
