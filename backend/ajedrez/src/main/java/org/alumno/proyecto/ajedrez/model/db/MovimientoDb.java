package org.alumno.proyecto.ajedrez.model.db;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "movimientos")
public class MovimientoDb implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(1)
    @Column(name = "numero_movimiento", nullable = false)
    private Integer numeroMovimiento;

    @NotBlank
    @Size(max = 20)
    @Column(name = "movimiento_notacion", length = 20, nullable = false)
    private String movimientoNotacion;

    @NotBlank
    @Column(name = "fen_inicial", columnDefinition = "TEXT", nullable = false)
    private String fenInicial;

    @NotBlank
    @Column(name = "fen_final", columnDefinition = "TEXT", nullable = false)
    private String fenFinal;

    @Column(nullable = true)
    private String tiempo;

    // Relación ManyToOne con PartidaDb
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_partida", nullable = false)
    private PartidaDb partida;
}
