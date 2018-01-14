package br.com.musicasparamissa.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StarVote {
    private boolean success;
    private boolean disable;
    private Float fuel;
    private String legend;

    public StarVote(Float fuel, String legend) {
        this.success = true;
        this.disable = true;
        this.fuel = fuel;
        this.legend = legend;
    }
}
