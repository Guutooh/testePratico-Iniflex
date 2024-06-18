import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
                new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
                new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
                new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
                new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
                new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
                new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
                new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
                new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        for (Funcionario funcionario : funcionarios) {
            System.out.println("Nome: " + funcionario.getNome() +
                    ", Data Nascimento: " + funcionario.getDataNascimento().format(formatter) +
                    ", Salário: " + String.format("%,.2f", funcionario.getSalario()) +
                    ", Função: " + funcionario.getFuncao());
        }


        for (Funcionario funcionario : funcionarios) {
            var aumento = funcionario.getSalario().multiply(BigDecimal.valueOf(0.10));
            funcionario.setSalario(funcionario.getSalario().add(aumento));
        }

        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));


        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(System.out::println);
        });


        System.out.println("Aniversariantes de Outubro e Dezembro:");
        funcionarios.stream()
                .filter(func -> func.getDataNascimento().getMonth() == Month.OCTOBER ||
                        func.getDataNascimento().getMonth() == Month.DECEMBER)
                .forEach(System.out::println);


        Funcionario maisVelho = Collections.max(funcionarios, Comparator.comparing(func -> func.getDataNascimento()));

        int idade = LocalDate.now().getYear() - maisVelho.getDataNascimento().getYear();
        System.out.println("Funcionário com maior idade: " + maisVelho.getNome() + " - Idade: " + idade);


        System.out.println("Funcionários em ordem alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(System.out::println);


        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total dos salários: " + String.format("%,.2f", totalSalarios));


        BigDecimal salarioMinimo = new BigDecimal("1212.00");

        for (Funcionario func : funcionarios) {
            BigDecimal salariosMinimos = func.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(func.getNome() + " ganha " + salariosMinimos + " salários mínimos.");
        }
    }
}