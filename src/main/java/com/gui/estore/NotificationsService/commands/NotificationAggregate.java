package com.gui.estore.NotificationsService.commands;

import com.gui.estore.core.commands.SendNotificationCommand;
import com.gui.estore.core.events.NotificationSentEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class NotificationAggregate {

    @AggregateIdentifier
    private String noticeId;
    private String orderId;
    private String notificationStatus;
    private String email;

    public NotificationAggregate() {}

    @CommandHandler
    public NotificationAggregate(SendNotificationCommand command) {

        // validaciones

        // creamos event
        NotificationSentEvent event = NotificationSentEvent.builder()
                .noticeId(command.getNoticeId())
                .orderId(command.getOrderId())
                .email(command.getEmail())
                .build();

        // lanzamos event al BUS para publicar y que llegue al HANDLER y a SAGA
        // publicamos evento y mandamos al eventHandler
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(NotificationSentEvent event) {

        this.noticeId = event.getNoticeId();
        this.orderId = event.getOrderId();
        this.notificationStatus = event.getEmail();
        this.email = event.getEmail();
    }
}
