package com.vladimirkondenko.yamblz.screens.history;

import com.vladimirkondenko.yamblz.R;

/**
 * Maps menu item ids to regular numbers to make sure presenters don't know about Android-specific ids.
 */
public final class TabCodes {
    public static final class History {
        public static final int SCREEN_ID = 0;
        public static final int TAB_ID = R.id.radiobutton_history_tab_history;
    }
    public static final class Bookmarks {
        public static final int SCREEN_ID = 1;
        public static final int TAB_ID = R.id.radiobutton_history_tab_bookmarks;
    }

    public static int tabToScreenId(int tabId) {
        switch (tabId) {
            case History.TAB_ID: return History.SCREEN_ID;
            case Bookmarks.TAB_ID: return Bookmarks.SCREEN_ID;
            default: return -1;
        }
    }
}
