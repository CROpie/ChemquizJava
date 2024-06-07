package chemquizAPI.models;

public class ResultsDTO {
    private int score;
    private boolean[] results;

    public ResultsDTO(int score, boolean[] results) {
        this.score = score;
        this.results = results;
    }

    public int getScore() {
        return this.score;
    }

    public boolean[] getResults() {
        return this.results;
    }
}
