package ua.ihromant.learning.util;

import ua.ihromant.learning.state.HistoryItem;
import ua.ihromant.learning.qtable.QTable;
import ua.ihromant.learning.state.State;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WriterUtil {
    public static <A> void writeHistory(List<HistoryItem> history, QTable qTable) {
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
}
