package org.example.persistence.Entity;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class CafeEntity {
     Integer id;
     String name;
     String description;


     public CafeEntity(Integer id, String name, String Desc) {
          this.name = name;
          this.id = id;
          this.description = Desc;
     }


     @Override
     public String toString() {
          return "CakeEntity{" +
                  "id=" + id +
                  ", name='" + name + '\'' +
                  ", description='" + description + '\'' +
                  '}';
     }
}
