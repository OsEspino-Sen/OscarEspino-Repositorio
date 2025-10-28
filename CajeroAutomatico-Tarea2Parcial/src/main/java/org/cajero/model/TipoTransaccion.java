package org.cajero.model;

public enum TipoTransaccion {
    RETIRO("Retiro"),
    DEPOSITO("Depósito"),
    TRANSFERENCIA_ENVIADA("Transferencia enviada"),
    TRANSFERENCIA_RECIBIDA("Transferencia recibida"),
    CONSULTA_SALDO("Consulta de saldo");

    private final String descripcion;

    TipoTransaccion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}