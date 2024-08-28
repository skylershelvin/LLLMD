package com.revature.LLL.User.dtos;

import com.revature.LLL.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * class for the owner_info json property in the PatientIdentification class
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerInfoDTO {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;

    public OwnerInfoDTO(User user) {
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }
}

