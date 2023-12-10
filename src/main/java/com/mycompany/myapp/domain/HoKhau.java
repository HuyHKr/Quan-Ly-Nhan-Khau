package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A HoKhau.
 */
@Entity
@Table(name = "ho_khau")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HoKhau implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ma_ho_khau", nullable = false)
    private String maHoKhau;

    @NotNull
    @Column(name = "ten_chu_ho", nullable = false)
    private String tenChuHo;

    @NotNull
    @Column(name = "dia_chi", nullable = false)
    private String diaChi;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HoKhau id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaHoKhau() {
        return this.maHoKhau;
    }

    public HoKhau maHoKhau(String maHoKhau) {
        this.setMaHoKhau(maHoKhau);
        return this;
    }

    public void setMaHoKhau(String maHoKhau) {
        this.maHoKhau = maHoKhau;
    }

    public String getTenChuHo() {
        return this.tenChuHo;
    }

    public HoKhau tenChuHo(String tenChuHo) {
        this.setTenChuHo(tenChuHo);
        return this;
    }

    public void setTenChuHo(String tenChuHo) {
        this.tenChuHo = tenChuHo;
    }

    public String getDiaChi() {
        return this.diaChi;
    }

    public HoKhau diaChi(String diaChi) {
        this.setDiaChi(diaChi);
        return this;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HoKhau)) {
            return false;
        }
        return getId() != null && getId().equals(((HoKhau) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoKhau{" +
            "id=" + getId() +
            ", maHoKhau='" + getMaHoKhau() + "'" +
            ", tenChuHo='" + getTenChuHo() + "'" +
            ", diaChi='" + getDiaChi() + "'" +
            "}";
    }
}
