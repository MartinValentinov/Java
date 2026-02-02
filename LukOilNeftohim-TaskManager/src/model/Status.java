package model;

public enum Status {

  NEW("new"),
  READY("ready"),
  IN_PROGRESS("in-progress"),
  BLOCKED("blocked"),
  ON_HOLD("on-hold"),
  DONE("done"),
  CANCELLED("cancelled");

  private final String label;

  Status(String label) {
      this.label = label;
  }

  public String label() {
      return label;
  }

  @Override
  public String toString() {
      return label;
  }

  public static Status from(String s) {
      for (Status st : values()) {
          if (st.label.equalsIgnoreCase(s)) {
              return st;
          }
      }
      throw new IllegalArgumentException("Unknown status: " + s);
  }
}
