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

package com.fhc25.percepcion.osiris.mapviewer.common;

import com.fhc25.percepcion.osiris.mapviewer.common.assemblers.Assembler;
import com.fhc25.percepcion.osiris.mapviewer.common.errors.Failure;
import com.fhc25.percepcion.osiris.mapviewer.common.json.JSONParser;

public class ParsingCallback<T, K> implements ICallback<String> {

    private JSONParser<T> parser;
    private Assembler<T, K> assembler;
    private ICallback<K> callback;

    public ParsingCallback(JSONParser<T> parser, Assembler<T, K> assembler, ICallback<K> callback) {
        this.parser = parser;
        this.assembler = assembler;
        this.callback = callback;
    }

    @Override
    public void onFinish(Failure error, String data) {
        if (error != null) {
            callback.onFinish(error, null);
        } else {
            T parsedObject = parser.parseObject(data);
            callback.onFinish(null, assembler.createDomainObject(parsedObject));
        }
    }
}
