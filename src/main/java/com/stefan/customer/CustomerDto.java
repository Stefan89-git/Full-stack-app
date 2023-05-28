package com.stefan.customer;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode
public class CustomerDto {
    private Integer id;
    private String name;
    private String email;
    private Integer age;
}
