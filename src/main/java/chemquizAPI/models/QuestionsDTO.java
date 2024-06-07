package chemquizAPI.models;

import java.util.List;

public class QuestionsDTO {
    private List<StructureQ> structureQs;
    private List<ReactionQ> reactionQs;

    public QuestionsDTO(List<StructureQ> structureQs, List<ReactionQ> reactionQs) {
        this.structureQs = structureQs;
        this.reactionQs = reactionQs;
    }

    public List<StructureQ> getStructureQs() {
        return this.structureQs;
    }

    public List<ReactionQ> getReactionQs() {
        return this.reactionQs;
    }
}
