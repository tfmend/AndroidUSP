package br.usp.ime.mapa;

import com.google.android.maps.MapActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public abstract class ActionBarActivity extends MapActivity {
    final ActionBarHelper mActionBarHelper = ActionBarHelper.createInstance(this);

    protected ActionBarHelper getActionBarHelper() {
        return mActionBarHelper;
    }

    @Override
    public MenuInflater getMenuInflater() {
        return mActionBarHelper.getMenuInflater(super.getMenuInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBarHelper.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarHelper.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean retValue = false;
        retValue |= mActionBarHelper.onCreateOptionsMenu(menu);
        retValue |= super.onCreateOptionsMenu(menu);
        return retValue;
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        mActionBarHelper.onTitleChanged(title, color);
        super.onTitleChanged(title, color);
    }
}
