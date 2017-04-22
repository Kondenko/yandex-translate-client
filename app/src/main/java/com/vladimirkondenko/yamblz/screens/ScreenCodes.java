package com.vladimirkondenko.yamblz.screens;

import com.vladimirkondenko.yamblz.R;

/**
 * Maps menu item ids to regular numbers to make sure presenters don't know about Android-specific ids.
 */
public final class ScreenCodes {
    public static final class Translation {
        public static final int SCREEN_ID = 0;
        public static final int MENU_ID = R.id.item_bottomnav_translation;
    }
    public static final class History {
        public static final int SCREEN_ID = 1;
        public static final int MENU_ID = R.id.item_bottomnav_history;
    }
    public static final class Bookmarks {
        public static final int SCREEN_ID = 2;
        public static final int MENU_ID = R.id.item_bottomnav_bookmarks;
    }

    public static int menuItemToScreenId(int screenId) {
        switch (screenId) {
            case Translation.MENU_ID: return Translation.SCREEN_ID;
            case History.MENU_ID: return History.SCREEN_ID;
            case Bookmarks.MENU_ID: return Bookmarks.SCREEN_ID;
            default: return -1;
        }
    }
}
