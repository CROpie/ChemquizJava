package chemquizAPI.models;

public class SubmitDTO {
    public String questionType;
    public int questionId;
    public String userAnswer;
    public String userAnswerSmiles;

    public SubmitDTO(String questionType, int questionId, String userAnswer, String userAnswerSmiles) {
        this.questionType = questionType;
        this.questionId = questionId;
        this.userAnswer = userAnswer;
        this.userAnswerSmiles = userAnswerSmiles;
    }

    public String getQuestionType() {
        return this.questionType;
    }

    public int getQuestionId() {
        return this.questionId;
    }

    public String getUserAnswer() {
        return this.userAnswer;
    }

    public String getUserAnswerSmiles() {
        return this.userAnswerSmiles;
    }
}
