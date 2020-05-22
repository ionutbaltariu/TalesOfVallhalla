package PaooGame.Graphics;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/*! \class public class AudioLoader
    \brief Clasa ce contine o metoda statica pentru incarcarea unui sunet din memorie. ( de tip .wav )
 */
public class AudioLoader {
    /*! \fn  public static AudioInputStream loadAudio(String path)
        \brief Incarca un stream audio intr-un obiect AudioInputStream si returneaza o referinta catre acesta.

        \param path Calea relativa pentru localizarea sunetului.
     */
    public static AudioInputStream LoadAudio(String path)
    {
        /// Avand in vedere exista situatii in care fisierul sursa sa nu poate fi accesat
        /// metoda read() arunca o exceptie ce trebuie tratata
        try
        {
            /// Clasa AudioSystem contine o serie de metode statice pentru "sampled-audio system resources".
            /// https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/AudioSystem.html
            /// Metoda getAudioInputStream() are ca argument un InputStream construit avand ca referinta
            /// directorul res, director declarat ca director de resurse in care se gasesc resursele
            /// proiectului sub forma de fisiere sursa.
            return AudioSystem.getAudioInputStream(AudioLoader.class.getResource(path));
        }
        catch (UnsupportedAudioFileException | IOException e)
        {
            /// Afiseaza informatiile necesare depanarii.
            e.printStackTrace();
        }
        return null;
    }
}
