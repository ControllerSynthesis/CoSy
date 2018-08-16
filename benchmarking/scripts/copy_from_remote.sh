#!/bin/bash

# author: Sven Schneider
# copyright: all rights reserved, 2018

# This script retrieves results from benchmarking from a remote host.
# NOTE: for reproducing the benchmarks, the files have to be copied from elsewhere with other credentials

cd ../results
if [ ! "$?" -eq "0" ] 
then
    echo "wrong directory!?"
    exit
fi

function average(){
    file=$1
    sum=0
    num=0
    for line in $(cat $file)
    do
        sum=$(echo "$sum + $line" | bc)
        let num++
    done
    echo "$sum / $num" | bc
}

function round(){
    RES=$(echo "scale=3;$1+.5"| bc | sed 's/\..*$//g')
    if [ -z $RES ] 
    then
        echo "0"
    else
        echo $RES
    fi
}

if [ -z $* ]
then
  echo "args empty"
  exit
fi

SERVER=fb14srv${1}
MACHINE=
if [ "${1}" -eq 5 ]
then
    MACHINE=B
fi
if [ "${1}" -eq 6 ]
then
   MACHINE=A
fi
REMOTEPATH=/home/sschneider/Cosy

mkdir -p $SERVER/

for file in $(ssh sschneider@${SERVER} ls -1 $REMOTEPATH | sed -n '/output.*\.txt/p')
do
#     echo $file
#     exit
    scp sschneider@${SERVER}:$REMOTEPATH/$file $SERVER/
done
# exit


rm results_$SERVER.txt
for example in automated_fabrication_scenario_A automated_fabrication_scenario_B automated_fabrication_scenario_CV3 automated_fabrication_scenario_CV2 automated_fabrication_scenario_CV1
do
    echo
    echo $example >>  results_$SERVER.txt
    rm /tmp/copy_from_remote.tmp.txt
    for file in $(ls -1 $SERVER/*.txt)
    do
#         echo $file
        if [ $(echo $file | sed -n '/output_example_'${example}'__ARGS__.*__TIME.txt$/p' | wc -l) -gt 0 ]
        then
            echo $file | sed 's/__TIME.txt$//g' >> /tmp/copy_from_remote.tmp.txt
        fi
    done
    for file in $(cat /tmp/copy_from_remote.tmp.txt | sort -u)
    do
        TIMEms=$(average ${file}__TIME.txt)
        TIMEs=$(round "$TIMEms / 1000")
        TIMEm=$(echo "$TIMEs / 60" | bc)
        TIMEs=$(echo "$TIMEs - 60 * $TIMEm" | bc)              
        SPACEact=$(round "$(average ${file}__SPACE.txt) / 1024 / 1024")
        CPUact=$(round "$(average ${file}__CPU.txt)")
        approximationvalue=$(echo $file | sed 's/^.*\/output_.*__ARGS__approximationvalue_\([0-9]*\)__MEMORY_\([0-9A-Z]*\)__RUN_CONTROLLER_SYNTHESIS_\([a-z]*\)/\1/g')
        MEMORY=$(echo $file | sed 's/^.*\/output_.*__ARGS__approximationvalue_\([0-9]*\)__MEMORY_\([0-9A-Z]*\)__RUN_CONTROLLER_SYNTHESIS_\([a-z]*\)/\2/g')
        RUN_CONTROLLER_SYNTHESIS=$(echo $file | sed 's/^.*\/output_.*__ARGS__approximationvalue_\([0-9]*\)__MEMORY_\([0-9A-Z]*\)__RUN_CONTROLLER_SYNTHESIS_\([a-z]*\)/\3/g')
#             echo $file
        printf "\\BENCHMARKentry{${MACHINE}}{%5s}{%1s}{%3s}{%2s}{%2s}{%2s}{%3s}%% %s\n" $RUN_CONTROLLER_SYNTHESIS $approximationvalue $MEMORY $SPACEact $TIMEm $TIMEs $CPUact $(cat ${file}__TIME.txt | wc -l) >> results_$SERVER.txt
    done
done

# # # # 
# # # # function moveto {
# # # #     scp -r $1 
# # # # }
# # # # moveto out/artifacts/cosy_jar/cosy.jar
# # # # moveto cosy.xsd
# # # # moveto run.sh
# # # # moveto view.sh
# # # # moveto src/log4j2.xml
# # # # moveto test_cases
# # # # # moveto run.sh
# # # # 
