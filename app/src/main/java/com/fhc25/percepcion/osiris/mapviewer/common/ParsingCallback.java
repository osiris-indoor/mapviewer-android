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
