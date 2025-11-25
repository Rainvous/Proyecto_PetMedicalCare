package pe.edu.pucp.softpetws.correo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GmailService {

    // --- CONFIGURACIÓN ---
    private final String usuario = "petmedicalcarecorreo@gmail.com";
    private final String clave = "wqhf nspt kyoh njhx";

    // Constructor: Lo dejamos vacío (o quitamos la línea recursiva)
    public GmailService() {
        // Ya no creamos 'new GmailService()' aquí adentro.
    }

    // ---------------------
    public int enviarCorreo_VerficarCuenta(String destinatario) {
        String linkPagRedireccionada = "http://localhost:8080/SoftPet/recuperar?token=12345ABCDE";
        String asunto = "Recuperación de Cuenta - PetMedicalCare";

        // CORRECCIÓN: Usamos 'this' o llamamos al método directamente, no a una variable externa
        String cuerpoDelCorreo = this.cargarTemplateVerificacion(linkPagRedireccionada);

        if (!cuerpoDelCorreo.equals("Error al cargar plantilla")) {
            // CORRECCIÓN: Llamamos al método interno directamente
            return this.enviarCorreo(destinatario, asunto, cuerpoDelCorreo);
        } else {
            System.out.println("No se pudo cargar el archivo HTML.");
            return 0;
        }
    }

    private int enviarCorreo(String destinatario, String asunto, String cuerpoHtml) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        // A veces necesario para evitar errores de certificado
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
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
            message.setContent(cuerpoHtml, "text/html; charset=utf-8");

            // --- INICIO DEL TRUCO (FIX PARA GLASSFISH) ---
            // Guardamos el cargador de clases actual del servidor
            Thread t = Thread.currentThread();
            ClassLoader original = t.getContextClassLoader();

            // Forzamos a usar el cargador de esta clase (que tiene las librerías correctas)
            t.setContextClassLoader(GmailService.class.getClassLoader());

            try {
                // Enviamos el correo con el cargador forzado
                Transport.send(message);
            } finally {
                // IMPORTANTE: Devolvemos el cargador original al servidor para no romper nada más
                t.setContextClassLoader(original);
            }
            // --- FIN DEL TRUCO ---

            System.out.println("✅ Correo enviado correctamente a: " + destinatario);
            return 1;

        } catch (MessagingException e) {
            System.err.println("❌ Error al enviar el correo: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    private String cargarTemplateVerificacion(String linkHtml) {
        String contenidoHtml = "";

        try {
            // Buscamos el archivo en la carpeta resources/templates
            InputStream is = getClass().getClassLoader().getResourceAsStream("templates/verificacion.html");

            if (is == null) {
                // Tip: Imprimimos esto para ver si falla la ruta
                System.err.println("No se encontró el archivo en: templates/verificacion.html");
                throw new RuntimeException("No se pudo encontrar el archivo verificacion.html");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                contenidoHtml = reader.lines().collect(Collectors.joining("\n"));
            }

            contenidoHtml = contenidoHtml.replace("{{LINK_RECUPERACION}}", linkHtml);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al cargar plantilla";
        }

        return contenidoHtml;
    }
}
