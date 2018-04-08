package ru.andreevcode.soap;

import unisoft.ws.FNSNDSCAWS2;
import unisoft.ws.FNSNDSCAWS2Port;
import unisoft.ws.fnsndscaws2.request.NdsRequest2;
import unisoft.ws.fnsndscaws2.request.ObjectFactory;
import unisoft.ws.fnsndscaws2.response.NdsResponse2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

//EGRN agents data check
public class NalogCheckAgents {

	public static void main(String[] args) {
        //INN input variable
        String inn = null;
        //KPP input variable
        String kpp = null;
        //Date for request
        String dateRequest = null;

        Date dateNow = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        //Start program with arguments. It's possible to make your own data.
        if (args.length>0)
        {
	        if (args.length == 1) inn = args[0];
            if (args.length >= 2) kpp=args[1];
            if (inn.length() !=10 || !isValidParamLong(inn))  System.out.println("ИНН введен неправильно");
            if (kpp.length() !=9 || !isValidParamLong(kpp))  System.out.println("KПП введен неправильно");
            if (args.length == 3) {
                dateRequest=getDateRequest(sdf, args[2]);
            }
                else dateRequest=sdf.format(dateNow);
        }
        else
        {
            //INN input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите ИНН: ");
            if (!scanner.hasNextLong ())  System.out.println("ИНН введен неправильно");
            inn = scanner.next();
            if (inn.length() != 10)  System.out.println("ИНН введен неправильно");

            //KPP input
            System.out.print("Введите КПП: ");
            if (!scanner.hasNextLong ()) System.out.println("КПП введен неправильно");
            kpp = scanner.next();
            if (kpp.length() != 9)  System.out.println("КПП введен неправильно");

            dateRequest=sdf.format(dateNow);
        }

        System.out.println("Запрос к ЕГРН: " + inn + " " + kpp + " " + dateRequest);
        FNSNDSCAWS2 fnsndscaws2= new FNSNDSCAWS2();
        FNSNDSCAWS2Port fnsndscaws2Port = fnsndscaws2.getFNSNDSCAWS2Port();

        ObjectFactory objectRequest = new ObjectFactory();
        NdsRequest2.NP np = objectRequest.createNdsRequest2NP();
        np.setINN(inn);
        np.setKPP(kpp);
        np.setDT(dateRequest);
        NdsRequest2 ndsRequest =  objectRequest.createNdsRequest2();
        List<NdsRequest2.NP> npList = ndsRequest.getNP();
        npList.add(np);

        NdsResponse2 ndsResponse = fnsndscaws2Port.ndsRequest2(ndsRequest);
        List<NdsResponse2.NP> responseList =  ndsResponse.getNP();

        //Console output of response main data
        for (NdsResponse2.NP item: responseList) {
            System.out.println("Ответ ЕГРН: " + item.getState() + " "+ item.getINN() + " " + item.getINN() +
                    " " + item.getDT());
        }

        //Console output of response secondary data
        System.out.println("Доп.данные ответа ЕГРН: " + ndsResponse.getDTActFL() + " " +ndsResponse.getDTActUL() + " "+
                " " + ndsResponse.getErrMsg());
	}

	// Date format check
    private static String getDateRequest(SimpleDateFormat sdf, String value) {
        Date date = null;
        String dateRequest=null;
        try {
            date = sdf.parse(value);
            if (value.equals(sdf.format(date))) {
                dateRequest=value;
            }
        } catch (ParseException ex) {
            System.out.println("Дата введена неправильно");
        }
        return dateRequest;
    }

    //String to int check
    private static boolean isValidParamLong(String st){
        boolean flag = true;
	    try {
            Long i = Long.parseLong(st);
        } catch (NumberFormatException e) {
            flag=false;
        }
        return flag;
    }


}
