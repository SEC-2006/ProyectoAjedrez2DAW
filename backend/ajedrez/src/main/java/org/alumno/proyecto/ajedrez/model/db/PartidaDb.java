package org.alumno.proyecto.ajedrez.model.db;



import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "partidas")
public class PartidaDb implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador_blancas", nullable = false)
    private UsuarioDb jugadorBlancas;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador_negras", nullable = false)
    private UsuarioDb jugadorNegras;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ganador")
    private UsuarioDb ganador;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio = LocalDateTime.now();

    // Relación OneToMany con MovimientoDb
    @OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovimientoDb> movimientos = new ArrayList<>();
}

