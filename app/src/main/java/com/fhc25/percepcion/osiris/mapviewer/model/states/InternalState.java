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

import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;
import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingGroup;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;
import com.fhc25.percepcion.osiris.mapviewer.model.states.api.IInternalState;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import biz.source.code.Base64Coder;

public class InternalState implements Serializable, IInternalState {

    private static final String TAG = InternalState.class.getName();
    private static final long serialVersionUID = 1L;

    private static final String APP_ID_STR = "appID";
    private static final String BUILDING_GROUP_STR = "buildingGroup";
    private static final String METADATA_STR = "metadata";

    private String appID;
    private InternalStatePersistent statePersitent;

    /**
     * Default constructor
     */
    public InternalState() {
        statePersitent = new InternalStatePersistent();
    }

    /**
     * Gets the persistent state
     *
     * @return
     */
    public InternalStatePersistent getStatePersitent() {
        return statePersitent;
    }

    /**
     * Sets the persistent state
     *
     * @param statePersitent
     */
    public void setStatePersitent(InternalStatePersistent statePersitent) {
        this.statePersitent = statePersitent;
    }

    /**
     * Gets the application ID
     *
     * @return name
     */
    public String getAppID() {
        return appID;
    }

    /**
     * Sets the application ID
     *
     * @param appID
     */
    public void setAppID(String appID) {
        this.appID = appID;
    }

    /**
     * Gets the building group related to the application
     *
     * @return building group
     */
    public BuildingGroup getBuildingGroup() {
        return statePersitent.getBuildingGroup();
    }

    /**
     * Sets the building group associated with this application
     *
     * @param buildingGroup
     */
    public void setBuildingGroup(BuildingGroup buildingGroup) {
        statePersitent.setBuildingGroup(buildingGroup);
    }

    /**
     * Gets the metadata related to the application
     *
     * @return metadata
     */
    @Override
    public MetaData getMetadata() {
        return statePersitent.getMetaData();
    }

    /**
     * Sets the metadata information associated with this application
     *
     * @param metadata
     */
    @Override
    public void setMetadata(MetaData metadata) {
        statePersitent.setMetaData(metadata);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(statePersitent);
    }

    /**
     * Creates a map with the serialized objects inside this class
     *
     * @return
     */
    public Map<String, String> getObjects() {

        Map<String, String> map = new HashMap<String, String>();

        map.put(APP_ID_STR, getSerializedObject(appID));
        map.put(BUILDING_GROUP_STR, getSerializedObject(statePersitent.getBuildingGroup()));
        map.put(METADATA_STR, getSerializedObject(statePersitent.getMetaData()));

        return map;
    }

    private String getSerializedObject(Object obj) {

        String sobj = null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(obj);

            sobj = new String(Base64Coder.encode(baos.toByteArray()));

        } catch (IOException e) {
            Lgr.e(TAG, e);
            e.printStackTrace();
        }

        return sobj;
    }

    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
        statePersitent = (InternalStatePersistent) ois.readObject();
    }

    public void copy(InternalState state) {

        appID = state.getAppID();
        statePersitent.copy(state.getStatePersitent());
    }

    @Override
    public String toString() {

        return "appID: " + appID + "," + statePersitent.toString();
    }
}
