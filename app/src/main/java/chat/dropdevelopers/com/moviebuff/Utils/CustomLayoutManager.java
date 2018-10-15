package chat.dropdevelopers.com.moviebuff.Utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class CustomLayoutManager extends LinearLayoutManager {
    public CustomLayoutManager(Context context) {
        super(context);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int orientation = getOrientation();
        if (orientation == VERTICAL) {
            int scrolled = super.scrollVerticallyBy(dy, recycler, state);

            //Log.e("--->",String.valueOf(scrolled));

            return scrolled;
        } else {
            return 0;
        }
    }

}
