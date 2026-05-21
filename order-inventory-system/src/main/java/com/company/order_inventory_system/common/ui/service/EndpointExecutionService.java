package com.company.order_inventory_system.common.ui.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.*;

import org.springframework.stereotype.Service;

import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class EndpointExecutionService {

    private final RestTemplate restTemplate;

    public EndpointExecutionService(
            RestTemplate restTemplate
    ) {

        this.restTemplate = restTemplate;
    }

    public Map<String, Object> executeGetRequest(
            String url,
            String username,
            String password
    ) {
        Map<String, Object> result = new HashMap<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Object> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Object.class
            );

            result.put("success", true);
            result.put("statusCode", response.getStatusCode().value());
            processResponseBody(response.getBody(), result, response.getStatusCode().value());
        }
        catch (HttpStatusCodeException ex) {
            handleException(ex, result);
        }
        catch (Exception ex) {
            handleGenericException(ex, result);
        }
        return result;
    }

    public Map<String, Object> executePostRequest(
            String url,
            Object requestBody,
            String username,
            String password
    ) {
        Map<String, Object> result = new HashMap<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Object> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    Object.class
            );

            result.put("success", true);
            result.put("statusCode", response.getStatusCode().value());
            processResponseBody(response.getBody(), result, response.getStatusCode().value());
        }
        catch (HttpStatusCodeException ex) {
            handleException(ex, result);
        }
        catch (Exception ex) {
            handleGenericException(ex, result);
        }
        return result;
    }

    public Map<String, Object> executePutRequest(
            String url,
            Object requestBody,
            String username,
            String password
    ) {
        Map<String, Object> result = new HashMap<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Object> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    entity,
                    Object.class
            );

            result.put("success", true);
            result.put("statusCode", response.getStatusCode().value());
            processResponseBody(response.getBody(), result, response.getStatusCode().value());
        }
        catch (HttpStatusCodeException ex) {
            handleException(ex, result);
        }
        catch (Exception ex) {
            handleGenericException(ex, result);
        }
        return result;
    }

    public Map<String, Object> executeDeleteRequest(
            String url,
            String username,
            String password
    ) {
        Map<String, Object> result = new HashMap<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Object> response = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    entity,
                    Object.class
            );

            result.put("success", true);
            result.put("statusCode", response.getStatusCode().value());
            processResponseBody(response.getBody(), result, response.getStatusCode().value());
        }
        catch (HttpStatusCodeException ex) {
            handleException(ex, result);
        }
        catch (Exception ex) {
            handleGenericException(ex, result);
        }
        return result;
    }

    public Map<String, Object> executeMultipartPostRequest(
            String url,
            String paramName,
            org.springframework.web.multipart.MultipartFile file,
            String username,
            String password
    ) {
        Map<String, Object> result = new HashMap<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            org.springframework.util.MultiValueMap<String, Object> body = new org.springframework.util.LinkedMultiValueMap<>();
            
            org.springframework.core.io.Resource resource = new org.springframework.core.io.ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
            
            body.add(paramName, resource);

            HttpEntity<org.springframework.util.MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Object> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    Object.class
            );

            result.put("success", true);
            result.put("statusCode", response.getStatusCode().value());
            processResponseBody(response.getBody(), result, response.getStatusCode().value());
        }
        catch (HttpStatusCodeException ex) {
            handleException(ex, result);
        }
        catch (Exception ex) {
            handleGenericException(ex, result);
        }
        return result;
    }

    private void handleException(HttpStatusCodeException ex, Map<String, Object> result) {
        result.put("success", false);
        result.put("statusCode", ex.getStatusCode().value());
        String body = ex.getResponseBodyAsString();
        result.put("responseData", body);

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> errorMap = mapper.readValue(body, new TypeReference<Map<String, Object>>() {});
            if (errorMap.containsKey("error") || errorMap.containsKey("status") || errorMap.containsKey("message")) {
                result.put("errorResponse", errorMap);
            } else {
                result.put("validationErrors", errorMap);
            }
        } catch (Exception e) {
            // ignore
        }
    }

    private void handleGenericException(Exception ex, Map<String, Object> result) {
        result.put("success", false);
        result.put("statusCode", 500);
        result.put("responseData", ex.getMessage());
        ex.printStackTrace();
    }

    private void processResponseBody(Object responseBody, Map<String, Object> result, int statusCode) {
        if (responseBody == null) {
            return;
        }
        ObjectMapper mapper = new ObjectMapper();

        try {
            String jsonResponse = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(responseBody);
            result.put("responseData", jsonResponse);
        } catch (Exception e) {
            result.put("responseData", String.valueOf(responseBody));
        }

        List<Map<String, Object>> tableData = new ArrayList<>();
        List<String> columns = new ArrayList<>();

        Object unwrapped = responseBody;
        String successMessage = null;
        if (responseBody instanceof Map<?, ?> map) {
            if (map.containsKey("message") && map.get("message") instanceof String msg) {
                successMessage = msg;
            }
            if (map.containsKey("data")) {
                Object dataVal = map.get("data");
                if (dataVal != null) {
                    unwrapped = dataVal;
                }
            }
        }

        if (successMessage != null) {
            result.put("successMessage", successMessage);
        }

        if (unwrapped instanceof List<?> responseList) {
            if (!responseList.isEmpty()) {
                for (Object item : responseList) {
                    try {
                        Map<String, Object> row = mapper.convertValue(
                                item,
                                new TypeReference<Map<String, Object>>() {}
                        );
                        tableData.add(flattenRow(row));
                    } catch (Exception e) {
                        Map<String, Object> row = new LinkedHashMap<>();
                        row.put("Value", String.valueOf(item));
                        tableData.add(row);
                    }
                }
                if (!tableData.isEmpty()) {
                    columns = new ArrayList<>(tableData.get(0).keySet());
                }
            }
        } else if (unwrapped instanceof Map<?, ?> map) {
            try {
                Map<String, Object> row = mapper.convertValue(
                        unwrapped,
                        new TypeReference<Map<String, Object>>() {}
                );
                Map<String, Object> flattened = flattenRow(row);
                tableData.add(flattened);
                columns = new ArrayList<>(flattened.keySet());
            } catch (Exception e) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("Value", String.valueOf(unwrapped));
                tableData.add(row);
                columns = List.of("Value");
            }
        } else {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("Result", String.valueOf(unwrapped));
            tableData.add(row);
            columns = List.of("Result");
        }

        result.put("tableData", tableData);
        result.put("columns", columns);
    }

    private Map<String, Object> flattenRow(Map<String, Object> row) {
        Map<String, Object> flattened = new LinkedHashMap<>();
        if (row == null) {
            return flattened;
        }
        flattenMapRecursive(row, "", flattened);
        return flattened;
    }

    private void flattenMapRecursive(Map<?, ?> map, String prefix, Map<String, Object> target) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = prefix.isEmpty() ? String.valueOf(entry.getKey()) : prefix + "." + entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map<?, ?> nestedMap) {
                flattenMapRecursive(nestedMap, key, target);
            } else if (value instanceof List<?> list) {
                target.put(key, list.toString());
            } else {
                target.put(key, value);
            }
        }
    }
}