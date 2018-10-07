package chat.dropdevelopers.com.moviebuff.model;

public class FileModel {
    String name;
    String size;
    String date;
    String type;
    String filePath;

    public FileModel(String name, String size, String date, String type, String filePath) {
        this.name = name;
        this.size = size;
        this.date = date;
        this.type = type;
        this.filePath = filePath;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
