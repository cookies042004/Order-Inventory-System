package com.company.order_inventory_system.shipment.converter;

import com.company.order_inventory_system.shipment.enums.ShipmentStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)

public class ShipmentStatusConverter
        implements AttributeConverter<ShipmentStatus, String> {

    @Override
    public String convertToDatabaseColumn(
            ShipmentStatus shipmentStatus) {

        if (shipmentStatus == null) {
            return null;
        }

        return shipmentStatus.getValue();
    }

    @Override
    public ShipmentStatus convertToEntityAttribute(
            String dbData) {

        if (dbData == null) {
            return null;
        }

        return ShipmentStatus.fromValue(dbData);
    }
}