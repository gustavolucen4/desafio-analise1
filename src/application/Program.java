package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {
	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner keyBoard = new Scanner(System.in);
		
		System.out.print("Entre o caminho do arquivo: ");
		String path = keyBoard.next();
		
		List<Sale> listSale = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))){
		
			String line = br.readLine();
			while(line != null) {
				String[] lineFile = line.split(",");
				listSale.add(new Sale(Integer.parseInt(lineFile[0]), Integer.parseInt(lineFile[1]), lineFile[2], Integer.parseInt(lineFile[3]), Double.parseDouble(lineFile[4])));
				line = br.readLine();
			}
			
			Comparator<Sale> comp = (o1, o2) -> o1.compareTo(o2);
			
			List<Sale> topFive = listSale.stream()
					.filter(x -> x.getYear().equals(2016))
					.sorted(comp.reversed())
					.limit(5)
					.collect(Collectors.toList());
					
			topFive.forEach(System.out::println);
			
			double sum = listSale.stream()
					.filter(x -> x.getSeller().toUpperCase().equals("LOGAN"))
					.filter(x -> x.getMonth().equals(1) || x.getMonth().equals(7))
					.map(x -> x.getTotal())
					.reduce(0.0, (x,y) -> x + y);
					
			System.out.printf("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f\n", sum);
			
		} 
		catch (FileNotFoundException e) {
			System.out.println("Erro: " + path + " (O sistema não pode encontrar o arquivo especificado)");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		keyBoard.close();
	}
}
