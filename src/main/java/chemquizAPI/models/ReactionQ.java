package chemquizAPI.models;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "reaction_qs")
public class ReactionQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reaction_id")
    private int reactionId;

    @Column(name = "reactant")
    private String reactant;

    @Column(name = "reagent")
    private String reagent;

    @Column(name = "productSmile")
    private String productSmile;

    @Column(name = "productInchi")
    private String productInchi;

    @Column(name = "catalyst")
    private String catalyst;

    @Column(name = "solvent")
    private String solvent;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "time")
    private String time;

    @Column(name = "is_difficult")
    private boolean isDifficult;

    ReactionQ() {}

    public ReactionQ(String reactant, String reagent, String productSmile, String productInchi, String catalyst, String solvent , String temperature, String time, boolean isDifficult) {
        this.reactant = reactant;
        this.reagent = reagent;
        this.productSmile = productSmile;
        this.productInchi = productInchi;
        this.catalyst = catalyst;
        this.solvent = solvent;
        this.temperature = temperature;
        this.time = time;
        this.isDifficult = isDifficult;
    }

    public int getReactionId() {
        return this.reactionId;
    }

    public String getReactant() {
        return this.reactant;
    }

    public String getReagent() {
        return this.reagent;
    }

    public String getProductSmile() {
        return this.productSmile;
    }

    public String getProductInchi() {
        return this.productInchi;
    }

    public String getCatalyst() {
        return this.catalyst;
    }

    public String getSolvent() {
        return this.solvent;
    }

    public String getTemperature() {
        return this.temperature;
    }

    public String getTime() {
        return this.time;
    }

    public boolean getIsDifficult() {
        return this.isDifficult;
    }

    public void setReactionId(int reactionId) {
        this.reactionId = reactionId;
    }

    public void setReactant(String reactant) {
        this.reactant = reactant;
    }

    public void setReagent(String reagent) {
        this.reagent = reagent;
    }

    public void setProductSmile(String productSmile) {
        this.productSmile = productSmile;
    }

    public void setProductInchi(String productInchi) {
        this.productInchi = productInchi;
    }

    public void setCatalyst(String catalyst) {
        this.catalyst = catalyst;
    }

    public void setSolvent(String solvent) {
        this.solvent = solvent;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setIsDifficult(boolean isDifficult) {
        this.isDifficult = isDifficult;
    }

    @Override
    public String toString() {
        return "{ " + this.reactionId + " " + this.reactant + " " + this.reagent + " " + this.productSmile + " " + this.productInchi + " " + this.catalyst + " " + this.solvent + " " + this.temperature + " " + this.time + " " + this.isDifficult + " }";
    }

    }