package com.abme.portal.domain.role;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="role_table", schema = "public")
@EqualsAndHashCode(of = {"id"})
@Accessors(chain = true)
@NoArgsConstructor
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    @NaturalId
    @Column(length = 50)
    private RoleName name;

    public Role(RoleName name) {
        this.name = name;
    }
}
