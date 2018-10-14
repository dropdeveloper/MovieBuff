package chat.dropdevelopers.com.moviebuff.model;

public class StatusModel {

    String title;
    String name;
    String userId;
    String video_url;
    int seen;
    String statusId;
    int like;
    int unLike;

    public StatusModel(String title, String name, String userId, String video_url, int seen, String statusId, int like, int unLike) {
        this.title = title;
        this.name = name;
        this.userId = userId;
        this.video_url = video_url;
        this.seen = seen;
        this.statusId = statusId;
        this.like = like;
        this.unLike = unLike;
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
}
