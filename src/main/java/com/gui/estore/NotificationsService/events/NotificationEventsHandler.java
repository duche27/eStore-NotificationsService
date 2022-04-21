package com.gui.estore.NotificationsService.events;

import com.gui.estore.NotificationsService.model.NotificationEntity;
import com.gui.estore.NotificationsService.repositories.NotificationRepository;
import com.gui.estore.NotificationsService.service.impl.EmailNotificationSender;
import com.gui.estore.core.events.NotificationSentEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class NotificationEventsHandler {

    private final NotificationRepository notificationRepository;
    private final EmailNotificationSender emailNotificationSender;

    public NotificationEventsHandler(NotificationRepository notificationRepository, EmailNotificationSender emailNotificationSender) {
        this.notificationRepository = notificationRepository;
        this.emailNotificationSender = emailNotificationSender;
    }

    @EventHandler
    public void on(NotificationSentEvent notificationSentEvent) {

        // mandamos notificación
        try {
            emailNotificationSender.sendSimpleMessage(notificationSentEvent.getEmail(),
                    "Envío realizado - orden " + notificationSentEvent.getOrderId(),
                    "Estimado cliente, /n/n Su orden de compra " + notificationSentEvent.getOrderId() + " ha sido enviada." +
                            " /n/n Gracias por su compra.");
        } catch (Exception e) {
            e.getStackTrace();
        }

        log.info("Notificatión vía email mandada para la orden {} al correo {}",
                notificationSentEvent.getOrderId(), notificationSentEvent.getEmail());

        NotificationEntity notificationEntity = new NotificationEntity();

        BeanUtils.copyProperties(notificationSentEvent, notificationEntity);

        try {
            notificationRepository.save(notificationEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    // lanza la excepción controlada si no persiste notificationEntity
    // sin persistir nada, es transaccional
    // de aquí va a NotificationServiceEventHandler - después a NotificationErrorHandler - excepción controlada
    @ExceptionHandler()
    private void handle(Exception exception) throws Exception {
        throw exception;
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    private void handle(IllegalArgumentException exception) throws IllegalArgumentException {
        throw exception;
    }
}
