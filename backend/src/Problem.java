
public class Problem {

    private int id;
    private int contestId;
    private String index;
    private String name;
    private int rating;
    private String file;


    public Problem() {
    }


    public Problem(
            int id,
            int contestId,
            String index,
            String name,
            int rating,
            String file
    ) {

        this.id = id;
        this.contestId = contestId;
        this.index = index;
        this.name = name;
        this.rating = rating;
        this.file = file;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public int getContestId() {
        return contestId;
    }


    public void setContestId(int contestId) {
        this.contestId = contestId;
    }


    public String getIndex() {
        return index;
    }


    public void setIndex(String index) {
        this.index = index;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getRating() {
        return rating;
    }


    public void setRating(int rating) {
        this.rating = rating;
    }


    public String getFile() {
        return file;
    }


    public void setFile(String file) {
        this.file = file;
    }
}
