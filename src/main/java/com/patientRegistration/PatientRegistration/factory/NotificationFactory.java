package com.patientRegistration.PatientRegistration.factory;

import com.patientRegistration.PatientRegistration.exception.UnsupportedNotificationTypeException;
import com.patientRegistration.PatientRegistration.model.NotificationType;
import com.patientRegistration.PatientRegistration.service.NotificationService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NotificationFactory {
    private final Map<NotificationType, NotificationService> services;

    public NotificationFactory(List<NotificationService> services) {
        this.services = services.stream()
                .collect(Collectors.toMap(
                        NotificationService::getNotificationType,
                        Function.identity()
                ));
    }

    public NotificationService getNotificationService(NotificationType type) {
        return Optional.ofNullable(services.get(type))
                .orElseThrow(() -> new UnsupportedNotificationTypeException(type));
    }
}
