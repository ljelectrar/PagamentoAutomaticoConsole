package application;

import entities.Contract;
import entities.Installment;
import services.ContractService;
import services.PaypalService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Enter with the contract data: ");
        System.out.print("Number: ");
        int number = sc.nextInt();
        System.out.print("Date: (dd/MM/yyyy): ");
        LocalDate data = LocalDate.parse(sc.next(), fmt);
        System.out.print("Contract value R$: ");
        double contractValue = sc.nextDouble();

        Contract contract = new Contract(number, data, contractValue);

        System.out.print("Enter with the number of installments: ");
        int instalments = sc.nextInt();

        ContractService contractService = new ContractService(new PaypalService());

        contractService.processContract(contract, instalments);

        String destinityPath = "C:\\Users\\ljele\\IdeaProjects\\parcelas.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(destinityPath))) {
            for (Installment installment : contract.getInstallments()) {
                bw.write(  installment + "\n");
                bw.newLine();
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        sc.close();
    }

}