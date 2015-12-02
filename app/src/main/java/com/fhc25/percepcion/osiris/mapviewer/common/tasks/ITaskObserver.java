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

import java.util.ArrayList;

public interface ITaskObserver {

    void onTaskFinishSuccess();

    void onTaskFinishFailure();

    class Collection extends ArrayList<ITaskObserver> implements ITaskObserver {

        @Override
        public void onTaskFinishSuccess() {

            for (ITaskObserver observer : this) {
                observer.onTaskFinishSuccess();
            }
        }

        @Override
        public void onTaskFinishFailure() {

            for (ITaskObserver observer : this) {
                observer.onTaskFinishFailure();
            }
        }
    }

}
