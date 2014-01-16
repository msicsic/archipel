package com.tentelemed.archipel.site.domain.pub;

import com.tentelemed.archipel.core.domain.model.BaseVO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 10/12/13
 * Time: 12:13
 */
@Embeddable
public class Bed extends BaseVO {
    @Column(name="BED_NAME")
    @NotNull String name;
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
//    @NotNull @ManyToOne RoomQ room;

    Bed() {
    }

    public Bed(String name) {
        this.name = name;
        validate();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bed)) return false;
        Bed bed = (Bed) o;
        if (!name.equals(bed.name)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
