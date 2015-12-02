/**
Copyright 2015 Osiris Project Team

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/   

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
