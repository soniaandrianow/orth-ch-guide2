package com.example.sofia.orth_ch_guide;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Sofia on 13.09.2016.
 */
public class SwipeViewsPagerForDioceses extends FragmentStatePagerAdapter {

    int tabCount;
    ArrayList<Church> selected = new ArrayList<>();
    Helper helper;
    String[] tabTitle = new String[]{
            "Wrocławsko-Szczecińska",
            "Białostocko-Gdańska",
            "Lubelsko-Chełmska",
            "Warszawsko-Bielska",
            "Łódzko-Poznańska",
            "Przemysko-Gorlicka",
            "Ordynariat Wojska Polskiego"
    };

    public SwipeViewsPagerForDioceses(FragmentManager fragmentManager, int tabCount, Context context) {
        super(fragmentManager);
        this.tabCount = tabCount;
        helper = new Helper();
    }

    @Override
    public Fragment getItem(int position) {
        String input = "";
        Bundle extras1;
        Diocese diocese;
        //System.out.println("Wybrany indeks: "+position);

        switch (position) {
            case 0:
                input = "Wrocławsko-Szczecińska";
                ArrayList<Church>list = new Helper().selectByDiocese(input);
                return Diocese.newInstance(list);
                //break;
            case 1:
                input = "Białostocko-Gdańska";
                ArrayList<Church>list1 = new Helper().selectByDiocese(input);
                return Diocese.newInstance(list1);
            case 2:
                input = "Lubelsko-Chełmska";
                ArrayList<Church>list2 = new Helper().selectByDiocese(input);
                return Diocese.newInstance(list2);
            case 3:
                input = "Warszawsko-Bielska";
                ArrayList<Church>list3 = new Helper().selectByDiocese(input);
                return Diocese.newInstance(list3);
            case 4:
                input = "Łódzko-Poznańska";
                ArrayList<Church>list4 = new Helper().selectByDiocese(input);
                return Diocese.newInstance(list4);
            case 5:
                input = "Przemysko-Gorlicka";
                ArrayList<Church>list5 = new Helper().selectByDiocese(input);
                return Diocese.newInstance(list5);
            case 6:
                input = "Ordynariat Wojska Polskiego";
                ArrayList<Church>list6 = new Helper().selectByDiocese(input);
                return Diocese.newInstance(list6);
            default:
                return null;
        }
        /*selected.clear();
        //System.out.println("Wybrany input: "+input);
        selected = helper.selectByDiocese(input);
        extras1 = new Bundle();
        extras1.putParcelableArrayList("selected", selected);
        diocese = new Diocese();
        diocese.setArguments(extras1);
        return diocese;*/
    }


    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }

}
