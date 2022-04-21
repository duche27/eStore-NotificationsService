package com.gui.estore.NotificationsService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notifications", schema = "public")
public class NotificationEntity {

    @Id
    @Column(unique = true)
    public String noticeId;
    public String orderId;
    public String notificationStatus;
}
