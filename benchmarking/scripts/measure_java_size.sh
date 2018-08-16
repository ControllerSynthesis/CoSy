#!/bin/bash

# author: Sven Schneider
# copyright: all rights reserved, 2018

# This script collects some 'random' information about CoSy for the thesis.

cd ../..
if [ ! -d "src" ] 
then
    echo "wrong directory!?"
    exit
fi

ClassesALL=$(find src | grep "\.java$" | wc -l)
ClassesDatatypes=$(find src/main/java/datatypes | grep "\.java$" | wc -l)
ClassesMultithread=$(find src/main/java/multithreading | grep "\.java$" | wc -l)
ClassesBuildingBlocks=$(expr $(find src/main/java/operations | grep "\.java$" | wc -l) + $(find src/main/java/supporting_operations | grep "\.java$" | wc -l))
ClassesTest=$(find src/test/java/ | grep "\.java$" | wc -l)

LinesOfCodeAll=$(cat $(find . | grep "\.java$") | wc -l)
LinesOfCodeDuplicate=$(cat $(find . | grep "\.java$") | sort -u | wc -l)

# echo $ClassesALL $ClassesDatatypes $ClassesMultithread $ClassesBuildingBlocks $ClassesTest $LinesOfCodeAll $LinesOfCodeDuplicate

# \num{158} classes
#     \num{55} classes for building blocks, 
#     \num{35} classes for multithread implementations of building blocks, 
#     \num{59} classes for datatypes such as \EPDA, \Parsers, and \CFG
# \num{18153} lines
#     \num{7377} are pairwise distinct
# \num{83} test classes
# 
# 
echo "We implemented the concrete controller synthesis algorithm along the formal \ISABELLE-based definitions in \JAVA using \num{${ClassesALL}} classes (\num{${ClassesBuildingBlocks}} classes for building blocks, \num{${ClassesMultithread}} classes for multithread implementations of building blocks, and \num{${ClassesDatatypes}} classes for datatypes such as \EPDA, \Parsers, and \CFG) with \num{${LinesOfCodeAll}} lines (of which \num{${LinesOfCodeDuplicate}} are pairwise distinct) and \num{${ClassesTest}} test classes." > ../thesis/additional_files/measure_java_size.tex
