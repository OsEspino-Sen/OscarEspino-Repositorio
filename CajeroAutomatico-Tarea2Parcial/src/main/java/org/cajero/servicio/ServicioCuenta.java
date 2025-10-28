package org.cajero.servicio;

import jakarta.enterprise.context.ApplicationScoped;
import org.cajero.model.Cuenta;
import org.cajero.model.Transaccion;
import org.cajero.model.TipoTransaccion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        // Cuenta bancaria con permisos especiales
        cuentas.put("1234567810", new Cuenta("1234567810", "9999", 0.0));
        // Cuentas de ejemplo
        cuentas.put("12345678", new Cuenta("12345678", "1234", 1000.0));
        cuentas.put("87654321", new Cuenta("87654321", "4321", 2000.0));
    }

    public Optional<Cuenta> validarLogin(String numeroCuenta, String pin) {
        // Validar que el número de cuenta contenga solo dígitos
        if (numeroCuenta == null || !numeroCuenta.matches("^[0-9]{8}$|^[0-9]{10}$")) {
            throw new IllegalArgumentException("El número de cuenta debe contener únicamente 8 o 10 dígitos numéricos");
        }
        
        // Validar que el PIN contenga solo dígitos
        if (pin == null || !pin.matches("^[0-9]{4}$")) {
            throw new IllegalArgumentException("El PIN debe contener únicamente 4 dígitos numéricos");
        }
        
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
        
        // Validar que el monto sea un número válido (sin letras)
        String montoStr = String.valueOf(monto);
        if (!montoStr.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
            throw new IllegalArgumentException("El monto debe contener únicamente números y un punto decimal opcional");
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

        // Validar que el monto sea un número válido (sin letras)
        String montoStr = String.valueOf(monto);
        if (!montoStr.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
            throw new IllegalArgumentException("El monto debe contener únicamente números y un punto decimal opcional");
        }

        cuenta.setSaldo(cuenta.getSaldo() + monto);
        Transaccion transaccion = new Transaccion(numeroCuenta, TipoTransaccion.DEPOSITO, monto, "Depósito de efectivo");
        cuenta.agregarTransaccion(transaccion);
    }

    public void transferir(String numeroCuentaOrigen, String numeroCuentaDestino, double monto) {
        if (numeroCuentaOrigen.equals(numeroCuentaDestino)) {
            throw new IllegalArgumentException("No se puede transferir a la misma cuenta");
        }

        // Validar que los números de cuenta contengan solo dígitos
        if (numeroCuentaOrigen == null || !numeroCuentaOrigen.matches("^[0-9]{8}$|^[0-9]{10}$")) {
            throw new IllegalArgumentException("El número de cuenta origen debe contener únicamente 8 o 10 dígitos numéricos");
        }
        
        if (numeroCuentaDestino == null || !numeroCuentaDestino.matches("^[0-9]{8}$|^[0-9]{10}$")) {
            throw new IllegalArgumentException("El número de cuenta destino debe contener únicamente 8 o 10 dígitos numéricos");
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

        // Validar que el monto sea un número válido (sin letras)
        String montoStr = String.valueOf(monto);
        if (!montoStr.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
            throw new IllegalArgumentException("El monto debe contener únicamente números y un punto decimal opcional");
        }

        // Verificar que la cuenta destino existe
        Cuenta cuentaDestino = cuentas.get(numeroCuentaDestino);
        if (cuentaDestino == null) {
            throw new IllegalArgumentException("La cuenta destino no existe. Solo se pueden realizar transferencias a cuentas registradas en el sistema.");
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
    
    public List<Cuenta> obtenerTodasLasCuentas() {
        return new ArrayList<>(cuentas.values());
    }
    
    public void eliminarCuenta(String numeroCuenta) {
        if (!cuentas.containsKey(numeroCuenta)) {
            throw new IllegalArgumentException("La cuenta no existe");
        }
        if ("1234567810".equals(numeroCuenta)) {
            throw new IllegalArgumentException("No se puede eliminar la cuenta bancaria");
        }
        cuentas.remove(numeroCuenta);
    }
    
    public void actualizarPin(String numeroCuenta, String nuevoPin) {
        Cuenta cuenta = cuentas.get(numeroCuenta);
        if (cuenta == null) {
            throw new IllegalArgumentException("La cuenta no existe");
        }
        if (nuevoPin == null || nuevoPin.length() != 4) {
            throw new IllegalArgumentException("El PIN debe tener 4 dígitos");
        }
        if (!nuevoPin.matches("[0-9]{4}")) {
            throw new IllegalArgumentException("El PIN debe contener solo dígitos");
        }
        // Como no hay setter para PIN en el modelo, necesitamos agregarlo
        cuenta.setPin(nuevoPin);
    }
    
    public void actualizarSaldo(String numeroCuenta, double nuevoSaldo) {
        Cuenta cuenta = cuentas.get(numeroCuenta);
        if (cuenta == null) {
            throw new IllegalArgumentException("La cuenta no existe");
        }
        if (nuevoSaldo < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo");
        }
        cuenta.setSaldo(nuevoSaldo);
    }
    
    public void registrarCuenta(String cuentaBancaria, String nuevoNumeroCuenta, String nuevoPin) {
        // Solo la cuenta bancaria 1234567810 puede registrar usuarios
        if (!"1234567810".equals(cuentaBancaria)) {
            throw new IllegalArgumentException("No tiene permisos para registrar usuarios");
        }
        
        if (nuevoNumeroCuenta == null || (nuevoNumeroCuenta.length() != 8 && nuevoNumeroCuenta.length() != 10)) {
            throw new IllegalArgumentException("El número de cuenta debe tener 8 o 10 dígitos");
        }
        
        if (!nuevoNumeroCuenta.matches("[0-9]{8}|[0-9]{10}")) {
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