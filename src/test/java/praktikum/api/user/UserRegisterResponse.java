package praktikum.api.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterResponse {
    private User user;
    private AccessToken access_token;
}
