package com.camcorderio.userservice.dto;

import com.camcorderio.userservice.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    public AddressDto(Long id, String address, Integer post) {
        this.id = id;
        this.address = address;
        this.post = post;
    }
    private Long id;
    @NotBlank(message = "Address cannot be blank")
    private String address;
    @NotNull(message = "Post cannot be blank")
    private Integer post;
    private Long userId;
}
