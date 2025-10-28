package org.cajero.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaccion {
    private Long id;
    private String numeroCuenta;
    private TipoTransaccion tipo;
    private double monto;
    private LocalDateTime fecha;
    private String descripcion;
    private double saldoDespuesTransaccion;
    private String numeroCuentaRelacionada; // Para transferencias
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public Transaccion(String numeroCuenta, TipoTransaccion tipo, double monto, String descripcion) {
        this.id = Long.valueOf(System.currentTimeMillis()); // Simulación de ID único
        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
        this.fecha = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public TipoTransaccion getTipo() {
        return tipo;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getSaldoDespuesTransaccion() {
        return saldoDespuesTransaccion;
    }

    public void setSaldoDespuesTransaccion(double saldoDespuesTransaccion) {
        this.saldoDespuesTransaccion = saldoDespuesTransaccion;
    }
    
    // Alias method for JSF access
    public double getSaldoResultante() {
        return saldoDespuesTransaccion;
    }

    public String getNumeroCuentaRelacionada() {
        return numeroCuentaRelacionada;
    }

    public void setNumeroCuentaRelacionada(String numeroCuentaRelacionada) {
        this.numeroCuentaRelacionada = numeroCuentaRelacionada;
    }
    
    // Method to format the date as string for JSF display
    public String getFechaFormateada() {
        if (fecha == null) {
            return "";
        }
        return fecha.format(FORMATTER);
    }
    
    // Method to get id as String for JSF compatibility
    public String getIdAsString() {
        return id != null ? id.toString() : "";
    }
}