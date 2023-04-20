package Lab6;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class MainApp {

    public static void scriere(List<Angajati> lista) {
        try {
            ObjectMapper mapper=new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            File file=new File("src/main/resources/angajati.json"); mapper.writeValue(file,lista);
        } catch (IOException e) { e.printStackTrace();
        } }

    public static List<Angajati> citire() { try {
        File file=new File("src/main/resources/angajati.json");
        ObjectMapper mapper=new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        List<Angajati> angajati = mapper.readValue(file, new TypeReference<List<Angajati>>(){});
        return angajati;
    } catch (IOException e) {
        e.printStackTrace();
    }
        return null; }

    public static void main(String[] args) throws InterruptedException {
        List<Angajati> angajati=citire();

        Scanner sc= new Scanner(System.in);
        int opt=-1;
        while(opt!=0)
        {
            System.out.println("\n-------------------------MENIU-------------------------\n");
            System.out.println("1. Afisare angajati.");
            System.out.println("2. Angajati cu salariul mai mare de 2500 RON");
            System.out.println("3. Afisarea angajatiilor din luna aprile a anului trecut care au pozitii de conducere: \"Sef\" sau \"Director\"");
            System.out.println("4. Angajati care nu functii de conducere");
            System.out.println("5. Afisarea angajatilor cu majuscule");
            System.out.println("6. Salarii mai mici de 3000 RON");
            System.out.println("7. Primul angajat");
            System.out.println("8. Afisarea de statistici referitoare la salariul angajatiilor");
            System.out.println("9. Gasire angajat dupa nume");
            System.out.println("10. Afisarea nr. de persoane care s-au angajat in vara");
            System.out.println("0. Exit.");
            System.out.println("\n-------------------------------------------------------");

            System.out.println("\t\t\t\tDati optiunea:");
            opt= sc.nextInt();

            switch (opt) {
                case 0:
                    System.out.println("EXIT");
                    break;
                case 1:
                    for (Angajati a:angajati) //1
                    {
                        System.out.println(a);
                    }
                        System.out.println();
                    break;

                case 2:
                    //2
                    angajati
                            .stream()
                            .filter((a) -> a.getSalariul() > 2500)
                            .forEach(System.out::println);
                    break;

                case 3:
                    //3
                    List conducere= angajati
                            .stream()
                            .filter((a)-> a.getData_angajarii().getYear()==Year.now().minusYears(1).getValue() && a.getData_angajarii().getMonth().getValue()==4
                                    && (a.getPostul().equals("Sef") || a.getPostul().equals("Director")))
                            .map(Angajati::getNumele)
                            .collect(Collectors.toList());
                    System.out.println(conducere);
                    break;

                case 4:
                    //4
                    angajati
                            .stream()
                            .filter((a)-> !a.getPostul().equals("Sef")&&!a.getPostul().equals("Director"))
                            .sorted(Comparator.comparing(Angajati::getSalariul))
                            .forEach((a)-> System.out.println(a.getNumele()));
                    break;

                case 5:
                    //5
                    List angajatiM=angajati
                            .stream()
                            .filter((a)-> a.getNumele().equals(a.getNumele().toUpperCase()))
                            .map(Angajati::getNumele)
                            .collect(Collectors.toList());

                    System.out.println(angajatiM);
                    break;

                case 6:
                    //6
                    angajati
                            .stream().map(Angajati::getSalariul)
                            .filter((a) -> a<3000)
                            .forEach(System.out::println);
                    break;

                case 7:
                    //7
                    Optional<Angajati> primul= Optional.ofNullable(angajati
                            .stream()
                            .min(Comparator.comparing((a) -> a.getData_angajarii()))
                            .orElse(null));
                    if(primul!=null) System.out.println(primul.toString());
                    else System.out.println("Angajatul nu a fost gasit!");
                    break;

                case 8:
                     //8
                    DoubleSummaryStatistics statistici= angajati
                            .stream()
                            .collect(Collectors.summarizingDouble(Angajati::getSalariul));
                    System.out.println("Salariul mediu: "+statistici.getAverage());
                    System.out.println("Salariul minim: "+statistici.getMin());
                    System.out.println("Salariul maxim: "+statistici.getMax());
                    break;

                case 9:
                     //9
                    System.out.println("\tDati numele pe care il cautati: ");
                    Scanner scanner= new Scanner(System.in);
                    String nume= scanner.nextLine();

                    Optional<Angajati> optional= angajati
                            .stream()
                            .filter((a)-> a.getNumele().equals(nume)).findAny();
                    optional.ifPresentOrElse(
                            (value) -> System.out.println("Firma are cel putin un " +nume +" angajat."),
                            () -> System.out.println("Firma nu are nici un "+nume+" angajat.") );
                    break;

                case 10:
                    //10
                    int count= (int)angajati
                            .stream()
                            .filter((a)->a.getData_angajarii().getYear()== Year.now().minusYears(1).getValue()
                                    && a.getData_angajarii().getMonth().getValue()>5 && a.getData_angajarii().getMonth().getValue()<9)
                            .count();
                    if(count==0) System.out.println("In vara anunului precedent nu au fost persoane angajate.");
                    else if(count==1) System.out.println("O persoana a fost angajata in vara anului precedent.");
                    else System.out.println(count+ " persoane au fost angajate in vara anului trecut.");
                    break;

                default:
                    System.out.println("Aceasta nu este o optiune!");
                    break;
            }
            Thread.sleep(4000);
        }
        sc.close();
    }
}
