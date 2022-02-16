package POJO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Stamps {

    private int amount;
    private String promotion;
    private String id;
    private String clientId;

    public Stamps( int amount, String promotion){
        this.amount = amount;
        this.promotion = promotion;
    }


}
