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

package com.fhc25.percepcion.osiris.mapviewer.model.states;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.fhc25.percepcion.osiris.mapviewer.manager.IApplicationManagerProvider;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingGroup;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalState;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalStatePersistor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InternalStatePersistor implements IInternalStatePersistor {

    private static final String TAG = InternalStatePersistor.class.getName();

    private static final String SHARED_PREFERENCES_FILE_PERSISTENT = "com.fhc25.percepcion.osiris.model.states.persistentState";

    private static final String METADATA_ID = "metadata";
    private static final String BUILDINGGROUP_ID = "buildinggroup";

    private Context context;
    private IApplicationManagerProvider applicationManagerProvider;

    public InternalStatePersistor(Context context, IApplicationManagerProvider applicationManagerProvider) {
        this.context = context;
        this.applicationManagerProvider = applicationManagerProvider;
    }

    @Override
    public void persistInternalState(IInternalState internalState) {
        persistInternalStatePersistent(internalState);
        persistInternalStateVariable(internalState);
    }

    @Override
    public void persistInternalStatePersistent(IInternalState internalState) {

        SharedPreferences sharedPref  = context.getSharedPreferences(SHARED_PREFERENCES_FILE_PERSISTENT, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(METADATA_ID, compressObject(internalState.getMetadata()));
        editor.putString(BUILDINGGROUP_ID, compressObject(internalState.getBuildingGroup()));

        editor.commit();
    }

    @Override
    public void persistInternalStateVariable(IInternalState internalState) {

        // Nothing here
    }

    @Override
    public void loadInternalState(IInternalState internalState) {

        loadInternalStatePersistent(internalState);
        loadInternalStateVariable(internalState);
    }

    @Override
    public MetaData persistedDataExists() {

        SharedPreferences sharedPref  = context.getSharedPreferences(SHARED_PREFERENCES_FILE_PERSISTENT, Context.MODE_PRIVATE);

        if (sharedPref.contains(METADATA_ID)) {
            MetaData metaData = (MetaData) decompressObject(sharedPref.getString(METADATA_ID, ""));
            return metaData;
        }

        return null;
    }

    private void loadInternalStatePersistent(IInternalState internalState) {

        SharedPreferences sharedPref  = context.getSharedPreferences(SHARED_PREFERENCES_FILE_PERSISTENT, Context.MODE_PRIVATE);

        MetaData metaData = (MetaData) decompressObject(sharedPref.getString(METADATA_ID, ""));
        internalState.setMetadata(metaData);

        BuildingGroup buildingGroup = (BuildingGroup) decompressObject(sharedPref.getString(BUILDINGGROUP_ID, ""));
        internalState.setBuildingGroup(buildingGroup);
    }

    private void loadInternalStateVariable(IInternalState internalState) {

        // Nothing here
    }

    private String compressObject(Object object) {

        ByteArrayOutputStream baos = null;
        //GZIPOutputStream gzipOut = null;
        ObjectOutputStream out = null;

        String output = "";

        try {

            baos = new ByteArrayOutputStream();
            //gzipOut = new GZIPOutputStream(baos);
            out = new ObjectOutputStream(baos);

            out.writeObject(object);
            output = Base64.encodeToString(baos.toByteArray(),Base64.NO_WRAP);

            out.close();
            //gzipOut.close();
            baos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    private Object decompressObject(String input) {

        ObjectInputStream in = null;
        ByteArrayInputStream bais = null;
        //GZIPInputStream gzipIn = null;

        Object object = null;

        try {
            bais = new ByteArrayInputStream(Base64.decode(input, Base64.NO_WRAP));
            //gzipIn = new GZIPInputStream(bais);
            in = new ObjectInputStream(bais);

            object = in.readObject();

            in.close();
            //gzipIn.close();
            bais.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return object;
    }
}
