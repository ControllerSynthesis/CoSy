#!/bin/bash

# author: Sven Schneider
# copyright: all rights reserved, 2018

# This script executes benchmarks.
# NOTE: for reproducing the benchmarks, the different names of the machines and the different available tools will require adaptation of the script below.


THISBASH=$$
# echo $THISBASH
trap ctrl_c INT

function ctrl_c() {
    echo "** Trapped CTRL-C: killing with all grandchildren"
    #https://stackoverflow.com/a/15139734
    kill -- -$(ps -o pgid= $THISBASH | grep -o [0-9]*)
}

function test {
#     echo "========================================="
    echo "started: $(date)"
    echo "started: $(date)" > send.txt
    cat send.txt | mail -s "${HOSTNAME} done : ${TITLE}" sven.schneider@hpi.de
    TITLE=
    MEMORY=
    LOG4J=

    if [ "$#" -eq "0" ]
    then
        echo "TITLE              : title of test"
        echo "MEMORY             : a number for GiB or MAX for the maximum of the machine"
        echo "LOG4J              : either DEVELOPMENT or PRODUCTIVE"

    fi

    while [ "$#" -gt "0" ]
    do
        ##### TITLE
        if [ "$1" = "TITLE" ]
        then
            shift
            TITLE=$1
            #echo "TITLE=$TITLE"
            shift
            continue
        fi

        ##### MEMORY
        if [ "$1" = "MEMORY" ]
        then
            shift
            if [ "$1" = "MAX" ]
            then
                if [ "$HOSTNAME" = "fb14srv5" ]
                then
                    MEMORY=355
                fi
                if [ "$HOSTNAME" = "fb14srv6" ]
                then
                    MEMORY=230
                fi
            else
                if [[ $1 =~ ^-?[0-9]+$ ]]
                then
                    MEMORY=$1
                else
                    echo "invalid MEMORY argument = $1"
                    exit
                fi
            fi
            #echo "MEMORY=$MEMORY"
            shift
            continue
        fi

        ##### LOG4J
        if [ "$1" = "LOG4J" ]
        then
            shift
            if [ "$1" = "DEVELOPMENT" ]
            then
                LOG4J=log4j2_development.xml
            else
                if [ "$1" = "PRODUCTIVE" ]
                then
                    LOG4J=log4j2_productive.xml
                else
                    echo "invalid LOG4J argument = $1"
                fi
            fi
            #echo "LOG4J=$LOG4J"
            shift
            continue
        fi

        ##### abort --
        if [ "$1" = "--" ]
        then
            shift
            break
        fi

        echo "UNEXPECTED ARGUMENT $1"
        exit
    done

    echo "test (jar date: "$(stat -c %y CoSy-1.0-jar-with-dependencies.jar)")"
    echo "arguments: TITLE                    = ${TITLE}"
    echo "arguments: MEMORY                   = ${MEMORY}"
    echo "arguments: LOG4J                    = ${LOG4J}"
    echo "arguments: (forwarded)              = $*"

    JAVA_PATH="/usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java"
    JAVA_ARGS="-Xmx${MEMORY}G -XX:+CrashOnOutOfMemoryError "
    TIMEFORMAT="-v"
    # the parameter "-Dlog4j.configurationFile=$LOG4J" is no longer necessary as the configuration file is in the jar now
    /usr/bin/time ${TIMEFORMAT} ${JAVA_PATH} ${JAVA_ARGS} -cp CoSy-1.0-jar-with-dependencies.jar main.Main $* 2>&1 | tee output_${TITLE}.txt
    # OBTAIN EXIT CODE FROM FIRST PIPE!
    RETVAL=${PIPESTATUS[0]}
    cat output_${TITLE}.txt | tail -n 500 > send.txt
    cat send.txt | mail -s "${HOSTNAME} done : ${TITLE}" sven.schneider@hpi.de
#     echo "========================================="
    return $RETVAL
}

function convertTime(){
    VAL=$1
    if [ $(echo $VAL| sed -n '/[0-9]*:[0-9]*:[0-9]*/p' | wc -l) -gt 0 ]
    then
#         echo $VAL:3
        VALh=$(echo $VAL| sed 's/\([0-9]*\):\([0-9]*\):\([0-9]*\)$/\1/g')
        VALm=$(echo $VAL| sed 's/\([0-9]*\):\([0-9]*\):\([0-9]*\)$/\2/g')
        VALs=$(echo $VAL| sed 's/\([0-9]*\):\([0-9]*\):\([0-9]*\)$/\3/g')
        VALms=0
    else
#         echo $VAL:2
        VALh=0
        VALm=$(echo $VAL| sed 's/\([0-9]*\):\([0-9]*\)\.\([0-9]*\)$/\1/g')
        VALs=$(echo $VAL| sed 's/\([0-9]*\):\([0-9]*\)\.\([0-9]*\)$/\2/g')
        VALms=$(echo $VAL| sed 's/\([0-9]*\):\([0-9]*\)\.\([0-9]*\)$/\3/g')
    fi
#     echo VALh=$VALh VALm=$VALm VALs=$VALs VALms=$VALms
    echo "((($VALh * 60) + $VALm) * 60 +$VALs)* 100 + $VALms" | bc
}
# convertTime 0:0.22
# convertTime 0:1.22
# convertTime 12:33.22
# convertTime 21:12:33
# exit

function test_example() {
    echo "test_example $*"
    EXAMPLE=$1
    shift
    SUBTITLE=$1
    shift
    TIMEOUT=$1
    shift
    ITERATIONS=$1
    shift
    RUN_CONTROLLER_SYNTHESIS=$1
    shift
    MEMORY=$1
    shift
    MIN_approximationvalue=$1
    shift
    MAX_approximationvalue=$1
    shift
    LOG4J=$1
    shift
    debug_computations=$1
    shift
    echo "test_example"
    echo "arguments: EXAMPLE                  = ${EXAMPLE}"
    echo "arguments: SUBTITLE                 = ${SUBTITLE}"
    echo "arguments: TIMEOUT                  = ${TIMEOUT}"
    echo "arguments: ITERATIONS               = ${ITERATIONS}"
    echo "arguments: RUN_CONTROLLER_SYNTHESIS = ${RUN_CONTROLLER_SYNTHESIS}"
    echo "arguments: MEMORY                   = ${MEMORY}"
    echo "arguments: MIN_approximationvalue   = ${MIN_approximationvalue}"
    echo "arguments: MAX_approximationvalue   = ${MAX_approximationvalue}"
    echo "arguments: LOG4J                    = ${LOG4J}"
    echo "arguments: debug_computations       = ${debug_computations}"
    echo "arguments: (forwarded)              = $*"
    for approximationvalue in `seq ${MIN_approximationvalue} ${MAX_approximationvalue}`
    do
        for iteration in `seq 1 ${ITERATIONS}`
        do
            echo
            RESULTFILE="${EXAMPLE}${SUBTITLE}__ARGS__approximationvalue_${approximationvalue}__MEMORY_${MEMORY}__RUN_CONTROLLER_SYNTHESIS_${RUN_CONTROLLER_SYNTHESIS}"
            TITLE="${RESULTFILE}__iteration_${iteration}"
            echo "$TITLE"
            if [ -f "exit_value.tmp" ]
            then
                rm exit_value.tmp
            fi
            (
                test \
                    TITLE ${TITLE} \
                    MEMORY ${MEMORY} \
                    LOG4J ${LOG4J} \
                    -- \
                    debug_computations ${debug_computations} \
                    run_controller_synthesis ${RUN_CONTROLLER_SYNTHESIS} \
                    approximationvalue ${approximationvalue} \
                    example ${EXAMPLE} \
                    -- $*
                EXITVALUE=$?
                echo "EXITVALUE=$EXITVALUE"
                echo "$EXITVALUE" > exit_value.tmp
                if [ "$EXITVALUE" -eq "0" ]
                then
                    echo ":: SUCCESS :: $EXITVALUE"
                    cat output_${TITLE}.txt | grep "Maximum resident set size" | sed 's/^.*: //g' >> output_${RESULTFILE}__SPACE.txt
                    cat output_${TITLE}.txt | grep "Percent of CPU" | sed 's/^.*: //g;s/%//g' >> output_${RESULTFILE}__CPU.txt
                    echo "$(convertTime $(cat output_${TITLE}.txt | grep "wall clock" | sed 's/^.*): //g'))0" >> output_${RESULTFILE}__TIME.txt
                fi
            ) &
            pid=$!
            timeout $TIMEOUT tail --pid=$pid -f /dev/null
            if [ "$?" -gt "0" ]
            then
                killall -9 java
                echo ":: TIMEOUT ::"
                echo ":: TIMEOUT ::" >> output_${TITLE}.txt
#                 echo "========================================="
            fi
            if [ -f "exit_value.tmp" ]
            then
                EXITVALUE=$(cat exit_value.tmp)
                if [ "$EXITVALUE" -gt "0" ]
                then
                    echo ":: JAVA ERROR ::"
                    echo ":: JAVA ERROR ::" >> output_${TITLE}.txt
    #                 echo "========================================="
                fi
            fi
        done
    done
}

if [ "${HOSTNAME}" = "fb14srv6" ]
then
    MODE=PRODUCTIVE
#     MODE=DEVELOPMENT

    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    echo testA
    
    echo COMPUTE=FALSE
    test_example example_automated_fabrication_scenario_A "" 20 10 false 230 0 0 $MODE false
    
#     AAvalue=0
#     echo COMPUTE=TRUE AA=$AAvalue
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 230 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 128 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 64 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 32 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 16 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 8 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 4 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 2 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 1 $AAvalue $AAvalue $MODE false
    
    AAvalue=1
    echo COMPUTE=TRUE AA=$AAvalue
    test_example example_automated_fabrication_scenario_A "" 300 10 true 230 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 128 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 64 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 32 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 16 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 8 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 4 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 2 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 1 $AAvalue $AAvalue $MODE false
    
    AAvalue=2
    echo COMPUTE=TRUE AA=$AAvalue
    test_example example_automated_fabrication_scenario_A "" 300 10 true 230 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 128 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 64 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 32 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 16 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 8 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 4 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 2 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 1 $AAvalue $AAvalue $MODE false
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    echo testB
    
    echo COMPUTE=FALSE
    test_example example_automated_fabrication_scenario_B "" 20 10 false 230 0 0 $MODE false
    
    AAvalue=0
    echo COMPUTE=TRUE AA=$AAvalue
    test_example example_automated_fabrication_scenario_B "" 300 10 true 230 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 128 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 64 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 32 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 16 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 8 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 4 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 2 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 1 $AAvalue $AAvalue $MODE false
    
    AAvalue=1
    echo COMPUTE=TRUE AA=$AAvalue
    test_example example_automated_fabrication_scenario_B "" 300 10 true 230 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 128 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 64 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 32 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 16 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 8 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 4 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 2 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 1 $AAvalue $AAvalue $MODE false
    
    AAvalue=2
    echo COMPUTE=TRUE AA=$AAvalue
    test_example example_automated_fabrication_scenario_B "" 300 10 true 230 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 128 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 64 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 32 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 16 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 8 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 4 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 2 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 1 $AAvalue $AAvalue $MODE false
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    echo testCV3
    
    echo COMPUTE=FALSE
    test_example example_automated_fabrication_scenario_C V3 20 10 false 230 0 0 $MODE false 0 1 1 2 1
    
#     AAvalue=0
#     echo COMPUTE=TRUE AA=$AAvalue
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 230 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 128 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 64 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 32 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 16 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 8 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 4 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 2 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 1 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    
    AAvalue=1
    echo COMPUTE=TRUE AA=$AAvalue
    test_example example_automated_fabrication_scenario_C V3 500 10 true 230 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 128 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 64 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 32 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 16 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 8 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 4 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 2 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 1 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    
#     AAvalue=2
#     echo COMPUTE=TRUE AA=$AAvalue
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 230 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 128 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 64 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 32 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 16 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 8 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 4 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 2 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 1 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    echo testCV2
    
    echo COMPUTE=FALSE
    test_example example_automated_fabrication_scenario_C V2 20 10 false 230 0 0 $MODE false 1 1 1 3 1
    
#     AAvalue=0
#     echo COMPUTE=TRUE AA=$AAvalue
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 230 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 128 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 64 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 32 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 16 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 8 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 4 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 2 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 1 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    
    AAvalue=1
    echo COMPUTE=TRUE AA=$AAvalue
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 230 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 128 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 64 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 32 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 16 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 8 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 4 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 2 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 1 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    
#     AAvalue=2
#     echo COMPUTE=TRUE AA=$AAvalue
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 230 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 128 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 64 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 32 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 16 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 8 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 4 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 2 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 1 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    echo testCV1
    
    echo COMPUTE=FALSE
    test_example example_automated_fabrication_scenario_C V1 20 10 false 230 0 0 $MODE false 1 1 1 5 2
    
#     AAvalue=0
#     echo COMPUTE=TRUE AA=$AAvalue
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 230 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 128 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 64 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 32 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 16 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 8 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 4 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 2 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 1 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    
    AAvalue=1
    echo COMPUTE=TRUE AA=$AAvalue
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 230 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 128 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 64 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 32 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 16 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 8 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 4 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 2 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 1 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    
#     AAvalue=2
#     echo COMPUTE=TRUE AA=$AAvalue
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 230 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 128 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 64 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 32 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 16 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 8 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 4 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 2 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 1 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    

fi
if [ "${HOSTNAME}" = "fb14srv5" ]
then
    MODE=PRODUCTIVE
#     MODE=DEVELOPMENT

    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    echo testA
    
    echo COMPUTE=FALSE
    test_example example_automated_fabrication_scenario_A "" 20 10 false 355 0 0 $MODE false
    
#     AAvalue=0
#     echo COMPUTE=TRUE AA=$AAvalue
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 355 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 256 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 128 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 64 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 32 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 16 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 8 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 4 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 2 $AAvalue $AAvalue $MODE false
#     test_example example_automated_fabrication_scenario_A "" 300 10 true 1 $AAvalue $AAvalue $MODE false
    
    AAvalue=1
    echo COMPUTE=TRUE AA=$AAvalue
    test_example example_automated_fabrication_scenario_A "" 300 10 true 355 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 256 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 128 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 64 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 32 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 16 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 8 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 4 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 2 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 1 $AAvalue $AAvalue $MODE false
    
    AAvalue=2
    echo COMPUTE=TRUE AA=$AAvalue
    test_example example_automated_fabrication_scenario_A "" 300 10 true 355 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 256 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 128 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 64 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 32 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 16 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 8 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 4 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 2 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_A "" 300 10 true 1 $AAvalue $AAvalue $MODE false
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    echo testB
    
    echo COMPUTE=FALSE
    test_example example_automated_fabrication_scenario_B "" 20 10 false 355 0 0 $MODE false
    
    AAvalue=0
    echo COMPUTE=TRUE AA=$AAvalue
    test_example example_automated_fabrication_scenario_B "" 300 10 true 355 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 256 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 128 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 64 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 32 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 16 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 8 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 4 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 2 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 1 $AAvalue $AAvalue $MODE false
    
    AAvalue=1
    echo COMPUTE=TRUE AA=$AAvalue
    test_example example_automated_fabrication_scenario_B "" 300 10 true 355 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 256 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 128 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 64 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 32 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 16 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 8 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 4 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 2 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 1 $AAvalue $AAvalue $MODE false
    
    AAvalue=2
    echo COMPUTE=TRUE AA=$AAvalue
    test_example example_automated_fabrication_scenario_B "" 300 10 true 355 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 256 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 128 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 64 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 32 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 16 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 8 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 4 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 2 $AAvalue $AAvalue $MODE false
    test_example example_automated_fabrication_scenario_B "" 300 10 true 1 $AAvalue $AAvalue $MODE false
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    echo testCV3
    
    echo COMPUTE=FALSE
    test_example example_automated_fabrication_scenario_C V3 20 10 false 355 0 0 $MODE false 0 1 1 2 1
    
#     AAvalue=0
#     echo COMPUTE=TRUE AA=$AAvalue
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 355 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 256 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 128 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 64 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 32 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 16 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 8 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 4 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 2 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 1 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    
    AAvalue=1
    echo COMPUTE=TRUE AA=$AAvalue
    test_example example_automated_fabrication_scenario_C V3 500 10 true 355 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 256 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 128 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 64 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 32 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 16 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 8 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 4 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 2 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    test_example example_automated_fabrication_scenario_C V3 500 10 true 1 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    
#     AAvalue=2
#     echo COMPUTE=TRUE AA=$AAvalue
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 355 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 256 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 128 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 64 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 32 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 16 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 8 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 4 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 2 $AAvalue $AAvalue $MODE false 0 1 1 2 1
#     test_example example_automated_fabrication_scenario_C V3 500 10 true 1 $AAvalue $AAvalue $MODE false 0 1 1 2 1
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    echo testCV2
    
    echo COMPUTE=FALSE
    test_example example_automated_fabrication_scenario_C V2 20 10 false 355 0 0 $MODE false 1 1 1 3 1
    
#     AAvalue=0
#     echo COMPUTE=TRUE AA=$AAvalue
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 355 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 256 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 128 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 64 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 32 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 16 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 8 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 4 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 2 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 1 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    
    AAvalue=1
    echo COMPUTE=TRUE AA=$AAvalue
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 355 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 256 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 128 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 64 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 32 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 16 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 8 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 4 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 2 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    test_example example_automated_fabrication_scenario_C V2 86400 10 true 1 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    
#     AAvalue=2
#     echo COMPUTE=TRUE AA=$AAvalue
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 355 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 256 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 128 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 64 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 32 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 16 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 8 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 4 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 2 $AAvalue $AAvalue $MODE false 1 1 1 3 1
#     test_example example_automated_fabrication_scenario_C V2 86400 10 true 1 $AAvalue $AAvalue $MODE false 1 1 1 3 1
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    echo testCV1
    
    echo COMPUTE=FALSE
    test_example example_automated_fabrication_scenario_C V1 20 10 false 355 0 0 $MODE false 1 1 1 5 2
    
#     AAvalue=0
#     echo COMPUTE=TRUE AA=$AAvalue
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 355 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 256 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 128 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 64 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 32 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 16 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 8 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 4 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 2 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 1 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    
    AAvalue=1
    echo COMPUTE=TRUE AA=$AAvalue
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 355 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 256 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 128 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 64 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 32 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 16 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 8 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 4 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 2 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    test_example example_automated_fabrication_scenario_C V1 86400 10 true 1 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    
#     AAvalue=2
#     echo COMPUTE=TRUE AA=$AAvalue
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 355 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 256 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 128 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 64 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 32 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 16 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 8 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 4 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 2 $AAvalue $AAvalue $MODE false 1 1 1 5 2
#     test_example example_automated_fabrication_scenario_C V1 86400 10 true 1 $AAvalue $AAvalue $MODE false 1 1 1 5 2
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    

fi



