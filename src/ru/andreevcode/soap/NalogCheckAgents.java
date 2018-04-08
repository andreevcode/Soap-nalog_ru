package ru.andreevcode.soap;

import java.util.Scanner;

//EGRN agents data check
public class NalogCheckAgents {

	public static void main(String[] args) {
		//INN input variable
		String inn;
		//KPP input variable
		String kpp;

		while (true) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Введите ИНН: ");
			inn = scanner.next();
			if (inn.length() !=10) {
				System.out.println("ИНН введен неправильно");
			}
			else break;
		}

		while (true) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Введите КПП: ");
			kpp = scanner.nextLine();
			if ((kpp.length() ==9) || (kpp.length()==0)) {
				break;
			}
			else System.out.println("КПП введен неправильно");
		}
	}

}
