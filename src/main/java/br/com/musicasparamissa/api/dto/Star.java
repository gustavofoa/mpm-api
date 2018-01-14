package br.com.musicasparamissa.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Star {
    private float r;
    private int v;

    public Star(Float rating, Integer votes) {
        this.r = Math.round((rating != null ? rating : 0) * 10)/10;
        this.v = votes != null ? votes : 0;
    }
}
