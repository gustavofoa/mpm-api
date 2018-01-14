package br.com.musicasparamissa.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Star {
    private double r;
    private int v;

    public Star(Float rating, Integer votes) {
        this.r = Math.floor((rating != null ? rating : 0) * 100)/100;
        this.v = votes != null ? votes : 0;
    }
}
