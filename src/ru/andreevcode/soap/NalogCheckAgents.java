package ru.andreevcode.soap;

import unisoft.ws.FNSNDSCAWS2;
import unisoft.ws.FNSNDSCAWS2Port;
import unisoft.ws.fnsndscaws2.request.NdsRequest2;
import unisoft.ws.fnsndscaws2.request.ObjectFactory;
import unisoft.ws.fnsndscaws2.response.NdsResponse2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        System.out.println("Текущая дата " + formatForDateNow.format(dateNow));

        FNSNDSCAWS2 fnsndscaws2= new FNSNDSCAWS2();
        FNSNDSCAWS2Port fnsndscaws2Port = fnsndscaws2.getFNSNDSCAWS2Port();

        ObjectFactory objectRequest = new ObjectFactory();
        NdsRequest2.NP np = objectRequest.createNdsRequest2NP();
        np.setINN(inn);
        np.setKPP(kpp);
        np.setDT(formatForDateNow.format(dateNow));
        NdsRequest2 ndsRequest =  objectRequest.createNdsRequest2();
        List<NdsRequest2.NP> npList = ndsRequest.getNP();
        npList.add(np);

        NdsResponse2 ndsResponse = fnsndscaws2Port.ndsRequest2(ndsRequest);
        List<NdsResponse2.NP> responseList =  ndsResponse.getNP();

        //Console output of response main data
        for (NdsResponse2.NP item: responseList) {
            System.out.println(item.getState() +" "+ item.getINN() + " " + item.getINN() +
                    " " + item.getDT());
        }

        //Console output of response secondary data
        System.out.println(ndsResponse.getDTActFL() + " " +ndsResponse.getDTActUL() + " "+
                " " + ndsResponse.getErrMsg());
	}

}
