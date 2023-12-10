package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A NopTien.
 */
@Entity
@Table(name = "nop_tien")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NopTien implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "so_tien")
    private Long soTien;

    @Column(name = "ngay_nop")
    private Instant ngayNop;

    @ManyToOne(fetch = FetchType.LAZY)
    private HoKhau hoKhau;

    @ManyToOne(fetch = FetchType.LAZY)
    private KhoanThu khoanThu;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NopTien id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSoTien() {
        return this.soTien;
    }

    public NopTien soTien(Long soTien) {
        this.setSoTien(soTien);
        return this;
    }

    public void setSoTien(Long soTien) {
        this.soTien = soTien;
    }

    public Instant getNgayNop() {
        return this.ngayNop;
    }

    public NopTien ngayNop(Instant ngayNop) {
        this.setNgayNop(ngayNop);
        return this;
    }

    public void setNgayNop(Instant ngayNop) {
        this.ngayNop = ngayNop;
    }

    public HoKhau getHoKhau() {
        return this.hoKhau;
    }

    public void setHoKhau(HoKhau hoKhau) {
        this.hoKhau = hoKhau;
    }

    public NopTien hoKhau(HoKhau hoKhau) {
        this.setHoKhau(hoKhau);
        return this;
    }

    public KhoanThu getKhoanThu() {
        return this.khoanThu;
    }

    public void setKhoanThu(KhoanThu khoanThu) {
        this.khoanThu = khoanThu;
    }

    public NopTien khoanThu(KhoanThu khoanThu) {
        this.setKhoanThu(khoanThu);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NopTien)) {
            return false;
        }
        return getId() != null && getId().equals(((NopTien) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NopTien{" +
            "id=" + getId() +
            ", soTien=" + getSoTien() +
            ", ngayNop='" + getNgayNop() + "'" +
            "}";
    }
}
