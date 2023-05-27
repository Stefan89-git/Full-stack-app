package com.stefan.customer;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode
public class CustomerDto {
    private Integer id;
    @NotNull(message = "Name can not be null or empty")
    private String name;
    @NotNull(message = "Email can not be null or empty")
    private String email;
    @NotNull(message = "Age can not be null or empty")
    private Integer age;
}
