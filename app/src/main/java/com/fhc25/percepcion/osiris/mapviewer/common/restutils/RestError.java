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

package com.fhc25.percepcion.osiris.mapviewer.common.restutils;

import org.json.JSONException;
import org.json.JSONObject;

public class RestError {
	/** Status code */
	private int statusCode;
	/** Status reason */
	private String statusReason;
	/** Error subcode **/
	private int subcode;
	/** Response exception */
	private String exception;
	/** Error description*/
	private String description;
	/** Error message (for additional info) */
	private String message;

	public RestError(int statusCode, String statusReason, int subcode, String exception, String description, String message) {
		this.statusCode = statusCode;
		this.statusReason = statusReason;
		this.subcode = subcode;
		this.exception = exception;
		this.description = description;
		this.message = message;
	}

	public RestError(int statusCode, int subcode, String description) {
		this.statusCode = statusCode;
		this.statusReason = "";
		this.subcode = subcode;
		this.exception = "";
		this.description = description;
		this.message = "";
	}

	public RestError(String serverResponse) throws JSONException {
		JSONObject errorResponse = new JSONObject(serverResponse);
		if(errorResponse.has("status"))
			this.statusCode = errorResponse.getInt("status");
		else
			this.statusCode = -1;
		if(errorResponse.has("statusReason"))
			this.statusReason = errorResponse.getString("statusReason");
		else
			this.statusReason = "";
		if(errorResponse.has("code"))
			this.subcode = errorResponse.getInt("code");
		else
			this.subcode = -3;
		if(errorResponse.has("exception"))
			this.exception = errorResponse.getString("exception");
		else
			this.exception = "";
		if(errorResponse.has("description"))
			this.description = errorResponse.getString("description");
		else
			this.description = "Malformed response";
		if(errorResponse.has("message"))
			this.message = errorResponse.getString("message");
		else
			this.message = "";
			
	}
	/**
	 * Gets the status code
	 * @return the status code
	 */
	public int getStatusCode(){
		return statusCode;
	}
	/**
	 * Gets the status error reason message
	 * @return the status reason
	 */
	public String getStatusReason(){
		return statusReason;
	}
	/**
	 * Gets the error subcode
	 * @return the error subcode
	 */
	public int getSubcode(){
		return subcode;
	}
	/**
	 * Gets the exception type
	 * @return the exception type
	 */
	public String getException(){
		return exception;
	}
	/**
	 * Gets a readable error description
	 * @return the error description
	 */
	public String getDescription(){
		return description;
	}
	/**
	 * Gets the error message
	 * @return the error message
	 */
	public String getMessage(){
		return message;
	}

	@Override
	public String toString(){
		return "status: " + statusCode + "\n"
				+ "statusReason: " + statusReason + "\n"
				+ "code: " + subcode + "\n"
				+ "exception: " + exception + "\n"
				+ "description: " + description + "\n"
				+ "message: " + message;
	}
}
