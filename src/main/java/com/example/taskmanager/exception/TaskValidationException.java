package com.example.taskmanager.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception thrown when task validation fails.
 */
public class TaskValidationException extends RuntimeException {
  private List<String> errors;

  /**
   * Constructs a TaskValidationException with a single error message.
   *
   * @param message the validation error message
   */
  public TaskValidationException(String message) {
    super(message);
    this.errors = new ArrayList<>();
    this.errors.add(message);
  }

  public TaskValidationException(String message, List<String> errors) {
    super(message);
    this.errors = errors;
  }

  public TaskValidationException(List<String> errors) {
    super("Validation failed");
    this.errors = errors;
  }

  public List<String> getErrors() {
    return errors;
  }

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }
}
