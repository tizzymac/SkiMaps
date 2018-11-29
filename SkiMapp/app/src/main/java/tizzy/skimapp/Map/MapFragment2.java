package tizzy.skimapp.Map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mousebird.maply.AttrDictionary;
import com.mousebird.maply.GlobeMapFragment;
import com.mousebird.maply.MBTiles;
import com.mousebird.maply.MBTilesImageSource;
import com.mousebird.maply.MapController;
import com.mousebird.maply.MaplyBaseController;
import com.mousebird.maply.MarkerInfo;
import com.mousebird.maply.Point2d;
import com.mousebird.maply.QuadImageTileLayer;
import com.mousebird.maply.ScreenMarker;
import com.mousebird.maply.SelectedObject;
import com.mousebird.maply.SphericalMercatorCoordSystem;
import com.mousebird.maply.VectorObject;

import org.osmdroid.config.Configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import tizzy.skimapp.R;

public class MapFragment2 extends GlobeMapFragment {

    private static String MBTILES_DIR = "mbtiles";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle inState) {
        super.onCreateView(inflater, container, inState);

        Context ctx = getActivity().getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        return baseControl.getContentView();
    }

    @Override
    protected MapDisplayType chooseDisplayType() {
        return MapDisplayType.Map;
    }

    @Override
    protected void controlHasStarted() {
        Activity activity = getActivity();
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                        activity,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
            );
        }

        // MBT tile source
        File mbtilesFile = null;
        try {
            mbtilesFile = getMbTileFile("france_pistes_only.mbt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!mbtilesFile.exists()) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Missing MBTiles")
                    .setMessage("Could not find MBTiles file.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }}).show();
        }
        MBTiles mbtiles = new MBTiles(mbtilesFile);
        MBTilesImageSource localTileSource = new MBTilesImageSource(mbtiles);

        SphericalMercatorCoordSystem coordSystem = new SphericalMercatorCoordSystem();

        QuadImageTileLayer baseLayer = new QuadImageTileLayer(mapControl, coordSystem, localTileSource);
        baseLayer.setImageDepth(1);
        baseLayer.setSingleLevelLoading(false);
        baseLayer.setUseTargetZoomLevel(false);
        baseLayer.setCoverPoles(true);
        baseLayer.setHandleEdges(true);

        // add layer and position
        mapControl.addLayer(baseLayer);
        mapControl.animatePositionGeo( 0.121, 0.802, 0.01, 1.0); // radians
        mapControl.setAllowRotateGesture(false);
        mapControl.gestureDelegate = this;

        insertMarker();
    }

    private File getMbTileFile(String mbTileFilename) throws IOException {

        ContextWrapper wrapper = new ContextWrapper(getActivity());
        File mbTilesDirectory =  wrapper.getDir(MBTILES_DIR, Context.MODE_PRIVATE);

        InputStream is = getActivity().getAssets().open("france_pistes_only.mbt");
        File of = new File(mbTilesDirectory, mbTileFilename);

        if (of.exists()) {
            return of;
        }

        OutputStream os = new FileOutputStream(of);
        byte[] mBuffer = new byte[1024];
        int length;
        while ((length = is.read(mBuffer))>0) {
            os.write(mBuffer, 0, length);
        }
        os.flush();
        os.close();
        is.close();

        return of;

    }

    private void insertMarker() {
        MarkerInfo markerInfo = new MarkerInfo();
        Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.map_point_round);
        Point2d markerSize = new Point2d(116, 116);

        ScreenMarker chamonix = new ScreenMarker();
        chamonix.loc = Point2d.FromDegrees(6.942812, 45.968239); // Longitude, Latitude
        chamonix.image = icon;
        chamonix.size = markerSize;

        mapControl.addScreenMarker(chamonix, markerInfo, MaplyBaseController.ThreadMode.ThreadAny);
    }

    @Override
    public void userDidSelect(MapController mapControl, SelectedObject[] selectedObjects, Point2d loc, Point2d screenLoc) {

        String msg = "Selected feature count: " + selectedObjects.length;
        for (SelectedObject obj : selectedObjects) {
            // GeoJSON
            if (obj.selObj instanceof VectorObject) {
                VectorObject vectorObject = (VectorObject) obj.selObj;
                AttrDictionary attributes = vectorObject.getAttributes();
                String adminName = attributes.getString("ADMIN");
                msg += "\nVector Object: " + adminName;
            }
        }

        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
