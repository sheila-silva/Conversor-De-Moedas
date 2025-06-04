import java.util.ArrayList;
import java.util.List;

public class ConversionHistory {
    private final List<String> entries = new ArrayList<>();

    public void addEntry(String entry) {
        entries.add(entry);
    }

    public void displayHistory() {
        System.out.println("===== Histórico de Conversões =====");
        if (entries.isEmpty()) {
            System.out.println("Nenhuma conversão realizada ainda.");
        } else {
            for (String entry : entries) {
                System.out.println(entry);
            }
        }
    }

    public List<String> getEntries() {
        return new ArrayList<>(entries);
    }
}
