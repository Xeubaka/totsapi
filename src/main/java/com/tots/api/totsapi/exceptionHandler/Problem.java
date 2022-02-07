package com.tots.api.totsapi.exceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Problem {
    
    private Integer status;
    private OffsetDateTime dateTime;
    private String title;
    private List<Field> field;

    public static class Field {

        private String field;
        private String message;

        public Field(String field, String message) {
            super();
            this.setField(field);
            this.setMessage(message);
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        
    }

    public List<Field> getField() {
        return field;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setField(List<Field> field) {
        this.field = field;
    }
}
