package ua.ihromant.learning.util;

import ua.ihromant.learning.state.HistoryItem;
import ua.ihromant.learning.qtable.QTable;
import ua.ihromant.learning.state.Player;
import ua.ihromant.learning.state.State;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WriterUtil {
    private static final Map<Player, Integer> statistics = new EnumMap<>(Player.class);

    public static void writeHistory(List<HistoryItem> history, QTable qTable, int episode) {
        if (history.stream().noneMatch(HistoryItem::isRandom)) {
            Player winner = history.get(history.size() - 1).getTo().getUtility(Player.X) == 1.0 ? Player.X : Player.O;
            if (winner != Player.O) {
                writeHistory(history, qTable);
            }
            statistics.put(winner, statistics.get(winner) == null ? 1 : statistics.get(winner) + 1);
        }
        if (episode % 1000 == 999) {
            System.out.println("Statistics: for " + (episode + 1) + " episode: " + statistics);
            writeHistory(history, qTable);
            statistics.clear();
        }
    }

    public static void writeHistory(List<HistoryItem> history, QTable qTable) {
        List<String[]> lines = history.stream()
                .map(h -> h.getTo().toString())
                .map(s -> s.split("\n")).collect(Collectors.toList());
        Map<State, Double> evaluated = qTable.getMultiple(history.stream().map(HistoryItem::getTo));
        String[] firstLine = lines.get(0);
        for (int i = 0; i < history.size(); i++) {
            String format = history.get(i).isRandom()
                    ? "R%." + (lines.get(i)[0].length() - 2) + "f"
                    : "%." + (lines.get(i)[0].length() - 1) + "f";
            System.out.print(String.format(format, evaluated.get(history.get(i).getTo())) + " ");
        }
        System.out.println();
        for (int i = 0; i < firstLine.length; i++) {
            for (int j = 0; j < history.size(); j++) {
                System.out.print(lines.get(j)[i] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void writeHistory(List<HistoryItem> history) {
        List<String[]> lines = history.stream()
                .map(h -> h.getTo().toString())
                .map(s -> s.split("\n")).collect(Collectors.toList());
        for (int i = 0; i < lines.get(0).length; i++) {
            for (int j = 0; j < history.size(); j++) {
                System.out.print(lines.get(j)[i] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
