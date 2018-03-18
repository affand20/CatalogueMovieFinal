package com.belajar.trydev.cataloguemovie;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by user on 3/14/2018.
 */

public class FavoriteFilmService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavoriteFilmViewsFactory(this.getApplicationContext(), intent);
    }
}
