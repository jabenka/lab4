package org.example.persistence.Entity;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class BusEntity {
     Integer id;
     String name;
     Integer Power;
     Integer seatings;


     public BusEntity(Integer id,String name, Integer power, Integer seatings) {
          this.seatings = seatings;
          this.Power = power;
          this.name = name;
          this.id = id;
     }


     @Override
     public String toString() {
          return "BusEntity{" +
                  "id=" + id +
                  ", name='" + name + '\'' +
                  ", Power=" + Power +
                  ", seating's=" + seatings +
                  '}';
     }
}
