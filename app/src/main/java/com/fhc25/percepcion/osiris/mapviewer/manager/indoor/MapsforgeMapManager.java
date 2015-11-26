package com.fhc25.percepcion.osiris.mapviewer.manager.indoor;

import android.os.Environment;

import com.fhc25.percepcion.osiris.mapviewer.common.ICallback;
import com.fhc25.percepcion.osiris.mapviewer.common.ICancellableTask;
import com.fhc25.percepcion.osiris.mapviewer.common.errors.Failure;
import com.fhc25.percepcion.osiris.mapviewer.common.log.Lgr;
import com.fhc25.percepcion.osiris.mapviewer.data.indoor.api.IMapFileRepository;
import com.fhc25.percepcion.osiris.mapviewer.model.location.MapsforgeMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MapsforgeMapManager {

    private static final String TAG = MapsforgeMapManager.class.getName();

    private final IMapFileRepository mapFileRepository;

    public MapsforgeMapManager(IMapFileRepository mapFileRepository) {
        this.mapFileRepository = mapFileRepository;
    }

    public ICancellableTask importMap(String fileName, final ICallback<File> callback) {

        final File file = new File(Environment.getExternalStorageDirectory().getPath(), fileName);
        return importMap(file, callback);
    }

    public ICancellableTask importMap(final File file, final ICallback<File> callback) {

        return mapFileRepository.getMapFile(new ICallback<MapsforgeMap>() {
            @Override
            public void onFinish(Failure error, MapsforgeMap data) {
                saveMapOnFile(data, file, callback);
            }
        });
    }

    private void saveMapOnFile(final MapsforgeMap map, final File file, final ICallback<File> callback) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                InputStream in = map.getInputStream();
                OutputStream out;
                try {
                    out = new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    out.close();
                    in.close();

                } catch (final IOException e) {
                    Lgr.e(TAG, e);
                    callback.onFinish(new Failure(""), null);
                    return;
                }

                callback.onFinish(null, file);
            }
        });

        thread.start();
    }
}
