package com.revature.LLL.User.dtos;

import com.revature.LLL.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private User.userType userType;

    public UserResponseDTO(User user) {
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.userType = user.getUserType();
    }
}
