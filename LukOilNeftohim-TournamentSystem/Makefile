SRC_DIR := src
OUT_DIR := out

.PHONY: compile clean run

all: compile run clean

compile:
	javac -d out $(shell find . -name "*.java")

run: compile
	java -cp $(OUT_DIR) Main

clean:
	rm -rf $(OUT_DIR)/*
