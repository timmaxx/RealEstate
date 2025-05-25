package com.timmax.realestate.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NamedQueries({
        @NamedQuery(name = RealEstate.GET_ALL_SORTED_BY_ADDRESS, query = """
                SELECT re
                  FROM RealEstate re
                 WHERE re.user.id = :userId
                 ORDER BY re.address
                """),
        @NamedQuery(name = RealEstate.DELETE_BY_ID, query = """
                DELETE
                  FROM RealEstate re
                 WHERE re.id = :id
                   AND re.user.id = :userId
                """),
        @NamedQuery(name = RealEstate.GET_BETWEEN_SQUARE, query = """
                SELECT re
                  FROM RealEstate re
                 WHERE re.user.id = :userId
                   AND re.square >= :startSquare
                   AND re.square < :endSquare
                 ORDER BY re.address
                """),
})
@Entity
@Table(
        name = "real_estate",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "address"},
                        name = "real_estate_unique_user_address_idx"
                )
        }
)
public class RealEstate extends AbstractBaseEntity {
    public static final String GET_ALL_SORTED_BY_ADDRESS = "RealEstate.getAllSortedByAddress";
    public static final String DELETE_BY_ID = "RealEstate.deleteById";
    public static final String GET_BETWEEN_SQUARE = "RealEstate.getBetweenSquare";

    @Column(name = "address", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String address;

    @Column(name = "square", nullable = false)
    @Range(min = 1)
    private float square;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    // @NotNull
    private User user;

    public RealEstate() {
    }

    public RealEstate(String address, float square) {
        this(null, address, square);
    }

    public RealEstate(Integer id, String address, float square) {
        super(id);
        this.address = address;
        this.square = square;
    }

    public String getAddress() {
        return address;
    }

    public float getSquare() {
        return square;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSquare(float square) {
        this.square = square;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "RealEstate{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", square=" + square +
                '}';
    }
}
