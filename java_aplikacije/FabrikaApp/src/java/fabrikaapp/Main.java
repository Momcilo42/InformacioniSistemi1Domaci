/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrikaapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
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
    
    public static void main(String[] args) throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("FabrikaAppPU");
        EntityManager em = emf.createEntityManager();
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        while(true)
        {
            reader.readLine();
            System.out.println("Izaberite funkciju:\n"
                    + "1)Napravi\n"
                    + "2)Promena cene\n"
                    + "3)Izlaz");
            int izbor = Integer.parseInt(reader.readLine());
            switch(izbor)
            {
                case 1:
                {
                    System.out.println("Za koju prodavnicu pravite?");
                    int prod = Integer.parseInt(reader.readLine());
                    System.out.println("Koji proizvod pravite?");
                    int id = Integer.parseInt(reader.readLine());
                    System.out.println("Koliko proizvoda pravite?");
                    int kol = Integer.parseInt(reader.readLine());
                    Main.Napravi(em, prod, id, kol);
                }break;
                case 2:
                {
                    System.out.println("Za koji proizvod menjate cenu?");
                    int id = Integer.parseInt(reader.readLine());
                    System.out.println("Koja je nova cena?");
                    float br = Float.parseFloat(reader.readLine());
                    Main.PromenaCene(em, id, br);
                }break;
                case 3:System.exit(0);break;
            }
        }
        //Main.PosaljiProdavnici(em, 1, 11, 11);
    }
    
    public static void Napravi(EntityManager em, int prod, int id, int kol) //pravi zadati predmet
    {
        float vreme = (float) em.createQuery("SELECT a.vreme FROM Artikal a WHERE a.idAr = '" + id + "'").getSingleResult();
        System.out.println("Potrebno vreme za izradu: " + vreme + "s");
        try {
            Thread.sleep((long) vreme*1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Salje se prodavnici " + prod);
        Main.PosaljiProdavnici(em, prod, id, kol);
    }
    
    public static void PosaljiProdavnici(EntityManager em, int prod, int id, int kol) 
    {
        try {
            Destination destination = topic;
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            Message poruka = context.createMessage();
            poruka.setStringProperty("tip", "DodajNaLager");
            poruka.setIntProperty("proizvod", id);
            poruka.setIntProperty("prodavnica", prod);
            poruka.setIntProperty("kolicina", kol);
            producer.send(destination, poruka);
            System.out.println("Menadzer je obavesten o prispeloj posiljci");
            } //javljanje menadzeru da promeni tabelu proizvodi
        catch (JMSException ex) {
            Logger.getLogger("greska").log(Level.SEVERE, null, ex);
        }
    }
    
    public static void PromenaCene(EntityManager em, int id, float cena) 
    {
        try 
        { 
            em.getTransaction().begin();
            Query query = em.createQuery("UPDATE Artikal a SET a.cena = '" + cena + "' WHERE a.idAr = '" + id + "'");
            query.executeUpdate(); 
            em.getTransaction().commit(); 
        } //promena cene u tabeli artikli
        finally 
        { 
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } 
        try {
            Destination destination = topic;
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            Message poruka = context.createMessage();
            poruka.setStringProperty("tip", "PromenaCene");
            poruka.setIntProperty("proizvod", id);
            poruka.setIntProperty("prodavnica", 0);//vrednost 0 znaci da primaju svi menadzeri
            poruka.setFloatProperty("cena", cena);
            producer.send(destination, poruka);
            System.out.println("Menadzer je obavesten o promeni cene");
        } //javljanje menadzeru da promeni tabelu proizvodi
        catch (JMSException ex) {
            Logger.getLogger("greska").log(Level.SEVERE, null, ex);
        }
    }
    
}
