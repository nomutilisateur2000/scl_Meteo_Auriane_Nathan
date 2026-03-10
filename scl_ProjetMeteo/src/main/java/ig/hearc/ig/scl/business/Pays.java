package ig.hearc.ig.scl.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pays {
    static Integer num = 0;
    private Integer numero;
    private String name;
    private String code;

    public  Pays() {
        this.numero = num++;
    }

    public Pays(String name, String code) {
        this.numero = num++;
        this.name = name;
        this.code = code;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pays {" +
                "numero=" + numero +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
