package pe.edu.pucp.softpet.bo.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.stream.Collectors;

//Para enviar pdfs
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import jakarta.activation.DataHandler;
import jakarta.mail.util.ByteArrayDataSource;
///


import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class GmailService {

    // --- CONFIGURACIÓN ---
    private final String usuario = "petmedicalcarecorreo@gmail.com";
    private final String clave = "wqhf nspt kyoh njhx";

    public GmailService() {
        // Constructor vacío
    }

    // 1. MÉTODO PÚBLICO
    public String enviarCorreo_VerficarCuenta(String destinatario) {
        String linkPagRedireccionada = "http://localhost:8080/SoftPet/recuperar?token=12345ABCDE";
        String asunto = "Recuperación de Cuenta - PetMedicalCare";

        // PASO 1: Cargamos el HTML desde el archivo
        String cuerpoDelCorreo = this.cargarTemplateVerificacion(linkPagRedireccionada);

        // Validación: Si falló la carga del archivo, retornamos el error
        if (cuerpoDelCorreo.startsWith("ERROR")) {
            return cuerpoDelCorreo;
        }

        // PASO 2: Enviamos el correo
        return this.enviarCorreo(destinatario, asunto, cuerpoDelCorreo);
    }

    // 2. MÉTODO DE ENVÍO (SMTP - Con el fix de seguridad)
    private String enviarCorreo(String destinatario, String asunto, String cuerpoHtml) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // --- SOLUCIÓN AL ERROR PKIX (PUCP / Firewall) ---
        props.put("mail.smtp.ssl.trust", "*");
        // ------------------------------------------------

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, clave);
            }
        });

        // session.setDebug(true); // Descomenta si necesitas ver logs de nuevo
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usuario));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);

            message.setContent(cuerpoHtml, "text/html; charset=utf-8");

            Transport.send(message);

            return "EXITO: Correo enviado correctamente a " + destinatario;

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR SMTP: " + e.getMessage();
        }
    }

    // 3. MÉTODO PARA LEER EL ARCHIVO (Regresamos a la versión limpia)
    private String cargarTemplateVerificacion(String linkHtml) {
        String contenidoHtml = "";

        try {
            // Buscamos el archivo en src/main/resources/templates/verificacion.html
            // NOTA: Java busca en la carpeta compilada, no en "src". Por eso se requiere Clean & Build.
            InputStream is = getClass().getClassLoader().getResourceAsStream("templates/verificacion.html");

            if (is == null) {
                return "ERROR ARCHIVO: No se encontró 'templates/verificacion.html'. Haz Clean & Build.";
            }

            // Leemos el archivo
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                contenidoHtml = reader.lines().collect(Collectors.joining("\n"));
            }

            // Reemplazamos la variable
            contenidoHtml = contenidoHtml.replace("{{LINK_RECUPERACION}}", linkHtml);

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR LECTURA: " + e.getMessage();
        }

        return contenidoHtml;
    }
    // ... (Tus métodos anteriores siguen aquí arriba) ...

    // --- NUEVO MÉTODO PARA ENVIAR CONTRASEÑA ---
    public String enviarCorreo_Credenciales(String destinatario, String passwordRecuperada) {

        // El link a tu pantalla de Login (ajústalo si tu login tiene otra URL)
        String linkLogin = "http://localhost:8080/SoftPet/faces/login.xhtml"; // O login.jsp
        String asunto = "Tus Credenciales de Acceso - SoftPet";

        // 1. Cargamos la nueva plantilla
        String cuerpoDelCorreo = this.cargarTemplateCredenciales(passwordRecuperada, linkLogin);

        // Validación de error
        if (cuerpoDelCorreo.startsWith("ERROR")) {
            return cuerpoDelCorreo;
        }

        // 2. Reutilizamos el método de envío que ya funciona (el que tiene el fix SSL)
        return this.enviarCorreo(destinatario, asunto, cuerpoDelCorreo);
    }

    // Método privado específico para leer el HTML de credenciales
    private String cargarTemplateCredenciales(String password, String linkLogin) {
        String contenidoHtml = "";
        try {
            // Buscamos el NUEVO archivo
            InputStream is = getClass().getClassLoader().getResourceAsStream("templates/credenciales.html");

            if (is == null) {
                return "ERROR ARCHIVO: No se encontró 'templates/credenciales.html'. Haz Clean & Build.";
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                contenidoHtml = reader.lines().collect(Collectors.joining("\n"));
            }

            // Reemplazamos las DOS variables del HTML
            contenidoHtml = contenidoHtml.replace("{{PASSWORD}}", password);
            contenidoHtml = contenidoHtml.replace("{{LINK_LOGIN}}", linkLogin);

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR LECTURA: " + e.getMessage();
        }
        return contenidoHtml;
    }

    // --- 1. MÉTODO PÚBLICO PARA COMPROBANTE ---
    public String enviarCorreo_ComprobantePago(String destinatario, byte[] pdfBytes) {
        String asunto = "Su Comprobante de Pago - SoftPet";

        // Cargamos el diseño bonito
        String cuerpoHtml = this.cargarTemplateComprobante();

        if (cuerpoHtml.startsWith("ERROR")) {
            return cuerpoHtml;
        }

        // Llamamos al método de envío inteligente (detecta si hay PDF o no)
        return this.enviarCorreoConAdjunto(destinatario, asunto, cuerpoHtml, pdfBytes, "Comprobante_SoftPet.pdf");
    }

    // --- 2. MÉTODO PARA LEER EL HTML DEL COMPROBANTE ---
    private String cargarTemplateComprobante() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("templates/comprobante.html");
            if (is == null) {
                return "ERROR ARCHIVO: No se encontró 'templates/comprobante.html'.";
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            return "ERROR LECTURA: " + e.getMessage();
        }
    }

    // --- 3. MÉTODO PRIVADO (MODIFICADO PARA SOPORTAR NULL EN PRUEBAS) ---
    private String enviarCorreoConAdjunto(String destinatario, String asunto, String cuerpoHtml, byte[] archivoBytes, String nombreArchivo) {
        // ... (Tu configuración de Properties y Session sigue igual) ...
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", "*");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, clave);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usuario));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);

            // PARTE 1: El HTML
            MimeBodyPart textoPart = new MimeBodyPart();
            textoPart.setContent(cuerpoHtml, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textoPart);

            // PARTE 2: El PDF (Solo si NO es null)
            if (archivoBytes != null && archivoBytes.length > 0) {
                MimeBodyPart adjuntoPart = new MimeBodyPart();
                ByteArrayDataSource bds = new ByteArrayDataSource(archivoBytes, "application/pdf");
                adjuntoPart.setDataHandler(new DataHandler(bds));
                adjuntoPart.setFileName(nombreArchivo);
                multipart.addBodyPart(adjuntoPart);
            } else {
                System.out.println("⚠️ ALERTA: Enviando correo de comprobante SIN PDF (Modo Prueba)");
            }

            message.setContent(multipart);
            Transport.send(message);

            return "EXITO: Comprobante enviado a " + destinatario;

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR SMTP: " + e.getMessage();
        }
    }

}
