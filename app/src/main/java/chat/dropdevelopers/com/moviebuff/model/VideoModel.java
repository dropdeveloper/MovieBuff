package chat.dropdevelopers.com.moviebuff.model;

public class VideoModel {

    String name;
    String disc;
    String image_tem_url;
    String video_url;
    int like;
    int dislike;
    String timeStamp;
    int viewer;

    public VideoModel(String name, String disc, String image_tem_url, String video_url, int like, int dislike, String timeStamp, int viewer) {
        this.name = name;
        this.disc = disc;
        this.image_tem_url = image_tem_url;
        this.video_url = video_url;
        this.like = like;
        this.dislike = dislike;
        this.timeStamp = timeStamp;
        this.viewer = viewer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getImage_tem_url() {
        return image_tem_url;
    }

    public void setImage_tem_url(String image_tem_url) {
        this.image_tem_url = image_tem_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getViewer() {
        return viewer;
    }

    public void setViewer(int viewer) {
        this.viewer = viewer;
    }
}
