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

package com.fhc25.percepcion.osiris.mapviewer.ui.overlays;

/**
 * Displayer interface for describing the functionality of displaying Visual
 * Elements. This interface should be specialized for each map library
 */
public interface IVisualElementDisplayer {

	/**
	 * Displays a visual element in the screen
	 * 
	 * @param visualElement
	 */
	void display(VisualElement visualElement);

	/**
	 * Removes a visual element from the screen
	 * 
	 * @param visualElement
	 */
	void remove(VisualElement visualElement);

	/**
	 * Redraws all the visual elements
	 */
	void update();

	/**
	 * Removes all the visual elements that are currently being displayed
	 */
	void clear();
	
}
