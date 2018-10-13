package chat.dropdevelopers.com.moviebuff.model;

import chat.dropdevelopers.com.moviebuff.Utils.StringData;

public class StatusCateModel  {

    String category;
    String id;

    public StatusCateModel(String category, String id) {
        this.category = category;
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
