package com.fhc25.percepcion.osiris.mapviewer.common;

import com.fhc25.percepcion.osiris.mapviewer.common.errors.Failure;

public interface ICallback<T> {

    void onFinish(Failure error, T data);
}
