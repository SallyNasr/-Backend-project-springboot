package com.example.Final.assessment.Services;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Service
public class BusinessService {

    public void updateEntity(Map<String, Object> entityDTO, Object entityToUpdate, Class entityToUpdateClass) {
        entityDTO.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(entityToUpdateClass, k);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, entityToUpdate, v);
            }
        });
    }

    public String getMessage(String msgCode, Map<String, Object> params) {

        String defaultMessage = "Default message for code: " + msgCode;

        return defaultMessage;
    }


}

