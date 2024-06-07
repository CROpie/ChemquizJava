package chemquizAPI.models;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "structure_qs")
public class StructureQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "structure_id")
    private int structureId;

    @Column(name = "molecule")
    private String molecule;

    @Column(name = "answer")
    private String answer;

    @Column(name = "incorrect_1")
    private String incorrect1;

    @Column(name = "incorrect_2")
    private String incorrect2;

    @Column(name = "incorrect_3")
    private String incorrect3;

    @Column(name = "is_difficult")
    private boolean isDifficult;

    StructureQ() {}

    public StructureQ(String molecule, String answer, String incorrect1, String incorrect2, String incorrect3, boolean isDifficult) {
        this.molecule = molecule;
        this.answer = answer;
        this.incorrect1 = incorrect1;
        this.incorrect2 = incorrect2;
        this.incorrect3 = incorrect3;
        this.isDifficult = isDifficult;
    }

    public int getStructureId() {
        return this.structureId;
    }

    public String getMolecule() {
        return this.molecule;
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getIncorrect1() {
        return this.incorrect1;
    }

    public String getIncorrect2() {
        return this.incorrect2;
    }

    public String getIncorrect3() {
        return this.incorrect3;
    }

    public boolean getIsDifficult() {
        return this.isDifficult;
    }

    public void setStructureId(int structureId) {
        this.structureId = structureId;
    }

    public void setMolecule(String molecule) {
        this.molecule = molecule;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setIncorrect1(String incorrect1) {
        this.incorrect1 = incorrect1;
    }

    public void setIncorrect2(String incorrect2) {
        this.incorrect2 = incorrect2;
    }

    public void setIncorrect3(String incorrect3) {
        this.incorrect3 = incorrect3;
    }

    public void setIsDifficult(boolean isDifficult) {
        this.isDifficult = isDifficult;
    }

    @Override
    public String toString() {
        return "{ " + this.structureId + " " + this.molecule + " " + this.answer + " " + this.incorrect1 + " " + this.incorrect2 + " " + this.incorrect3 + " " + this.isDifficult + " }";
    }

    }