package com.gui.estore.NotificationsService.repositories;


import com.gui.estore.NotificationsService.model.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<NotificationEntity, String> {

    Optional<NotificationEntity> findByOrderId(String shipmentId);
}
