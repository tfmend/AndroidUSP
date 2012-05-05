package br.usp.ime.mapa;

import com.google.android.maps.MapActivity;

import android.content.Context;

public class ActionBarHelperICS extends ActionBarHelperHoneycomb {
    protected ActionBarHelperICS(MapActivity activity) {
        super(activity);
    }

    @Override
    protected Context getActionBarThemedContext() {
        return mActivity.getActionBar().getThemedContext();
    }
}
