package org.cajero.web;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.cajero.model.Cuenta;
import org.cajero.model.Transaccion;
import org.cajero.servicio.ServicioCuenta;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CajeroBean implements Serializable {
    
    @Inject
    private ServicioCuenta servicioCuenta;
    
    private String numeroCuenta;
    private String pin;
    private Cuenta cuentaActual;
    private double monto;
    private String cuentaDestino;
    private String nuevoNumeroCuenta;
    private String nuevoPin;
    
    // Método de login
    public String login() {
        try {
            var cuentaOpcional = servicioCuenta.validarLogin(numeroCuenta, pin);
            if (cuentaOpcional.isPresent()) {
                cuentaActual = cuentaOpcional.get();
                return "menu?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Acceso", "Número de cuenta o PIN incorrectos"));
                return null;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error del Sistema", "Ha ocurrido un error. Por favor intente más tarde."));
            return null;
        }
    }
    
    // Consulta de saldo
    public String consultarSaldo() {
        try {
            if (cuentaActual == null) {
                return "login?faces-redirect=true";
            }
            return "saldo?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return null;
        }
    }
    
    // Retiro de dinero
    public String retirar() {
        try {
            servicioCuenta.retirar(cuentaActual.getNumeroCuenta(), monto);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Retiro realizado con éxito"));
            monto = 0;
            return "menu?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return null;
        }
    }
    
    // Depósito de dinero
    public String depositar() {
        try {
            servicioCuenta.depositar(cuentaActual.getNumeroCuenta(), monto);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Depósito realizado con éxito"));
            monto = 0;
            return "menu?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return null;
        }
    }
    
    // Transferencia
    public String transferir() {
        try {
            servicioCuenta.transferir(cuentaActual.getNumeroCuenta(), cuentaDestino, monto);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Transferencia realizada con éxito"));
            monto = 0;
            cuentaDestino = null;
            return "menu?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return null;
        }
    }
    
    // Cerrar sesión
    public String cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }
    
    // Obtener historial de transacciones
    public List<Transaccion> obtenerHistorialTransacciones() {
        if (cuentaActual == null) {
            return null;
        }
        return cuentaActual.getTransacciones();
    }
    
    // Obtener últimas 3 transacciones
    public List<Transaccion> obtenerUltimasTransacciones() {
        if (cuentaActual == null) {
            return null;
        }
        List<Transaccion> transacciones = cuentaActual.getTransacciones();
        if (transacciones == null || transacciones.isEmpty()) {
            return transacciones;
        }
        int size = transacciones.size();
        int start = Math.max(0, size - 3);
        return transacciones.subList(start, size);
    }
    
    // Validar si es cuenta bancaria
    public boolean esCuentaBancaria() {
        return cuentaActual != null && "1234567810".equals(cuentaActual.getNumeroCuenta());
    }
    
    // Obtener todas las cuentas del sistema
    public List<Cuenta> obtenerTodasLasCuentas() {
        if (!esCuentaBancaria()) {
            return null;
        }
        return servicioCuenta.obtenerTodasLasCuentas();
    }
    
    // Eliminar usuario
    public String eliminarUsuario() {
        try {
            if (!esCuentaBancaria()) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No tiene permisos"));
                return null;
            }
            servicioCuenta.eliminarCuenta(cuentaDestino);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario eliminado exitosamente"));
            cuentaDestino = null;
            return "gestionar?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return null;
        }
    }
    
    // Actualizar PIN de usuario
    public String actualizarPinUsuario() {
        try {
            if (!esCuentaBancaria()) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No tiene permisos"));
                return null;
            }
            servicioCuenta.actualizarPin(cuentaDestino, pin);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "PIN actualizado exitosamente"));
            cuentaDestino = null;
            pin = null;
            return "gestionar?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return null;
        }
    }
    
    // Actualizar saldo de usuario
    public String actualizarSaldoUsuario() {
        try {
            if (!esCuentaBancaria()) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No tiene permisos"));
                return null;
            }
            servicioCuenta.actualizarSaldo(cuentaDestino, monto);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Saldo actualizado exitosamente"));
            cuentaDestino = null;
            monto = 0;
            return "gestionar?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return null;
        }
    }
    
    // Registrar nueva cuenta
    public String registrarNuevaCuenta() {
        try {
            servicioCuenta.registrarCuenta(cuentaActual.getNumeroCuenta(), nuevoNumeroCuenta, nuevoPin);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Cuenta registrada exitosamente"));
            nuevoNumeroCuenta = null;
            nuevoPin = null;
            return "menu?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return null;
        }
    }
    
    // Getters y Setters
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
    
    public String getPin() {
        return pin;
    }
    
    public void setPin(String pin) {
        this.pin = pin;
    }
    
    public Cuenta getCuentaActual() {
        return cuentaActual;
    }
    
    public double getMonto() {
        return monto;
    }
    
    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    public String getCuentaDestino() {
        return cuentaDestino;
    }
    
    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }
    
    public String getNuevoNumeroCuenta() {
        return nuevoNumeroCuenta;
    }
    
    public void setNuevoNumeroCuenta(String nuevoNumeroCuenta) {
        this.nuevoNumeroCuenta = nuevoNumeroCuenta;
    }
    
    public String getNuevoPin() {
        return nuevoPin;
    }
    
    public void setNuevoPin(String nuevoPin) {
        this.nuevoPin = nuevoPin;
    }
    
}