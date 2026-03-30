package praktikum.api.profile;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdCreateResponse {
    Integer id;
    String name;
    String category;
    String condition;
    String city;
    String description;
    Integer price;
    Integer owner;
    String updatedAt;
    String createdAt;
    String img1;
    String img2;
    String img3;
    String isFavorite;
}
