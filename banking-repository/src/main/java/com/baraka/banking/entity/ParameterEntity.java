package com.baraka.banking.entity;

import com.baraka.banking.enums.ParameterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PARAMETERS")
public class ParameterEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "OPERATION", nullable = false, unique = false)
    private String operation;

    @Column(name = "PARAMETER", nullable = false, unique = false)
    private String parameter;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false, unique = false)
    private ParameterType type;

    @Column(name = "REQUIRED", nullable = false, unique = false)
    private Boolean required;
}
