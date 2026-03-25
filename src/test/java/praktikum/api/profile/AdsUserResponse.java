package praktikum.api.profile;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsUserResponse {
    private List<Offer> offers;
    private int totalPages;
}
