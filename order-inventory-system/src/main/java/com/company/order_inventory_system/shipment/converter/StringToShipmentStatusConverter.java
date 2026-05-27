package com.company.order_inventory_system.shipment.converter;

import com.company.order_inventory_system.shipment.enums.ShipmentStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToShipmentStatusConverter
        implements Converter<String, ShipmentStatus> {

    @Override
    public ShipmentStatus convert(String source) {
        return ShipmentStatus.fromValue(source);
    }
}
