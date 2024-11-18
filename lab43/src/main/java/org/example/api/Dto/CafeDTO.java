package org.example.api.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.persistence.Entity.CafeEntity;

@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CafeDTO {
    Integer id;
    String name;
    String description;


    public CafeDTO(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.description = desc;
    }

    public CafeDTO(CafeEntity dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.description = dto.getDescription();
    }



}
