package datatypes.formalisms;

import datatypes.basic_datatypes_of_algorithm.option__None;
import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.option__abstract;
import datatypes.basic_java_datatypes.*;
import datatypes.formalisms.language_check.Comparison_inclusion;
import main.Config;
import multithreading.DETERMINE_SHORTEST_MARKED_WORDS.DETERMINE_SHORTEST_MARKED_WORDS_Problem;
import multithreading.DETERMINE_SHORTEST_MARKED_WORDS.DETERMINE_SHORTEST_MARKED_WORDS_Store_Result_Callback;
import multithreading.general.abstract_components.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class epda<state extends General, event extends General, stack extends General> implements General {

    public static final int POP = 0;
    public static final int PUSH = 1;
    public static final int EXECUTE = 2;
    private static Logger log = LogManager.getLogger(epda.class.getName());
    private static int info_on_reachability_COUNT = 0;
    Map<event, List<epda_step_label<state, event, stack>>> epda_step_labels_by_event = null;
    Map<state, Map<option__abstract<event>, Map<stack, List<epda_step_label<state, event, stack>>>>> map_source_execute_pop = null;
    Map<state, Map<stack, List<epda_step_label<state, event, stack>>>> map_source_pop_edges = null;
    Map<state, Map<stack, List<epda_step_label<state, event, stack>>>> map_source_stack_edges = null;
    Map<state, Map<stack, List<epda_step_label<state, event, stack>>>> map_target_pop_edges = null;
    Map<state, Map<option__abstract<event>, List<epda_step_label<state, event, stack>>>> map_target_event = null;
    Map<Integer, Map<state, Map<stack, Set<epda_step_label<state, event, stack>>>>> edges_by_type_and_stack_and_source = null;
    private List<state> epda_states;
    private List<event> epda_events;
    private List<stack> epda_gamma;
    private List<epda_step_label<state, event, stack>> epda_delta;
    private state epda_initial;
    private stack epda_box;
    private List<state> epda_marking;
    private int cached_hashcode = 0;
    private Map<state, Map<option__abstract<event>, List<epda_step_label<state, event, stack>>>> map_source_execute = null;
    private Map<state, List<epda_step_label<state, event, stack>>> map_source = null;
    private Map<state, List<epda_step_label<state, event, stack>>> map_target = null;
    private Map<Integer, Map<state, General_Set<General_List<stack>>>> approximate_accessible_stack = null;

    public epda(List<state> epda_states,
                List<event> epda_events,
                List<stack> epda_gamma,
                List<epda_step_label<state, event, stack>> epda_delta,
                state epda_initial,
                stack epda_box,
                List<state> epda_marking) {
        this.epda_states = Collections.unmodifiableList(epda_states);
        this.epda_events = Collections.unmodifiableList(epda_events);
        this.epda_gamma = Collections.unmodifiableList(epda_gamma);
        this.epda_delta = Collections.unmodifiableList(epda_delta);
        this.epda_initial = epda_initial;
        this.epda_box = epda_box;
        this.epda_marking = Collections.unmodifiableList(epda_marking);
    }

    public static <state extends General,
            event extends General,
            stack extends General>
    boolean check_word_inclusion(epda<state, event, stack> epda, List<event> word, int expected) {
        if (General_Test_Sequence.MARK == expected)
            return new Comparison_inclusion<
                    epda<state, event, stack>,
                    epdaH_semantics<state, event, stack>,
                    epdaH_configuration<state, event, stack>,
                    epda_step_label<state, event, stack>,
                    epda<state, event, stack>,
                    epdaH_semantics<state, event, stack>,
                    epdaH_configuration<state, event, stack>,
                    epda_step_label<state, event, stack>,
                    event>()
                    .compare_check_word(
                            epda,
                            new epdaH_semantics<>(),
                            true,
                            word,
                            Long.MAX_VALUE,
                            Long.MAX_VALUE);
        else if (General_Test_Sequence.UNMARK == expected)
            return new Comparison_inclusion<
                    epda<state, event, stack>,
                    epdaH_semantics<state, event, stack>,
                    epdaH_configuration<state, event, stack>,
                    epda_step_label<state, event, stack>,
                    epda<state, event, stack>,
                    epdaH_semantics<state, event, stack>,
                    epdaH_configuration<state, event, stack>,
                    epda_step_label<state, event, stack>,
                    event>()
                    .compare_check_word(
                            epda,
                            new epdaH_semantics<>(),
                            false,
                            word,
                            Long.MAX_VALUE,
                            Long.MAX_VALUE);
        else if (General_Test_Sequence.REJECT == expected)
            return !new Comparison_inclusion<
                    epda<state, event, stack>,
                    epdaH_semantics<state, event, stack>,
                    epdaH_configuration<state, event, stack>,
                    epda_step_label<state, event, stack>,
                    epda<state, event, stack>,
                    epdaH_semantics<state, event, stack>,
                    epdaH_configuration<state, event, stack>,
                    epda_step_label<state, event, stack>,
                    event>()
                    .compare_check_word(
                            epda,
                            new epdaH_semantics<>(),
                            false,
                            word,
                            Long.MAX_VALUE,
                            Long.MAX_VALUE);
        else throw new RuntimeException();
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        epda<?, ?, ?> epda = (epda<?, ?, ?>) o;

        if (epda_states != null ? !epda_states.equals(epda.epda_states) : epda.epda_states != null) return false;
        if (epda_events != null ? !epda_events.equals(epda.epda_events) : epda.epda_events != null) return false;
        if (epda_gamma != null ? !epda_gamma.equals(epda.epda_gamma) : epda.epda_gamma != null) return false;
        if (epda_delta != null ? !epda_delta.equals(epda.epda_delta) : epda.epda_delta != null) return false;
        if (epda_initial != null ? !epda_initial.equals(epda.epda_initial) : epda.epda_initial != null) return false;
        if (epda_box != null ? !epda_box.equals(epda.epda_box) : epda.epda_box != null) return false;
        return epda_marking != null ? epda_marking.equals(epda.epda_marking) : epda.epda_marking == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0) {
            int result = epda_states != null ? epda_states.hashCode() : 0;
            result = 31 * result + (epda_events != null ? epda_events.hashCode() : 0);
            result = 31 * result + (epda_gamma != null ? epda_gamma.hashCode() : 0);
            result = 31 * result + (epda_delta != null ? epda_delta.hashCode() : 0);
            result = 31 * result + (epda_initial != null ? epda_initial.hashCode() : 0);
            result = 31 * result + (epda_box != null ? epda_box.hashCode() : 0);
            result = 31 * result + (epda_marking != null ? epda_marking.hashCode() : 0);
            cached_hashcode = result;
        }
        return cached_hashcode;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        append(stringBuffer);
        return stringBuffer.toString();
    }

    public List<state> get_epda_states() {
        return epda_states;
    }

    public List<event> get_epda_events() {
        return epda_events;
    }

    public List<stack> get_epda_gamma() {
        return epda_gamma;
    }

    public List<epda_step_label<state, event, stack>> get_epda_delta() {
        return epda_delta;
    }

    public state get_epda_initial() {
        return epda_initial;
    }

    public stack get_epda_box() {
        return epda_box;
    }

    public List<state> get_epda_marking() {
        return epda_marking;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪");
        new Append_helper().append(epda_states, stringBuffer);
        stringBuffer.append(", ");
        new Append_helper().append(epda_events, stringBuffer);
        stringBuffer.append(", ");
        new Append_helper().append(epda_gamma, stringBuffer);
        stringBuffer.append(", ");
        new Append_helper().append(epda_delta, stringBuffer);
        stringBuffer.append(", ");
        epda_initial.append(stringBuffer);
        stringBuffer.append(", ");
        epda_box.append(stringBuffer);
        stringBuffer.append(", ");
        new Append_helper().append(epda_marking, stringBuffer);
        stringBuffer.append("⟫");
    }

    public List<epda_step_label<state, event, stack>> get_map_source_execute(state q, option__abstract<event> event) {
        if (map_source_execute == null) {
            map_source_execute = new HashMap<>();
            for (epda_step_label<state, event, stack> e : get_epda_delta()) {
                map_source_execute.putIfAbsent(e.get_edge_src(), new HashMap<>());
                map_source_execute.get(e.get_edge_src()).putIfAbsent(e.get_edge_event(), new ArrayList<>());
                map_source_execute.get(e.get_edge_src()).get(e.get_edge_event()).add(e);
            }
        }
        Map<option__abstract<event>, List<epda_step_label<state, event, stack>>> map_execute = map_source_execute.get(q);
        if (map_execute == null)
            return new ArrayList<>();
        List<epda_step_label<state, event, stack>> result = map_execute.get(event);
        if (result == null)
            return new ArrayList<>();
        return result;
    }

    public List<epda_step_label<state, event, stack>> get_map_source(state q) {
        if (map_source == null) {
            map_source = new HashMap<>();
            for (epda_step_label<state, event, stack> e : get_epda_delta()) {
                map_source.putIfAbsent(e.get_edge_src(), new ArrayList<>());
                map_source.get(e.get_edge_src()).add(e);
            }
        }
        if (q == null)
            return null;
        List<epda_step_label<state, event, stack>> result = map_source.get(q);
        if (result == null)
            return new ArrayList<>();
        else
            return result;
    }

    public List<epda_step_label<state, event, stack>> get_map_target(state q) {
        if (map_target == null) {
            map_target = new HashMap<>();
            for (epda_step_label<state, event, stack> e : get_epda_delta()) {
                map_target.putIfAbsent(e.get_edge_trg(), new ArrayList<>());
                map_target.get(e.get_edge_trg()).add(e);
            }
        }
        List<epda_step_label<state, event, stack>> result = map_target.get(q);
        if (result == null)
            return new ArrayList<>();
        else
            return result;
    }

    public boolean is_valid(boolean check_determinism) {
        if (check_determinism)
            for (epda_step_label<state, event, stack> step_label1 : get_epda_delta()) {
                for (epda_step_label<state, event, stack> step_label2 : get_epda_delta()) {
                    if (step_label1 == step_label2)
                        continue;
                    if (!step_label1.get_edge_src().equals(step_label2.get_edge_src()))
                        continue;
                    if (step_label1.get_edge_event() instanceof option__Some
                            && step_label2.get_edge_event() instanceof option__Some
                            && !((option__Some<event>) step_label1.get_edge_event()).get_value().equals(
                            ((option__Some<event>) step_label2.get_edge_event()).get_value()))
                        continue;
                    if (!General_List.prefix(step_label1.get_edge_pop(), step_label2.get_edge_pop())
                            && !General_List.prefix(step_label2.get_edge_pop(), step_label1.get_edge_pop()))
                        continue;
                    return false;
                }
            }
        int done = 0;
        int max = get_epda_delta().size();
        for (epda_step_label<state, event, stack> step_label : get_epda_delta()) {
            done++;
            if (log.isInfoEnabled()) {
                if (done % 1000 == 0)
                    log.info("epda.is_valid: " + 1.0 * done / max);
            }
            if (!get_epda_states().contains(step_label.get_edge_src()))
                return false;
            if (!get_epda_states().contains(step_label.get_edge_trg()))
                return false;
            if (step_label.get_edge_event() instanceof option__Some)
                if (!get_epda_events().contains(((option__Some<event>) step_label.get_edge_event()).get_value()))
                    return false;
            if (!get_epda_gamma().containsAll(step_label.get_edge_pop()))
                return false;
            if (!get_epda_gamma().containsAll(step_label.get_edge_push()))
                return false;
        }
        if (!get_epda_states().containsAll(get_epda_marking()))
            return false;
        return get_epda_states().contains(get_epda_initial());
    }

    public Map<event, List<epda_step_label<state, event, stack>>> get_epda_step_labels_by_event() {
        if (epda_step_labels_by_event == null) {
            epda_step_labels_by_event = new HashMap<>();
            for (epda_step_label<state, event, stack> step_label : get_epda_delta())
                if (step_label.get_edge_event() instanceof option__Some) {
                    event event = ((option__Some<event>) step_label.get_edge_event()).the();
                    epda_step_labels_by_event.putIfAbsent(event, new ArrayList<>());
                    epda_step_labels_by_event.get(event).add(step_label);
                }
        }
        return epda_step_labels_by_event;
    }

    public List<epda_step_label<state, event, stack>> get_map_source_execute_pop(state q, option__abstract<event> event, stack X) {
        if (map_source_execute_pop == null) {
            map_source_execute_pop = new HashMap<>();
            for (epda_step_label<state, event, stack> step_label : get_epda_delta()) {
                map_source_execute_pop.putIfAbsent(step_label.get_edge_src(), new HashMap<>());
                map_source_execute_pop.get(step_label.get_edge_src()).putIfAbsent(step_label.get_edge_event(), new HashMap<>());
                map_source_execute_pop.get(step_label.get_edge_src()).get(step_label.get_edge_event()).putIfAbsent(step_label.get_edge_pop().get(0), new ArrayList<>());
                map_source_execute_pop.get(step_label.get_edge_src()).get(step_label.get_edge_event()).get(step_label.get_edge_pop().get(0)).add(step_label);
            }
        }
        Map<option__abstract<event>, Map<stack, List<epda_step_label<state, event, stack>>>> map_execute_pop = map_source_execute_pop.get(q);
        if (map_execute_pop == null)
            return new ArrayList<>();
        Map<stack, List<epda_step_label<state, event, stack>>> map_pop = map_execute_pop.get(event);
        if (map_pop == null)
            return new ArrayList<>();
        List<epda_step_label<state, event, stack>> result = map_pop.get(X);
        if (result == null)
            return new ArrayList<>();
        return result;
    }

    public List<epda_step_label<state, event, stack>> get_map_source_pop_edges(state q, stack X) {
        if (map_source_pop_edges == null) {
            map_source_pop_edges = new HashMap<>();
            for (epda_step_label<state, event, stack> step_label : get_epda_delta()) {
                if (!step_label.get_edge_push().isEmpty())
                    continue;
                map_source_pop_edges.putIfAbsent(step_label.get_edge_src(), new HashMap<>());
                map_source_pop_edges.get(step_label.get_edge_src()).putIfAbsent(step_label.get_edge_pop().get(0), new ArrayList<>());
                map_source_pop_edges.get(step_label.get_edge_src()).get(step_label.get_edge_pop().get(0)).add(step_label);
            }
        }
        if (q == null || X == null)
            return null;
        Map<stack, List<epda_step_label<state, event, stack>>> map_pop_edges = map_source_pop_edges.get(q);
        if (map_pop_edges == null)
            return new ArrayList<>();
        List<epda_step_label<state, event, stack>> pop_edges = map_pop_edges.get(X);
        if (pop_edges == null)
            return new ArrayList<>();
        return pop_edges;
    }

    public List<epda_step_label<state, event, stack>> get_map_source_stack_edges(state q, stack X) {
        if (map_source_stack_edges == null) {
            map_source_stack_edges = new HashMap<>();
            for (epda_step_label<state, event, stack> step_label : get_epda_delta()) {
                map_source_stack_edges.putIfAbsent(step_label.get_edge_src(), new HashMap<>());
                map_source_stack_edges.get(step_label.get_edge_src()).putIfAbsent(step_label.get_edge_pop().get(0), new ArrayList<>());
                map_source_stack_edges.get(step_label.get_edge_src()).get(step_label.get_edge_pop().get(0)).add(step_label);
            }
        }
        if (q == null || X == null)
            return null;
        Map<stack, List<epda_step_label<state, event, stack>>> map_pop_edges = map_source_stack_edges.get(q);
        if (map_pop_edges == null)
            return new ArrayList<>();
        List<epda_step_label<state, event, stack>> pop_edges = map_pop_edges.get(X);
        if (pop_edges == null)
            return new ArrayList<>();
        return pop_edges;
    }

    public List<epda_step_label<state, event, stack>> get_map_target_pop_edges(state q, stack X) {
        if (map_target_pop_edges == null) {
            map_target_pop_edges = new HashMap<>();
            for (epda_step_label<state, event, stack> step_label : get_epda_delta()) {
                if (!step_label.get_edge_push().isEmpty())
                    continue;
                map_target_pop_edges.putIfAbsent(step_label.get_edge_trg(), new HashMap<>());
                map_target_pop_edges.get(step_label.get_edge_trg()).putIfAbsent(step_label.get_edge_pop().get(0), new ArrayList<>());
                map_target_pop_edges.get(step_label.get_edge_trg()).get(step_label.get_edge_pop().get(0)).add(step_label);
            }
        }
        if (q == null || X == null)
            return null;
        Map<stack, List<epda_step_label<state, event, stack>>> map_pop_edges = map_target_pop_edges.get(q);
        if (map_pop_edges == null)
            return new ArrayList<>();
        List<epda_step_label<state, event, stack>> pop_edges = map_pop_edges.get(X);
        if (pop_edges == null)
            return new ArrayList<>();
        return pop_edges;
    }

    public List<epda_step_label<state, event, stack>> get_map_target_event(state q, option__abstract<event> X) {
        if (map_target_event == null) {
            map_target_event = new HashMap<>();
            for (epda_step_label<state, event, stack> step_label : get_epda_delta()) {
                map_target_event.putIfAbsent(step_label.get_edge_trg(), new HashMap<>());
                map_target_event.get(step_label.get_edge_trg()).putIfAbsent(step_label.get_edge_event(), new ArrayList<>());
                map_target_event.get(step_label.get_edge_trg()).get(step_label.get_edge_event()).add(step_label);
            }
        }
        if (q == null || X == null)
            return null;
        Map<option__abstract<event>, List<epda_step_label<state, event, stack>>> map_event = map_target_event.get(q);
        if (map_event == null)
            return new ArrayList<>();
        List<epda_step_label<state, event, stack>> edges = map_event.get(X);
        if (edges == null)
            return new ArrayList<>();
        return edges;
    }

    public String log_info() {
        if (Config.CHECK_VALID_EDPDA_ON_LOG_INFO && !is_valid(true))
            throw new RuntimeException();
        return "(states=" + get_epda_states().size() + ", edges=" + get_epda_delta().size() + ", stacks=" + get_epda_gamma().size() + ")";
    }

    public void print_epda_info(String name, int num) {
        System.out.println(name + " : get_epda_delta().size() = " + this.get_epda_delta().size() + ", get_epda_states().size() = " + this.get_epda_states().size() + ", get_epda_marking().size() = " + this.get_epda_marking().size());
    }

    public Map<Integer, Map<state, Map<stack, Set<epda_step_label<state, event, stack>>>>> get_edges_by_type_and_stack_and_source() {
        if (edges_by_type_and_stack_and_source == null) {
            edges_by_type_and_stack_and_source = new HashMap<>();
            edges_by_type_and_stack_and_source.putIfAbsent(POP, new HashMap<>());
            edges_by_type_and_stack_and_source.putIfAbsent(PUSH, new HashMap<>());
            edges_by_type_and_stack_and_source.putIfAbsent(EXECUTE, new HashMap<>());
            for (epda_step_label<state, event, stack> e : get_epda_delta()) {
                if (e.get_edge_event() instanceof option__Some) {
                    edges_by_type_and_stack_and_source.get(EXECUTE).putIfAbsent(e.get_edge_src(), new HashMap<>());
                    edges_by_type_and_stack_and_source.get(EXECUTE).get(e.get_edge_src()).putIfAbsent(e.get_edge_pop().get(0), new HashSet<>());
                    edges_by_type_and_stack_and_source.get(EXECUTE).get(e.get_edge_src()).get(e.get_edge_pop().get(0)).add(e);
                } else if (e.get_edge_push().isEmpty()) {
                    edges_by_type_and_stack_and_source.get(POP).putIfAbsent(e.get_edge_src(), new HashMap<>());
                    edges_by_type_and_stack_and_source.get(POP).get(e.get_edge_src()).putIfAbsent(e.get_edge_pop().get(0), new HashSet<>());
                    edges_by_type_and_stack_and_source.get(POP).get(e.get_edge_src()).get(e.get_edge_pop().get(0)).add(e);
                } else {
                    edges_by_type_and_stack_and_source.get(PUSH).putIfAbsent(e.get_edge_src(), new HashMap<>());
                    edges_by_type_and_stack_and_source.get(PUSH).get(e.get_edge_src()).putIfAbsent(e.get_edge_pop().get(0), new HashSet<>());
                    edges_by_type_and_stack_and_source.get(PUSH).get(e.get_edge_src()).get(e.get_edge_pop().get(0)).add(e);
                }
            }
        }
        return edges_by_type_and_stack_and_source;
    }

    public Map<state, General_Set<General_List<stack>>> get_approximate_accessible_stack(int k) {
        if (approximate_accessible_stack == null)
            return null;
        return approximate_accessible_stack.get(k);
    }

    public void set_approximate_accessible_stack(int k, Map<state, General_Set<General_List<stack>>> approximate_accessible_stack) {
        if (this.approximate_accessible_stack == null)
            this.approximate_accessible_stack = new HashMap<>();
        this.approximate_accessible_stack.put(k, approximate_accessible_stack);
    }

    public void dot_export(String filename) throws IOException {
        new File(filename.substring(0, filename.lastIndexOf('/'))).mkdirs();
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "utf-8"))) {
            writer.write("digraph G {\n");
            for (state q : get_epda_states()) {
                String color = "";
                if (get_epda_marking().contains(q))
                    color = "fillcolor=red, style=filled";
                writer.write("\"" + q.toString() + "\" [" + color + "];\n");
                if (get_epda_initial().equals(q)) {
                    writer.write("init -> \"" + q.toString() + "\";\n");
                }
            }

            for (epda_step_label<state, event, stack> step_label : get_epda_delta()) {
                String event = null;
                if (step_label.get_edge_event() instanceof option__Some) {
                    event = ((option__Some<event>) step_label.get_edge_event()).the().toString();
                } else if (step_label.get_edge_event() instanceof option__None) {
                    event = "-";
                } else throw new RuntimeException();
                //System.out.println(step_label.get_edge_push().toString());
                writer.write("\"" + step_label.get_edge_src().toString() + "\" -> \"" + step_label.get_edge_trg().toString()
                        + "\" [label=\"" + event + ","
                        + step_label.get_edge_pop().toString() + ","
                        + step_label.get_edge_push().toString()
                        + "\"];\n");
            }
            writer.write("}\n");
            writer.close();
        }
    }

    public void info_on_reachability(Set<state> states) {
        Config.instance.measuring_thread.push("info_on_reachability");
        info_on_reachability_COUNT++;
        Set<List<event>> result = new HashSet<>();

        final List<Callback> callbackList = new ArrayList<>();
        callbackList.add(new DETERMINE_SHORTEST_MARKED_WORDS_Store_Result_Callback<>(result));
        AtomicBoolean abort = new AtomicBoolean(false);
        Config.instance.local_solver.register_callbacks(abort, callbackList);
        for (state q : states) {
            Config.instance.local_solver.add_problem(
                    Config.instance.DETERMINE_SHORTEST_MARKED_WORDS_PRIORITY,
                    abort,
                    new DETERMINE_SHORTEST_MARKED_WORDS_Problem<>(this, q, String.valueOf(info_on_reachability_COUNT), 100, 20));
        }

        try {
            Config.instance.local_solver.wait_for_completion(abort);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
        for (List<event> word : result) {
            boolean print = true;
            for (List<event> cmp : result)
                if (cmp != word && General_List.prefix(word, cmp)) {
                    log.info("removed: " + word);
                    print = false;
                    break;
                }
            if (print)
                log.info("info_on_reachability : epda : num=" + info_on_reachability_COUNT + " : " + word);
        }
        Config.instance.measuring_thread.pop("info_on_reachability");

    }


}
