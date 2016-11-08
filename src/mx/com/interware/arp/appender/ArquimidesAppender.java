package mx.com.interware.arp.appender;

import java.util.Calendar;
import mx.com.interware.arp.appender.remote.BufferedSender;
import mx.com.interware.arp.appender.remote.PersistentSocket;
import mx.com.interware.arp.appender.util.ArpUtil;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 *
 * @author Jorge Esteban Zaragoza Salazar
 * @version 1.0.0 - Clase que funciona como appender para Log4j
 *
 */
public class ArquimidesAppender extends AppenderSkeleton {

    private String regexp;
    private String ednFormat;
    private String host;
    private String port;
    private String reconnectionTime;
    private String sendDelta;
    private String maxQueue;
    private BufferedSender bSender;
    private PersistentSocket worker;
    private static Boolean initizalized = false;

    public ArquimidesAppender() {
    }

    /**
     *
     * Método que se invoca en cada entrada de log de log4j (debug, info, error,
     * fatal, etc)
     *
     * @param loggingEvent Objeto que representa el evento de logging
     *
     *
     */
    @Override
    protected void append(LoggingEvent loggingEvent) {
        if (!ArquimidesAppender.initizalized) {
            synchronized (ArquimidesAppender.initizalized) {
                if (!ArquimidesAppender.initizalized) {
                    worker = new PersistentSocket(Integer.parseInt(this.reconnectionTime));
                    worker.start(this.host, Integer.parseInt(this.port));
                    ArquimidesAppender.initizalized = true;
                    bSender = new BufferedSender(Calendar.getInstance().getTimeInMillis(),
                            worker,
                            Long.parseLong(this.sendDelta),
                            Integer.parseInt(this.maxQueue),
                            regexp,
                            ednFormat
                    );
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        if (ArpUtil.matches(loggingEvent.getMessage().toString(), this.regexp) && worker.getIsConnected()) {
            bSender.add(loggingEvent);
        }
    }

    /**
     * Método que se ejecuta al cerrar el log del appender
     *
     */
    @Override
    public void close() {

    }

    /**
     * Método que verifica si el Appender necesita un layout en la
     * configuración.
     *
     * @return Devuelve si el appender necesita un layout en el archivo de
     * configuración
     */
    @Override
    public boolean requiresLayout() {
        return false;
    }

    /**
     * Método para obtener el host del servidor
     *
     * @return devuelve el host del servidor configurado
     */
    public String getHost() {
        return host;
    }

    /**
     * Método para setear el host del servidor, usualmente se inyecta del
     * archivo de configuración
     *
     * @param host host a setear
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Método para obtener el puerto del servidor
     *
     * @return devuelve el host configurado
     */
    public String getPort() {
        return port;
    }

    /**
     * Método para setear el puerto del servidor, usualmente se inyecta del
     * archivo de configuración
     *
     * @param port puerto a setear
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * Método para obtener el tiempo de reconexión
     *
     * @return devuelve el tiempo de reconexión
     */
    public String getReconnectionTime() {
        return reconnectionTime;
    }

    /**
     * Método para setear el tiempo de reconexión, usualmente es inyectado désde el archivo de configuración
     *
     * @param reconnectionTime tiempo de reconexión en milisegundos
     */
    public void setReconnectionTime(String reconnectionTime) {
        this.reconnectionTime = reconnectionTime;
    }

    /**
     * Método para obtener la delta de envío.
     *
     * @return tiempo máximo de envío de entradas del log al servidor
     */
    public String getSendDelta() {
        return sendDelta;
    }

    /**
     * Método para setear el tiempo de delay de envío al socket, usualmente es
     * inyectado désde el archivo de configuración
     *
     * @param sendDelta delta de envío de entradas del log al servidor
     */
    public void setSendDelta(String sendDelta) {
        this.sendDelta = sendDelta;
    }

    /**
     * Método que obtiene el tamaño máximo de elementos que puede haber en la
     * cola
     *
     * @return regresa el tamaño máximo de elementos que puede haber en la cola
     * de envío
     */
    public String getMaxQueue() {
        return maxQueue;
    }

    /**
     * Método que setea el tamaño máximo de elementos que puede haber en la cola
     * de envío, usualmente es inyectado désde el archivo de configuración
     *
     * @param maxQueue tamaño máximo de elementos que puede haber en la cola de
     * envío
     */
    public void setMaxQueue(String maxQueue) {
        this.maxQueue = maxQueue;
    }

    /**
     * Método que obtiene el formato del EDN configurado désde el archivo de
     * configuración
     *
     * @return devuelve el formato del EDN configurado désde el archivo de
     * configuración
     */
    public String getEdnFormat() {
        return ednFormat;
    }

    /**
     * Método que setea el formato del EDN para ser enviado envío, usualmente es
     * inyectado désde el archivo de configuración.
     *
     * @param ednFormat Formato del EDN
     */
    public void setEdnFormat(String ednFormat) {
        this.ednFormat = ednFormat;
    }
    /**
     * Método que obtiene la expresión regular para filtrar los mensajes del log.
     * @return expresión regular para realizar el filtro.
     */
    public String getRegexp() {
        return regexp;
    }
    /**
     * Método que setea- la expresión regular para realizar el filtro del log.
     * @param regexp expresión 
     */
    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }
}
