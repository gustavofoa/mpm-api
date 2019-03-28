package br.com.musicasparamissa.api.mympm.entity;

import br.com.musicasparamissa.api.util.DateTimeJsonDeserializer;
import br.com.musicasparamissa.api.util.DateTimeJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "mympm_log_login")
public class LogLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "data")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateTimeJsonDeserializer.class)
    private Date data;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogLogin logEmail = (LogLogin) o;
        return Objects.equals(id, logEmail.id) &&
                Objects.equals(data, logEmail.data) &&
                Objects.equals(email, logEmail.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, email);
    }
}
