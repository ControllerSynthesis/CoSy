package main;

import datatypes.basic_java_datatypes.General_Tuple2;
import datatypes.basic_java_datatypes.General_Tuple4;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Measuring_Thread extends Thread {

    private static final int mb = 1024 * 1024;
    private static Logger log = LogManager.getLogger(Measuring_Thread.class.getName());
    final long START = System.currentTimeMillis();
    List<General_Tuple2<String, Long>> stack = new ArrayList<>();
    int min_untaken_index = 0;
    Map<Integer, String> index = new HashMap<>();
    Map<String, General_Tuple2<Long, Long>> data = new HashMap<>();
    long forced_gc = 0;
    long last_gc = 0;
    AtomicBoolean aborted = new AtomicBoolean(false);

    public static void main(String... args) {
        new Measuring_Thread().duration_output(405791);
    }

    public void push(String str) {
        if (!Thread.currentThread().getName().equals("main"))
            return;
        synchronized (stack) {
            if (!data.containsKey(str)) {
                index.put(min_untaken_index, str);
                data.put(str, new General_Tuple2<>(Long.valueOf(0), Long.valueOf(0)));
                min_untaken_index++;
            }
            stack.add(0, new General_Tuple2<>(str, System.currentTimeMillis()));
        }
    }

    public void pop(String str) {
        if (!Thread.currentThread().getName().equals("main"))
            return;
        synchronized (stack) {
            General_Tuple2<String, Long> top = stack.remove(0);

            if (top.getElemA().equals(str)) {
                long current = System.currentTimeMillis();
                long duration = current - top.getElemB();
                General_Tuple2<Long, Long> pair = data.get(str);
                pair.setElemA(pair.getElemA() + 1);
                pair.setElemB(pair.getElemB() + duration);


                for (General_Tuple2<String, Long> elem : stack)
                    elem.setElemB(elem.getElemB() + duration);

            } else {
                throw new RuntimeException(top.toString() + " / " + str);
            }
        }
    }

    public void abort() {
        synchronized (aborted) {
            aborted.set(true);
        }
        interrupt();
    }

    private String duration_output(long time) {
        String result = String.valueOf(time) + "=";
        long factor;

        factor = 1000;
        long result_ms = time % factor;
        time -= result_ms;
        time /= factor;

        factor = 60;
        long result_s = time % factor;
        time -= result_s;
        time /= factor;

        factor = 60;
        long result_min = time % factor;
        time -= result_min;
        time /= factor;

        factor = 24;
        long result_h = time % factor;
        time -= result_h;
        time /= factor;

        long result_d = time;

        String suffix = null;
        int num = 0;
        if (num < 2 && (num > 0 && result_d >= 0 || result_d > 0)) {
            result += (num > 0 ? "." : "") + result_d;
            if (num == 0)
                suffix = "d";
            num++;
        }
        if (num < 2 && (num > 0 && result_h >= 0 || result_h > 0)) {
            result += (num > 0 ? "." : "") + result_h;
            if (num == 0)
                suffix = "h";
            num++;
        }
        if (num < 2 && (num > 0 && result_min >= 0 || result_min > 0)) {
            result += (num > 0 ? "." : "") + result_min;
            if (num == 0)
                suffix = "m";
            num++;
        }
        if (num < 2 && (num > 0 && result_s >= 0 || result_s > 0)) {
            result += (num > 0 ? "." : "") + result_s;
            if (num == 0)
                suffix = "s";
            num++;
        }
        if (num < 2 && (num > 0 && result_ms >= 0 || result_ms > 0)) {
            result += (num > 0 ? "." : "") + result_ms;
            if (num == 0)
                suffix = "ms";
            num++;
        }
        if (suffix == null) {
            result += "0";
            suffix = "ms";
        }


        return result + suffix;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (aborted) {
                if (aborted.get())
                    return;
            }
            try {
                long start = System.currentTimeMillis();
                if (start - last_gc > 30 * 60 * 1000) {
                    System.gc();
                    last_gc = System.currentTimeMillis();
                }
                long end = System.currentTimeMillis();
                forced_gc += (end - start);


                synchronized (stack) {
                    String result = "";
                    if (min_untaken_index > 0) {
                        result += "measure: data: \n";
                        List<General_Tuple4<String, Long, Long, Long>> table = new ArrayList<>();


                        for (int i = 0; i < min_untaken_index; i++) {
                            String str = index.get(i);
                            General_Tuple2<Long, Long> pair = data.get(str);
                            table.add(new General_Tuple4<>(str, pair.getElemA(), pair.getElemB(), (pair.getElemA() == 0 ? 0 : pair.getElemB() / pair.getElemA())));
                        }
                        table.sort(new Comparator<General_Tuple4<String, Long, Long, Long>>() {
                            @Override
                            public int compare(General_Tuple4<String, Long, Long, Long> t1, General_Tuple4<String, Long, Long, Long> t2) {
                                if (t1.getElemC() - t2.getElemC() < 0)
                                    return -1;
                                else if (t1.getElemC() - t2.getElemC() > 0)
                                    return 1;
                                return 0;
                            }
                        });
                        General_Tuple4<Integer, Integer, Integer, Integer> width = new General_Tuple4<>(0, 0, 0, 0);
                        for (General_Tuple4<String, Long, Long, Long> tuple : table) {
                            width.setElemA(Math.max(width.getElemA(), tuple.getElemA().length()));
                            width.setElemB(Math.max(width.getElemB(), ("" + tuple.getElemB()).length()));
                            width.setElemC(Math.max(width.getElemC(), ("" + duration_output(tuple.getElemC())).length()));
                            width.setElemD(Math.max(width.getElemD(), ("" + duration_output(tuple.getElemD())).length()));
                        }
                        for (General_Tuple4<String, Long, Long, Long> tuple : table)
                            result += "measure: "
                                    + extend(tuple.getElemA(), width.getElemA()) + " : "
                                    + extend("" + tuple.getElemB(), width.getElemB()) + " : "
                                    + extend(duration_output(tuple.getElemC()), width.getElemC()) + " : "
                                    + extend(duration_output(tuple.getElemD()), width.getElemD()) + "\n";

                        if (!stack.isEmpty()) {
                            result += "measure: stack: \n";
                            long current = System.currentTimeMillis();
                            for (General_Tuple2<String, Long> elem : stack) {
                                Long duration = (current - elem.getElemB());
                                result += "measure: " + elem.getElemA() + " : " + duration_output(duration) + "\n";
                                current -= duration;
                            }
                        }
                        final long current = System.currentTimeMillis();
                        result += "measure: total-time: " + duration_output(current - START) + "\n";
                        for (String s : Config.instance.local_solver.get_stat())
                            result += "measure: queue: " + s + "\n";

                        result += "measure: gc-time: " + (duration_output(forced_gc)) + "\n";


                        //Getting the runtime reference from system
                        Runtime runtime = Runtime.getRuntime();

                        result += "measure: ##### Heap utilization statistics [MB] #####\n";

                        //Print used memory
                        result += "measure: Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb + "\n";

                        //Print free memory
                        result += "measure: Free Memory:" + runtime.freeMemory() / mb + "\n";

                        //Print total available memory
                        result += "measure: Total Memory:" + runtime.totalMemory() / mb + "\n";

                        //Print Maximum available memory
                        result += "measure: Max Memory:" + runtime.maxMemory() / mb;

//                        result += Config.instance.local_solver.thread_states();

                        log.info(result);
                    }
                }
                sleep(10000);
            } catch (InterruptedException e) {
            }
        }
    }

    private String extend(String string, Integer num) {
        while (string.length() < num)
            string += " ";
        return string;
    }

}

