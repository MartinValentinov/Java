JAVAC = javac
JAVA  = java
SRC_DIR = src
OUT_DIR = out
MAIN_CLASS = Main

SOURCES := $(shell find $(SRC_DIR) -name "*.java")

all: $(OUT_DIR)/classes.stamp

$(OUT_DIR)/classes.stamp: $(SOURCES)
	mkdir -p $(OUT_DIR)
	$(JAVAC) -d $(OUT_DIR) $(SOURCES)

run: all
	$(JAVA) -cp $(OUT_DIR) $(MAIN_CLASS)

clean:
	rm -rf $(OUT_DIR)

.PHONY: all run clean
