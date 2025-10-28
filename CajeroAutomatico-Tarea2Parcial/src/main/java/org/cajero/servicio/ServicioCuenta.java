package org.cajero.servicio;

import jakarta.enterprise.context.ApplicationScoped;
import org.cajero.model.Cuenta;
import org.cajero.model.Transaccion;
import org.cajero.model.TipoTransaccion;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class ServicioCuenta {
    private final Map<String, Cuenta> cuentas;

    public ServicioCuenta() {
        this.cuentas = new HashMap<>();
        // Inicializar algunas cuentas de ejemplo
        inicializarCuentasPrueba();
    }

    private void inicializarCuentasPrueba() {
        cuentas.put("12345678", new Cuenta("12345678", "1234", 1000.0));
        cuentas.put("87654321", new Cuenta("87654321", "4321", 2000.0));
    }

    public Optional<Cuenta> validarLogin(String numeroCuenta, String pin) {
        Cuenta cuenta = cuentas.get(numeroCuenta);
        if (cuenta != null && cuenta.validarPin(pin)) {
            return Optional.of(cuenta);
        }
        return Optional.empty();
    }

    public double consultarSaldo(String numeroCuenta) {
        Cuenta cuenta = cuentas.get(numeroCuenta);
        if (cuenta != null) {
            Transaccion transaccion = new Transaccion(numeroCuenta, TipoTransaccion.CONSULTA_SALDO, 0, "Consulta de saldo");
            cuenta.agregarTransaccion(transaccion);
            return cuenta.getSaldo();
        }
        throw new IllegalArgumentException("Cuenta no encontrada");
    }

    public void retirar(String numeroCuenta, double monto) {
        Cuenta cuenta = cuentas.get(numeroCuenta);
        if (cuenta == null) {
            throw new IllegalArgumentException("Cuenta no encontrada");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        if (monto > cuenta.getSaldo()) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        
        cuenta.setSaldo(cuenta.getSaldo() - monto);
        Transaccion transaccion = new Transaccion(numeroCuenta, TipoTransaccion.RETIRO, monto, "Retiro de efectivo");
        cuenta.agregarTransaccion(transaccion);
    }

    public void depositar(String numeroCuenta, double monto) {
        Cuenta cuenta = cuentas.get(numeroCuenta);
        if (cuenta == null) {
            throw new IllegalArgumentException("Cuenta no encontrada");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }

        cuenta.setSaldo(cuenta.getSaldo() + monto);
        Transaccion transaccion = new Transaccion(numeroCuenta, TipoTransaccion.DEPOSITO, monto, "Depósito de efectivo");
        cuenta.agregarTransaccion(transaccion);
    }

    public void transferir(String numeroCuentaOrigen, String numeroCuentaDestino, double monto) {
        if (numeroCuentaOrigen.equals(numeroCuentaDestino)) {
            throw new IllegalArgumentException("No se puede transferir a la misma cuenta");
        }

        Cuenta cuentaOrigen = cuentas.get(numeroCuentaOrigen);
        
        if (cuentaOrigen == null) {
            throw new IllegalArgumentException("Cuenta origen no encontrada");
        }
        
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        if (monto > cuentaOrigen.getSaldo()) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        // Obtener o crear cuenta destino
        Cuenta cuentaDestino = cuentas.get(numeroCuentaDestino);
        if (cuentaDestino == null) {
            // Crear cuenta destino si no existe (para permitir transferir a cualquier cuenta)
            cuentaDestino = new Cuenta(numeroCuentaDestino, "0000", 0.0);
            cuentas.put(numeroCuentaDestino, cuentaDestino);
        }

        // Realizar la transferencia
        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);

        // Registrar transacciones
        Transaccion transaccionEnviada = new Transaccion(numeroCuentaOrigen, TipoTransaccion.TRANSFERENCIA_ENVIADA, monto,
                "Transferencia enviada a la cuenta " + numeroCuentaDestino);
        transaccionEnviada.setNumeroCuentaRelacionada(numeroCuentaDestino);
        cuentaOrigen.agregarTransaccion(transaccionEnviada);

        Transaccion transaccionRecibida = new Transaccion(numeroCuentaDestino, TipoTransaccion.TRANSFERENCIA_RECIBIDA, monto,
                "Transferencia recibida de la cuenta " + numeroCuentaOrigen);
        transaccionRecibida.setNumeroCuentaRelacionada(numeroCuentaOrigen);
        cuentaDestino.agregarTransaccion(transaccionRecibida);
    }

    public Cuenta getCuenta(String numeroCuenta) {
        return cuentas.get(numeroCuenta);
    }
    
    public void registrarCuenta(String cuentaBancaria, String nuevoNumeroCuenta, String nuevoPin) {
        // Solo la cuenta bancaria 1234567810 puede registrar usuarios
        if (!"1234567810".equals(cuentaBancaria)) {
            throw new IllegalArgumentException("No tiene permisos para registrar usuarios");
        }
        
        if (nuevoNumeroCuenta == null || nuevoNumeroCuenta.length() != 8) {
            throw new IllegalArgumentException("El número de cuenta debe tener 8 dígitos");
        }
        
        if (!nuevoNumeroCuenta.matches("[0-9]{8}")) {
            throw new IllegalArgumentException("El número de cuenta debe contener solo dígitos");
        }
        
        if (cuentas.containsKey(nuevoNumeroCuenta)) {
            throw new IllegalArgumentException("La cuenta ya existe");
        }
        
        if (nuevoPin == null || nuevoPin.length() != 4) {
            throw new IllegalArgumentException("El PIN debe tener 4 dígitos");
        }
        
        if (!nuevoPin.matches("[0-9]{4}")) {
            throw new IllegalArgumentException("El PIN debe contener solo dígitos");
        }
        
        // Crear nueva cuenta con saldo inicial de 0
        Cuenta nuevaCuenta = new Cuenta(nuevoNumeroCuenta, nuevoPin, 0.0);
        cuentas.put(nuevoNumeroCuenta, nuevaCuenta);
    }
}