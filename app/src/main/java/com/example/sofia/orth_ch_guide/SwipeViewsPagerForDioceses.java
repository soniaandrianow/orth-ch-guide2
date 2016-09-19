package com.example.sofia.orth_ch_guide;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Sofia on 13.09.2016.
 */
public class SwipeViewsPagerForDioceses extends FragmentStatePagerAdapter {

    int tabCount;

    public SwipeViewsPagerForDioceses(FragmentManager fragmentManager, int tabCount) {
        super(fragmentManager);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                WroclawskoSzczecinska wr_sz = new WroclawskoSzczecinska();
                return wr_sz;
            case 1:
                BialostockoGdanska bi_gd = new BialostockoGdanska();
                return bi_gd;
            case 2:
                LubelskoChelmska lu_ch = new LubelskoChelmska();
                return lu_ch;
            case 3:
                WroclawskoSzczecinska wr_szcz = new WroclawskoSzczecinska();
                return wr_szcz;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
