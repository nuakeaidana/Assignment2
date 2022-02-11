package POJO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Efteling {
    private String name;
    private String lastname;
    private String id;
    private List<Card> cards = null;
    private List<Stamp> stamps = null;
}
