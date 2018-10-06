package chat.dropdevelopers.com.moviebuff.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import chat.dropdevelopers.com.moviebuff.main_activity.Defalt;
import chat.dropdevelopers.com.moviebuff.main_activity.Recent;
import chat.dropdevelopers.com.moviebuff.main_activity.Trending;

public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                Defalt tab1 = new Defalt();
                return tab1;
            case 1:
                Trending tab2 = new Trending();
                return tab2;
            case 2:
                Recent tab3 = new Recent();
                return tab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}