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

import com.fhc25.percepcion.osiris.mapviewer.model.indoor.BuildingGroup;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MetaData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Indoor internal state that rarely changes, only during the application's startup
 */
public class InternalStatePersistent implements Serializable {

    private MetaData metaData;
    private BuildingGroup buildingGroup;


    public InternalStatePersistent() {
        metaData = new MetaData();
        buildingGroup = new BuildingGroup();
    }

    /**
     * Copy a persistent state into this
     *
     * @param statePersistent
     */
    public void copy(InternalStatePersistent statePersistent) {
        setBuildingGroup(statePersistent.getBuildingGroup());
        setMetaData(statePersistent.getMetaData());
    }

    /**
     * Gets the metadata info
     *
     * @return
     */
    public MetaData getMetaData() {
        return metaData;
    }

    /**
     * Sets the metadata info
     *
     * @param metaData
     */
    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    /**
     * Gets the building group
     *
     * @return
     */
    public BuildingGroup getBuildingGroup() {
        return buildingGroup;
    }

    /**
     * Sets the building group
     *
     * @param buildingGroup
     */
    public void setBuildingGroup(BuildingGroup buildingGroup) {
        this.buildingGroup = buildingGroup;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(buildingGroup);
        oos.writeObject(metaData);
    }

    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
        buildingGroup = (BuildingGroup) ois.readObject();
        metaData = (MetaData) ois.readObject();
    }

    @Override
    public String toString() {

        String out = "";
        if (metaData != null) {
            out += " Metadata: " + metaData.toString();
        } else {
            out += " Metadata: null";
        }

        if (buildingGroup != null) {
            out += " BuildingGroup: " + buildingGroup.toString();
        } else {
            out += " BuildingGroup: null";
        }
        return out;
    }
}
