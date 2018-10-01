/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menadzerapp;

import entiteti.*;
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
    
    private static int prodavnicaid;
    private static int id;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Main.id = id;
    }

    public static int getProdavnicaid() {
        return prodavnicaid;
    }

    public static void setProdavnicaid(int prodavnicaid) {
        Main.prodavnicaid = prodavnicaid;
    }
    
    public static void main(String[] args) throws JMSException {
        Main.setId(Integer.parseInt(args[0]));
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MenadzerAppPU");
        EntityManager em = emf.createEntityManager();
        Main.setProdavnicaid(em.find(Menadzeri.class, Main.getId()).getIdPro());
        Destination destination = topic;
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(destination);
        while(true)
        {
            Message poruka = consumer.receive();
            if(poruka.getIntProperty("prodavnica")==Main.getProdavnicaid() || poruka.getIntProperty("prodavnica")==0)
            {
                switch(poruka.getStringProperty("tip"))
                {
                    case "NapraviRezervaciju": Main.NapraviRezervaciju(em, poruka.getIntProperty("proizvod"), Main.getProdavnicaid(), poruka.getIntProperty("korisnik"), poruka.getIntProperty("kolicina")); break;
                    case "ProveriStanje":Main.ProveraStanja(em, Main.getProdavnicaid(), poruka.getIntProperty("proizvod"));break;
                    case "PromenaCene": Main.PromenaCene(em, poruka.getIntProperty("proizvod"), poruka.getFloatProperty("cena")); break;
                    case "DodajNaLager": Main.DodajNaLager(em, Main.getProdavnicaid(), poruka.getIntProperty("proizvod"), poruka.getIntProperty("kolicina"));break;
                }
            }
        }
    }
    
    public static void NapraviRezervaciju(EntityManager em, int p, int s, int k,  int kol) throws JMSException //javljeno mu je koju rezervaciju da napravi
    {//treba dodati tip u message
        em.getTransaction().begin();
        Query query = em.createNativeQuery("INSERT INTO Rezervacije (Proizvod, Prodavnica, Korisnik, Kolicina) VALUES ('" + p + "', '" + s + "', '" + k + "', '" + kol + "')");
        query.executeUpdate();
        em.getTransaction().commit();
        Destination destination = topic;
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        Message poruka = context.createMessage();
        poruka.setStringProperty("tip", "Rezervisanje");
        poruka.setIntProperty("prodavnica", Main.getProdavnicaid());
        int broj = (int) em.createQuery("SELECT MAX(r.idRez) FROM Rezervacije r").getSingleResult();
        poruka.setIntProperty("id", broj);
        producer.send(destination, poruka);
        System.out.println("Napravljena je rezervacija:" + broj);
        //mora return id
    }
    
    public static void ProveraStanja(EntityManager em, int p, int id) throws JMSException //proverava dal je proizvod po id-u zapakovan/otpakovan
    {
        //true = zapakovano; false = otpakovano
        //70%zapakovano
        int i = (int) (Math.random()*10);
        System.out.println(i);
        Destination destination = topic;
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        Message poruka = context.createMessage();
        poruka.setStringProperty("tip", "ProveraStanja");
        poruka.setIntProperty("prodavnica", Main.getProdavnicaid());
        if(i>6)
        {
            System.out.println("Otpakovano");
            poruka.setBooleanProperty("stanje", true);
        }
        else
        {
            System.out.println("Zapakovano");
            poruka.setBooleanProperty("stanje", false);
        }
        producer.send(destination, poruka);
    }
    
    public static void PromenaCene(EntityManager em, int id, float cena) //iz fabrike mu je javljeno da je cena promenjena
    {
        em.getTransaction().begin();
        Query query = em.createQuery("UPDATE Proizvodi p SET p.cena = '" + cena + "' WHERE p.idPrz = '" + id + "'");
        query.executeUpdate();
        em.getTransaction().commit();
        System.out.println("Cena je promenjena");
    }
    
    public static void DodajNaLager(EntityManager em, int p, int id, int kol) //iz fabrike mu je javljeno da je koliko kog proizvoda da doda na lager
    {
        //da li treba govoriti menadzeru u koju prodavnicu da doda ako ima 1 menadzer za svaku prodavnicu?
        Query query = em.createQuery("SELECT l FROM NaLageru l WHERE l.prodavnice.idPrd = " + p + " AND l.proizvodi.idPrz = " + id);//provera dal artikal vec postoji
        if(query.getResultList().isEmpty())
        {
            em.getTransaction().begin();
            em.createNativeQuery("INSERT INTO Na_Lageru (Prodavnica, Proizvod, Kolicina) VALUES ('" + p + "', '" + id + "', '" + kol + "')").executeUpdate();
            em.getTransaction().commit();
        }
        else
        {
            em.getTransaction().begin();
            NaLageru n = (NaLageru) query.getSingleResult();//get former kol
            em.createQuery("UPDATE NaLageru l SET l.kolicina = " + (n.getKolicina()+kol) + " WHERE l.prodavnice.idPrd = " + p + " AND  l.proizvodi.idPrz = " + id).executeUpdate();
            em.getTransaction().commit();
        }
        System.out.println("Dodato na lager");
    }
    
}
