/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prodavnicaapp;

import entiteti.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Momcilo
 */
public class Main {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = "MenadzerTopic")                    
    private static Topic topic;
    
    private static int TrenutnoProdato = 0;
    private static float TrenutnoZaradjeno = 0;
    private static int id;
    
    public static void main(String[] args) throws JMSException, IOException {
        Main.setId(Integer.parseInt(args[0]));
        System.out.println("Id prodavnice je " + Main.getId());
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProdavnicaAppPU");
        EntityManager em = emf.createEntityManager();
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        while(true)
        {
            reader.readLine();
            System.out.println("Izaberite funkciju:\n"
                    + "1)Nadji proizvode\n"
                    + "2)Nadji po tipu\n"
                    + "3)Provera stanja\n"
                    + "4)Ima na stanju\n"
                    + "5)Provera rezervacije\n"
                    + "6)Rezervisanje\n"
                    + "7)Brisanje rezervacije\n"
                    + "8)Dodaj korisnika\n"
                    + "9)Kupovina\n"
                    + "10)Proveri druge prodavnice\n"
                    + "11)Izlaz");
            int izbor = Integer.parseInt(reader.readLine());
            switch(izbor)
            {
                case 1:
                {
                    System.out.println("Naziv proizvoda koji trazite:");
                    String nzv = reader.readLine();
                    Main.NadjiProizvode(em, nzv);
                }break;
                case 2:
                {
                    System.out.println("Naziv tipa proizvoda koji trazite:");
                    String nzv = reader.readLine();
                    Main.NadjiPoTipu(em, nzv);
                }break;
                case 3:
                {
                    System.out.println("ID proizvoda cije stanje proveravate:");
                    String i = reader.readLine();
                    System.out.println("U kojoj prodavnici?");
                    int br = Integer.parseInt(reader.readLine());
                    Main.ProveraStanja(em, br, Integer.parseInt(i));
                }break;
                case 4:
                {
                    System.out.println("Naziv proizvoda koji proveravate da li je na stanju:");
                    String nzv = reader.readLine();
                    System.out.println("Kolicina proizvoda koji proveravate da li je na stanju:");
                    int kol = Integer.parseInt(reader.readLine());
                    if(Main.ImaNaStanju(em, nzv, kol))System.out.println("ima");
                    else System.out.println("nema");
                }break;
                case 5:
                {
                    System.out.println("Broj rezervacije koju proveravate:");
                    int br = Integer.parseInt(reader.readLine());
                    if(Main.ProveraRezervacije(em, br))System.out.println("Postoji");
                    else System.out.println("Ne postoji/isteklo");
                }break;
                case 6:
                {
                    System.out.println("Broj predmeta koji zelite da rezervisete:");
                    int br = Integer.parseInt(reader.readLine());
                    System.out.println("Kolicina predmeta koji zelite da rezervisete:");
                    int kol = Integer.parseInt(reader.readLine());
                    Main.Rezervisanje(em, Main.getId(), br, kol);
                }break;
                case 7:
                {
                    System.out.println("Broj rezervacije koju zelite da izbrisete:");
                    int br = Integer.parseInt(reader.readLine());
                    Main.BrisanjeRezervacije(em, br);
                }break;
                case 8:
                {
                    System.out.println("Ime novog korisnika:");
                    String ime = reader.readLine();
                    System.out.println("Prezime novog korisnika:");
                    String prez = reader.readLine();
                    System.out.println("Telefon novog korisnika:");
                    String tel = reader.readLine();
                    Main.DodajKorisnika(em,ime,prez,tel);
                }break;
                case 9:
                {
                    System.out.println("Naziv proizvoda koji kupujete:");
                    String nzv = reader.readLine();
                    System.out.println("Kolicina predmeta koji kupujete:");
                    int kol = Integer.parseInt(reader.readLine());
                    Main.Kupovina(em, nzv, kol, Main.getId());
                }break;
                case 10:
                {
                    System.out.println("Naziv predmeta koji zelite da proverite:");
                    String nzv = reader.readLine();
                    System.out.println("Kolicina predmeta koji zelite da proverite:");
                    int kol = Integer.parseInt(reader.readLine());
                    Main.ProveriDrugeProdavnice(em, nzv, kol);
                }break;
                case 11:System.exit(0);break;
            }
            Date danas = new Date();
            int h = danas.getHours();
            int m = danas.getMinutes();
            if(h == 23 && m == 59)Main.DnevniPromet(em);
        }
    }

    public static int getTrenutnoProdato() {
        return TrenutnoProdato;
    }

    public static void incTrenutnoProdato() {
        Main.TrenutnoProdato = TrenutnoProdato++;
    }
    
    public static void resTrenutnoProdato() {
        Main.TrenutnoProdato = 0;
    }

    public static float getTrenutnoZaradjeno() {
        return TrenutnoZaradjeno;
    }

    public static void incTrenutnoZaradjeno(float DodatnoZaradjeno) {
        Main.TrenutnoZaradjeno += DodatnoZaradjeno;
    }

    public static void resTrenutnoZaradjeno() {
        Main.TrenutnoZaradjeno = 0;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Main.id = id;
    }
    
    public static void DnevniPromet(EntityManager em) //racunanje dnevnog prometa za lokalnu prodavnicu
    {
        try
        { 
            Date datum = new Date();
            java.sql.Date sqlDatum = new java.sql.Date(datum.getTime());//mora da se prebaci u sqldate da bi moglo da se ubaci u bazu
            em.getTransaction().begin();
            Query query = em.createNativeQuery("INSERT INTO Promet (Prodavnica, Datum, Kolicina, Ukupno) VALUES ('" + Main.getId() + "', '" + sqlDatum + "', '" + Main.getTrenutnoProdato() + "', '" + Main.getTrenutnoZaradjeno() + "')");
            query.executeUpdate(); 
            em.getTransaction().commit(); 
        } 
        finally 
        { 
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } 
        resTrenutnoProdato();
        resTrenutnoZaradjeno();
    }
    
    public static List<Proizvodi> NadjiProizvode(EntityManager em, String nzv) //nalazi poizvode po imenu
    {
        Query query = em.createQuery("SELECT p FROM Proizvodi p WHERE p.naziv = '" + nzv + "'");
        List<Proizvodi> lista = query.getResultList();
        lista.forEach((p) -> {
            System.out.println(p.getNaziv() + "-" + String.valueOf(p.getCena()));
        });
        return lista;
    }
    
    public static List<Proizvodi> NadjiPoTipu(EntityManager em, String nzv) //nalazi proizvode po nazivu tipa
    {
        Query query = em.createQuery("SELECT p FROM Proizvodi p, Tip t WHERE t.naziv = '" + nzv + "' AND p.tip.idTip = t.idTip");
        List<Proizvodi> lista = query.getResultList();
        lista.forEach((p) -> {
            System.out.println(p.getNaziv());
        });
        return query.getResultList();
    }
    
    public static void ProveraStanja(EntityManager em, int p, int id) throws JMSException //proverava da li je Proizvod otpakovan/zapakovan
    {//pricanje s menadzerom
        Destination destination = topic;
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        Message poruka = context.createMessage();
        poruka.setStringProperty("tip", "ProveriStanje");
        poruka.setIntProperty("proizvod", id);
        poruka.setIntProperty("prodavnica", p);
        producer.send(destination, poruka);
        System.out.println("Menadzer je upitan o stanju proizvoda");
        JMSConsumer consumer = context.createConsumer(destination);
        poruka = consumer.receive();
        while(!"ProveraStanja".equals(poruka.getStringProperty("tip")) && poruka.getIntProperty("prodavnica")==Main.getId()){
            poruka = consumer.receive();
        } 
        if(poruka.getBooleanProperty("stanje")) System.out.println("Proizvod je otpakovan");
        else System.out.println("Proizvod je zapakovan");
        //javljanje menadzeru da promeni tabelu proizvodi
    }
    
    public static boolean ImaNaStanju(EntityManager em, String nzv, int kol) //proverava da li ima trazena kolicina proizvoda na stanju
    {
        Query query = em.createQuery("SELECT l FROM NaLageru l WHERE l.prodavnice.idPrd = '" + Main.getId() + "' AND l.proizvodi.naziv = '" + nzv + "' AND l.kolicina > '" + kol + "'");
        return !query.getResultList().isEmpty();
    }
    
    public static boolean ProveraRezervacije(EntityManager em, int id) //provera da li rezervacija postoji/je validna
    {
        Rezervacije r = em.find(Rezervacije.class, id);
        if(r != null && r.getProdavnica().getIdPrd() == Main.getId())
        {
            long pre = r.getVreme().getTime();
            long sad = new java.util.Date().getTime();
            long proslo = sad - pre;
            if(proslo > 48*60*60*1000) 
            {
                Main.BrisanjeRezervacije(em, r.getIdRez());
                return false;
            }
            else return true;               
        }   
        else return false;
    }
    
    public static void Rezervisanje(EntityManager em, int p, int id, int kol) throws JMSException //rezervisanje
    {
        Destination destination = topic;
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        Message poruka = context.createMessage();
        poruka.setStringProperty("tip", "NapraviRezervaciju");
        poruka.setIntProperty("proizvod", id);
        poruka.setIntProperty("prodavnica", p);
        poruka.setIntProperty("korisnik", p);
        poruka.setIntProperty("kolicina", kol);
        producer.send(destination, poruka);
        System.out.println("Menadzeru je poslata rezervacija");
        JMSConsumer consumer = context.createConsumer(destination);
        poruka = consumer.receive();
        while(!"Rezervisanje".equals(poruka.getStringProperty("tip")) && poruka.getIntProperty("prodavnica")==Main.getId())
        {
            poruka = consumer.receive();
        } 
        System.out.println("Broj rezervacije:" + poruka.getIntProperty("id"));    
        //reci menadzeru da rezervise i treba proveriti da li se kreira novi korisnik
    }
    
    public static void BrisanjeRezervacije(EntityManager em, int id)
    {
        Rezervacije r = em.find(Rezervacije.class, id);
        em.getTransaction().begin();
        em.remove(r);
        em.getTransaction().commit();
        System.out.println("Izbrisana rezervacija ID:" + id);
    }
    
    public static void DodajKorisnika(EntityManager em, String ime, String prez, String tel)
    {
        em.getTransaction().begin();
        Query query = em.createNativeQuery("INSERT INTO Korisnik (Ime, Prezime, Telefon) VALUES ('" + ime + "', '" + prez + "', '" + tel + "')");
        query.executeUpdate(); 
        em.getTransaction().commit(); 
        System.out.println("Novi korisnik: " + ime + " " + prez + "-" + tel);
    }
    
    public static void Kupovina(EntityManager em, String nzv, int kol, int p) //kupovina artikla, smanjenje kolicine preostalog ?ukidanje rezervacije
    {
        Query query = em.createQuery("SELECT p FROM Proizvodi p WHERE p.naziv = :nzv");
        query.setParameter("nzv", nzv);
        float cena = ((Proizvodi) query.getSingleResult()).getCena();
        int i = ((Proizvodi) query.getSingleResult()).getIdPrz();
        Main.incTrenutnoZaradjeno(cena);
        Main.incTrenutnoProdato();
        em.getTransaction().begin();
        NaLageru nl = em.find(NaLageru.class, new NaLageruPK(p,i));
        nl.setKolicina(nl.getKolicina()-kol);
        em.getTransaction().commit();
        System.out.println("Kupili ste " + kol + " " + nzv);
    }
    
    public static List<Prodavnice> ProveriDrugeProdavnice(EntityManager em, String nzv, int kol) //provera dostupnosti narudzbine u drugim prodavnicama
    {
        Query query = em.createQuery("SELECT p FROM Prodavnice p, NaLageru l WHERE l.prodavnice.idPrd = p.idPrd AND l.proizvodi.naziv = '" + nzv + "' AND l.kolicina > '" + kol + "'");
        List<Prodavnice> lista = query.getResultList();
        lista.forEach((p) -> {
            System.out.println(p.getNaziv());
        });
        return lista;
    }
}
