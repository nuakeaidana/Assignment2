package POJO;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cards {

    private int amount_full_cards;
    private String id;
    private String stampId;

    public Cards(int amount_full_cards){
        this.amount_full_cards = amount_full_cards;
    }



}
