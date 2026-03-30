package praktikum.api.profile;

import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
    private int id;
    private int price;
    private String name;
    private String category;
    private String condition;
    private String city;
    private String description;
    private String img1;
    private String img2;
    private String img3;
    private Boolean isFavorite;
    private int owner;
    private Instant createdAt;
    private Instant updatedAt;
}
