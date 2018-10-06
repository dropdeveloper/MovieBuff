package chat.dropdevelopers.com.moviebuff.model;

public class FileModel {
    String name;
    String size;
    String date;
    String type;

    public FileModel(String name, String size, String date, String type) {
        this.name = name;
        this.size = size;
        this.date = date;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
