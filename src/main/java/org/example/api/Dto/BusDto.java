package org.example.api.Dto;

import lombok.*;
import org.example.persistence.Entity.BusEntity;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BusDto extends BusEntity {
    public BusDto(int id, String name, Integer power,Integer seatings ) {
        super(id,name,power,seatings);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
