package com.lhr.water.util.mapView;

/**
 * MapViewListener
 *
 * @author: onlylemi
 */
public interface MapViewListener {

    /**
     * when mapview load complete to callback
     */
    void onMapLoadSuccess();

    /**
     * when mapview load error to callback
     */
    void onMapLoadFail();
}
