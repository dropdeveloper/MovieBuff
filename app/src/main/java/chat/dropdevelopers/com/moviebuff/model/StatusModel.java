package chat.dropdevelopers.com.moviebuff.model;

import com.google.android.exoplayer2.ExoPlayer;

public class StatusModel {

    String title;
    String name;
    String userId;
    String video_url;
    int seen;
    String statusId;
    int like;
    int unLike;
    String thump;
    Boolean playWhenReady = false;
    int currentWindow = 0;
    long playbackPosition = 0;
    ExoPlayer player;

    public StatusModel(String title, String name, String userId, String video_url, int seen, String statusId, int like, int unLike , String thump ) {
        this.title = title;
        this.name = name;
        this.userId = userId;
        this.video_url = video_url;
        this.seen = seen;
        this.statusId = statusId;
        this.like = like;
        this.unLike = unLike;
        this.thump = thump;
    }

    public String getThump() {
        return thump;
    }

    public void setThump(String thump) {
        this.thump = thump;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getSeen() {
        return seen;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getUnLike() {
        return unLike;
    }

    public void setUnLike(int unLike) {
        this.unLike = unLike;
    }

    public Boolean getPlayWhenReady() {
        return playWhenReady;
    }

    public void setPlayWhenReady(Boolean playWhenReady) {
        this.playWhenReady = playWhenReady;
    }

    public int getCurrentWindow() {
        return currentWindow;
    }

    public void setCurrentWindow(int currentWindow) {
        this.currentWindow = currentWindow;
    }

    public long getPlaybackPosition() {
        return playbackPosition;
    }

    public void setPlaybackPosition(long playbackPosition) {
        this.playbackPosition = playbackPosition;
    }

    public ExoPlayer getPlayer() {
        return player;
    }

    public void setPlayer(ExoPlayer player) {
        this.player = player;
    }
}
