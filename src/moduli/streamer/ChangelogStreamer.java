/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli.streamer;

import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import moduli.Streamer;
import moduli.StreamerListener;

/**
 *
 * @author simone
 */
public class ChangelogStreamer extends Streamer{
    
    private StreamerListener gui;

    public ChangelogStreamer(StreamerListener gui) {
        this.gui = gui;
    }
    
    

    @Override
    public void getData() {
         //La nostra sorgente Feed
        URL url;
        XmlReader reader = null;
        ArrayList<ChangelogStreamer.Change> list = null;
         
        try {
            url = new URL("http://minslayer.playat.ch/category/changelog/feed/");
            //Istanziamo uno stream reader dall'url della nostra sorgente feed
            reader = new XmlReader(url);
            //Diamo in pasto il nostro reader al parser RSS
            SyndFeedInput in = new SyndFeedInput();
            SyndFeed feed = in.build(reader);
            //System.out.println("Titolo Feed: "+ feed.getAuthor());
            list = new ArrayList<>();
            //System.out.println(feed.getEntries().size());
            for (Iterator<SyndEntry> i = feed.getEntries().iterator(); i.hasNext();) {
                //Iteriamo tutte le voci presenti nel nostro feed e ne stampiano le proprietà fondmentali
                SyndEntry entry = i.next();
                ChangelogStreamer.Change p = new ChangelogStreamer.Change(entry.getTitle(), entry.getLink(), entry.getPublishedDate(), ((SyndContentImpl)(entry.getContents().get(0))).getValue());
//                System.out.println("titolo:" + entry.getTitle());
//                System.out.println("link:" + entry.getLink());
//                System.out.println("descrizione:" + entry.getDescription().getValue()+"n");
                list.add(p);
            }
         } catch (IOException ex) {
            Logger.getLogger(NewsStreamer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsStreamer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FeedException ex) {
            Logger.getLogger(NewsStreamer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Chiudiamo lo stream precedentemente aperto.
            if (reader != null) try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(NewsStreamer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        gui.refreshData(list);
        
    }
    
    public class Change{
        
        private String title;
        private String link;
        private Date date;
        private String content;

        private Change(String title, String link, Date publishedDate, String value) {
            this.title = title;
            this.link = link;
            this.date = publishedDate;
            if(value.length()>1000) value = value.substring(0, 1000);
            this.content = value;
        }

        public String getTitle() {
            return title;
        }

        public String getLink() {
            return link;
        }

        public Date getDate() {
            return date;
        }

        public String getContent() {
            return content;
        }

    }
    
}
