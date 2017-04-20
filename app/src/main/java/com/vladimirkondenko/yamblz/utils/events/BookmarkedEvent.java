package com.vladimirkondenko.yamblz.utils.events;

public class BookmarkedEvent {

    private int id;

    private boolean isBookmarked;

    public BookmarkedEvent(int id, boolean isBookmarked) {
        this.id = id;
        this.isBookmarked = isBookmarked;
    }

    public boolean isTranslationBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public int getTranslationId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
