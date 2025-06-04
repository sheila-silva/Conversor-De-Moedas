import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CurrencyConverter converter = new CurrencyConverter();
        ConversionHistory history = new ConversionHistory();

        while (true) {
            System.out.println("===== Conversor de Moedas =====");
            System.out.println("1. Converter moedas");
            System.out.println("2. Ver histórico de conversões");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            String inputOption = scanner.nextLine();
            int option;

            try {
                option = Integer.parseInt(inputOption);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
                continue;
            }

            switch (option) {
                case 1:
                    String fromCode;
                    while (true) {
                        System.out.print("Digite o código da moeda de origem (ex: USD): ");
                        fromCode = scanner.nextLine().toUpperCase(Locale.ROOT);
                        if (fromCode.matches("^[A-Z]{3}$") && converter.getService().isValidCurrencyCode(fromCode)) {
                            break;
                        } else {
                            System.out.println("Código inválido ou moeda não suportada. Tente novamente.");
                        }
                    }

                    String toCode;
                    while (true) {
                        System.out.print("Digite o código da moeda de destino (ex: BRL): ");
                        toCode = scanner.nextLine().toUpperCase(Locale.ROOT);
                        if (toCode.matches("^[A-Z]{3}$") && converter.getService().isValidCurrencyCode(toCode)) {
                            break;
                        } else {
                            System.out.println("Código inválido ou moeda não suportada. Tente novamente.");
                        }
                    }

                    System.out.print("Digite o valor a converter: ");
                    String valueText = scanner.nextLine().replace(",", ".");
                    double amount;

                    try {
                        amount = Double.parseDouble(valueText);
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido. Use apenas números.");
                        continue;
                    }

                    Currency fromCurrency = new Currency(fromCode);
                    Currency toCurrency = new Currency(toCode);

                    double convertedAmount = converter.convert(fromCurrency, toCurrency, amount);

                    System.out.printf("Valor convertido: %.2f %s%n", convertedAmount, toCurrency.getCode());

                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    String logEntry = String.format("[%s] %.2f %s -> %.2f %s",
                            now.format(formatter), amount, fromCurrency.getCode(), convertedAmount, toCurrency.getCode());

                    history.addEntry(logEntry);
                    break;

                case 2:
                    history.displayHistory();
                    break;

                case 3:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}
