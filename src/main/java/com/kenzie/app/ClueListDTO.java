package com.kenzie.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

//Used to create a List of Clues
@JsonPropertyOrder({"clues"})
public class ClueListDTO {

    @JsonProperty("clues")
    private List<ClueDTO> clues;

    public List<ClueDTO> getClues() {
        return clues;
    }

    public void setClues(List<ClueDTO> clues) {
        this.clues = clues;
    }
}
