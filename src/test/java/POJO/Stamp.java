package POJO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Stamp {
    private int amount;
    private String promotion;
    private String id;
    private String clientId;

}
