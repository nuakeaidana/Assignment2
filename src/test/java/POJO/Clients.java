package POJO;

import POJO.Cards;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Clients {

    private String name;
    private String lastname;
    private String id;
    private List<Stamps> stamps = null;
    private List<Cards> cards = null;

    public Clients(String name, String lastname){

        this.name = name;
        this.lastname = lastname;

    }





}

