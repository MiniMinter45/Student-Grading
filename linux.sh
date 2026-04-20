#!/bin/bash

# Define paths
OUT_DIR="out/production/Student-Grading-System"
LIB_DIR="lib/*"
DATA_DIR="data/*"

# Run the program
java -cp "$OUT_DIR:$LIB_DIR:$DATA_DIR" Main
