#!/bin/bash

# author: Sven Schneider
# copyright: all rights reserved, 2018

# This script copies required files to a certain remote host for benchmarking.
# NOTE: for reproducing the benchmarks, the files have to be copied elsewhere with other credentials

cd ../..
if [ ! -d "src" ] 
then
    echo "wrong directory!?"
    exit
fi


if [ "$#" -eq "0" ]
then
  echo "args empty"
  exit
fi

function copyTHIS {
    SERVER=$1

    REMOTEPATH=/home/sschneider/Cosy
    function moveto {
        scp -r $1 sschneider@fb14srv${SERVER}:$REMOTEPATH
    }
    moveto target/CoSy-1.0-jar-with-dependencies.jar
    moveto cosy.xsd
    moveto benchmarking/scripts/run_benchmarks.sh
    moveto src
 }
 

for i in $*
do
    copyTHIS $i
done
