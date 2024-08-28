package com.revature.LLL.User.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * class for the vet_details json property in the VetRecord class
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VetDetailsDTO {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
}


