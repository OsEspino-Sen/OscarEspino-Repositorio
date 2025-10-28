package org.cajero.model;

import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    private String numeroCuenta;
    private String pin;
    private double saldo;
    private List<Transaccion> transacciones;

    public Cuenta(String numeroCuenta, String pin, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.pin = pin;
        this.saldo = saldoInicial;
        this.transacciones = new ArrayList<>();
    }

    // Getters y Setters
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getPin() {
        return pin;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void agregarTransaccion(Transaccion transaccion) {
        transaccion.setSaldoDespuesTransaccion(this.saldo);
        transacciones.add(transaccion);
    }

    // Validación de PIN
    public boolean validarPin(String pinIngresado) {
        return this.pin.equals(pinIngresado);
    }
}