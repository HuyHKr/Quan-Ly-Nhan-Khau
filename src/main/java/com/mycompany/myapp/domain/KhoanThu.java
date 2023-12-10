package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.Loai;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A KhoanThu.
 */
@Entity
@Table(name = "khoan_thu")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KhoanThu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ma_khoan_thu", nullable = false)
    private String maKhoanThu;

    @NotNull
    @Column(name = "ten_khoan_thu", nullable = false)
    private String tenKhoanThu;

    @NotNull
    @Column(name = "don_gia", nullable = false)
    private String donGia;

    @Enumerated(EnumType.STRING)
    @Column(name = "loai")
    private Loai loai;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KhoanThu id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaKhoanThu() {
        return this.maKhoanThu;
    }

    public KhoanThu maKhoanThu(String maKhoanThu) {
        this.setMaKhoanThu(maKhoanThu);
        return this;
    }

    public void setMaKhoanThu(String maKhoanThu) {
        this.maKhoanThu = maKhoanThu;
    }

    public String getTenKhoanThu() {
        return this.tenKhoanThu;
    }

    public KhoanThu tenKhoanThu(String tenKhoanThu) {
        this.setTenKhoanThu(tenKhoanThu);
        return this;
    }

    public void setTenKhoanThu(String tenKhoanThu) {
        this.tenKhoanThu = tenKhoanThu;
    }

    public String getDonGia() {
        return this.donGia;
    }

    public KhoanThu donGia(String donGia) {
        this.setDonGia(donGia);
        return this;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }

    public Loai getLoai() {
        return this.loai;
    }

    public KhoanThu loai(Loai loai) {
        this.setLoai(loai);
        return this;
    }

    public void setLoai(Loai loai) {
        this.loai = loai;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KhoanThu)) {
            return false;
        }
        return getId() != null && getId().equals(((KhoanThu) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KhoanThu{" +
            "id=" + getId() +
            ", maKhoanThu='" + getMaKhoanThu() + "'" +
            ", tenKhoanThu='" + getTenKhoanThu() + "'" +
            ", donGia='" + getDonGia() + "'" +
            ", loai='" + getLoai() + "'" +
            "}";
    }
}
